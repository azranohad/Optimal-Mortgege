/**
 * the katz route.
 */
public class Katz extends BaseRoute implements route {

    private double numOfSplit;


    //katz constructor.
    public Katz(double pv, int nper, CustomerData customerData, String name) {
        super(pv, nper, name, customerData);
    }

    public void calculate(int scenario, DataList dataList) throws Exception {

        super.clearLinePaymentLists( scenario );

        double totalPaymentTemp = 0;
        double rateTemp = rateFromList(dataList.getKatzTable());
        super.setRate( rateTemp );


        int nperTemp;
        double pvTemp = 0;
        switch (scenario) {
            case 1:
                /*
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

        for(nperTemp = super.getNper(); nperTemp > 0; nperTemp--) {
            int numOfPayment = super.getNper() - nperTemp  + 1;

            ratePayment = pvTemp * (rateTemp / 1200);
            double monthlyRepayment = calculatePmt(pvTemp , rateTemp, nperTemp);
            double fundPayment = monthlyRepayment - ratePayment;
            sumPayment = sumPayment + monthlyRepayment;
            double nextMonthCpi = 0;
            double maxMonthRepayment = 0;
            if (monthlyRepayment > maxMonthRepayment) {
                maxMonthRepayment = monthlyRepayment;
            }
            LinePaymentData linePaymentDataTemp = new LinePaymentData( numOfPayment, pvTemp, monthlyRepayment,
                    fundPayment, ratePayment, sumPayment);
            totalPaymentTemp = totalPaymentTemp + monthlyRepayment;


            switch (scenario) {
                case 1:
/*                    monthlyCpi = dataList.getCpiData().get(numOfPayment - 1).getLenient() / 1200;
                    nextMonthCpi = (dataList.getCpiData().get( numOfPayment ).getLenient()) / 1200;
                    totalPaymentLenient = totalPaymentTemp;
                    linePaymentDataListLenient.add( linePaymentDataTemp );
                    break;*/
                case 2:
                    nextMonthCpi = (dataList.getCpiData().get( numOfPayment ).getMain()) / 1200;
                    super.setMaxMonthRepaymentMain( maxMonthRepayment );
                    super.setTotalPaymentMain( totalPaymentTemp );
                    super.getLinePaymentDataListMain().add( linePaymentDataTemp );
                    break;
                case 3:
                    nextMonthCpi = (dataList.getCpiData().get( numOfPayment ).getWorst()) / 1200;
                    super.setMaxMonthRepaymentWorst( maxMonthRepayment );
                    super.setTotalPaymentWorst( totalPaymentTemp );
                    super.getLinePaymentDataListWorst().add(linePaymentDataTemp);
                    break;
            }

            pvTemp = (pvTemp - fundPayment) * (1 + nextMonthCpi );
            if (super.getPv() == 0) {
                return;
            }
        }
    }
}
