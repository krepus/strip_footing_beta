package sibuyas.SF;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Typeface;

import static sibuyas.SF.Util.getCurTextLengthInPixels;
import static java.lang.Math.max;


public class StripfootingbitmapGeometry {

    public Canvas canvas;
    public Bitmap mbitmap_final;


    public int geombitmapWidth, geombitmapHeight;
    public double mscale_geom;
    public float mx0, my0, mtxtht;
    public PointF[] mPtsSF;
    public Paint mpaint;
    public Typeface mtypeface = Typeface.SANS_SERIF;

    MyDouble b1, b2, c1, c2, Hb, Df, Hf, d0, dp;
    public float fb1, fb2, fc1, fc2, fhb, fdf, fhf, fd0, fdp;

    //constructor
    public StripfootingbitmapGeometry(
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
        mbitmap_final = bitmap;
        mtxtht = txtht;  //20 for hdpi
        this.b1 = b1;
        this.b2 = b2;
        this.c1 = c1;
        this.c2 = c2;
        this.Hb = Hb;
        this.Df = Df;
        this.Hf = Hf;
        this.d0 = d0;
        this.dp = dp;

        geombitmapWidth = bitmap.getWidth();
        geombitmapHeight = bitmap.getHeight();


    }

    /**
     * @param paint
     * @param LM    = left margin of grid box
     * @param RM    = right margin of gridbox
     */

    public void drawSFelev(Paint paint, int LM, int RM) {

        mscale_geom = (geombitmapWidth - (LM + RM)) / b1.v();


        //set class fields value scaled to bitmap size
        fb1 = (float) (b1.v() * mscale_geom);
        fb2 = (float) (b2.v() * mscale_geom);
        fc1 = (float) (c1.v() * mscale_geom);
        fc2 = (float) (c2.v() * mscale_geom);
        fhb = (float) (Hb.v() * mscale_geom);
        fdf = (float) (Df.v() * mscale_geom);
        fhf = (float) (Hf.v() * mscale_geom);
        fd0 = (float) (d0.v() * mscale_geom);
        fdp = (float) (dp.v() * mscale_geom);

        //adjust bitmap width

        //init following
        mx0 = LM + (geombitmapWidth - (LM + RM)) / 2.f; //central location for translation
        my0 = geombitmapHeight / 2.f;

        //set strip footing elevation coord, in clockwise order
        mPtsSF = new PointF[12];
        mPtsSF[0] = new PointF(0.f, fhb + fdf + fhf);
        mPtsSF[1] = new PointF(0.f, fhb + fdf);
        mPtsSF[2] = new PointF(fd0 - fc1 / 2.f, fhb + fdf);
        mPtsSF[3] = new PointF(mPtsSF[2].x, 0.f);
        mPtsSF[4] = new PointF(mPtsSF[3].x + fc1, 0.f);
        mPtsSF[5] = new PointF(mPtsSF[4].x, mPtsSF[2].y);
        mPtsSF[6] = new PointF(mPtsSF[5].x + fdp - fc1, mPtsSF[5].y);
        mPtsSF[7] = new PointF(mPtsSF[6].x, 0.f);
        mPtsSF[8] = new PointF(mPtsSF[7].x + fc1, 0.f);
        mPtsSF[9] = new PointF(mPtsSF[8].x, mPtsSF[2].y);
        mPtsSF[10] = new PointF(fb1, mPtsSF[2].y);
        mPtsSF[11] = new PointF(fb1, mPtsSF[0].y);

        float[] boundaryPts = {mPtsSF[0].x, mPtsSF[0].y,
                mPtsSF[1].x, mPtsSF[1].y,
                mPtsSF[2].x, mPtsSF[2].y,
                mPtsSF[3].x, mPtsSF[3].y,
                mPtsSF[4].x, mPtsSF[4].y,
                mPtsSF[5].x, mPtsSF[5].y,
                mPtsSF[6].x, mPtsSF[6].y,
                mPtsSF[7].x, mPtsSF[7].y,
                mPtsSF[8].x, mPtsSF[8].y,
                mPtsSF[9].x, mPtsSF[9].y,
                mPtsSF[10].x, mPtsSF[10].y,
                mPtsSF[11].x, mPtsSF[11].y,
                mPtsSF[0].x, mPtsSF[0].y
        };
        //create matrix to translate
        Matrix matrix = new Matrix();
        //translate points using matrix object
        matrix.setTranslate(mx0 - (fb1 / 2.f), my0 - ((fhb + fdf + fhf) / 2.f));
        matrix.mapPoints(boundaryPts);

        //build path from translated points
        Path path = new Path();
        path.reset();
        path.moveTo(boundaryPts[0], boundaryPts[1]);
        path.lineTo(boundaryPts[2], boundaryPts[3]);
        path.lineTo(boundaryPts[4], boundaryPts[5]);
        path.lineTo(boundaryPts[6], boundaryPts[7]);
        path.lineTo(boundaryPts[8], boundaryPts[9]);
        path.lineTo(boundaryPts[10], boundaryPts[11]);
        path.lineTo(boundaryPts[12], boundaryPts[13]);
        path.lineTo(boundaryPts[14], boundaryPts[15]);
        path.lineTo(boundaryPts[16], boundaryPts[17]);
        path.lineTo(boundaryPts[18], boundaryPts[19]);
        path.lineTo(boundaryPts[20], boundaryPts[21]);
        path.lineTo(boundaryPts[22], boundaryPts[23]);
        path.lineTo(boundaryPts[0], boundaryPts[1]);
        //draw actual lines
        canvas.drawPath(path, paint);

    }


