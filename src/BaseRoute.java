
import java.util.ArrayList;

/**
 * The parent class of all routs.
 */
public abstract class BaseRoute implements route {

    private CustomerData customerData;

    private String name;
    private int nper;
    private double pv;
    private double rate;
    private double propPv;

    private double maxMonthRepaymentMain = 0;
    private double maxMonthRepaymentWorst = 0;

    private double totalPaymentMain = 0;
    private double totalPaymentWorst = 0;

    private ArrayList<LinePaymentData> linePaymentDataListMain;
    private ArrayList<LinePaymentData> linePaymentDataListWorst;

    /**
     * constructor.
     * @param pv the Loan amount.
     * @param nper Number of payments
     * @param name the name route.
     * @param customerData Customer data.
     */
    public BaseRoute(double pv, int nper, String name, CustomerData customerData ) {
        this.name = name;
        this.nper = nper;
        this.pv = pv;
        this.linePaymentDataListMain = new ArrayList<LinePaymentData>();
        this.linePaymentDataListWorst = new ArrayList<LinePaymentData>();
        this.customerData = customerData;
        this.propPv = pv / customerData.getMortgage();
    }

    // base  Pmt calculate.
    public double calculatePmt ( double pv, double rate, double nper){
        double rateTemp = rate / 1200;
        double pmt = pv * (rateTemp / (1 - Math.pow( (1 + rateTemp), (-1) * nper )));
        return pmt;
    }

    /**
     * deletes the information in the lists before calculating the route.
     * @param scenario 1 lenient,  main, 3 worst.
     */
    public void clearLinePaymentLists(int scenario) {
        if (scenario == 2) {
            linePaymentDataListMain.clear();
            linePaymentDataListWorst.clear();
        } else if (scenario == 3) {
            linePaymentDataListWorst.clear();
        }
    }

    /**
     * Performs the route calculation.
     * Each Class is promised to exercise on its own.
     * @param scenario scenario.
     * @param dataList all list of rate and cpi.
     * @throws Exception
     */
    public abstract void calculate(int scenario, DataList dataList) throws Exception;

    /**
     *
     * @return the first repay.
     * @throws Exception
     */
    public double firstMonthRepay() throws Exception {

        if (linePaymentDataListMain.size() != 0) {
            return this.getLinePaymentDataListMain().get( 0 ).getMonthlyRepayment();
        } else {
            return 0;
        }
    }

    /**
     *
     * @param rankingRate
     * @return Returns the interest rate to the ranking customer.
     * */
    public double rankingRate(RankingRate rankingRate) {
        double rateTemp;
        switch (customerData.getRateCustomer()) {
            case 1:
                rateTemp = rankingRate.getRate1();
                break;
            case 2:
                rateTemp = rankingRate.getRate2();
                break;
            case 3:
                rateTemp = rankingRate.getRate3();
                break;
            case 4:
                rateTemp = rankingRate.getRate4();
                break;
            case 5:
                rateTemp = rankingRate.getRate5();
                break;
            case 6:
            default:
                rateTemp = rankingRate.getRate6();
        }
        return rateTemp;
    }

    /**
     *
     * @param rateList rate list per route.
     * @return the appropriate interest rate to the rating that takes the payments.
     */
    public double rateFromList( ArrayList<RateLineFromURL> rateList ) {

        for (int i = rateList.size() - 1; i >= 0; i--) {
            if ( (nper / 12) >= rateList.get( i ).getYears()) {
                if (customerData.getPercentage() < 0.45) {
                    rate = rankingRate( rateList.get( i ).getPercentUntil45() );
                    break;
                } else if (customerData.getPercentage() < 0.60) {
                    rate = rankingRate( rateList.get( i ).getPercentUntil60() );
                    break;
                } else if (customerData.getPercentage() >= 0.60) {
                    rate = rankingRate( rateList.get( i ).getPercentAbove61() );
                    break;
                }
            }
        }
        return this.rate;
    }

    @Override
    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public CustomerData getCustomerData() {
        return customerData;
    }

    public int getNper() {
        return nper;
    }

    public void setNper(int nper) {
        this.nper = nper;
    }

    public double getPv() {
        return pv;
    }

    public void setPv(double pv) {
        this.pv = pv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPropPv() {
        return propPv;
    }

    public void setPropPv(double propPv) {
        this.propPv = propPv;
    }

    @Override
    public double getMaxMonthRepaymentMain() {
        return maxMonthRepaymentMain;
    }

    public void setMaxMonthRepaymentMain(double maxMonthRepaymentMain) {
        this.maxMonthRepaymentMain = maxMonthRepaymentMain;
    }

    public double getMaxMonthRepaymentWorst() {
        return maxMonthRepaymentWorst;
    }

    public void setMaxMonthRepaymentWorst(double maxMonthRepaymentWorst) {
        this.maxMonthRepaymentWorst = maxMonthRepaymentWorst;
    }

    public double getTotalPaymentMain() {
        return totalPaymentMain;
    }

    public void setTotalPaymentMain(double totalPaymentMain) {
        this.totalPaymentMain = totalPaymentMain;
    }

    public double getTotalPaymentWorst() {
        return totalPaymentWorst;
    }

    public void setTotalPaymentWorst(double totalPaymentWorst) {
        this.totalPaymentWorst = totalPaymentWorst;
    }

    public double getTotalRatePaymentMain() {
        return this.totalPaymentMain - this.pv;
    }


    public double getTotalRatePaymentWorst() {
        return this.totalPaymentWorst - this.pv;
    }


    public void setCustomerData(CustomerData customerData) {
        this.customerData = customerData;
    }

    public ArrayList<LinePaymentData> getLinePaymentDataListMain() {
        return linePaymentDataListMain;
    }

    public void setLinePaymentDataListMain(ArrayList<LinePaymentData> linePaymentDataListMain) {
        this.linePaymentDataListMain = linePaymentDataListMain;
    }

    public ArrayList<LinePaymentData> getLinePaymentDataListWorst() {
        return linePaymentDataListWorst;
    }

    public void setLinePaymentDataListWorst(ArrayList<LinePaymentData> linePaymentDataListWorst) {
        this.linePaymentDataListWorst = linePaymentDataListWorst;
    }

    @Override
    public void printData() throws Exception {
        System.out.print("\n" + name + " - first month repayment - ");
        System.out.printf("%.2f" , firstMonthRepay());
        System.out.print("\n" + name + " - max month repayment main - ");
        System.out.printf("%.2f" , maxMonthRepaymentMain);
        System.out.print("\n" + name + " - total payment main - ");
        System.out.printf("%.2f" , totalPaymentMain);
        System.out.print("\n" + name + " - total rate payment main - ");
        System.out.printf("%.2f" , getTotalRatePaymentMain());
        System.out.print("\n" + name + " - max month repayment worst - ");
        System.out.printf("%.2f" , maxMonthRepaymentWorst);
        System.out.print("\n" + name + " - total payment main worst- ");
        System.out.printf("%.2f" , totalPaymentWorst);
        System.out.print("\n" + name + " - total rate payment main worst- ");
        System.out.printf("%.2f" , getTotalRatePaymentWorst());
    }
}


