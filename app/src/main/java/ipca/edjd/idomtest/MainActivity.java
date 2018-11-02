package ipca.edjd.idomtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import ipca.edjd.idomtest.Utils.PreferencesHelper;
import ipca.edjd.idomtest.models.Device;
import ipca.edjd.idomtest.models.DeviceRepository;
import ipca.edjd.idomtest.models.Zone;
import ipca.edjd.idomtest.models.ZoneRepository;

public class MainActivity extends AppCompatActivity {

    SystemFileEventReceiver systemFileEventReceiver;

    List<Device> deviceList =  new ArrayList<>();

    private class SystemFileEventReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra(DomConnector.DOM_CONNECTOR_DATA);



            try {
                InputStream targetStream = new ByteArrayInputStream(data.getBytes());
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = null;
                dBuilder = dbFactory.newDocumentBuilder();
                final Document doc = dBuilder.parse(targetStream);

                Element element=doc.getDocumentElement();
                element.normalize();



                new AsyncTask<Void, Void, List<Device>>() {
                    @Override
                    protected List<Device> doInBackground(Void... voids) {

                        NodeList nZoneList = doc.getElementsByTagName("zone");

                        for (int i=0; i<nZoneList.getLength(); i++) {

                            Node node = nZoneList.item(i);
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                Element element2 = (Element) node;

                                Zone zone =  new Zone();
                                zone.id  = element2.getAttribute(Zone.ZONE_ID);
                                zone.name  = element2.getAttribute(Zone.ZONE_NAME);
                                zone.path  = element2.getAttribute(Zone.ZONE_PATH);
                                zone.parent  = element2.getAttribute(Zone.ZONE_PARENT);

                                //Zone.add(zone,realm)

                                Zone z = new ZoneRepository(getApplication()).get(zone.id);
                                if (z==null)
                                    new ZoneRepository(getApplication()).insert(zone);
                            }
                        }

                        final NodeList nList = doc.getElementsByTagName("device");


                        for (int i=0; i<nList.getLength(); i++) {

                            Node node = nList.item(i);
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                Element element2 = (Element) node;

                                Device device = new Device();
                                device.id = element2.getAttribute(Device.DEVICE_ID);
                                device.idName = element2.getAttribute(Device.DEVICE_IDNAME);
                                device.name = element2.getAttribute(Device.DEVICE_NAME);
                                device.idZone = element2.getAttribute(Device.DEVICE_ZONE);

                                Device d = new DeviceRepository(getApplication()).get(device.id);
                                if (d == null)
                                    new DeviceRepository(getApplication()).insert(device);
                            }
                        }
                        List<Device> devices =  new DeviceRepository(getApplication()).getAll();

                        return devices;
                    }

                    @Override
                    protected void onPostExecute(List<Device> devices) {
                        super.onPostExecute(devices);



                        String listdev = "";

                        for (Device d : devices){
                            listdev += d.name + "\n";
                        }

                        textViewMain.setText(listdev);
                    }
                }.execute(null,null,null);





/*
                RealmResults<Device> devices = realm.
                        where(Device.class).
                        findAll();

*/


            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }


    TextView textViewMain;

    @Override
    protected void onResume() {
        super.onResume();
        if (systemFileEventReceiver==null){
            systemFileEventReceiver = new SystemFileEventReceiver();
            this.registerReceiver(systemFileEventReceiver,
                    new IntentFilter(DomConnector.BROADCAST_DOMCONNECTOR_SYSTEM_FILE_EVENT));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (systemFileEventReceiver!=null){
            this.unregisterReceiver(systemFileEventReceiver);
            systemFileEventReceiver = null;
        }
    }

    //Realm realm;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        textViewMain = findViewById(R.id.textViewMain);
        PreferencesHelper preferencesHelper= new PreferencesHelper(MainActivity.this);

        String ip = preferencesHelper.getIp();
        int port = preferencesHelper.getPort();


        DomConnector.getInstance(ip,port,this).connect();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_temp:
                {
                //iDom3SDK.RequestLogRecords(0, 1000);
                Intent intent = new Intent(MainActivity.this, TemperatureActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.menu_settings:
                {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
