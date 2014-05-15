package sibuyas.SF;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import static sibuyas.SF.MyDouble.*;

/**
 * Created by j0sua3 on 13/05/2014.
 */
public class DesignInputFragment extends Fragment {
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.geominputlayout, container, false);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        saveDesignInput();


    }

    @Override
    public void onResume() {
        super.onResume();
        updateInputUI();

    }

    private void saveDesignInput() {


        //save data in sharedpref
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor ed = sp.edit();

        TextView v = (TextView) view.findViewById(R.id.b1_input);
        ed.putString(getString(R.string.B1_PREF), v.getText().toString());

        v = (TextView) view.findViewById(R.id.b2_in);
        ed.putString(getString(R.string.B2_PREF), v.getText().toString());

        v = (TextView) view.findViewById(R.id.Hb_input);
        ed.putString(getString(R.string.HB_PREF), v.getText().toString());

        v = (TextView) view.findViewById(R.id.Df_input);
        ed.putString(getString(R.string.DF_PREF), v.getText().toString());

        v = (TextView) view.findViewById(R.id.Hf_input);
        ed.putString(getString(R.string.HF_PREF), v.getText().toString());
        v = (TextView) view.findViewById(R.id.d0_input);
        ed.putString(getString(R.string.D0_PREF), v.getText().toString());
        v = (TextView) view.findViewById(R.id.dp_input);
        ed.putString(getString(R.string.DP_PREF), v.getText().toString());
        v = (TextView) view.findViewById(R.id.P0v_input);
        ed.putString(getString(R.string.P0V_PREF), v.getText().toString());
        v = (TextView) view.findViewById(R.id.P0h_input);
        ed.putString(getString(R.string.P0H_PREF), v.getText().toString());
        v = (TextView) view.findViewById(R.id.P1v_input);
        ed.putString(getString(R.string.P1V_PREF), v.getText().toString());
        v = (TextView) view.findViewById(R.id.P1h_input);
        ed.putString(getString(R.string.P1H_PREF), v.getText().toString());
        v = (TextView) view.findViewById(R.id.wtsoil_input);
        ed.putString(getString(R.string.WSOIL_PREF), v.getText().toString());


        ed.apply();


    }

    private void updateInputUI() {

        //read preferences
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String SIUnit = getString(R.string.SI);
        String unit = pref.getString(getString(R.string.UNIT), SIUnit);

        if (unit.equals(SIUnit)) {
            TextView v = (TextView) view.findViewById(R.id.b1_text);
            v.setText(getString(R.string.b1_txt) + ", " + Unit.m.toString());

            v = (TextView) view.findViewById(R.id.b2_text);
            v.setText(getString(R.string.b2_txt) + ", " + Unit.m.toString());
            v = (TextView) view.findViewById(R.id.Hb_text);
            v.setText(getString(R.string.Hb_txt) + ", " + Unit.m.toString());
            v = (TextView) view.findViewById(R.id.Df_text);
            v.setText(getString(R.string.Df_txt) + ", " + Unit.m.toString());

            v = (TextView) view.findViewById(R.id.Hf_text);
            v.setText(getString(R.string.Hf_txt) + ", " + Unit.m.toString());

            v = (TextView) view.findViewById(R.id.d0_text);
            v.setText(getString(R.string.d0_txt) + ", " + Unit.m.toString());
            v = (TextView) view.findViewById(R.id.dp_text);
            v.setText(getString(R.string.dp_txt) + ", " + Unit.m.toString());


            v = (TextView) view.findViewById(R.id.P0v_text);
            v.setText(getString(R.string.p0v_txt) + ", " + Unit.kN.toString());
            v = (TextView) view.findViewById(R.id.P0h_text);
            v.setText(getString(R.string.p0h_txt) + ", " + Unit.kN.toString());
            v = (TextView) view.findViewById(R.id.P1v_text);
            v.setText(getString(R.string.p1v_txt) + ", " + Unit.kN.toString());
            v = (TextView) view.findViewById(R.id.P1h_text);
            v.setText(getString(R.string.p1h_txt) + ", " + Unit.kN.toString());
            v = (TextView) view.findViewById(R.id.wtsoil_text);
            v.setText(getString(R.string.soilwt_txt) + ", " + Unit.kN_per_m3.toString());

        } else {
            TextView v = (TextView) view.findViewById(R.id.b1_text);
            v.setText(getString(R.string.b1_txt) + ", " + Unit.ft.toString());

            v = (TextView) view.findViewById(R.id.b2_text);
            v.setText(getString(R.string.b2_txt) + ", " + Unit.ft.toString());
            v = (TextView) view.findViewById(R.id.Hb_text);
            v.setText(getString(R.string.Hb_txt) + ", " + Unit.ft.toString());
            v = (TextView) view.findViewById(R.id.Df_text);
            v.setText(getString(R.string.Df_txt) + ", " + Unit.ft.toString());

            v = (TextView) view.findViewById(R.id.Hf_text);
            v.setText(getString(R.string.Hf_txt) + ", " + Unit.ft.toString());

            v = (TextView) view.findViewById(R.id.d0_text);
            v.setText(getString(R.string.d0_txt) + ", " + Unit.ft.toString());
            v = (TextView) view.findViewById(R.id.dp_text);
            v.setText(getString(R.string.dp_txt) + ", " + Unit.ft.toString());


            v = (TextView) view.findViewById(R.id.P0v_text);
            v.setText(getString(R.string.p0v_txt) + ", " + Unit.kip.toString());
            v = (TextView) view.findViewById(R.id.P0h_text);
            v.setText(getString(R.string.p0h_txt) + ", " + Unit.kip.toString());
            v = (TextView) view.findViewById(R.id.P1v_text);
            v.setText(getString(R.string.p1v_txt) + ", " + Unit.kip.toString());
            v = (TextView) view.findViewById(R.id.P1h_text);
            v.setText(getString(R.string.p1h_txt) + ", " + Unit.kip.toString());
            v = (TextView) view.findViewById(R.id.wtsoil_text);
            v.setText(getString(R.string.soilwt_txt) + ", " + Unit.pcf.toString());
        }


        //edittexts
        EditText v = (EditText) view.findViewById(R.id.b1_input);
        v.setText(pref.getString(getString(R.string.B1_PREF), "6.0"));

        v = (EditText) view.findViewById(R.id.b2_in);
        v.setText(pref.getString(getString(R.string.B2_PREF), "2"));

        v = (EditText) view.findViewById(R.id.Hb_input);
        v.setText(pref.getString(getString(R.string.HB_PREF), "0.3"));

        v = (EditText) view.findViewById(R.id.Df_input);
        v.setText(pref.getString(getString(R.string.DF_PREF), "0.5"));

        v = (EditText) view.findViewById(R.id.Hf_input);
        v.setText(pref.getString(getString(R.string.HF_PREF), "0.4"));

        v = (EditText) view.findViewById(R.id.d0_input);
        v.setText(pref.getString(getString(R.string.D0_PREF), "1.5"));

        v = (EditText) view.findViewById(R.id.dp_input);
        v.setText(pref.getString(getString(R.string.DP_PREF), "3.0"));

        v = (EditText) view.findViewById(R.id.P0v_input);
        v.setText(pref.getString(getString(R.string.P0V_PREF), "100"));

        v = (EditText) view.findViewById(R.id.P0h_input);
        v.setText(pref.getString(getString(R.string.P0H_PREF), "0.0"));

        v = (EditText) view.findViewById(R.id.P1v_input);
        v.setText(pref.getString(getString(R.string.P1V_PREF), "100"));

        v = (EditText) view.findViewById(R.id.P1h_input);
        v.setText(pref.getString(getString(R.string.P1H_PREF), "0.0"));

        v = (EditText) view.findViewById(R.id.wtsoil_input);
        v.setText(pref.getString(getString(R.string.WSOIL_PREF), "16.0"));

    }

}
