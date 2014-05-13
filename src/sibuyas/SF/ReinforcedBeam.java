package sibuyas.SF;

/**
 * Created by IntelliJ IDEA.
 * User: j0sua3
 * Date: 9/19/11
 * Time: 9:44 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ReinforcedBeam {


    double getphiMn(int nbars, int bardia);

    double getphiVn(int nbars, int bardia);

    double getTieSpacing(double Vu);

    double getBendingReo(double Mu);
}
