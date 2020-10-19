import java.util.ArrayList;

/**
 * Contains all the data of the mix required for export.
 */
public class ExportData {

    private int property;
    private int mortgage;
    private double firstMonthlyRepaymentDesirable;
    private double maxMonthlyRepaymentWorstDesirable;
    private double primeDecrease;
    private double percentage;
    private double diffNperFactor;
    private int rateCustomer;
    private ArrayList<LinePaymentData> mergeListMain;
    private ArrayList<LinePaymentData> mergeListWorst;


    private String mixName;
    private double allTotalPaymentMain;
    private double allRateTotalPaymentMain;
    private double allTotalPaymentWorst;
    private double allRateTotalPaymentWorst;
    private double maxMonthRepayMain;
    private double maxMonthRepayWorst;
    private double allFirstMonthRepayment;

    private Kalatz kalatz;
    private String kalatzName;
    private double kalatzRate;
    private int kalatzNper;
    private double kalatzPv;
    private double kalatzFirstMonthRepayment;
    private double kalatzProp;
    private double kalatzTotalPaymentKalatz;
    private double kalatzTotalPaymentRateKalatz;


    private Katz katz;
    private String katzName;
    private double katzRate;
    private int katzNper;
    private double katzPv;
    private double katzFirstMonthRepayment;
    private double katzProp;
    private double katzTotalPaymentMain;
    private double katzTotalRateMain;
    private double katzTotalPaymentWorst;
    private double katzTotalRateWorst;

    private Var5CPI var5CPI;
    private String var5CpiName;
    private double var5CpiRate;
    private int var5CpiNper;
    private double var5CpiPv;
    private double var5CpiFirstMonthRepayment;
    private double var5CpiProp;
    private double var5CpiTotalPaymentMain;
    private double var5CpiTotalRateMain;
    private double var5CpiTotalPaymentWorst;
    private double var5CpiTotalRateWorst;

    private Var5Const var5Const;
    private String var5ConstName;
    private double var5ConstRate;
    private int var5ConstNper;
    private double var5ConstPv;
    private double var5ConstFirstMonthRepayment;
    private double var5ConstProp;
    private double var5ConstTotalPaymentMain;
    private double var5ConstTotalRateMain;
    private double var5ConstTotalPaymentWorst;
    private double var5ConstTotalRateWorst;

    private PrimeRoute prime;
    private String primeName;
    private double primeRate;
    private int primeNper;
    private double primePv;
    private double primeFirstMonthRepayment;
    private double primeProp;
    private double primeTotalPaymentMain;
    private double primeTotalRateMain;
    private double primeTotalPaymentWorst;
    private double primeTotalRateWorst;



    public ExportData() {

    }

    public ArrayList<LinePaymentData> getMergeListMain() {
        return mergeListMain;
    }

    public void setMergeListMain(ArrayList<LinePaymentData> mergeListMain) {
        this.mergeListMain = mergeListMain;
    }

    public ArrayList<LinePaymentData> getMergeListWorst() {
        return mergeListWorst;
    }

    public void setMergeListWorst(ArrayList<LinePaymentData> mergeListWorst) {
        this.mergeListWorst = mergeListWorst;
    }

    public Kalatz getKalatz() {
        return kalatz;
    }

    public void setKalatz(Kalatz kalatz) {
        this.kalatz = kalatz;
    }

    public Katz getKatz() {
        return katz;
    }

    public void setKatz(Katz katz) {
        this.katz = katz;
    }

    public Var5CPI getVar5CPI() {
        return var5CPI;
    }

    public void setVar5CPI(Var5CPI var5CPI) {
        this.var5CPI = var5CPI;
    }

    public Var5Const getVar5Const() {
        return var5Const;
    }

    public void setVar5Const(Var5Const var5Const) {
        this.var5Const = var5Const;
    }

    public PrimeRoute getPrime() {
        return prime;
    }

