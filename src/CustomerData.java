public class CustomerData {
    private int property;
    private int mortgage;
    private double firstMonthlyRepayment; //first repay desirable.
    private double maxMonthlyRepaymentWorst; //Maximum repay is desirable in the worst case.
    private double percentage; //percentage of funding.
    private int rateCustomer; //Customer rating by bank
    private double primeDecrease;  //The reduction from the prime route.
    private double diffNperFactor; //Relative difference between max nper to min nper.
    // 0 - all nper is equal. 1 - there is no limit.

    public CustomerData(int property, int mortgage, double firstMonthlyRepayment, double maxMonthlyRepaymentWorst, int rateCustomer , double primeDecrease, double diffNperFactor) {
        this.property = property;
        this.mortgage = mortgage;
        this.percentage =  (double) this.mortgage / this.property;
        this.firstMonthlyRepayment = firstMonthlyRepayment;
        this.maxMonthlyRepaymentWorst = maxMonthlyRepaymentWorst;
        this.rateCustomer = rateCustomer;
        this.primeDecrease = primeDecrease;
        this.diffNperFactor = diffNperFactor;
    }

    public CustomerData(CustomerData customerData) {
        this.property = customerData.getProperty();
        this.mortgage = customerData.getMortgage();
        this.percentage =  (double) this.mortgage / this.property;
        this.firstMonthlyRepayment = customerData.getFirstMonthlyRepayment();
        this.maxMonthlyRepaymentWorst = customerData.getMaxMonthlyRepaymentWorst();
        this.rateCustomer = customerData.getRateCustomer();
        this.primeDecrease = customerData.getPrimeDecrease();
        this.diffNperFactor = customerData.getDiffNperFactor();
    }

    public void setRateCustomer(int rateCustomer) {
        this.rateCustomer = rateCustomer;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public void setProperty(int property) {
        this.property = property;
    }

    public void setFirstMonthlyRepayment(double firstMonthlyRepayment) {
        this.firstMonthlyRepayment = firstMonthlyRepayment;
    }

    public void setMaxMonthlyRepaymentWorst(double maxMonthlyRepaymentWorst) {
        this.maxMonthlyRepaymentWorst = maxMonthlyRepaymentWorst;
    }

    public void setMortgage(int mortgage) {
        this.mortgage = mortgage;
    }

    public void setDiffNperFactor(double diffNperFactor) {
        this.diffNperFactor = diffNperFactor;
    }

    public void setPrimeDecrease(double primeDecrease) {
        this.primeDecrease = primeDecrease;
    }

    public double getFirstMonthlyRepayment() {
        return firstMonthlyRepayment;
    }

    public double getMaxMonthlyRepaymentWorst() {
        return maxMonthlyRepaymentWorst;
    }

    public int getMortgage() {
        return mortgage;
    }

    public int getProperty() {
        return property;
    }

    public double getPercentage() {
        return percentage;
    }

    public int getRateCustomer() {
        return rateCustomer;
    }

    public double getDiffNperFactor() {
        return diffNperFactor;
    }

    public double getPrimeDecrease() {
        return primeDecrease;
    }
}
