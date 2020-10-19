/**
 * The information required for each payment.
 */
public class LinePaymentData {

    private int numberPayment;
    private double fundPv; //The balance of the fund.
    private double monthlyRepayment;
    private double fundPayment;//At the expense of the fund.
    private double ratePayment;//At the expense of the rate.
    private double sumPayment; //total payment.

    /**
     * constructor.
     * @param numberPayment
     * @param fundPv
     * @param monthlyRepayment
     * @param fundPayment
     * @param ratePayment
     * @param sumPayment
     */
    public LinePaymentData(int numberPayment,double fundPv, double monthlyRepayment, double fundPayment
            , double ratePayment, double sumPayment) {
        this.numberPayment = numberPayment;
        this.fundPv = fundPv;
        this.monthlyRepayment = monthlyRepayment;
        this.fundPayment = fundPayment;
        this.ratePayment = ratePayment;
        this.sumPayment = sumPayment;
    }

    public void setNumberPayment(int numberPayment) {
        this.numberPayment = numberPayment;
    }

    public void setMonthlyRepayment(double monthlyRepayment) {
        this.monthlyRepayment = monthlyRepayment;
    }

    public void setFundPayment(double fundPayment) {
        this.fundPayment = fundPayment;
    }

    public void setRatePayment(double ratePayment) {
        this.ratePayment = ratePayment;
    }

    public void setSumPayment(double sumPayment) {
        this.sumPayment = sumPayment;
    }

    public int getNumberPayment() {
        return numberPayment;
    }

    public double getMonthlyRepayment() {
        return monthlyRepayment;
    }

    public double getFundPayment() {
        return fundPayment;
    }

    public double getRatePayment() {
        return ratePayment;
    }

    public double getSumPayment() {
        return sumPayment;
    }

    public double getFundPv() {
        return fundPv;
    }

    public void setFundPv(double fundPv) {
        this.fundPv = fundPv;
    }
}