    public void setPrime(PrimeRoute prime) {
        this.prime = prime;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage() {
        this.percentage = (double) this.mortgage / this.property;
    }

    public int getProperty() {
        return property;
    }

    public void setProperty(int property) {
        this.property = property;
    }

    public int getMortgage() {
        return mortgage;
    }

    public void setMortgage(int mortgage) {
        this.mortgage = mortgage;
    }

    public double getFirstMonthlyRepaymentDesirable() {
        return firstMonthlyRepaymentDesirable;
    }

    public void setFirstMonthlyRepaymentDesirable(double firstMonthlyRepaymentDesirable) {
        this.firstMonthlyRepaymentDesirable = firstMonthlyRepaymentDesirable;
    }

    public double getMaxMonthlyRepaymentWorstDesirable() {
        return maxMonthlyRepaymentWorstDesirable;
    }

    public void setMaxMonthlyRepaymentWorstDesirable(double maxMonthlyRepaymentWorstDesirable) {
        this.maxMonthlyRepaymentWorstDesirable = maxMonthlyRepaymentWorstDesirable;
    }

    public double getPrimeDecrease() {
        return primeDecrease;
    }

    public void setPrimeDecrease(double primeDecrease) {
        this.primeDecrease = primeDecrease;
    }

    public double getDiffNperFactor() {
        return diffNperFactor;
    }

    public void setDiffNperFactor(double diffNperFactor) {
        this.diffNperFactor = diffNperFactor;
    }

    public int getRateCustomer() {
        return rateCustomer;
    }

    public void setRateCustomer(int rateCustomer) {
        this.rateCustomer = rateCustomer;
    }

    public double getVar5ConstFirstMonthRepayment() {
        return var5ConstFirstMonthRepayment;
    }

    public void setAllRateTotalPaymentMain(double allRateTotalPaymentMain) {
        this.allRateTotalPaymentMain = allRateTotalPaymentMain;
    }

    public void setAllTotalPaymentMain(double allTotalPaymentMain) {
        this.allTotalPaymentMain = allTotalPaymentMain;
    }

    public void setAllTotalPaymentWorst(double allTotalPaymentWorst) {
        this.allTotalPaymentWorst = allTotalPaymentWorst;
    }

    public void setAllRateTotalPaymentWorst(double allRateTotalPaymentWorst) {
        this.allRateTotalPaymentWorst = allRateTotalPaymentWorst;
    }

    public void setAllFirstMonthRepayment(double allFirstMonthRepayment) {
        this.allFirstMonthRepayment = allFirstMonthRepayment;
    }

    public void setMaxMonthRepayMain(double maxMonthRepayMain) {
        this.maxMonthRepayMain = maxMonthRepayMain;
    }

    public void setMaxMonthRepayWorst(double maxMonthRepayWorst) {
        this.maxMonthRepayWorst = maxMonthRepayWorst;
    }

    public void setKalatzName(String kalatzName) {
        this.kalatzName = kalatzName;
    }

    public void setKalatzRate(double kalatzRate) {
        this.kalatzRate = kalatzRate;
    }

    public void setKalatzNper(int kalatzNper) {
        this.kalatzNper = kalatzNper;
    }

    public void setKalatzPv(double kalatzPv) {
        this.kalatzPv = kalatzPv;
    }

    public void setKalatzFirstMonthRepayment(double kalatzFirstMonthRepayment) {
        this.kalatzFirstMonthRepayment = kalatzFirstMonthRepayment;
    }

    public void setKalatzProp(double kalatzProp) {
        this.kalatzProp = kalatzProp;
    }

    public void setKalatzTotalPaymentMain(double kalatzTotalPaymentMain) {
        this.kalatzTotalPaymentKalatz = kalatzTotalPaymentMain;
    }

    public String getMixName() {
        return mixName;
    }

    public void setMixName(String mixName) {
        this.mixName = mixName;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public double getKalatzTotalPaymentRateKalatz() {
        return kalatzTotalPaymentRateKalatz;
    }

    public void setKalatzTotalPaymentRateKalatz(double kalatzTotalPaymentRateKalatz) {
        this.kalatzTotalPaymentRateKalatz = kalatzTotalPaymentRateKalatz;
    }

    public void setKalatzTotalPaymentKalatz(double kalatzTotalPaymentWorst) {
        this.kalatzTotalPaymentKalatz = kalatzTotalPaymentWorst;
    }


    public void setKatzName(String katzName) {
        this.katzName = katzName;
    }

    public void setKatzRate(double katzRate) {
        this.katzRate = katzRate;
    }

    public void setKatzNper(int katzNper) {
        this.katzNper = katzNper;
    }

    public void setKatzPv(double katzPv) {
        this.katzPv = katzPv;
    }

    public void setKatzFirstMonthRepayment(double katzFirstMonthRepayment) {
        this.katzFirstMonthRepayment = katzFirstMonthRepayment;
    }

    public void setKatzProp(double katzProp) {
        this.katzProp = katzProp;
    }

    public void setKatzTotalPaymentMain(double katzTotalPaymentMain) {
        this.katzTotalPaymentMain = katzTotalPaymentMain;
    }

    public void setKatzTotalRateMain(double katzTotalRateMain) {
        this.katzTotalRateMain = katzTotalRateMain;
    }

    public void setKatzTotalPaymentWorst(double katzTotalPaymentWorst) {
        this.katzTotalPaymentWorst = katzTotalPaymentWorst;
    }

    public void setKatzTotalRateWorst(double katzTotalRateWorst) {
        this.katzTotalRateWorst = katzTotalRateWorst;
    }

    public void setVar5CpiName(String var5CpiName) {
        this.var5CpiName = var5CpiName;
    }

    public void setVar5CpiRate(double var5CpiRate) {
        this.var5CpiRate = var5CpiRate;
    }

    public void setVar5CpiNper(int var5CpiNper) {
        this.var5CpiNper = var5CpiNper;
    }

    public void setVar5CpiPv(double var5CpiPv) {
        this.var5CpiPv = var5CpiPv;
    }

    public void setVar5CpiFirstMonthRepayment(double var5CpiFirstMonthRepayment) {
        this.var5CpiFirstMonthRepayment = var5CpiFirstMonthRepayment;
    }

    public void setVar5CpiProp(double var5CpiProp) {
        this.var5CpiProp = var5CpiProp;
    }

    public void setVar5CpiTotalPaymentMain(double var5CpiTotalPaymentMain) {
        this.var5CpiTotalPaymentMain = var5CpiTotalPaymentMain;
    }

    public void setVar5CpiTotalRateMain(double var5CpiTotalRateMain) {
        this.var5CpiTotalRateMain = var5CpiTotalRateMain;
    }

    public void setVar5CpiTotalPaymentWorst(double var5CpiTotalPaymentWorst) {
        this.var5CpiTotalPaymentWorst = var5CpiTotalPaymentWorst;
    }

    public void setVar5CpiTotalRateWorst(double var5CpiTotalRateWorst) {
        this.var5CpiTotalRateWorst = var5CpiTotalRateWorst;
    }

    public void setVar5ConstName(String var5ConstName) {
        this.var5ConstName = var5ConstName;
    }

    public void setVar5ConstRate(double var5ConstRate) {
        this.var5ConstRate = var5ConstRate;
    }

    public void setVar5ConstNper(int var5ConstNper) {
        this.var5ConstNper = var5ConstNper;
    }

    public void setVar5ConstPv(double var5ConstPv) {
        this.var5ConstPv = var5ConstPv;
    }

    public void setVar5ConstFirstMonthRepayment(double varConstFirstMonthRepayment) {
        this.var5ConstFirstMonthRepayment = varConstFirstMonthRepayment;
    }

    public void setVar5ConstProp(double var5ConstProp) {
        this.var5ConstProp = var5ConstProp;
    }

    public void setVar5ConstTotalPaymentMain(double var5ConstTotalPaymentMain) {
        this.var5ConstTotalPaymentMain = var5ConstTotalPaymentMain;
    }

    public void setVar5ConstTotalRateMain(double var5ConstTotalRateMain) {
        this.var5ConstTotalRateMain = var5ConstTotalRateMain;
    }

    public void setVar5ConstTotalPaymentWorst(double var5ConstTotalPaymentWorst) {
        this.var5ConstTotalPaymentWorst = var5ConstTotalPaymentWorst;
    }

    public void setVar5ConstTotalRateWorst(double var5ConstTotalRateWorst) {
        this.var5ConstTotalRateWorst = var5ConstTotalRateWorst;
    }

    public void setPrimeName(String primeName) {
        this.primeName = primeName;
    }

    public void setPrimeRate(double primeRate) {
        this.primeRate = primeRate;
    }

    public void setPrimeNper(int primeNper) {
        this.primeNper = primeNper;
    }

    public void setPrimePv(double primePv) {
        this.primePv = primePv;
    }

    public void setPrimeFirstMonthRepayment(double primeFirstMonthRepayment) {
        this.primeFirstMonthRepayment = primeFirstMonthRepayment;
    }

    public void setPrimeProp(double primeProp) {
        this.primeProp = primeProp;
    }

    public void setPrimeTotalPaymentMain(double primeTotalPaymentMain) {
        this.primeTotalPaymentMain = primeTotalPaymentMain;
    }

    public void setPrimeTotalRateMain(double primeTotalRateMain) {
        this.primeTotalRateMain = primeTotalRateMain;
    }

    public void setPrimeTotalPaymentWorst(double primeTotalPaymentWorst) {
        this.primeTotalPaymentWorst = primeTotalPaymentWorst;
    }

    public void setPrimeTotalRateWorst(double primeTotalRateWorst) {
        this.primeTotalRateWorst = primeTotalRateWorst;
    }

    public double getAllTotalPaymentMain() {
        return allTotalPaymentMain;
    }

    public double getAllRateTotalPaymentMain() {
        return allTotalPaymentMain - (kalatzPv + katzPv + var5ConstPv + var5CpiPv + primePv);
    }

    public double getAllTotalPaymentWorst() {
        return allTotalPaymentWorst;
    }

    public double getAllRateTotalPaymentWorst() {
        return allTotalPaymentWorst - (kalatzPv + katzPv + var5ConstPv + var5CpiPv + primePv);
    }

    public double getMaxMonthRepayMain() {
        return maxMonthRepayMain;
    }

    public double getMaxMonthRepayWorst() {
        return maxMonthRepayWorst;
    }

    public String getKalatzName() {
        return kalatzName;
    }

    public double getKalatzRate() {
        return kalatzRate;
    }

    public int getKalatzNper() {
        return kalatzNper;
    }

    public double getKalatzPv() {
        return kalatzPv;
    }

    public double getKalatzProp() {
        return kalatzPv/ (kalatzPv + katzPv + var5ConstPv + var5CpiPv + primePv);
    }

    public double getKalatzTotalPaymentKalatz() {
        return kalatzTotalPaymentKalatz;
    }

    public double getKalatzTotalRateKalatz() {
        return kalatzTotalPaymentKalatz - kalatzPv;
    }

    public String getKatzName() {
        return katzName;
    }

    public double getKatzRate() {
        return katzRate;
    }

    public int getKatzNper() {
        return katzNper;
    }

    public double getKatzPv() {
        return katzPv;
    }

    public double getKatzProp() {
        return katzPv/ (kalatzPv + katzPv + var5ConstPv + var5CpiPv + primePv);    }

    public double getKatzTotalPaymentMain() {
        return katzTotalPaymentMain;
    }

    public double getKatzTotalRateMain() {
        return katzTotalPaymentMain - katzPv;
    }

    public double getKatzTotalPaymentWorst() {
        return katzTotalPaymentWorst;
    }

    public double getKatzTotalRateWorst() {
        return katzTotalPaymentWorst - katzPv;
    }

    public String getVar5CpiName() {
        return var5CpiName;
    }

    public double getVar5CpiRate() {
        return var5CpiRate;
    }

    public int getVar5CpiNper() {
        return var5CpiNper;
    }

    public double getVar5CpiPv() {
        return var5CpiPv;
    }

    public double getVar5CpiProp() {
        return var5CpiPv / (kalatzPv + katzPv + var5ConstPv + var5CpiPv + primePv);    }

    public double getVar5CpiTotalPaymentMain() {
        return var5CpiTotalPaymentMain;
    }

    public double getVar5CpiTotalRateMain() {
        return var5CpiTotalPaymentMain - var5CpiPv;
    }

    public double getVar5CpiTotalPaymentWorst() {
        return var5CpiTotalPaymentWorst;
    }

    public double getVar5CpiTotalRateWorst() {
        return var5CpiTotalPaymentWorst - var5CpiPv;
    }

    public String getVar5ConstName() {
        return var5ConstName;
    }

    public double getVar5ConstRate() {
        return var5ConstRate;
    }

    public int getVar5ConstNper() {
        return var5ConstNper;
    }

    public double getVar5ConstPv() {
        return var5ConstPv;
    }

    public double getVar5ConstProp() {
        return var5ConstPv / (kalatzPv + katzPv + var5ConstPv + var5CpiPv + primePv);    }

    public double getVar5ConstTotalPaymentMain() {
        return var5ConstTotalPaymentMain;
    }

    public double getVar5ConstTotalRateMain() {
        return var5ConstTotalPaymentMain - var5ConstPv;
    }

    public double getVar5ConstTotalPaymentWorst() {
        return var5ConstTotalPaymentWorst;
    }

    public double getVar5ConstTotalRateWorst() {
        return var5ConstTotalPaymentWorst - var5ConstPv;
    }

    public String getPrimeName() {
        return primeName;
    }

    public double getPrimeRate() {
        return primeRate;
    }

    public int getPrimeNper() {
        return primeNper;
    }

    public double getPrimePv() {
        return primePv;
    }

    public double getPrimeProp() {
        return primePv / (kalatzPv + katzPv + var5ConstPv + var5CpiPv + primePv);    }

    public double getPrimeTotalPaymentMain() {
        return primeTotalPaymentMain;
    }

    public double getPrimeTotalRateMain() {
        return primeTotalPaymentMain -primePv;
    }

    public double getPrimeTotalPaymentWorst() {
        return primeTotalPaymentWorst;
    }

    public double getPrimeTotalRateWorst() {
        return primeTotalPaymentWorst -primePv;
    }

    public double getAllFirstMonthRepayment() {
        return allFirstMonthRepayment;
    }

    public double getKalatzFirstMonthRepayment() {
        return kalatzFirstMonthRepayment;
    }

    public double getKatzFirstMonthRepayment() {
        return katzFirstMonthRepayment;
    }

    public double getVar5CpiFirstMonthRepayment() {
        return var5CpiFirstMonthRepayment;
    }

    public double getVarConstFirstMonthRepayment() {
        return var5ConstFirstMonthRepayment;
    }

    public double getPrimeFirstMonthRepayment() {
        return primeFirstMonthRepayment;
    }
}
