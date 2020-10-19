import java.util.ArrayList;

public class Var5Const extends Var5 implements route {

    public Var5Const(double pv, int nper , CustomerData customerData, String name) {
        super(pv, nper, customerData, name, 2 );
    }
    /**
     *
     * @param scenario 1 lenient, 2 main, 3 worst.
     * @throws Exception
     */
    public void calculate(int scenario, DataList dataList) throws Exception {

        super.clearLinePaymentLists( scenario );

        double totalPaymentTemp = 0;
        super.setRate( super.rateFromList(dataList.getVariable5ConstTable()) );

        int nperTemp;
        double pvTemp = super.getPv();

        double ratePayment;
        double sumPayment = 0;

        for(nperTemp = super.getNper(); nperTemp > 0; nperTemp--) {
            int numOfPayment = super.getNper() - nperTemp + 1;
            double rateUpdate = super.monthlyRateVar( scenario, numOfPayment, dataList.getVariable5ConstTable());

            ratePayment = pvTemp * (rateUpdate / 1200);
            double monthlyRepayment = calculatePmt(pvTemp , rateUpdate, nperTemp);
            double fundPayment = monthlyRepayment - ratePayment;
            sumPayment = sumPayment + monthlyRepayment;

            double maxMonthRepayment = 0;
            if (monthlyRepayment > maxMonthRepayment) {
                maxMonthRepayment = monthlyRepayment;
            }
            LinePaymentData linePaymentDataTemp = new LinePaymentData( getNper() - nperTemp + 1 , pvTemp, monthlyRepayment,
                    fundPayment, ratePayment, sumPayment);
            totalPaymentTemp = totalPaymentTemp + monthlyRepayment;


            switch (scenario) {
                case 1:
/*                    maxMonthRepaymentLenient = maxMonthRepayment;
                    totalPaymentLenient = totalPaymentTemp;
                    linePaymentVar5ConstDataListLenient.add( linePaymentDataTemp );
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
            if (super.getPv() == 0) {
                return;
            }
        }
    }
}
