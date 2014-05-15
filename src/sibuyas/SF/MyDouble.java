package sibuyas.SF;

/**
 * Created with IntelliJ IDEA.
 * User: j0sua3
 * Date: 17/04/13
 * Time: 11:14 PM
 * To change this template use File | Settings | File Templates.
 */

import static java.lang.Math.pow;


public class MyDouble {


    private Double mValue;
    private Unit mUnit;
    private static final double tolerance = 1e-6;


    public MyDouble() {
        mValue = 0.d;
        mUnit = Unit.mm;
    }

    public MyDouble(double value, Unit unit) {
        mValue = value; //set teh value according to users chosen unit
        mUnit = unit; //set unit string according to chosen unit
    }


    /**
     * return the double value of the value field converted to base unit
     * length units --> mm
     * Force units --> N
     * Moment units --> N*mm
     * Stress untis --> MPa
     * subgrade modulus --> N/mm^3
     */
    public double v() {

        switch (mUnit) {
         /*length*/
            case mm:
                return mValue;
            case in:
                return mValue * 25.4d;
            case m:
                return mValue * 1000.d;
            case ft:
                return mValue * 304.8d;

            /*force*/
            case N:
                return mValue;
            case kN:
                return mValue * 1000.d;

            case lbf:
                return mValue * 4.4482216152605d;
            case kip:
                return mValue * 4448.2216152605d;


            /*pressure*/
            case MPa:
                return mValue;
            case kPa:
                return mValue * 0.001d;
            case ksi:
                return mValue * 6.894757293168361d;
            case psi:
                return mValue * 0.00689475729316836d;
            case psf:
                return mValue * 0.00004788025898034d;




            /*moment or energy units*/
            case Nm:
                return mValue * 1000.d;
            case kNm:
                return mValue * 1000.d * 1000.d;

            case kipft:
                return mValue * 1355817.9483314003d;


            //area
            case mm2:
                return mValue;
            case in2:
                return mValue * 25.4d * 25.4d;

            //modulus
            case kPa_per_mm:
                return mValue * 0.001d;

            case ksf_per_in:
                return mValue * 0.00188504956615495d;

            // force per length

            case kN_per_m:
                return mValue * 1.d;

            case kip_per_ft:
                return mValue * 14.593902937206364d;

            case N_per_m:
                return mValue * 0.001d;
            case lbf_per_ft:
                return mValue * 0.01459390293720636d;


            //density
            case kN_per_m3:
                return mValue * 0.000001d;
            case pcf:
                return mValue * 0.00000015708746385d;


            default:
                return 1e99;
        }

    }

    /**
     * @return a converted double value specified by Unit unit
     */
    public MyDouble toUnit(Unit unit) {


        switch (unit) {

          /*forces*/
            case N:
                return new MyDouble(v(), Unit.N);
            case kN:
                return new MyDouble(v() / 1e3, Unit.kN);
            case lbf:
                return new MyDouble(v() * 0.2248089430997105d, Unit.ft);
            case kip:
                return new MyDouble(v() * 0.00022480894309971d, Unit.kip);

           /*length*/
            case mm:
                return new MyDouble(v(), Unit.mm);
            case m:
                return new MyDouble(v() / 1e3, Unit.m);
            case in:
                return new MyDouble(v() / 25.4d, Unit.in);
            case ft:
                return new MyDouble(v() / 304.8d, Unit.ft);


           /*pressure*/
            case MPa:
                return new MyDouble(v(), Unit.MPa);
            case kPa:
                return new MyDouble(v() * 1000.d, Unit.kPa);
            case ksi:
                return new MyDouble(v() * 0.14503773773020923d, Unit.ksi);
            case psi:
                return new MyDouble(v() * 145.03773773020922d, Unit.psi);
            case psf:
                return new MyDouble(v() * 20885.43423315013d, Unit.psf);

           /*moment or enrgy*/
            case kNm:
                return new MyDouble(v() * 1e-6, Unit.kNm);
            case kipft:
                return new MyDouble(v() * 0.00000073756214928d, Unit.kipft);

            //area
            case in2:
                return new MyDouble(v() / 25.4d / 25.4d, Unit.in2);

            //modulus
            case kPa_per_mm:
                return new MyDouble(v() * 1000.d, Unit.kPa_per_mm);

            case ksf_per_in:
                return new MyDouble(v() * 530.4900295220133d, Unit.ksf_per_in);

            //linear force
            case kN_per_m:
                return new MyDouble(v(), Unit.kN_per_m);

            case kip_per_ft:
                return new MyDouble(v() * 0.06852176585679176d, Unit.kip_per_ft);

            //density
            case kN_per_m3:
                return new MyDouble(v() * 1e6, Unit.kN_per_m3);
            case pcf:
                return new MyDouble(v() * 6365880.354264159d, Unit.pcf);


            default:
                return new MyDouble(1e99, mUnit);
        }

    }

