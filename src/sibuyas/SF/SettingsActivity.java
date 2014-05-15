package sibuyas.SF;


import android.app.Activity;
import android.os.Bundle;

/**
 * Created by josua.arnigo on 8/07/13.
 */
public class SettingsActivity extends Activity {

    PrefFragment prefFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingsactlayout);


        prefFragment = new PrefFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer_settings,
                prefFragment).commit();

    }


}
