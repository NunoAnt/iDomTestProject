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
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
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

    ListView listViewDev;
    AdapterDevices  adapter;

    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }



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
        PreferencesHelper preferencesHelper= new PreferencesHelper(MainActivity.this);

        String ip = preferencesHelper.getIp();
        int port = preferencesHelper.getPort();


        DomConnector.getInstance(ip,port,this).connect();


        new DeviceRepository(getApplication()).getAll()
                .observe(MainActivity.this, new Observer<List<Device>>() {
            @Override
            public void onChanged(@Nullable List<Device> devices) {
                MainActivity.this.deviceList = devices;
                adapter.notifyDataSetChanged();
            }
        });

        listViewDev = findViewById(R.id.listView);
        adapter = new AdapterDevices();
        listViewDev.setAdapter(adapter);

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

    class AdapterDevices extends BaseAdapter implements View.OnClickListener {

        @Override
        public int getCount() {
            return deviceList.size();
        }

        @Override
        public Object getItem(int position) {
            return deviceList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null)
                convertView = getLayoutInflater().inflate(R.layout.row_zone,null);

            TextView textViewName = convertView.findViewById(R.id.textViewName);
            TextView textViewDescription = convertView.findViewById(R.id.textViewDescription);

            textViewName.setText(deviceList.get(position).name);
            textViewDescription.setText(""+deviceList.get(position).data);

            convertView.setTag(new Integer(position));
            convertView.setClickable(true);
            convertView.setOnClickListener(this);

            return convertView;
        }

        @Override
        public void onClick(View v) {
            int position = (int)v.getTag();
            if (deviceList.get(position).devType.compareTo("Button")==0){
                Intent intent = new Intent(MainActivity.this, ButtonActivity.class);
                intent.putExtra("device_idname", deviceList.get(position).idname);
                startActivity(intent);
            }
        }
    }
}
