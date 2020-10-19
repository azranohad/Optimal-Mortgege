import java.util.ArrayList;

/**
 * the class parent of var5Cpi and var5Const.
 */
public abstract class Var5 extends BaseRoute implements route {

    private double addedRate5Main;
    private double addedRate5Worst;
    private double addedRate10Main;
    private double addedRate10Worst;
    private double addedRate15Main;
    private double addedRate15Worst;
    private double addedRate20Main;
    private double addedRate20Worst;
    private double addedRate25Main;
    private double addedRate25Worst;


    /**
     * constructor, creaqte value of added rate according the route (cpi or nonCpi).
     * @param pv
     * @param nper
     * @param customerData
     * @param name
     * @param typeVar5 1 Cpi, 2 nonCpi.
     */
    public Var5(double pv, int nper, CustomerData customerData, String name, int typeVar5) {
        super(pv, nper, name, customerData);

        if (typeVar5 == 1) {
            addedRate5Main = 0.74;
            addedRate5Worst = 1;
            addedRate10Main = 1.52;
            addedRate10Worst = 2 ;
            addedRate15Main = 2.07;
            addedRate15Worst = 3 ;
            addedRate20Main = 2.37;
            addedRate20Worst = 3 ;
            addedRate25Main = 2.51 ;
            addedRate25Worst = 3 ;
        } else if (typeVar5 == 2) {
            addedRate5Main = 0.79;
            addedRate5Worst = 1;
            addedRate10Main = 1.55;
            addedRate10Worst = 2 ;
            addedRate15Main = 2.09;
            addedRate15Worst = 3 ;
            addedRate20Main = 2.4;
            addedRate20Worst = 3 ;
            addedRate25Main = 2.57 ;
            addedRate25Worst = 3 ;
        }
    }

    /**
     * Returns the interest value according to the payment number.
     * @param scenario
     * @param numOfPayment
     * @param rateList
     * @return
     */
    public double monthlyRateVar(int scenario , int numOfPayment, ArrayList<RateLineFromURL> rateList) {

        double rateTemp = rateFromList(rateList);
        double monthlyRate = rateTemp;

        double monthlyRate60Main = monthlyRate + addedRate5Main;
        double monthlyRate120Main = monthlyRate + addedRate10Main;
        double monthlyRate180Main = monthlyRate + addedRate15Main;
        double monthlyRate240Main = monthlyRate + addedRate20Main;
        double monthlyRate300Main = monthlyRate + addedRate25Main;
        double monthlyRate60Worst = monthlyRate + addedRate5Worst;
        double monthlyRate120Worst = monthlyRate + addedRate10Worst;
        double monthlyRate180Worst = monthlyRate + addedRate15Worst;
        double monthlyRate240Worst = monthlyRate + addedRate20Worst;
        double monthlyRate300Worst = monthlyRate + addedRate25Worst;

        switch (scenario) {
            case 1:
                if (numOfPayment <= 60) {
                    return monthlyRate;
                } else if ((numOfPayment > 60) && (numOfPayment <= 120)) {
                    return monthlyRate;
                } else if ((numOfPayment > 120) && (numOfPayment <= 180)){
                    return monthlyRate;
                } else if ((numOfPayment > 180) && (numOfPayment <= 240)){
                    return monthlyRate;
                } else if ((numOfPayment > 240) && (numOfPayment <= 300)){
                    return monthlyRate;
                } else if ((numOfPayment > 300) && (numOfPayment <= 360)) {
                    return monthlyRate;
                }
                break;
            case 2:
                if (numOfPayment <= 60) {
                    return monthlyRate;
                } else if ((numOfPayment > 60) && (numOfPayment <= 120)) {
                    return monthlyRate60Main;
                } else if ((numOfPayment > 120) && (numOfPayment <= 180)){
                    return monthlyRate120Main;
                } else if ((numOfPayment > 180) && (numOfPayment <= 240)){
                    return monthlyRate180Main;
                } else if ((numOfPayment > 240) && (numOfPayment <= 300)){
                    return monthlyRate240Main;
                } else if ((numOfPayment > 300) && (numOfPayment <= 360)) {
                    return monthlyRate300Main;
                }
                break;
            case 3:
                if (numOfPayment <= 60) {
                    return monthlyRate;
                } else if ((numOfPayment > 60) && (numOfPayment <= 120)) {
                    return monthlyRate60Worst;
                } else if ((numOfPayment > 120) && (numOfPayment <= 180)){
                    return monthlyRate120Worst;
                } else if ((numOfPayment > 180) && (numOfPayment <= 240)){
                    return monthlyRate180Worst;
                } else if ((numOfPayment > 240) && (numOfPayment <= 300)){
                    return monthlyRate240Worst;
                } else if ((numOfPayment > 300) && (numOfPayment <= 360)) {
                    return monthlyRate300Worst;
                }
                break;
        }
        return monthlyRate;
    }
}


