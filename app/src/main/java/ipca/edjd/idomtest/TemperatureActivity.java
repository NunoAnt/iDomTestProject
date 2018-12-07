package ipca.edjd.idomtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import ipca.edjd.idomtest.models.Device;
import ipca.edjd.idomtest.models.DeviceRepository;

public class TemperatureActivity extends AppCompatActivity {


    TextView textViewTemp;
    SeekBar seekBar;

    TemperatureView temperatureView;

    String idname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
        textViewTemp = findViewById(R.id.textViewTemp);
        temperatureView = findViewById(R.id.tempertureView);
        idname = getIntent().getStringExtra("device_idname");
        new DeviceRepository(this).getByIdNameLive(idname).observe(this,
                new android.arch.lifecycle.Observer<Device>() {
                    @Override
                    public void onChanged(@Nullable Device device) {

                        if (device!=null) {

                            float tempCelcius = (device.data / 100.f - 32) * (5.f / 9.f);
                            textViewTemp.setText("" + tempCelcius);
                            temperatureView.setValue(tempCelcius / 50.f);
                        }
                    }
                });



        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("iDomTest", ""+progress);
                temperatureView.setValue(progress/100.f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
