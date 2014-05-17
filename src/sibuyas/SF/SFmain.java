package sibuyas.SF;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.*;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Collections;


public class SFmain extends ActionBarActivity {
    final String TAG = "sfmain";
    boolean debuglog = false;
    DisplayMetrics metrics;
    Configuration config;

    private InterstitialAd interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set defaults
        PreferenceManager.setDefaultValues(this, R.xml.preference, false);
        // Create the interstitial.
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(getString(R.string.MY_AD_UNIT_ID_INTERSTITAL));
        // Create ad request.
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
                .addTestDevice("E204AA8798DCD03CAF2D96BEAEFB39B3")  // My Galaxy Nexus test phone
                .build();
        // Begin loading your interstitial.
        interstitial.loadAd(adRequest);

        // setup action bar for tabs
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);

        ActionBar.Tab tab = actionBar.newTab()
                //.setIcon(R.drawable.geometry_input_image)
                .setText("DESIGN INPUT")
                .setTabListener(new TabListener<DesignInputFragment>(
                        this, "Inputtab", DesignInputFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                //.setIcon(R.drawable.result_image)
                .setText("DESIGN RESULT")
                .setTabListener(new TabListener<DesignOut>(
                        this, "Resulttab", DesignOut.class));
        actionBar.addTab(tab);


        //get display metrics and save
        //save data in sharedpref
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor ed = sp.edit();

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        log(String.valueOf(metrics.widthPixels));

        config = getResources().getConfiguration();
        log(String.valueOf(config.orientation));

        if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
            ed.putString(getString(R.string.WIDTHPIXEL_PREF), String.valueOf(metrics.widthPixels));
        } else {
            ed.putString(getString(R.string.WIDTHPIXEL_PREF), String.valueOf(metrics.heightPixels));
        }

        ed.apply();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    } // end of oncreate options menu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.preferences_id:
                // Display the fragment as the menu_main content.

                Intent intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent, 0);
                return true;

            case R.id.About:

                String msg = "This app will determine the bearing pressure under a rectangular" +
                        "combined pad or strip footing, including shear and moment diagram" + "\r\n\r\n" +
                        "For sanity check only";
                alert(msg);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
        //savedesignParam();
        // mChecker.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // getsaveddesignParam();
        // initializeinput();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();


        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        //Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }

    public void log(String debugstring) {
        if (debuglog) {
            Log.d(TAG, debugstring);
        }
    }

}
