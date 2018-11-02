package ipca.edjd.idomtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ipca.edjd.idomtest.Utils.PreferencesHelper;

public class SettingsActivity extends AppCompatActivity {

    EditText editTextIp;
    EditText editTextPort;
    Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        editTextIp =  findViewById(R.id.editTextIp);
        editTextPort =  findViewById(R.id.editTextPort);
        buttonSave =  findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesHelper preferencesHelper=new PreferencesHelper(SettingsActivity.this);
                preferencesHelper.setIp(editTextIp.getText().toString());
                preferencesHelper.setPort(Integer.parseInt(editTextPort.getText().toString()));
                preferencesHelper.savePreferences();
            }
        });

        PreferencesHelper preferencesHelper=new PreferencesHelper(SettingsActivity.this);
        editTextIp.setText(preferencesHelper.getIp());
        editTextPort.setText(""+preferencesHelper.getPort());

    }
}
