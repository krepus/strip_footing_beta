package sibuyas.SF;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import static sibuyas.SF.MyDouble.Unit.*;


public class DesignOut extends Fragment {
    boolean debuglog = true;
    private static final String TAG = "DesignOut";
    private String mUnit;
    private View view;

    MyDouble b1, b2, c1, c2, Hb, Df, Hf, d0, dp, P0v, P0h, P1v, P1h, Wsoil;
    StripFooting sf;


    DisplayMetrics metrics;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != savedInstanceState) {
            //Log.i(TAG, "premium fragment oncreate bundle not null");
            return;
        }
        setHasOptionsMenu(true);
        //Log.i(TAG, "onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.result, container, false);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String unit = sp.getString(getString(R.string.UNIT), "");

        if (getDesignInput()) {

            //get  sharedpref
            sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
            int mbitmapWidth = Integer.parseInt(sp.getString(getString(R.string.WIDTHPIXEL_PREF), ""));
            //scale the height to matchwith actual data
            int mbitmapHeight = (int) ((double) mbitmapWidth * ((Hb.v() + Df.v() + Hf.v()) * 1.0d / b1.v()));
            Bitmap bitmap = Bitmap.createBitmap(mbitmapWidth, mbitmapHeight, Bitmap.Config.ARGB_8888);
            int txtht = 15 * (int) getResources().getDisplayMetrics().density;
            sf = new StripFooting(bitmap, txtht, b1, b2, c1, c2, Hb, Df, Hf, d0, dp, P0v, P0h, P1v, P1h, Wsoil);
        }

        ImageView geomview = (ImageView) view.findViewById(R.id.geom_img);
        geomview.setImageBitmap(sf.getGeomSketch());

        ImageView bearing_img = (ImageView) view.findViewById(R.id.bearing_img_id);
        bearing_img.setImageBitmap(sf.getBearingPressureBitmap());

        ImageView shear_img = (ImageView) view.findViewById(R.id.shear_img_id);
        shear_img.setImageBitmap(sf.getShearDiagBitmap());

        ImageView moment_img = (ImageView) view.findViewById(R.id.moment_img_id);
        moment_img.setImageBitmap(sf.getMomentDiagBitmap());

        return view;


    }

    private boolean getDesignInput() throws NumberFormatException {
        //get  sharedpref
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //throw numberformatexception if entry is not a number
        try {

            //get unit
            mUnit = sp.getString(getString(R.string.UNIT), getString(R.string.SI));

            //for SI unit
            if (mUnit.equals(getString(R.string.SI))) {
                c1 = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.C1_PREF), "")), m);
                c2 = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.C2_PREF), "")), m);
                b1 = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.B1_PREF), "")), m);
                b2 = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.B2_PREF), "")), m);
                Hb = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.HB_PREF), "")), m);
                Df = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.DF_PREF), "")), m);
                Hf = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.HF_PREF), "")), m);
                d0 = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.D0_PREF), "")), m);
                dp = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.DP_PREF), "")), m);
                P0v = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.P0V_PREF), "")), kN);
                P0h = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.P0H_PREF), "")), kN);
                P1v = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.P1V_PREF), "")), kN);
                P1h = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.P1H_PREF), "")), kN);
                Wsoil = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.WSOIL_PREF), "")), kN_per_m3);

            } else {
                c1 = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.C1_PREF), "")), ft);
                c2 = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.C2_PREF), "")), ft);
                b1 = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.B1_PREF), "")), ft);
                b2 = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.B2_PREF), "")), ft);
                Hb = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.HB_PREF), "")), ft);
                Df = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.DF_PREF), "")), ft);
                Hf = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.HF_PREF), "")), ft);
                d0 = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.D0_PREF), "")), ft);
                dp = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.DP_PREF), "")), ft);
                P0v = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.P0V_PREF), "")), kip);
                P0h = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.P0H_PREF), "")), kip);
                P1v = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.P1V_PREF), "")), kip);
                P1h = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.P1H_PREF), "")), kip);
                Wsoil = new MyDouble(Double.parseDouble(sp.getString(getString(R.string.WSOIL_PREF), "")), pcf);

            }

            return true;

        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Check input for invalid entries..", Toast.LENGTH_LONG).show();
            return false;
        }


        //pass the data to activity
        //saveDesigInputData.SaveDesigInput(bundle);
        //then return the bundled data
        //return bundle;
    }


}
