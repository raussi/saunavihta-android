package fi.saunajaapo.saunavihta;

import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    public static final String PREFS_NAME = "Asetukset";

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    public int mTab;

    public MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean ambient = settings.getBoolean("taustaAanet", false);
        if(ambient) {
            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.saunaambient);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean ambient = settings.getBoolean("taustaAanet", false);
        if(ambient) {
            if(!mediaPlayer.isPlaying()) {
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.saunaambient);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        int id = item.getItemId();

        switch (id) {
            case R.id.action_info:
                intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());

        switch (tab.getPosition()) {
            case 0:
                mTab = 0;
                break;
            case 1:
                mTab = 1;
                break;
            case 2:
                mTab = 2;
                break;
            default:
                mTab = 0;
                break;
        }

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public void onSensorChanged(SensorEvent event, Context context) {
        // In this example, alpha is calculated as t / (t + dT),
        // where t is the low-pass filter's time-constant and
        // dT is the event delivery rate.

        final double alpha;
        alpha = 0.8;

        // Isolate the force of gravity with the low-pass filter.
        double[] gravity = new double[3];
        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        // Remove the gravity contribution with the high-pass filter.
        double[] linear_acceleration = new double[3];
        linear_acceleration[0] = event.values[0] - gravity[0];
        linear_acceleration[1] = event.values[1] - gravity[1];
        linear_acceleration[2] = event.values[2] - gravity[2];


        if((linear_acceleration[0] > 9) || (linear_acceleration[1] > 9) || (linear_acceleration[2] > 9)) {
            switch (mTab) {
                case 0:
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.saunavihta);
                    mediaPlayer.start();
                /*
                if(linear_acceleration[2] > 2) {
                    MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.saunavihta);
                    mediaPlayer.start();
                }
                */
                    break;
                case 1:

                /*
                if(linear_acceleration[2] > 2) {

                }
                */
                    break;
                case 2:

                /*
                if(linear_acceleration[2] > 2) {

                }
                */
                    break;
            }
        }
    }

    // class SectionsPagerAdapter

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }
}
