/**
 * the prime route.
 */
public class PrimeRoute extends BaseRoute implements route {

    public PrimeRoute(double pv,  int nper, CustomerData customerData, String name) {
        super(pv, nper, name, customerData );
    }
    public void calculate(int scenario, DataList dataList) throws Exception {

        super.clearLinePaymentLists( scenario );
        double totalPaymentTemp = 0;

        super.setRate( dataList.getPrimeData().get( 0 ).getMain() - super.getCustomerData().getPrimeDecrease());

        int nperTemp;
        double pvTemp = super.getPv();
        double ratePayment;
        double sumPayment = 0;

        for(nperTemp = super.getNper(); nperTemp > 0; nperTemp--) {
            int numOfPayment = super.getNper() - nperTemp  + 1;
            double rateTemp = 0;
            switch (scenario) {
                case 1:

                case 2:
                    rateTemp = dataList.getPrimeData().get( super.getNper() - nperTemp )
                            .getMain() - super.getCustomerData().getPrimeDecrease();
                    break;
                case 3:
                    rateTemp = dataList.getPrimeData().get( super.getNper() - nperTemp )
                            .getWorst() - super.getCustomerData().getPrimeDecrease();
                    break;
            }
            ratePayment = pvTemp * (rateTemp / 1200);
            double monthlyRepayment = calculatePmt(pvTemp , rateTemp, nperTemp);
            double fundPayment = monthlyRepayment - ratePayment;
            sumPayment = sumPayment + monthlyRepayment;

            double maxMonthRepayment = 0;
            if (monthlyRepayment > maxMonthRepayment) {
                maxMonthRepayment = monthlyRepayment;
            }
            LinePaymentData linePaymentDataTemp = new LinePaymentData( numOfPayment, pvTemp, monthlyRepayment,
                    fundPayment, ratePayment, sumPayment);
            totalPaymentTemp = totalPaymentTemp + monthlyRepayment;

            switch (scenario) {
                case 1:
/*                    maxMonthRepaymentLenient = maxMonthRepayment;
                    totalPaymentLenient = totalPaymentTemp;
                    linePaymentDataListLenient.add( linePaymentDataTemp );
                    break;*/
                case 2:
                    super.setMaxMonthRepaymentMain( maxMonthRepayment );
                    super.setTotalPaymentMain( totalPaymentTemp );
                    super.getLinePaymentDataListMain().add( linePaymentDataTemp );
                    break;
                case 3:
                    super.setMaxMonthRepaymentWorst( maxMonthRepayment );
                    super.setTotalPaymentWorst( totalPaymentTemp );
                    super.getLinePaymentDataListWorst().add( linePaymentDataTemp );
                    break;
            }

            pvTemp = (pvTemp - fundPayment);
        }
    }
}