    public void drawCenterLine(Paint paint) {
        //draw centerlines
        //Points
       /* PointF[] endpoint = new PointF[4];
        endpoint[0] = new PointF(0, 0.f); //left end, horizontal line
        endpoint[1] = new PointF(mfBx, 0.f);
        endpoint[2] = new PointF(0.f, 0.f);//vertical
        endpoint[3] = new PointF(0.f, mfBy);
*/
/*

        //lines
        float[] horline = {endpoint[0].x, endpoint[0].y, endpoint[1].x, endpoint[1].y};
        float[] verline = {endpoint[2].x, endpoint[2].y, endpoint[3].x, endpoint[3].y};

        Matrix matrix = new Matrix();
        matrix.setTranslate(mx0, my0 - mfBy / 2.f);
        matrix.mapPoints(verline);
        matrix.reset();
        matrix.setTranslate(mx0 - mfBx / 2.f, my0);
        matrix.mapPoints(horline);

        //draw lines
        canvas.drawLines(horline, paint);
        canvas.drawLines(verline, paint);

        //draw labels X & Y
        canvas.drawText("Y", verline[0], verline[1] - mtxtht, paint);
        canvas.drawText("X", horline[2] + 0.5f * mtxtht, horline[3] + 0.5f * mtxtht, paint);

*/

    }

    /**
     * @param length,           actual length of dim
     * @param extL1             offset of dim line from measure face
     * @param dx,               translation along x
     * @param dy,               translation along y
     * @param dtheta,           rotation, 0=left to right, and dim line at bottom of object
     * @param scale_dimoverall, overall scale of dimstyle
     */

