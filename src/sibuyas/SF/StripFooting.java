package sibuyas.SF;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.floor;
import static sibuyas.SF.MyDouble.Unit.*;
import static sibuyas.SF.Util.getCurTextLengthInPixels;
import static java.lang.Math.max;
import static sibuyas.SF.Util.r2str;

public class StripFooting extends StripfootingbitmapGeometry {

    double P0v, P0h, P1v, P1h, soilwt;
    double concwt = 24.d;
    int RM, LM;
    int bearing_chart_width, bearing_chart_height;
    final int ngshor = 4;


    String strpUnit, strlUnit, strfUnit, strmUnit;

    //global paint
    Paint labelPaint;
    float forceTitleHeight;
    int edgeclear = 10;

    //constructor
    public StripFooting(
            Bitmap bitmap,
            int txtht,
            MyDouble b1,
            MyDouble b2,
            MyDouble c1,
            MyDouble c2,
            MyDouble Hb,
            MyDouble Df,
            MyDouble Hf,
            MyDouble d0,
            MyDouble dp,
            //loading
            MyDouble P0v,
            MyDouble P0h,
            MyDouble P1v,
            MyDouble P1h,
            MyDouble Wsoil
    ) {
        //set fields for actual dim input by user
        super(bitmap, txtht, b1, b2, c1, c2, Hb, Df, Hf, d0, dp);
        this.P0v = P0v.dblVal(kN);
        this.P0h = P0h.dblVal(kN);
        this.P1h = P1h.dblVal(kN);
        this.P1v = P1v.dblVal(kN);
        soilwt = Wsoil.dblVal(kN_per_m3);

        //get unit labels
        strpUnit = "(kPa)";
        strlUnit = "(m)";
        strfUnit = "(kN)";
        strmUnit = "(kNm)";

        if (b1.u() != m) {
            strpUnit = "(psf)";
            strlUnit = "(ft)";
            strmUnit = "(kip-ft)";
            strfUnit = "(kip)";

        }


        //global paint for scaling
        labelPaint = new Paint();
        labelPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        labelPaint.setStrokeWidth(0.f);
        labelPaint.setTextSize(mtxtht);
        labelPaint.setAntiAlias(true);
        labelPaint.setTypeface(Typeface.SANS_SERIF);
        //margins are all referring to the location of the edges of teh foundation elevation
        forceTitleHeight = mtxtht + 2;
        RM = 30; //right and left, respectively
        LM = (int) (5.f + forceTitleHeight + 5.f + getMaxWidthGridLabel(labelPaint) + 5.f);

        // draw bearing pressure
        bearing_chart_width = geombitmapWidth;
        //  bearing_chart_height = (int) Math.rint(bearing_chart_width * 0.5d);

    }


    /**
     * @param P
     * @param M
     * @param Bx
     * @param Bz
     * @param x
     * @return the bearing pressure given x distance from point 0
     * eccentricity is assumed to be within middle third for case 1 units
     * assumed kN, m
     */

    private double funcqx(double P, double M, double Bx, double Bz, double x) {

        double q0, q1;
        double ecc = M / P;
        if (Math.abs(ecc) > Bx / 2.d) {
            return -1.d;
        } else if (Math.abs(ecc) <= Bx / 6.d) { // q0, q1 > 0
            q1 = (6.d * M + Bx * P) / (Bx * Bx * Bz);
            q0 = (2.d * P - (6.d * M + Bx * P) / Bx) / (Bx * Bz);
            return (q0 + x * (q1 - q0) / Bx);
        } else if (ecc > Bx / 6.d) {
            double beff = 3.d * (Bx / 2.d - ecc);
            double x0 = Bx - beff;
            q1 = (2.d * P) / (Bz * beff);
            return Math.max((x - x0) * q1 / beff, 0.d);
        } else {

            double x0 = 3.d * (Bx / 2.d + ecc);
            q0 = (2.d * P) / (Bz * x0);
            return Math.max((x0 - x) * q0 / x0, 0.d);
        }

    }


    /**
     * @param chart_image_width
     * @param chart_image_height
     * @param ytitle
     * @param xtitle
     * @param graphtitle
     * @param y_zero_true        = true if y=0 line
     * @param x                  distance from left of footing
     * @param y                  ordinate of design action to plot
     * @return
     */


