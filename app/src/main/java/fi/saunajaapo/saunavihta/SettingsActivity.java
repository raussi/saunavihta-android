package fi.saunajaapo.saunavihta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

public class SettingsActivity extends FragmentActivity {

    public static final String PREFS_NAME = "Asetukset";

    private Button mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch sw = (Switch) findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(taustaAanetSwitch);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean ambient = settings.getBoolean("taustaAanet", false);
        if(ambient) {
            sw.setChecked(true);
        } else {
            sw.setChecked(false);
        }

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(heiluttamisenHerkkyysSeekBar);

        int sensitive = settings.getInt("heiluttamisenHerkkyys", 5);
        seekBar.setProgress(sensitive);

        mBackButton = (Button) findViewById(R.id.button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private Switch.OnCheckedChangeListener taustaAanetSwitch =
            new Switch.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    Switch sw = (Switch) findViewById(R.id.switch1);
                    if(isChecked) {
                        sw.setChecked(true);
                    } else {
                        sw.setChecked(false);
                    }

                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean("taustaAanet", isChecked);
                    editor.commit();
                }

            };

    private SeekBar.OnSeekBarChangeListener heiluttamisenHerkkyysSeekBar =
            new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("heiluttamisenHerkkyys", progress);
                    editor.commit();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            };

}
