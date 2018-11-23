package ipca.edjd.idomtest;

import android.arch.lifecycle.Observer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
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


    List<Device> deviceList =  new ArrayList<>();


    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }


    TextView textViewMain;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
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


        new DeviceRepository(getApplication()).getAll().observe(MainActivity.this, new Observer<List<Device>>() {
            @Override
            public void onChanged(@Nullable List<Device> devices) {
                String listdev = "";

                for (Device d : devices){
                    listdev += d.name + "\n";
                }

                textViewMain.setText(listdev);
            }
        });

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
