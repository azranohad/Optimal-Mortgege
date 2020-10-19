/**
 * Divides the interest rates according to the customer rating.
 */
public class RankingRate {
    private int years;
    private double rate1;
    private double rate2;
    private double rate3;
    private double rate4;
    private double rate5;
    private double rate6;

    /**
     * constructor.
     */

    public RankingRate(int years, double rate1, double rate2, double rate3, double rate4, double rate5, double rate6) {
        this.years = years;
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.rate3 = rate3;
        this.rate4 = rate4;
        this.rate5 = rate5;
        this.rate6 = rate6;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public void setRate1(double rate1) {
        this.rate1 = rate1;
    }

    public void setRate2(double rate2) {
        this.rate2 = rate2;
    }

    public void setRate3(double rate3) {
        this.rate3 = rate3;
    }

    public void setRate4(double rate4) {
        this.rate4 = rate4;
    }

    public void setRate5(double rate5) {
        this.rate5 = rate5;
    }

    public void setRate6(double rate6) {
        this.rate6 = rate6;
    }

    public double getRate1() {
        return rate1;
    }

    public double getRate2() {
        return rate2;
    }

    public double getRate3() {
        return rate3;
    }

    public double getRate4() {
        return rate4;
    }

    public double getRate5() {
        return rate5;
    }

    public double getRate6() {
        return rate6;
    }
}
