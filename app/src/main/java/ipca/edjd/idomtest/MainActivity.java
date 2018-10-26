package ipca.edjd.idomtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import domatica.idom.iDom3SDK;

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
                Document doc = dBuilder.parse(targetStream);

                Element element=doc.getDocumentElement();
                element.normalize();

                NodeList nList = doc.getElementsByTagName("device");

                for (int i=0; i<nList.getLength(); i++) {

                    Node node = nList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element2 = (Element) node;

                        Device device = new Device();
                        device.id = element2.getAttribute(Device.DEVICE_ID);
                        device.idName = element2.getAttribute(Device.DEVICE_IDNAME);
                        device.name = element2.getAttribute(Device.DEVICE_NAME);
                        device.zone = element2.getAttribute(Device.DEVICE_ZONE);

                        deviceList.add(device);
                    }
                }

                String listdev = "";

                for (Device d : deviceList){
                    listdev += d.name + "\n";
                }

                textViewMain.setText(listdev);

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



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewMain = findViewById(R.id.textViewMain);
        DomConnector.getInstance("192.168.0.10",3002,this).connect();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_temp) {

            iDom3SDK.RequestLogRecords(0, 1000);
            Intent intent = new Intent(MainActivity.this, TemperatureActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
