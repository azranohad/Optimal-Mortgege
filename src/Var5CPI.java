import java.util.ArrayList;
import java.util.List;

public class Var5CPI extends Var5 implements route {

    private double numOfSplit;

    public Var5CPI(double pv, int nper, CustomerData customerData, String name) {
        super(pv, nper, customerData, name, 1 );
    }

    /**
     *
     * @param scenario 1 lenient, 2 main, 3 worst.
     * @throws Exception
     */
    public void calculate(int scenario, DataList dataList) throws Exception {

        super.clearLinePaymentLists( scenario );

        double totalPaymentTemp = 0;
        double rateTemp = rateFromList(dataList.getVariable5CPITable());
        super.setRate( rateTemp );

        int nperTemp = getNper();
        double pvTemp = 0;
        switch (scenario) {
            case 1:
/*                this.linePaymentVar5CPIDataListLenient = new ArrayList<LinePaymentData>();
                pvTemp = this.pv * (1 + (dataList.getCpiData().get( 0 ).getLenient() / 1200));
                break;*/
            case 2:
                pvTemp = super.getPv() * (1 + (dataList.getCpiData().get(0).getMain() / 1200));
                break;
            case 3:
                pvTemp = super.getPv() * (1 + (dataList.getCpiData().get(0).getWorst() / 1200));
                break;
        }
        double ratePayment;
        double sumPayment = 0;

        for(; nperTemp > 0; nperTemp--) {
            int numOfPayment = getNper() - nperTemp + 1;
            double rateUpdate = monthlyRateVar( scenario, numOfPayment, dataList.getVariable5CPITable());

            ratePayment = pvTemp * (rateUpdate / 1200);
            double monthlyRepayment = calculatePmt(pvTemp , rateUpdate, nperTemp);
            double fundPayment = monthlyRepayment - ratePayment;
            sumPayment = sumPayment + monthlyRepayment;

            double nextMonthCpi = 0;
            double maxMonthRepayment = 0;
            if (monthlyRepayment > maxMonthRepayment) {
                maxMonthRepayment = monthlyRepayment;
            }
            LinePaymentData linePaymentDataTemp = new LinePaymentData( super.getNper() - nperTemp + 1 , pvTemp, monthlyRepayment,
                    fundPayment, ratePayment, sumPayment);
            totalPaymentTemp = totalPaymentTemp + monthlyRepayment;

            switch (scenario) {
                case 1:
/*                    nextMonthCpi = (dataList.getCpiData().get( numOfPayment ).getLenient()) / 1200;
                    maxMonthRepaymentLenient = maxMonthRepayment;
                    totalPaymentLenient = totalPaymentTemp;
                    linePaymentVar5CPIDataListLenient.add( linePaymentDataTemp );*/
                    break;
                case 2:
                    nextMonthCpi = (dataList.getCpiData().get( numOfPayment ).getMain()) / 1200;
                    setMaxMonthRepaymentMain( maxMonthRepayment );
                    setTotalPaymentMain( totalPaymentTemp );
                    getLinePaymentDataListMain().add( linePaymentDataTemp );
                    break;
                case 3:
                    nextMonthCpi = (dataList.getCpiData().get( numOfPayment ).getWorst()) / 1200;
                    setMaxMonthRepaymentWorst( maxMonthRepayment );
                    setTotalPaymentWorst( totalPaymentTemp );
                    getLinePaymentDataListWorst().add( linePaymentDataTemp );
                    break;
            }

            pvTemp = (pvTemp - fundPayment) * (1 + nextMonthCpi );
            if (super.getPv() == 0) {
                return;
            }
        }
    }
}
