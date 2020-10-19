/**
 * For each payment number of interest or consumer price index there are three values:
 * lenient, main and worst.
 */

public class ThreeLevel {
    private int nper;
    private double main;
    private double worst;
    private double lenient;


    public double getLenient() {
        return lenient;
    }

    public double getMain() {
        return main;
    }

    public double getWorst() {
        return worst;
    }

    public int getNper() {
        return nper;
    }

    public void setLenient(double lenient) {
        this.lenient = lenient;
    }

    public void setMain(double main) {
        this.main = main;
    }

    public void setNper(int nper) {
        this.nper = nper;
    }

    public void setWorst(double worst) {
        this.worst = worst;
    }
}
