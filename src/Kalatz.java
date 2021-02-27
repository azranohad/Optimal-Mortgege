import java.util.ArrayList;

/**
 * the kalatz route.
 */
public class Kalatz extends BaseRoute implements route {

    //kalatz constructor.
    public Kalatz(double pv, int nper, CustomerData customerData, String name) {
        super( pv, nper, name, customerData );
    }

    public void calculate(int scenario, DataList dataList) throws Exception {
        super.clearLinePaymentLists( scenario );
        double rateTemp = rateFromList( dataList.getKalatzTable() );
        super.setRate( rateTemp );

        double monthlyRate = rateTemp / 1200;
        int nperTemp = super.getNper();
        double pvTemp = super.getPv();
        double ratePayment;
        double sumPayment = 0;

        //Loop of all payments.
        for (; nperTemp > 0; nperTemp--) {

            ratePayment = pvTemp * monthlyRate;
            double monthlyRepayment = calculatePmt( pvTemp, rateTemp, nperTemp );
            double fundPayment = monthlyRepayment - ratePayment;
            sumPayment = sumPayment + monthlyRepayment;
            if (monthlyRepayment > super.getMaxMonthRepaymentMain()) {
                super.setMaxMonthRepaymentMain( monthlyRepayment );
            }
            super.setTotalPaymentMain( super.getTotalRatePaymentMain() + monthlyRepayment );

            pvTemp = (pvTemp - fundPayment);
            LinePaymentData linePaymentDataTemp = new LinePaymentData( super.getNper() - nperTemp + 1, pvTemp, monthlyRepayment,
                    fundPayment, ratePayment, sumPayment );
            super.getLinePaymentDataListMain().add( linePaymentDataTemp );
            if (super.getPv() == 0) {
                return;
            }
        }
        super.setTotalPaymentMain( super.getLinePaymentDataListMain()
                .get( super.getNper() - 1 ) .getSumPayment());
    }
    @Override
    public double getMaxMonthRepaymentWorst() {
        return this.getMaxMonthRepaymentMain();
    }
    @Override
    public double getTotalPaymentWorst() {
        return this.getTotalPaymentMain();
    }
    @Override
    public double getTotalRatePaymentWorst() {
        return this.getTotalPaymentMain() - this.getPv();
    }
    @Override
    public ArrayList<LinePaymentData> getLinePaymentDataListWorst() {
        return this.getLinePaymentDataListMain();
    }
}

