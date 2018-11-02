package ipca.edjd.idomtest.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesHelper {

    private static final String PREF_IP = "pref_ip" ;
    private static final String PREF_PORT = "pref_port" ;
    String ip;
    int port;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    SharedPreferences sharedPreferences;

    public PreferencesHelper(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.ip = sharedPreferences.getString(PREF_IP,"192.168.1.10");
        this.port = sharedPreferences.getInt(PREF_PORT,3002);
    }

    public void savePreferences(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_IP,ip);
        editor.putInt(PREF_PORT,port);
        editor.apply();
    }

}
