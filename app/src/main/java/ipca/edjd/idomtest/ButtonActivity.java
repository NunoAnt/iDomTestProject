package ipca.edjd.idomtest;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Observer;

import ipca.edjd.idomtest.models.Device;
import ipca.edjd.idomtest.models.DeviceRepository;

public class ButtonActivity extends AppCompatActivity {

    String idname = "";
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);
        idname = getIntent().getStringExtra("device_idname");

        view = findViewById(R.id.view);

        new DeviceRepository(this).getByIdNameLive(idname).observe(this,
                new android.arch.lifecycle.Observer<Device>() {
            @Override
            public void onChanged(@Nullable Device device) {

                view.setBackgroundResource((device.data>0)?R.drawable.round_red:R.drawable.round_grey);
            }
        });


    }
}