    private Bitmap drawChart(int chart_image_width,
                             int chart_image_height, String ytitle, String xtitle,
                             String graphtitle, Boolean y_zero_true, // set to true if y=0 line
                             // to be drawn
                             ArrayList<Double> x, ArrayList<Double> y) {

        // get max and min of the data
        double xmax = b1.dblVal(m);
        double ymax = getmax(y, true);
        double ymin = getmax(y, false);

        if (ymin > 0.d) {
            ymin = 0;
        }

        //top and bottom margins
        labelPaint = new Paint();
        labelPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        labelPaint.setStrokeWidth(0.f);
        labelPaint.setTextSize(mtxtht);
        labelPaint.setAntiAlias(true);
        labelPaint.setTypeface(Typeface.SANS_SERIF);
        labelPaint.setStrokeWidth(0.f);

        int topmargin = 5 + (int) forceTitleHeight + 5; // margin of the grid box
        int bottmargin = 5 +
                +getCurTextLengthInPixels(labelPaint, String.valueOf(r2str((int) xmax + 0.795, 3)))
                + 5
                + (int) forceTitleHeight + edgeclear;


        final int labeltextht = (int) mtxtht;
        Bitmap output = Bitmap.createBitmap(chart_image_width,
                chart_image_height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        //paint.setTextSize(labeltextht);
        final Rect rect = new Rect(0, 0, chart_image_width, chart_image_height);
        final RectF rectF = new RectF(rect);
        final float roundPx = 0;

        // get the little rounded cornered outside
        Paint chartPaint = new Paint();
        canvas.drawARGB(0, 0, 0, 0);
        chartPaint.setColor(Color.WHITE);
        canvas.drawRoundRect(rectF, roundPx, roundPx, chartPaint);


        // set paint prop for grid box drawn as a rectangle

        canvas.drawRect(LM, topmargin, chart_image_width - RM,
                chart_image_height - bottmargin, chartPaint);
        chartPaint.setColor(Color.GRAY);
        canvas.drawLine(LM, topmargin, chart_image_width - RM,
                topmargin, chartPaint);
        canvas.drawLine(LM, chart_image_height - bottmargin,
                chart_image_width - RM, chart_image_height
                        - bottmargin, chartPaint
        );
        canvas.drawLine(LM, topmargin, LM, chart_image_height
                - bottmargin, chartPaint);
        canvas.drawLine(chart_image_width - RM, topmargin,
                chart_image_width - RM, chart_image_height
                        - bottmargin, chartPaint
        );

        // compute number of horizontal grid intervals in terms of Pn
        int Dy = (chart_image_height - topmargin - bottmargin);
        int Dx = (chart_image_width - RM - LM);
        // int ngshor = (int) Math.ceil(Dy / (3 * labeltextht));
        int ngsver = Dx / (Dy / ngshor);
        // draw vertical grid lines
        double dx = (chart_image_width - RM - LM) / ngsver; // auto
        // scale

        for (int i = 1; i < (ngsver); i++) {
            float x0 = LM + i * (float) dx;
            float y0 = Dy + topmargin;
            float x1 = x0;
            float y1 = y0 - Dy;
            canvas.drawLine(x0, y0, x1, y1, chartPaint);

        }// END OF FOR LOOP

        // draw horizontal grid

        double dy = (chart_image_height - bottmargin - topmargin) / ngshor;

        for (int i = 1; i < (ngshor); i++) {
            // draw left to right
            float x0 = LM;
            float y0 = topmargin + i * (float) dy;
            float x1 = x0 + Dx;
            float y1 = y0;
            canvas.drawLine(x0, y0, x1, y1, chartPaint);
        }// END OF FOR LOOP

        // draw hor axis labels from top to bottom
        labelPaint.setTextAlign(Paint.Align.RIGHT);
        labelPaint.setColor(Color.BLUE);

        String label = null;

        float x0 = LM - 5.f; // set label 5px from 0 moment grid line
        dy = (ymax - ymin) / ngshor;
        float y0 = 0.f;

        for (int i = 0; i < (ngshor + 1); i++) {
            label = r2str(ymax - i * dy, 1);
            y0 = (float) (i * Dy / ngshor + topmargin) + (float) labeltextht / 2.f;
            canvas.drawText(label, x0, y0, labelPaint);
        }// END OF FOR LOOP

        // draw vertical grid lables
        final Path path = new Path();
        label = null;
        labelPaint.setTextAlign(Paint.Align.LEFT);
        y0 = topmargin + Dy + 5.f; // px distance to bottom line
        path.lineTo(60.f, 150.f); // direction of writing the text labels
        x0 = (LM - labeltextht / 2);
        path.offset((float) x0, (float) y0);


        for (int i = 0; i < (ngsver + 1); i++) {
            if (this.b1.u() == m) {
                dx = (xmax / ngsver);
            } else {
                dx = this.b1.dblVal(ft) / ngsver;
            }
            label = r2str(dx * (double) (i), 2);

            canvas.drawTextOnPath(label, path, 0.f, 0.f, labelPaint);
            path.offset((float) Dx / ngsver, 0.f);
        }// END OF FOR LOOP

        // draw vertical axis title
        Paint titlePaint = new Paint(labelPaint);
        titlePaint.setTextSize(forceTitleHeight);
        y0 = (topmargin + Dy / 2 + getCurTextLengthInPixels(titlePaint, ytitle) / 2);
        x0 = 5.f + forceTitleHeight;

        path.reset();
        // path.moveTo(0.f, 500.f);
        path.lineTo(0.f, -500.f);
        path.offset((float) x0, (float) y0);

        canvas.drawTextOnPath(ytitle, path, 0.f, 0.f, titlePaint);

        // draw horizontal axis title, passed paramaeter

        y0 = chart_image_height - edgeclear;
        x0 = (LM + Dx / 2 - getCurTextLengthInPixels(titlePaint, xtitle) / 2);
        canvas.drawText(xtitle, (float) x0, (float) y0, titlePaint);

        // draw chart title, passed from the calling proc

        y0 = topmargin - 5;
        x0 = (LM + Dx / 2 - getCurTextLengthInPixels(titlePaint, graphtitle) / 2);
        canvas.drawText(graphtitle, x0, y0, titlePaint);

        // draw the lines connecting the data points in teh arraylist data

        Paint graphPaint = new Paint();
        graphPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        graphPaint.setColor(Color.RED);
        graphPaint.setAntiAlias(true);
        graphPaint.setStrokeWidth(2.f);
        float xscale = Dx / (float) (xmax);
        float yscale = Dy / (float) (ymax - ymin);

        int npts = x.size();

        float d_to_yzero = (float) ymax * yscale + (float) topmargin;
        // draw y=0 line if true
        if (y_zero_true) {
            canvas.drawLine(LM, d_to_yzero, LM + Dx,
                    d_to_yzero, graphPaint);
        }

        // draw curve lines
        for (int i = 0; i < (npts - 1); i++) {
            canvas.drawLine(x.get(i).floatValue() * xscale + LM,
                    d_to_yzero - y.get(i).floatValue() * yscale, x.get(i + 1)
                            .floatValue() * xscale + LM, d_to_yzero
                            - y.get(i + 1).floatValue() * yscale, graphPaint
            );
        }
        return output;

    }// END OF DRAW CHART


    /**
     * @param yy = true if required?
     * @return pressures in kPa
     */

    private ArrayList<Double> bearingpressurelist(Boolean yy) {


        // compute resultant P,M;


        double b1, b2, Df, Hf, d0, dp, Hb;
        b1 = this.b1.dblVal(m);
        b2 = this.b2.dblVal(m);
        d0 = this.d0.dblVal(m);
        dp = this.dp.dblVal(m);
        Df = this.Df.dblVal(m);
        Hf = this.Hf.dblVal(m);
        Hb = this.Hb.dblVal(m);


        double Wf = soilwt * b1 * b2 * Df + concwt * b1 * b2 * Hf;
        double P = P0v + P1v + Wf;
        double M = (P1v * (dp + d0 - b1 / 2.d) - P0v * (b1 / 2.d - d0) + (P1h + P0h)
                * (Hb + Df + Hf));

        // compute the reasonable interval
        double interval = 0.05d;
        int npoints = (int) Math.ceil(b1 / interval);
        interval = b1 / npoints;

        if (!yy) {
            ArrayList<Double> x = new ArrayList<Double>();
            for (int i = 0; i < npoints + 1; i++) {
                x.add((double) i * interval);
            }
            return x;
        } else {
            ArrayList<Double> y = new ArrayList<Double>();
            for (int i = 0; i < npoints + 1; i++) {
                MyDouble qofx = new MyDouble(-funcqx(P, M, b1, b2, (double) i * interval), kPa);
                if (this.b1.u() != m) { //imperial
                    y.add(qofx.dblVal(psf));
                } else {
                    y.add(qofx.dblUnit(kPa));
                }

            }
            return y;
        }

    }

    private ArrayList<Double> shearlist(Boolean yy) {

        double b1, b2, Df, Hf, d0, dp, Hb;
        b1 = this.b1.dblVal(m);
        b2 = this.b2.dblVal(m);
        d0 = this.d0.dblVal(m);
        dp = this.dp.dblVal(m);
        Df = this.Df.dblVal(m);
        Hf = this.Hf.dblVal(m);
        Hb = this.Hb.dblVal(m);

        // compute resultant P,M;

        double Wf = soilwt * b1 * b2 * Df + concwt * b1 * b2 * Hf;
        double P = P0v + P1v + Wf;
        double M = (P1v * (dp + d0 - b1 / 2.d) - P0v * (b1 / 2.d - d0) + (P1h + P0h)
                * (Hb + Df + Hf));

        // compute the reasonable interval
        double interval = 0.05d;
        int npoints = (int) Math.ceil(b1 / interval);
        int int_points = 10; // integration points factor
        interval = b1 / npoints;

        if (!yy) {
            ArrayList<Double> x = new ArrayList<Double>();
            for (int i = 0; i < npoints + 1; i++) {

                x.add((double) i * interval);
            }
            return x;
        } else {
            ArrayList<Double> y = new ArrayList<Double>();
            MyDouble netVu = new MyDouble(0.d, kN);
            y.add(netVu.v());
            //y.add(netVu);
            double dx = interval / (int_points);
            for (int i = 1; i < npoints + 1; i++) {
                // compute shear due to bearing pressure at point i
                double Vuiq = 0;
                for (int j = 1; j < int_points * i + 1; j++) {

                    Vuiq = Vuiq
                            + (funcqx(P, M, b1, b2, (double) (j - 1) * dx) + funcqx(
                            P, M, b1, b2, (double) j * dx)) / 2.d * dx
                            * b2;

                }
                // compute shear at section i due to weight of footing
                double Vu_wtfooting = (double) (i * interval) * b2
                        * (Df * soilwt + Hf * concwt);
                // compute net shear at section i
                if ((double) i * interval < d0) {
                    netVu = new MyDouble(Vuiq - Vu_wtfooting, kN);
                    if (this.b1.u() != m) {
                        y.add(netVu.dblVal(kip));
                    } else {
                        y.add(netVu.dblVal(kN));
                    }
                } else if ((double) i * interval < d0 + dp) {
                    netVu = new MyDouble(Vuiq - Vu_wtfooting - P0v, kN);
                    if (this.b1.u() != m) {
                        y.add(netVu.dblVal(kip));
                    } else {
                        y.add(netVu.dblVal(kN));
                    }
                } else {
                    netVu = new MyDouble(Vuiq - Vu_wtfooting - P0v - P1v, kN);
                    if (this.b1.u() != m) {
                        y.add(netVu.dblVal(kip));
                    } else {
                        y.add(netVu.dblVal(kN));
                    }
                }
            }
            return y;
        }

    }

    private ArrayList<Double> momentlist(Boolean yy) {

        double b1, b2, Df, Hf, d0, dp, Hb;
        b1 = this.b1.dblVal(m);
        b2 = this.b2.dblVal(m);
        d0 = this.d0.dblVal(m);
        dp = this.dp.dblVal(m);
        Df = this.Df.dblVal(m);
        Hf = this.Hf.dblVal(m);
        Hb = this.Hb.dblVal(m);

        // compute resultant P,M;

        double Wf = soilwt * b1 * b2 * Df + concwt * b1 * b2 * Hf;
        double hx = Hb + Df + Hf;
        double P = P0v + P1v + Wf;
        double M = (P1v * (dp + d0 - b1 / 2.d) - P0v * (b1 / 2.d - d0) + (P1h + P0h)
                * hx);

        // compute the reasonable interval
        double interval = 0.05d;
        int npoints = (int) Math.ceil(b1 / interval);
        int int_points = 10; // integration points factor
        interval = b1 / npoints;

        if (!yy) {
            ArrayList<Double> x = new ArrayList<Double>();
            for (int i = 0; i < npoints + 1; i++) {

                x.add((double) i * interval);
            }
            return x;
        } else {
            ArrayList<Double> y = new ArrayList<Double>();
            MyDouble netMu = new MyDouble(0.d, kN);
            y.add(netMu.v());
            double dx = interval / (int_points);
            for (int i = 1; i < npoints + 1; i++) {
                // compute total moment due to bearing pressure at point i
                double Muiq = 0.d;
                double xi = (double) i * interval; // section to get moment
                for (int j = 1; j < int_points * i + 1; j++) {

                    double xj = (double) (j - 1) * dx;
                    double xjj = (double) j * dx;

                    Muiq = Muiq + funcqx(P, M, b1, b2, xj) * dx / 2.d * b2
                            * (xi - xj + dx / 3.d) + funcqx(P, M, b1, b2, xjj)
                            * dx / 2.d * b2 * (xi - xjj + dx / 3.d);
                }
                // compute moment at section i due to weight of footing
                double Mu_wtfooting = xi * xi / 2.d * b2
                        * (Df * soilwt + Hf * concwt);
                // compute net shear at section i
                if (xi < d0) {
                    netMu = new MyDouble(-(Muiq - Mu_wtfooting), kNm);
                    if (this.b1.u() != m) {
                        y.add(netMu.dblVal(kNm));
                    } else {
                        y.add(netMu.dblVal(kipft));
                    }
                } else if (xi < d0 + dp) {
                    netMu = new MyDouble(-(Muiq - Mu_wtfooting - P0v * (xi - d0) + P0h * hx), kNm);
                    if (this.b1.u() != m) {
                        y.add(netMu.dblVal(kNm));
                    } else {
                        y.add(netMu.dblVal(kipft));
                    }
                } else {
                    netMu = new MyDouble(-(Muiq - Mu_wtfooting - P0v * (xi - d0) + P0h * hx
                            - P1v * (xi - d0 - dp) + P1h * hx), kNm);
                    if (this.b1.u() != m) {
                        y.add(netMu.dblVal(kNm));
                    } else {
                        y.add(netMu.dblVal(kipft));
                    }
                }
            }
            return y;
        }

    }


    public Bitmap getBearingPressureBitmap() {


        ArrayList<Double> x = bearingpressurelist(false);
        ArrayList<Double> y = bearingpressurelist(true);
        Bitmap bearingbmp = drawChart(bearing_chart_width, bearing_chart_width * 2 / 3,
                "Bearing" + strpUnit, " ",
                "BEARING PRESSURE", false, x, y);
        return (bearingbmp);
    }

    public Bitmap getShearDiagBitmap() {

        ArrayList<Double> x = bearingpressurelist(false);
        ArrayList<Double> y = shearlist(true);
        Bitmap shearbmp = drawChart(bearing_chart_width, bearing_chart_width * 2 / 3,
                "shear" + strfUnit, " ", "SHEAR DIAGRAM", true, x, y);
        return (shearbmp);
    }


    public Bitmap getMomentDiagBitmap() {

        ArrayList<Double> x = bearingpressurelist(false);
        ArrayList<Double> y = momentlist(true);
        Bitmap momentbmp = drawChart(bearing_chart_width, bearing_chart_width * 2 / 3,
                "Moment" + strmUnit, "Footing length" + strlUnit, "MOMENT DIAGRAM", true, x, y);
        return (momentbmp);
    }


    public static double getmax(ArrayList<Double> alist, Boolean ismax) {
        ArrayList<Double> tmp = new ArrayList<Double>(alist);
        Collections.sort(tmp);
        if (ismax) {
            return tmp.get(tmp.size() - 1);
        } else {
            return tmp.get(0);
        }
    }

    public float getMaxWidthGridLabel(Paint paint) {
        //global bearing, shear, moment magnitudes


        int width = 0;
        ArrayList<Double> arrayList = bearingpressurelist(true);
        double ymax = getmax(arrayList, true);
        double ymin = getmax(arrayList, false);
        width = max(width, getCurTextLengthInPixels(paint, r2str(ymax, 2)));
        width = max(width, getCurTextLengthInPixels(paint, r2str(ymin, 2)));

        arrayList = shearlist(true);
        ymax = getmax(arrayList, true);
        ymin = getmax(arrayList, false);
        width = max(width, getCurTextLengthInPixels(paint, r2str(ymax, 2)));
        width = max(width, getCurTextLengthInPixels(paint, r2str(ymin, 2)));

        arrayList = momentlist(true);
        ymax = getmax(arrayList, true);
        ymin = getmax(arrayList, false);
        width = max(width, getCurTextLengthInPixels(paint, r2str(ymax, 2)));
        width = max(width, getCurTextLengthInPixels(paint, r2str(ymin, 2)));


        return width;

    }


    public Bitmap getGeomSketch() {


        canvas = new Canvas(mbitmap_final);

        labelPaint = new Paint();
        labelPaint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        //draw footing elevation
        labelPaint.setColor(Color.BLUE);
        labelPaint.setStyle(Paint.Style.STROKE);
        labelPaint.setStrokeWidth(0.f);
        drawSFelev(labelPaint, LM, RM);


        return mbitmap_final;
    }


}