    public double dblUnit(Unit unit) {
        return dblValueInUnit(unit);
    }

    public double dblVal(Unit unit) {
        return dblValueInUnit(unit);
    }


    private double dblValueInUnit(Unit unit) {


        switch (unit) {

            //length
            case mm:
                return v();
            case m:
                return v() / 1000.d;
            case in:
                return v() / 25.4d;
            case ft:
                return v() / (12.d * 25.4d);


            //force
            case N:
                return v();
            case kN:
                return v() / 1000.d;
            case lbf:
                return v() / 4448.d;
            case kip:
                return v() / 4448.d;

            //moment
            case Nm:
                return v() / 1000.d;
            case kNm:
                return v() / 1e6;
            case kipft:
                return v() / (4448.d * 12.d * 25.4d);


            //pressure
            case MPa:
                return v();
            case kPa:
                return v() * 1000.d;
            case ksi:
                return v() * 0.14503773773020923d;
            case psi:
                return v() * 145.03773773020922d;
            case psf:
                return v() * 20885.43423315013d;

            //area
            case mm2:
                return v();

            case in2:
                return v() / pow(25.4d, 2);

            //modulus
            case kPa_per_mm:
                return v() * 1.d / 1000.d;

            case ksf_per_in:
                return v() * 530.4900295220133d;

            //linear force
            case N_per_m:
                return v() * 1000.d;

            case lbf_per_ft:
                return v() * 68.52176585679177d;

            //density
            case kN_per_m3:
                return v() * 1e6;
            case pcf:
                return v() * 6365880.354264159d;


            default:
                return 1e99;
        }

    }


    public boolean isEqual(MyDouble myDouble) {

        if (Math.abs(myDouble.v() - v()) < tolerance) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * set the value and units per users chosen unit
     */

    public void sv(double value, Unit unit) {
        mValue = value; //set the value according to users chosen unit
        mUnit = unit; //set unit string according to chosen unit
    }


    public String toString() {

        return Util.r2str(mValue, 2) + String.valueOf(mUnit);
    }

    public static enum Unit {
        mm, //base unit for length
        N, //base unit for force

        //derive units
        in, m, ft, //length
        mm2, in2, //area
        kN, lbf, kip, //force
        MPa, kPa, ksi, psi, psf, //pressure
        kipft, kNm, Nm, //moment
        kPa_per_mm, ksf_per_in, //subgrade reaction
        kN_per_m, kip_per_ft, lbf_per_ft, N_per_m,  //force per unit length
        //density
        kN_per_m3, pcf
    }

    public static enum UnitType {
        SI, Imperial
    }

    public static MyDouble dMax(MyDouble num1, MyDouble num2) {
        if (num1.v() > num2.v())
            return num1;

        return num2;
    }

    public static MyDouble dMin(MyDouble num1, MyDouble num2) {
        if (num1.v() > num2.v())
            return num2;

        return num1;
    }
}
