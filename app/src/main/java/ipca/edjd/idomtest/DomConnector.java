package ipca.edjd.idomtest;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import domatica.idom.iDom3SDK;

public class DomConnector {

    private static final String DOM_CONNECTOR = "idomtest.domconnector";

    public static final String BROADCAST_DOMCONNECTOR_ENERGY = "DomConnector.Energy";
    public static final String DOM_CONNECTOR_DATA = "domconnector.data" ;

    String ip;
    int port;

    static Context ctx;


    private Handler updateHandler = new Handler();

    private static DomConnector instance = null;
    protected DomConnector(String ip, int port) {
        // Exists only to defeat instantiation.
        this.ip=ip;
        this.port = port;

    }
    public static DomConnector getInstance(String ip, int port, Context context) {
        if(instance == null) {
            instance = new DomConnector(ip,port);
            ctx=context;
        }
        return instance;
    }

    public void connect (){
        String classPath = "ipca/edjd/idomtest/DomConnector";
        iDom3SDK.Init(0);
        //iDom3SDK.RegObjectValueCB(classPath, "iDomObjectValueEvent");
        //iDom3SDK.RegObjectDataCB(classPath, "iDomObjectDataEvent");



        iDom3SDK.RegConnectionStateCB(classPath, "iDomConnectionStateEvent");
        ////iDom3SDK.RegConnectionStateExCB(classPath, "iDomConnectionStateExEvent");
        iDom3SDK.RegEmittedStringCB(classPath, "iDomEmittedStringEvent");
        iDom3SDK.RegFileEventCB(classPath, "iDomFileEvent");
        ////iDom3SDK.RegHWObjectValueCB(classPath, "iDomHWObjectValueEvent");
        iDom3SDK.RegInfoEventCB(classPath, "iDomInfoEvent");
        iDom3SDK.RegLogFileCB(classPath, "iDomLogFileEvent");
        iDom3SDK.RegObjectDataCB(classPath, "iDomObjectDataEvent");
        iDom3SDK.RegObjectValueCB(classPath, "iDomObjectValueEvent");
        //iDom3SDK.RegSystemFileCB(classPath, "iDomSystemFileEvent");
        //iDom3SDK.RegUserFileCB(classPath, "iDomUserFileEvent");
        //iDom3SDK.RegUserFileInfoCB(classPath, "idomUserFileInfoEvent");
        //iDom3SDK.RegUserProgramFileCB(classPath, "iDomUserProgramFileEvent");



        if (!iDom3SDK.Connect(ip, port))
        {
        }

        threadUISync();
        updateHandler.removeCallbacks(threadUISyncTask);
        updateHandler.postDelayed(threadUISyncTask, 500);
    }

    public void disconnect(){
        iDom3SDK.Disconnect();
        if ( updateHandler != null )
        {
            updateHandler.removeCallbacks(threadUISyncTask);
            updateHandler = null;
        }
    }


    private void threadUISync()
    {
        try
        {
            iDom3SDK.PumpEvents();
        }
        catch(Exception e)
        {
            Log.e("idomtest", e.toString());
        }
    }

    private Runnable threadUISyncTask = new Runnable()
    {
        public void run()
        {
            threadUISync();
            updateHandler.postDelayed(this, 50);
        }
    };

    static public void iDomConnectionStateEvent(int conid, int conEventType)
    {
        if (conEventType == 2)
        {
            Log.d(DOM_CONNECTOR, "Connected " + Integer.toHexString(conid));
            return;
        }

        if (conEventType == 3)
        {
            Log.d(DOM_CONNECTOR, "Disconnected " + Integer.toHexString(conid));
            return;
        }
    }

    static public void iDomEmittedStringEvent(int conid, String str)
    {
        Log.d(DOM_CONNECTOR, "iDomEmittedStringEvent " + Integer.toHexString(conid) + " " + str );
    }


    static public void iDomFileEvent(int conid, int fileType, int fileID, int eventcode, int cursor, int size)
    {
        Log.d(DOM_CONNECTOR, "iDomFileEvent " + Integer.toHexString(conid)
                + " fileType " + fileType
                + " fileID " + fileID
                + " eventcode " + eventcode
                + " cursor " + cursor
                + " size " + size );
    }

    public static void iDomInfoEvent(int conid, int val, String param1, String param2, String param3)
    {
        Log.d(DOM_CONNECTOR, "iDomInfoEvent " + Integer.toHexString(conid)
                + " val " + val
                + " param1 " + param1
                + " param2 " + param2
                + " param3 " + param3
                );
    }

    static public void iDomLogFileEvent(int conid, int firstTimestamp, int lastTimestamp, int nItems, String filedata, int filesize)
    {
        Log.d(DOM_CONNECTOR, "iDomInfoEvent " + Integer.toHexString(conid)
                + " firstTimestamp " + firstTimestamp
                + " lastTimestamp " + lastTimestamp
                + " nItems " + nItems
                + " filedata " + filedata
                + " filesize " + filesize
        );
    }

    static public void iDomObjectDataEvent(int conid, String idname, String data, boolean is_event, String datatype, String objecttype)
    {
        //if (!is_event) return;
        if (idname.matches("SystemClock")) return;

        Log.d(DOM_CONNECTOR, "iDomObjectDataEvent " + Integer.toHexString(conid)
                + " idname " + idname
                + " data " + data
                + " is_event " + is_event
                + " datatype " + datatype
                + " objecttype " + objecttype
        );

        if (idname.contains("ProjectoA_A_Sensore_Temperatura")){
            sendBroadcasrDataEventEnergy(ctx, data);
        }
    }

    static void sendBroadcasrDataEventEnergy (Context context, String data){
        Intent intent = new Intent(BROADCAST_DOMCONNECTOR_ENERGY);
        intent.putExtra(DOM_CONNECTOR_DATA, data);
        context.sendBroadcast(intent);
    }


    static public void iDomObjectValueEvent(int conid, String idname, int value, boolean is_event)
    {
        //if (!is_event) return;
        if (idname.matches("SystemClock")) return;

        Log.d(DOM_CONNECTOR, "iDomObjectValueEvent " + Integer.toHexString(conid)
                + " idname " + idname
                + " value " + value
                + " is_event " + is_event
        );
    }
}
