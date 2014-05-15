package sibuyas.SF;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.*;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Collections;


public class SFmain extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set defaults
        PreferenceManager.setDefaultValues(this, R.xml.preference, false);

        // setup action bar for tabs
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);

        ActionBar.Tab tab = actionBar.newTab()
                .setIcon(R.drawable.geometry_input_image)
                        // .setText(R.string.artist)
                .setTabListener(new TabListener<DesignInputFragment>(
                        this, "Inputtab", DesignInputFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setIcon(R.drawable.result_image)
                .setTabListener(new TabListener<DesignOut>(
                        this, "Resulttab", DesignOut.class));
        actionBar.addTab(tab);
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
        //savedesignParam();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // getsaveddesignParam();
        // initializeinput();

    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        //Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }

}
