public interface route {

    double calculatePmt(double pv, double rate, double nper);

    double getPv();

    int getNper();

    double getRate();

    double getMaxMonthRepaymentMain();

    void printData() throws Exception;


}
