package sibuyas.SF;

/**
 * Created by IntelliJ IDEA.
 * User: j0sua3
 * Date: 9/19/11
 * Time: 8:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class ASSinglyReinforcedBeam {

    private double mb, mh, mcc, mAs, mMu, mgamma, mfc, mfy, mku;

    /**
     * default units are mm & N
     *
     * @param b
     * @param h
     * @param cc
     * @param fc
     * @param fy
     */


    public ASSinglyReinforcedBeam(double b, double h, double cc, double fc, double fy) {
        mb = b;
        mh = h;
        mcc = cc;
        mfc = fc;
        mfy = fy;
        mgamma = 0.85d - 0.007d * (fc - 28);
        if (mgamma < 0.65d) {
            mgamma = 0.65;
        } else if (mgamma > 0.85d) {
            mgamma = 0.85d;
        }

    }

    public Boolean isSinglyReinforced(int nbars, int bardia, int tiedia) {

        double fcef = 0.85d * mfc;
        double d = mh - mcc - tiedia - bardia / 2.d;
        double Ast = Math.PI / 4.d * bardia * bardia * nbars;
        mku = Ast * mfy / (fcef * mgamma * d * mb);

        if (mku > 0.4d) {
            return false;
        } else {
            return true;
        }
    }


    public double getphiMn(int nbars, int bardia, int tiedia) {

        double fcef = 0.85d * mfc;
        double d = mh - mcc - tiedia - bardia / 2.d;
        double Ast = Math.PI / 4.d * bardia * bardia * nbars;
        // double ku = Ast * mfy / (fcef * mgamma * d * mb);
        mku = Ast * mfy / (fcef * mgamma * d * mb);
        return (0.8d * Ast * mfy * (d - mgamma * mku * d * 0.5d));
    }

    public double getphiVnmax(int tiedia, int bardia) {

        double d = mh - mcc - tiedia - bardia / 2.d;

        return 0.2d * mfc * mb * d;
    }

    public double getphiVuc(int nbars, int bardia, int tiedia) {
        double d = mh - mcc - tiedia - bardia / 2.d;
        double beta1 = Math.max(1.1d, 1.6d - d / 1000);
        double Ast = Math.PI / 4.d * bardia * bardia * nbars;
        return (Math.pow(Ast * mfc / mb / d, 1.d / 3.d) * 0.7 * mb * d * beta1);
    }

    public double getphiVus(int nties, int tiedia, int scc, int bardia) {
        double d = mh - mcc - tiedia - bardia / 2.d;
        double Asv = Math.PI / 4d * tiedia * tiedia * nties;

        return (0.7d * Asv * mfy * d / scc);
    }
}
