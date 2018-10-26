package ipca.edjd.idomtest;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import domatica.idom.iDom3SDK;

public class MainActivity extends AppCompatActivity {

    Button bt1;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helloidom);

        DomConnector.getInstance("192.168.0.10",3002,this).connect();

        bt1 = (Button) findViewById(R.id.bt1);
        bt1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                iDom3SDK.RequestLogRecords(0, 1000);

        		/*
        		//MyHelloiDom.tv_xml.setText(iDom3SDK.GatewaySerial());
        		String device = "root_light1.control";
        		String str = iDom3SDK.GetData(0, device);
        		if (str.matches("0"))
        			iDom3SDK.SetData(0, device, "1");
        		else
        			iDom3SDK.SetData(0, device, "0");
        		MyHelloiDom.tv_xml.setText("Toggled device: " + device);
        		//*/

                Intent intent = new Intent(MainActivity.this, TemperatureActivity.class);
                startActivity(intent);
            }
        });

    }


}