    public void drawDim(MyDouble length, float extL1, float dx, float dy, float dtheta, float scale_dimoverall) {
        //basic dimensions
      /*  float dimLength = (float) (length.v() * mscale_geom);
        float txtheight = scale_dimoverall * mtxtht;
        //float extL1 = 2.f * txtheight;
        float extL2 = txtheight / 2.f;
        float arrbase = txtheight / 2.5f;
        float gap_ext = txtheight / 4.5f;
        float arrheight = txtheight;

        //Points
        PointF[] ptdim = new PointF[13];
        ptdim[0] = new PointF(0.f, 0.f);
        ptdim[1] = new PointF(dimLength, 0.f);
        ptdim[2] = new PointF(0.f, gap_ext);
        ptdim[3] = new PointF(0.f, extL1);
        ptdim[4] = new PointF(0.f, ptdim[3].y + extL2);
        ptdim[5] = new PointF(arrheight, ptdim[3].y - arrbase / 2.f);
        ptdim[6] = new PointF(arrheight, ptdim[5].y + arrbase);
        ptdim[7] = new PointF(ptdim[1].x - arrheight, ptdim[5].y);
        ptdim[8] = new PointF(ptdim[7].x, ptdim[6].y);
        ptdim[9] = new PointF(ptdim[1].x, ptdim[3].y);
        ptdim[10] = new PointF(ptdim[1].x, ptdim[4].y);
        ptdim[11] = new PointF(ptdim[1].x, ptdim[2].y);


        //lines
        float[] pts = {ptdim[2].x, ptdim[2].y, ptdim[4].x, ptdim[4].y,
                ptdim[10].x, ptdim[10].y, ptdim[11].x, ptdim[11].y,
                ptdim[3].x, ptdim[3].y, ptdim[9].x, ptdim[9].y,
                ptdim[3].x, ptdim[3].y, ptdim[5].x, ptdim[5].y,
                ptdim[3].x, ptdim[3].y, ptdim[6].x, ptdim[6].y,
                ptdim[7].x, ptdim[7].y, ptdim[9].x, ptdim[9].y,
                ptdim[8].x, ptdim[8].y, ptdim[9].x, ptdim[9].y};

        // dim label
        Paint paint_label = new Paint(mpaint);
        paint_label.setTextSize(mtxtht);
        paint_label.setTextAlign(Paint.Align.CENTER);
        paint_label.setStyle(Paint.Style.FILL);
        String strdim = length.toString();
        float[] pts_path = {ptdim[3].x, ptdim[3].y, ptdim[9].x, ptdim[9].y};

        //extended dim line incase pts_path is less than text width
        float dimtxtlength = paint_label.measureText(strdim);
        //PointF ptdim12 = new PointF(ptdim[9].x + dimtxtlength, ptdim[9].y);
        ptdim[12] = new PointF(ptdim[9].x + dimtxtlength, ptdim[9].y);
        float[] pts_path2 = {ptdim[9].x, ptdim[9].y, ptdim[12].x + txtheight, ptdim[12].y};

        //rotate about pt 0
        Matrix matrix = new Matrix();
        matrix.setRotate(dtheta);
        matrix.mapPoints(pts);
        matrix.mapPoints(pts_path);
        matrix.mapPoints(pts_path2);

        //translate by dx, dy
        matrix.reset();
        matrix.setTranslate(dx, dy);
        matrix.mapPoints(pts);
        matrix.mapPoints(pts_path);
        matrix.mapPoints(pts_path2);

        //path of text
        Path path = new Path();
        float gaplabel = 7.5f / 18.f * txtheight;
        float voffset = -gaplabel;
        if (dtheta > 89.f && dtheta < 269.f) {
            //voffset = -gaplabel;
            if (dimLength > dimtxtlength) {  //textwidth fits in teh space
                path.moveTo(pts_path[2], pts_path[3]);
                path.lineTo(pts_path[0], pts_path[1]);
            } else {
                path.moveTo(pts_path2[2], pts_path2[3]);
                path.lineTo(pts_path2[0], pts_path2[1]);
                //draw dim line extension
                canvas.drawLines(pts_path2, mpaint);
            }

        } else {
            //voffset = gaplabel + txtheight - mtxtht / 5.f * scale_dimoverall;


            if (dimLength > dimtxtlength) {  //textwidth fits in teh space
                path.moveTo(pts_path[0], pts_path[1]);
                path.lineTo(pts_path[2], pts_path[3]);
            } else {
                path.moveTo(pts_path2[0], pts_path2[1]);
                path.lineTo(pts_path2[2], pts_path2[3]);
                //draw dim line extension
                canvas.drawLines(pts_path2, mpaint);
            }
        }

        //draw lines
        canvas.drawLines(pts, mpaint);

        //draw label on path
        canvas.drawTextOnPath(length.toString(), path, 0.f, voffset, paint_label);
        */
    }

    public void drawPointLoadLocation(Paint paint) {

        //center coordinate of load point
       /* final float mfDB = 10.f; //diamter of circle to indicate locatioin of point load

        //points array, coor of center of each circles
        // float[] pts = {cx1, cy1, cx2, cy2, cx3, cy3, cx4, cy4};
        float[] pts = new float[2];
        pts[0] = 0.f;
        pts[1] = 0.f;

        //create matrix to translate
        Matrix matrix = new Matrix();
        //translate points using matrix object
        matrix.setTranslate(mx0 - mfex, my0 + mfey);
        matrix.mapPoints(pts);  //apply translation matrix to pts!!!

        //draw actual circles
        canvas.drawCircle(pts[0], pts[1], mfDB / 2.f, paint);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(pts[0], pts[1], mfDB, paint);*/

    }

    public void drawAllInfo(Canvas canvas) {


        //draw dimension at bottom of plan
    /*    mpaint.setStrokeWidth(1.f);
        drawDim(Bx, 2 * mtxtht, mx0 - mfBx / 2.f, my0 + mfBy / 2.f, 0.f, 1.f);
        //draw dim at left side of plan
        drawDim(By, 1.5f * mtxtht, mx0 - mfBx / 2.f, my0 - mfBy / 2.f, 90.f, 1.f);
        //draw dim ex
        drawDim(ex, 1.5f * mtxtht, mx0 - mfex, my0 + mfey, 0.f, 1.f);
        //draw dim ex
        drawDim(ey, 1.5f * mtxtht, mx0 - mfex, my0 + 0.f, 90.f, 1.f);

        //draw point load location
        Paint reopaint = mpaint;
        reopaint.setStyle(Paint.Style.FILL);
        reopaint.setColor(Color.DKGRAY);
        reopaint.setStrokeWidth(1.f);
        drawPointLoadLocation(reopaint);


        //draw CL and global axes labels
        Paint CLpaint = new Paint();
        CLpaint.setStyle(Paint.Style.FILL_AND_STROKE);
        CLpaint.setPathEffect(new DashPathEffect(new float[]{40, 10, 10, 10}, 0));
        CLpaint.setColor(Color.RED);
        CLpaint.setTextSize(1.2f * mtxtht);
        CLpaint.setTypeface(Typeface.create("Helvetica", Typeface.BOLD));
        drawCenterLine(CLpaint);*/
    }


}
