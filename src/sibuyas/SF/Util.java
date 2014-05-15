package sibuyas.SF;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by IntelliJ IDEA.
 * User: j0sua3
 * Date: 11/19/11
 * Time: 1:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class Util {

    public Util() {
    }

 /*   public static String round2string(double Rval, int Rpl) {
        double p = (double) Math.pow(10, Rpl);
        Rval = Rval * p;
        double tmp = Math.round(Rval);
        Double res = tmp / p;
        return res.toString();
    }*/

    public static int mod(int x, int y) {
        int result = x % y;
        if (result < 0)
            result += y;
        return result;
    }

    public static String r2str(double Rval, int Rpl) {
        double p = Math.pow(10, Rpl);
        Long tmp = Math.round(Rval * p);
        Double res = tmp / p;
        return res.toString();
    }
    /**
     *
     * @param ctx
     * @param v
     */
    public static void slide_down(Context ctx, View v){

        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }

    public static void slide_up(Context ctx, View v){

        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }



}
