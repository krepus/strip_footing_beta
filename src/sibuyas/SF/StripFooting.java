package sibuyas.SF;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class StripFooting extends StripfootingbitmapGeometry {


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
            MyDouble dp
    ) {
        //set fields for actual dim input by user
        super(bitmap, txtht, b1, b2, c1, c2, Hb, Df, Hf, d0, dp);


    }

    public Bitmap getSketch() {

        canvas = new Canvas(mbitmap_final);
        drawAllInfo(canvas); //pad geom drawn to canvas

        //draw bearing area on the same canvas specific to this footing case
       /* Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setAlpha(20);

        drawSFelev(paint);

*/
        return mbitmap_final;
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

    /*
    public double funcqx(double P, double M, double Bx, double Bz, double x) {

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
*/
    /**
     * @param chart_image_width
     * @param chart_image_height
     * @param ytitle
     * @param xtitle
     * @param graphtitle
     * @param y_zero_true        = true if y=0 line
     * @param x
     * @param y
     * @return
     */

/*

    public Bitmap drawChart(int chart_image_width,
                            int chart_image_height, String ytitle, String xtitle,
                            String graphtitle, Boolean y_zero_true, // set to true if y=0 line
                            // to be drawn
                            ArrayList<Double> x, ArrayList<Double> y) {

        // get max and min of the data
        double ymax = getmax(y, true);
        double ymin = getmax(y, false);
        if (ymin > 0.d) {
            ymin = 0;
        }
        double xmax = b1;

        final int labeltextht = 16;
        Bitmap output = Bitmap.createBitmap(chart_image_width,
                chart_image_height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, chart_image_width, chart_image_height);
        final RectF rectF = new RectF(rect);
        final float roundPx = 0;

        // get the little rounded cornered outside
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.WHITE);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setTypeface(Typeface.SANS_SERIF);
        paint.setTextSize(labeltextht);
        paint.setAntiAlias(true);

        int topmargin = 40; // margin of the grid box
        int bottmargin = labeltextht * 3
                + getCurTextLengthInPixels(paint, round2string(xmax, 2));
        int rightmargin = 20;

        // get leftmargin allow 2*textht for vertical axis title

        int leftmargin = labeltextht
                * 3
                + Math.max(
                getCurTextLengthInPixels(paint, round2string(ymax, 2)),
                getCurTextLengthInPixels(paint, round2string(ymin, 2)));

        // set paint prop for grid box drawn as a rectangle

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawRect(leftmargin, topmargin, chart_image_width - rightmargin,
                chart_image_height - bottmargin, paint);
        paint.setColor(Color.GRAY);
        canvas.drawLine(leftmargin, topmargin, chart_image_width - rightmargin,
                topmargin, paint);
        canvas.drawLine(leftmargin, chart_image_height - bottmargin,
                chart_image_width - rightmargin, chart_image_height
                        - bottmargin, paint
        );
        canvas.drawLine(leftmargin, topmargin, leftmargin, chart_image_height
                - bottmargin, paint);
        canvas.drawLine(chart_image_width - rightmargin, topmargin,
                chart_image_width - rightmargin, chart_image_height
                        - bottmargin, paint
        );

        // compute number of horizontal grid intervals in terms of Pn
        int Dy = (chart_image_height - topmargin - bottmargin);
        int Dx = (chart_image_width - rightmargin - leftmargin);
        // int ngshor = (int) Math.ceil(Dy / (3 * labeltextht));
        int ngshor = 4;
        int ngsver = 4;

        // style for vertical grid lines
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GRAY);
        // draw vertical grid lines
        double dx = (chart_image_width - rightmargin - leftmargin) / ngsver; // auto
        // scale

        for (int i = 1; i < (ngsver); i++) {
            float x0 = leftmargin + i * (float) dx;
            float y0 = Dy + topmargin;
            float x1 = x0;
            float y1 = y0 - Dy;
            canvas.drawLine(x0, y0, x1, y1, paint);

        }// END OF FOR LOOP

        // draw horizontal grid

        double dy = (chart_image_height - bottmargin - topmargin) / ngshor;

        for (int i = 1; i < (ngshor); i++) {
            // draw left to right
            float x0 = leftmargin;
            float y0 = topmargin + i * (float) dy;
            float x1 = x0 + Dx;
            float y1 = y0;
            canvas.drawLine(x0, y0, x1, y1, paint);
        }// END OF FOR LOOP

        // draw hor axis labels from top to bottom

        String label = null;
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(0);
        float x0 = leftmargin - 5.f; // set label 5px from 0 moment grid line
        dy = (ymax - ymin) / ngshor;
        float y0 = 0.f;

        for (int i = 0; i < (ngshor + 1); i++) {
            label = round2string(ymax - i * dy, 1);
            y0 = (float) (i * Dy / ngshor + topmargin) + 4.f;
            canvas.drawText(label, x0, y0, paint);
        }// END OF FOR LOOP

        // draw vertical grid lables
        final Path path = new Path();
        label = null;
        paint.setTextAlign(Paint.Align.LEFT);
        y0 = topmargin + Dy + labeltextht / 2; // px distance to bottom line
        path.lineTo(10.f, 50.f); // direction of writing the text labels
        x0 = (leftmargin - labeltextht / 2);
        path.offset((float) x0, (float) y0);
        dx = (xmax / ngsver);

        for (int i = 0; i < (ngsver + 1); i++) {
            label = round2string(dx * (double) (i), 1);
            canvas.drawTextOnPath(label, path, 0.f, 0.f, paint);
            path.offset((float) Dx / ngsver, 0.f);
        }// END OF FOR LOOP

        // draw vertical axis title
        y0 = (topmargin + Dy / 2 + getCurTextLengthInPixels(paint, ytitle) / 2);
        x0 = labeltextht * 1.5f;

        path.reset();
        // path.moveTo(0.f, 500.f);
        path.lineTo(0.f, -500.f);
        path.offset((float) x0, (float) y0);
        paint.setTextSize(labeltextht + 5);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawTextOnPath(ytitle, path, 0.f, 0.f, paint);

        // draw horizontal axis title, passed paramaeter

        y0 = (topmargin + Dy + bottmargin - labeltextht);
        x0 = (leftmargin + Dx / 2 - getCurTextLengthInPixels(paint, xtitle) / 2);
        canvas.drawText(xtitle, (float) x0, (float) y0, paint);

        // draw chart title, passed from the calling proc

        y0 = (topmargin / 2 - labeltextht / 4);
        x0 = (leftmargin + Dx / 2 - getCurTextLengthInPixels(paint, graphtitle) / 2);
        canvas.drawText(graphtitle, x0, y0, paint);

        // draw the lines connecting the data points in teh arraylist data

        paint.setColor(Color.RED);
        float xscale = Dx / (float) (xmax);
        float yscale = Dy / (float) (ymax - ymin);

        int npts = x.size();

        float d_to_yzero = (float) ymax * yscale + (float) topmargin;
        // draw y=0 line if true
        if (y_zero_true) {
            canvas.drawLine(leftmargin, d_to_yzero, leftmargin + Dx,
                    d_to_yzero, paint);
        }

        // draw curve lines
        for (int i = 0; i < (npts - 1); i++) {
            canvas.drawLine(x.get(i).floatValue() * xscale + leftmargin,
                    d_to_yzero - y.get(i).floatValue() * yscale, x.get(i + 1)
                            .floatValue() * xscale + leftmargin, d_to_yzero
                            - y.get(i + 1).floatValue() * yscale, paint
            );
        }
        return output;

    }// END OF DRAW CHART

    private static int getCurTextLengthInPixels(Paint this_paint,
                                                String this_text) {
        Rect rect = new Rect();
        this_paint.getTextBounds(this_text, 0, this_text.length(), rect);
        return rect.width();
    } // --- end of getCurTextLengthInPixels ---

    public static String round2string(double val, int decimal_places) {
        int tmp = (int) Math.floor(val);
        int mantissa = (int) Math.ceil((val - (double) tmp)
                * Math.pow(10, decimal_places));
        return (Integer.toString(tmp) + "." + Integer.toString(mantissa));

    }

    public ArrayList<Double> bearingpressurelist(Boolean yy) {
        */
/*
           * all units assume to be kN, mm accepts P, M, Bx, Bz and yy(true if
           * bearing pressure is requied returns a list containg the bearing
           * pressure values at some interval
           *//*


        // compute resultant P,M;
        double concwt = 24.d;
        double Wf = soilwt * b1 * b2 * Df + concwt * b1 * b2 * Hf;
        double P = P0v + P1v + Wf;
        double M = (P1v * (dp + d0 - b1 / 2.d) - P0v * (b1 / 2.d - d0) + (P1h + P0h)
                * (Hb + Df + Hf));

        // compute the reasonable interval
        double interval = 0.1d;
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
                y.add(-funcqx(P, M, b1, b2, (double) i * interval));
            }
            return y;
        }

    }

    public ArrayList<Double> shearlist(Boolean yy) {
        */
/*
           * all units assume to be kN, mm accepts P, M, Bx, Bz and yy(true if
           * bearing pressure is requied returns a list containg the bearing
           * pressure values at some interval
           *//*


        // compute resultant P,M;

        double Wf = soilwt * b1 * b2 * Df + concwt * b1 * b2 * Hf;
        double P = P0v + P1v + Wf;
        double M = (P1v * (dp + d0 - b1 / 2.d) - P0v * (b1 / 2.d - d0) + (P1h + P0h)
                * (Hb + Df + Hf));

        // compute the reasonable interval
        double interval = 0.1d;
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
            double netVu = 0.d;
            y.add(netVu);
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
                    netVu = Vuiq - Vu_wtfooting;
                    y.add(netVu);
                } else if ((double) i * interval < d0 + dp) {
                    netVu = Vuiq - Vu_wtfooting - P0v;
                    y.add(netVu);
                } else {
                    netVu = Vuiq - Vu_wtfooting - P0v - P1v;
                    y.add(netVu);
                }
            }
            return y;
        }

    }

    public ArrayList<Double> momentlist(Boolean yy) {
        */
/*
           * all units assume to be kN, mm accepts P, M, Bx, Bz and yy(true if
           * bearing pressure is requied returns a list containg the bearing
           * pressure values at some interval
           *//*


        // compute resultant P,M;

        double Wf = soilwt * b1 * b2 * Df + concwt * b1 * b2 * Hf;
        double hx = Hb + Df + Hf;
        double P = P0v + P1v + Wf;
        double M = (P1v * (dp + d0 - b1 / 2.d) - P0v * (b1 / 2.d - d0) + (P1h + P0h)
                * hx);

        // compute the reasonable interval
        double interval = 0.1d;
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
            double netMu = 0.d;
            y.add(netMu);
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
                    netMu = Muiq - Mu_wtfooting;
                    y.add(-netMu);
                } else if (xi < d0 + dp) {
                    netMu = Muiq - Mu_wtfooting - P0v * (xi - d0) + P0h * hx;
                    y.add(-netMu);
                } else {
                    netMu = Muiq - Mu_wtfooting - P0v * (xi - d0) + P0h * hx
                            - P1v * (xi - d0 - dp) + P1h * hx;
                    y.add(-netMu);
                }
            }
            return y;
        }

    }

    public void showchart() {

        // draw bearing pressure
        Display display = getWindowManager().getDefaultDisplay();
        bearing_chart_width = Math.min(display.getWidth(), 420);
        bearing_chart_height = (int) Math
                .rint(bearing_chart_width * 2.d / 3.d);

        ArrayList<Double> x = bearingpressurelist(false);
        ArrayList<Double> y = bearingpressurelist(true);
        ImageView im_bearing = (ImageView) findViewById(R.id.imageview_bearing);
        Bitmap bearingbmp = drawChart(bearing_chart_width,
                bearing_chart_height, "Bearing (kPa)", "Footing length(m)",
                "BEARING PRESSURE", false, x, y);
        im_bearing.setImageBitmap(bearingbmp);

        // draw shear diagram
        // x.clear();
        y.clear();
        // x = shearlist(false);
        y = shearlist(true);
        ImageView im_shear = (ImageView) findViewById(R.id.imageview_shear);
        Bitmap shearbmp = drawChart(bearing_chart_width, bearing_chart_height,
                "shear(kN)", "Footing length(m)", "SHEAR DIAGRAM", true, x, y);
        im_shear.setImageBitmap(shearbmp);

        // draw moment diagram
        y.clear();
        y = momentlist(true);
        ImageView im_moment = (ImageView) findViewById(R.id.imageview_moment);
        Bitmap momentbmp = drawChart(bearing_chart_width, bearing_chart_height,
                "Moment(kNm)", "Footing length(m)", "MOMENT DIAGRAM", true, x, y);
        im_moment.setImageBitmap(momentbmp);
    }
*/

    /*
    public static double getmax(ArrayList<Double> alist, Boolean ismax) {
        ArrayList<Double> tmp = new ArrayList<Double>(alist);
        Collections.sort(tmp);
        if (ismax) {
            return tmp.get(tmp.size() - 1);
        } else {
            return tmp.get(0);
        }
    }

    */

}