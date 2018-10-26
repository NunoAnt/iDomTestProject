package ipca.edjd.idomtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TemperatureActivity extends AppCompatActivity {

    EnergyReceiver energyReceiver;

    private class EnergyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra(DomConnector.DOM_CONNECTOR_DATA);
            textViewTemp.setText(data);
        }
    }

    TextView textViewTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
        textViewTemp = findViewById(R.id.textViewTemp);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (energyReceiver==null){
            energyReceiver = new EnergyReceiver();
            this.registerReceiver(energyReceiver,
                    new IntentFilter(DomConnector.BROADCAST_DOMCONNECTOR_ENERGY));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (energyReceiver!=null){
         this.unregisterReceiver(energyReceiver);
         energyReceiver = null;
        }
    }
}
