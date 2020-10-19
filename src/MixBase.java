import java.util.ArrayList;
import java.util.List;

/**
 * The mixer calculate.
 *
 * Finds the optimal layout according to the constraints for the desired mix
 */
public class MixBase {

    private boolean conditions = false; // Meets a threshold condition

    //The routes in the optimal distribution.
    private Kalatz kalatzOpt;
    private Katz katzOpt;
    private Var5CPI var5CPIOpt;
    private Var5Const var5ConstOpt;
    private PrimeRoute primeOpt;


    private ArrayList<LinePaymentData> linePaymentDataMergeMain;
    private ArrayList<LinePaymentData> linePaymentDataMergeWorst;


    private ArrayList<LinePaymentData> linePaymentDataMergeMainOpt;
    private ArrayList<LinePaymentData> linePaymentDataMergeWorstOpt;



    private double firstMonthlyRepaymentOpt;
    private double maxMonthRepaymentMainOpt = 0;
    private double totalPaymentMain = 0;
    private double maxMonthRepaymentWorstOpT = 0;
    private double totalPaymentWorst = 0;

    private final String name;
    private double part1; // kalatz
    private double part2; // katz
    private double part3; // var 5 cpi
    private double part4; // var 5 const


    private Constraints constraints;
    private CustomerData customerData;
    private final double firstMonthlyRepaymentDesirable;
    private final double maxMonthRepaymentWorstDesirable;

    private final double mortgage;
    private double percentage;
    private final double primeDecrease;
    private int rateCustomer;


    private final DataList dataList;



    /**
     * constructor.
     * @param part1 the kalatz part.
     * @param part2 the katz part.
     * @param part3 the var 5 cpi part.
     * @param part4 the car 5 const part.
     */
    public MixBase(String name, Constraints constraints, double part1, double part2, double part3, double part4, double primeDecrease,
                   DataList dataList) {
        this.constraints = constraints;
        this.customerData = constraints.getCustomerData();
        this.firstMonthlyRepaymentDesirable = customerData.getFirstMonthlyRepayment();
        this.maxMonthRepaymentWorstDesirable = customerData.getMaxMonthlyRepaymentWorst();
        this.mortgage = customerData.getMortgage();


        this.name = name;
        this.part1 = part1;
        this.part2 = part2;
        this.part3 = part3;
        this.part4 = part4;

        this.primeDecrease = primeDecrease;

        this.dataList = dataList;

        this.linePaymentDataMergeMain  = new ArrayList<>();
        this.linePaymentDataMergeWorst  = new ArrayList<>();
        this.linePaymentDataMergeMainOpt = new ArrayList<>();
        this.linePaymentDataMergeWorstOpt = new ArrayList<>();
    }

    /**
     *
     * @param num1
     * @param num2
     * @return return the max value from two double.
     */
    static int max2(int num1, int num2) {
        return Math.max( num1, num2 );
    }
    /**
     * @param num1 receives numbers
     * @param num2 receives numbers
     * @param num3 receives numbers
     * @return The biggest number
     */
    static int max3(int num1, int num2, int num3) {
        if (num1 > num2) {
            return max2(num1, num3);
        }
        return max2(num2, num3);
    }

    /**
     * checks whether the difference between the number of payments does not exceed the required ratio.
     * @return
     */
    public boolean nperDiff (int nperRoute1, int nperRoute2, int nperRoute3, int nperRoute4, int nperRoute5 ) {
        int maxNper = max2( max2( nperRoute4, nperRoute5 ), max3( nperRoute1, nperRoute2, nperRoute3 ) );
        double maxDiff = maxNper * this.customerData.getDiffNperFactor();
        int[] nperArray = new int[]{nperRoute1, nperRoute2, nperRoute3, nperRoute4, nperRoute5};

        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 5; j++) {
                if ((Math.abs( nperArray[i] - nperArray[j] ) > maxDiff)
                && (nperArray[i] != 0) && (nperArray[j] != 0)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * create new route.
     * @return
     * @throws Exception
     */
    public BaseRoute newRoute(double pv, int nper, int typeRoute )throws Exception {

        BaseRoute newRoute = null;

        switch (typeRoute) {
            case 1:
                newRoute = new Kalatz( pv, nper, customerData, "Kalatz"  );
                if (pv > 0) {
                    newRoute.calculate(0 ,dataList);
                    return newRoute;
                }
                break;
            case 2:
                newRoute = new Katz( pv, nper, customerData, "Katz" );
                break;
            case 3:
                newRoute = new Var5CPI( pv, nper, customerData, "Var5Cpi" );
                break;
            case 4 :
                newRoute = new Var5Const( pv, nper, customerData, "Var5Const"  );
                break;
            case 5:
                newRoute = new PrimeRoute( pv, nper, customerData, "Prime");
                break;
        }
        if (pv > 0) {
            if (newRoute != null) {
                newRoute.calculate( 2,dataList );
            }
        }
        return newRoute;
    }

    /**
     * search if the route exists in the dynamic tables
     * return the route data from dynamic table or create and calculate new route.
     * @param typeRoute 1 kalatz, 2 katz, 3 var5cpi, 4 var const, 5 prime.
     * @return
     */
    public BaseRoute searchRoute(double pv, int nper, double rate, int typeRoute, DynamicTables dynamicTables ) throws Exception {
        double epsilon = Math.pow( 10, -4 );
        BaseRoute routeReturn;
        ArrayList<PvSplit> routeList = null;
        switch (typeRoute) {
            case 1:
                routeList = dynamicTables.getKalatzList();
                break;
            case 2:
                routeList = dynamicTables.getKatzList();
                break;
            case 3:
                routeList = dynamicTables.getVar5CpiList();
                break;
            case 4 :
                routeList = dynamicTables.getVar5ConstList();
                break;
            case 5:
                routeList = dynamicTables.getPrimeList();
                break;
        }

        //search pv key.
        if (routeList != null) {
            for(PvSplit pvSplit : routeList) {
                if (Math.abs( pv - pvSplit.getPvKey() ) < epsilon) {
                    //search nper key.
                    for (NperSplit nperSplit : pvSplit.getPvCommon()) {
                        if (Math.abs( nper - nperSplit.getNperKey() ) < epsilon) {
                            //search rate.
                            for (BaseRoute route : nperSplit.getBaseRouteList()) {
                                if (Math.abs( rate - route.getRate() ) < epsilon) {
                                    routeReturn = route;
                                    return routeReturn;
                                }
                            }
                            //not found rate.
                            routeReturn = newRoute( pv,nper, typeRoute );
                            if (pv != 0) {
                                nperSplit.getBaseRouteList().add(routeReturn );
                            }
                            return routeReturn;
                        }
                    }
                    //not found nper key.
                    routeReturn = newRoute( pv,nper, typeRoute );
                    if (pv != 0) {
                        pvSplit.getPvCommon().add( new NperSplit(routeReturn) );
                    }
                    //clear dynamic list, but no prime.
                    if ((pvSplit.getPvCommon().size() > constraints.getSetting().getMaximumKatzNperSplit()) && (typeRoute != 5)) {
                        pvSplit.getPvCommon().remove( 0 );
                    }
                    return routeReturn;
                }
            }
        }
        //not found pv Key.
        routeReturn = newRoute( pv,nper, typeRoute );
        if (pv != 0) {
            routeList.add( new PvSplit( routeReturn ) );
        }
        return routeReturn;
    }

    /**
     * Finds the optimal payment layout for the selected mix.
     * @throws Exception
     */
    public void optimizationAllRoute(DynamicTables dynamicTables) throws Exception {

        double minimalTotalPayment = this.mortgage * 2;
        for (int nperKalatz = 96; nperKalatz <= 360; nperKalatz = nperKalatz + 12) {
            Kalatz kalatz = new Kalatz( mortgage * part1, nperKalatz, customerData, "kalatz" );
            kalatz = (Kalatz) searchRoute( mortgage * part1, nperKalatz, kalatz.rateFromList( dataList.getKalatzTable() ), 1, dynamicTables );
            if (kalatz.firstMonthRepay() > firstMonthlyRepaymentDesirable) { continue;
            }
            for (int nperKatz = 96; nperKatz <= 360; nperKatz = nperKatz + 12) {
                Katz katz = new Katz( mortgage * part2, nperKatz, customerData, "katz" );
                if(!nperDiff( 0, 0, 0, nperKalatz, nperKatz )) {
                    continue;
                } else {
                    katz = (Katz) searchRoute( mortgage * part2, nperKatz, katz.rateFromList( dataList.getKatzTable() ), 2, dynamicTables );
                    if (kalatz.firstMonthRepay()
                            + katz.firstMonthRepay() > firstMonthlyRepaymentDesirable) {
                        continue;
                    }
                }
                for (int nperVariable5Cpi = 60; nperVariable5Cpi <= 360; nperVariable5Cpi = nperVariable5Cpi + 60) {
                    Var5CPI var5CPI = new Var5CPI( mortgage * part3, nperVariable5Cpi, customerData, "variable5Cpi" );
                    if (!nperDiff( 0, 0, nperVariable5Cpi, nperKalatz, nperKatz )) {
                        continue;
                    } else {
                        var5CPI = (Var5CPI) searchRoute( mortgage * part3, nperVariable5Cpi,
                                var5CPI.rateFromList( dataList.getVariable5CPITable() ), 3, dynamicTables );
                        if (kalatz.firstMonthRepay()
                                + katz.firstMonthRepay()
                                + var5CPI.firstMonthRepay()
                                > firstMonthlyRepaymentDesirable) {
                            continue;
                        }
                    }
                    for (int nperVar5Const = 60; nperVar5Const <= 360; nperVar5Const = nperVar5Const + 60) {
                        Var5Const var5Const = new Var5Const( mortgage * part4, nperVar5Const, customerData, "var5Const" );
                        if (!nperDiff( 0, nperVar5Const, nperVariable5Cpi, nperKalatz, nperKatz )) {
                            continue;
                        } else {
                            var5Const = (Var5Const) searchRoute( mortgage * part4, nperVar5Const, var5Const.rateFromList( dataList.getVariable5ConstTable() ), 4, dynamicTables );
                            if (kalatz.firstMonthRepay()
                                    + katz.firstMonthRepay()
                                    + var5CPI.firstMonthRepay()
                                    + var5Const.firstMonthRepay()
                                    > firstMonthlyRepaymentDesirable) {
                                continue;
                            }
                        }
                        for (int nperPrime = 96; nperPrime <= 360; nperPrime = nperPrime + 12) {
                            if (!nperDiff( nperPrime, nperVar5Const, nperVariable5Cpi, nperKalatz, nperKatz )) {
                                continue;
                            }
                            PrimeRoute prime = (PrimeRoute) searchRoute( mortgage * 0.3, nperPrime, dataList.getPrimeData().get( 0 ).getMain() - primeDecrease, 5, dynamicTables );
                            double totalPaymentTemp = kalatz.getTotalPaymentMain()
                                    + katz.getTotalPaymentMain()
                                    + var5CPI.getTotalPaymentMain()
                                    + var5Const.getTotalPaymentMain()
                                    + prime.getTotalPaymentMain();

                                    double firstMonthRepayTemp = kalatz.firstMonthRepay()
                                            + katz.firstMonthRepay()
                                            + var5CPI.firstMonthRepay()
                                            + var5Const.firstMonthRepay()
                                            + prime.firstMonthRepay();
                                    if (firstMonthRepayTemp > firstMonthlyRepaymentDesirable) {
                                        continue;
                                    } else if (totalPaymentTemp < minimalTotalPayment) {

                                        prime.calculate( 3, dataList );
                                        katz.calculate( 3, dataList );
                                        var5CPI.calculate( 3, dataList );
                                        var5Const.calculate( 3, dataList );

                                        ArrayList<ArrayList<LinePaymentData>> listArrayListMain = new ArrayList<>();
                                        ArrayList<ArrayList<LinePaymentData>> listArrayListWorst = new ArrayList<>();
                                        createListOfList( kalatz, katz, var5CPI, var5Const, prime, listArrayListMain, listArrayListWorst );
                                        double maxMonthRepaymentMainTemp = getMaxMonthRepaymentMain();
                                        double maxMonthRepaymentWorstTemp = getMaxMonthRepaymentWorst();

                                        if (maxMonthRepaymentWorstTemp < maxMonthRepaymentWorstDesirable) {
                                            this.kalatzOpt = kalatz;
                                            this.katzOpt = katz;
                                            this.var5CPIOpt = var5CPI;
                                            this.var5ConstOpt = var5Const;
                                            this.primeOpt = prime;

                                            this.totalPaymentMain = totalPaymentTemp;

                                            this.linePaymentDataMergeMainOpt.clear();
                                            this.linePaymentDataMergeWorstOpt.clear();
                                            this.linePaymentDataMergeMainOpt.addAll( this.linePaymentDataMergeMain );
                                            this.linePaymentDataMergeWorstOpt.addAll( this.linePaymentDataMergeWorst );

                                            this.firstMonthlyRepaymentOpt = linePaymentDataMergeMainOpt.get( 0 ).getMonthlyRepayment();
                                            this.maxMonthRepaymentMainOpt = maxMonthRepaymentMainTemp;
                                            this.maxMonthRepaymentWorstOpT = maxMonthRepaymentWorstTemp;
                                            this.totalPaymentWorst = kalatz.getTotalPaymentMain()
                                                    + katz.getTotalPaymentWorst()
                                                    + var5CPI.getTotalPaymentWorst()
                                                    + var5Const.getTotalPaymentWorst()
                                                    + prime.getTotalPaymentWorst();
                                            ;
                                            minimalTotalPayment = totalPaymentTemp;
                                            conditions = true;
                                        }
                                    }
                        }
                    }
                }
            }
        }
    }



    /**
     * create list of list line data payment.
     */
    public void createListOfList(Kalatz kalatz, Katz katz, Var5CPI var5Cpi, Var5Const var5Const, PrimeRoute prime,
                                 ArrayList<ArrayList<LinePaymentData>> listArrayListMain,
                                 ArrayList<ArrayList<LinePaymentData>> listArrayListWorst) {

        listArrayListMain = new ArrayList<>();
        listArrayListWorst = new ArrayList<>();
        if ((kalatz != null) && (kalatz.getLinePaymentDataListMain() != null)) {
            listArrayListMain.add( kalatz.getLinePaymentDataListMain());
            listArrayListWorst.add( kalatz.getLinePaymentDataListMain());
        }
        if ((katz != null) && (katz.getLinePaymentDataListMain() != null)) {
            listArrayListMain.add( katz.getLinePaymentDataListMain());
            listArrayListWorst.add( katz.getLinePaymentDataListWorst());
        }
        if ((var5Const != null) && (var5Const.getLinePaymentDataListMain() != null)) {
            listArrayListMain.add( var5Const.getLinePaymentDataListMain());
            listArrayListWorst.add( var5Const.getLinePaymentDataListWorst());
        }
        if ((var5Cpi != null) && (var5Cpi.getLinePaymentDataListMain() != null)) {

            listArrayListMain.add( var5Cpi.getLinePaymentDataListMain());
            listArrayListWorst.add( var5Cpi.getLinePaymentDataListWorst());
        }
        if ((prime != null) && (prime.getLinePaymentDataListMain() != null)) {
            listArrayListMain.add( prime.getLinePaymentDataListMain());
            listArrayListWorst.add( prime.getLinePaymentDataListWorst());
        }
        linePaymentDataMergeMain = mergeLinePaymentList( listArrayListMain);
        linePaymentDataMergeWorst = mergeLinePaymentList( listArrayListWorst);
    }

    /**
     *
     * @param listArrayList list of LineData payment list.
     */
    public ArrayList<LinePaymentData> mergeLinePaymentList(ArrayList<ArrayList<LinePaymentData>> listArrayList) {

        ArrayList<LinePaymentData> mergeList = new ArrayList<>();

        int sizeListMax = 0;

        for (int s = 0; s < listArrayList.size(); s++) {
            if (listArrayList.get( s ).size() > sizeListMax) {
                sizeListMax = listArrayList.get( s ).size();
            }
        }
        for (int j = 0; j < sizeListMax; j++) {
            mergeList.add( new LinePaymentData( 0, 0, 0, 0, 0, 0 ) );
        }
        for (List<LinePaymentData> linePaymentDataList : listArrayList ) {
            for (int i = 0; i < linePaymentDataList.size(); i++) {
                if (i < mergeList.size()) {
                    mergeList.get( i ).setFundPv( mergeList.get( i ).getFundPv() + linePaymentDataList.get( i ).getFundPv() );
                    mergeList.get( i ).setMonthlyRepayment( mergeList.get( i ).getMonthlyRepayment() + linePaymentDataList.get( i ).getMonthlyRepayment() );
                    mergeList.get( i ).setFundPayment( mergeList.get( i ).getFundPayment() + linePaymentDataList.get( i ).getFundPayment() );
                    mergeList.get( i ).setRatePayment( mergeList.get( i ).getRatePayment() + linePaymentDataList.get( i ).getRatePayment() );
                    mergeList.get( i ).setSumPayment( mergeList.get( i ).getSumPayment() + linePaymentDataList.get( i ).getSumPayment() );
                }
            }
        }
        return mergeList;
    }


    public boolean isConditions() {
        return conditions;
    }

    public void setRateCustomer(int rateCustomer) {
        this.rateCustomer = rateCustomer;
    }



    public void setPart1(double part1) {
        this.part1 = part1;
    }

    public void setPart2(double part2) {
        this.part2 = part2;
    }

    public void setPart3(double part3) {
        this.part3 = part3;
    }

    public void setPart4(double part4) {
        this.part4 = part4;
    }

    public double getPart3() {
        return part3;
    }

    public double getPart4() {
        return part4;
    }


    public void setCustomerData(CustomerData customerData) {
        this.customerData = customerData;
    }

    public CustomerData getCustomerData() {
        return customerData;
    }



    public double getMaxMonthRepaymentWorst() {
        double maxMonthRepaymentWorstTemp = 0;
        try {
            for (LinePaymentData linePaymentData : linePaymentDataMergeWorst) {
                if (linePaymentData.getMonthlyRepayment() > maxMonthRepaymentWorstTemp) {
                    maxMonthRepaymentWorstTemp = linePaymentData.getMonthlyRepayment();
                }
            }
        } catch (Exception e) {
            System.out.println("linePaymentDataMergeWorst is null");
        }
        return maxMonthRepaymentWorstTemp;
    }
    public String getName() {
        return name;
    }

    public double getMortgage() {
        return mortgage;
    }

    public double getPercentage() {
        return percentage;
    }

    public double getFirstMonthlyRepaymentDesirable() {
        return firstMonthlyRepaymentDesirable;
    }

    public double getMaxMonthRepaymentWorstDesirable() {
        return maxMonthRepaymentWorstDesirable;
    }

    public int getRateCustomer() {
        return rateCustomer;
    }

    public double getTotalPaymentWorst() {
        return totalPaymentWorst;
    }


    public double getTotalPaymentMain() {
        return totalPaymentMain;
    }
    public double getTotalRatePaymentWorst() {
        return totalPaymentWorst - mortgage;
    }

    public double getPart1() {
        return part1;
    }

    public double getPart2() {
        return part2;
    }

    public DataList getDataList() {
        return dataList;
    }

    public double getMaxMonthRepaymentMain() {
        double maxMonthRepaymentMainTemp = 0;
        try {
            for (LinePaymentData linePaymentData : linePaymentDataMergeMain) {
                if (linePaymentData.getMonthlyRepayment() > maxMonthRepaymentMainTemp) {
                    maxMonthRepaymentMainTemp = linePaymentData.getMonthlyRepayment();
                }
            }
        } catch (Exception e) {
            System.out.println("linePaymentDataMergeMain is null");
        }
        return maxMonthRepaymentMainTemp;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }




    public double getTotalPayment() {
        return totalPaymentMain;
    }

    public double getFirstMonthlyRepayment() {
        return firstMonthlyRepaymentOpt;
    }
    public double totalRatePaymentMain() {
        return totalPaymentMain - this.mortgage;
    }


    public double getPrimeDecrease() {
        return primeDecrease;
    }


    public double getFirstMonthlyRepaymentOpt() {
        return firstMonthlyRepaymentOpt;
    }

    public void setFirstMonthlyRepaymentOpt(double firstMonthlyRepaymentOpt) {
        this.firstMonthlyRepaymentOpt = firstMonthlyRepaymentOpt;
    }

    public double getMaxMonthRepaymentMainOpt() {
        return maxMonthRepaymentMainOpt;
    }

    public void setMaxMonthRepaymentMainOpt(double maxMonthRepaymentMainOpt) {
        this.maxMonthRepaymentMainOpt = maxMonthRepaymentMainOpt;
    }

    public double getMaxMonthRepaymentWorstOpT() {
        return maxMonthRepaymentWorstOpT;
    }

    public void setMaxMonthRepaymentWorstOpT(double maxMonthRepaymentWorstOpT) {
        this.maxMonthRepaymentWorstOpT = maxMonthRepaymentWorstOpT;
    }

    public void setConditions(boolean conditions) {
        this.conditions = conditions;
    }

    public Kalatz getKalatzOpt() {
        return kalatzOpt;
    }

    public void setKalatzOpt(Kalatz kalatzOpt) {
        this.kalatzOpt = kalatzOpt;
    }

    public Katz getKatzOpt() {
        return katzOpt;
    }

    public void setKatzOpt(Katz katzOpt) {
        this.katzOpt = katzOpt;
    }

    public Var5CPI getVar5CPIOpt() {
        return var5CPIOpt;
    }

    public void setVar5CPIOpt(Var5CPI var5CPIOpt) {
        this.var5CPIOpt = var5CPIOpt;
    }

    public Var5Const getVar5ConstOpt() {
        return var5ConstOpt;
    }

    public void setVar5ConstOpt(Var5Const var5ConstOpt) {
        this.var5ConstOpt = var5ConstOpt;
    }

    public PrimeRoute getPrimeOpt() {
        return primeOpt;
    }

    public void setPrimeOpt(PrimeRoute primeOpt) {
        this.primeOpt = primeOpt;
    }


    public ArrayList<LinePaymentData> getLinePaymentDataMergeMain() {
        return linePaymentDataMergeMain;
    }

    public void setLinePaymentDataMergeMain(ArrayList<LinePaymentData> linePaymentDataMergeMain) {
        this.linePaymentDataMergeMain = linePaymentDataMergeMain;
    }

    public ArrayList<LinePaymentData> getLinePaymentDataMergeWorst() {
        return linePaymentDataMergeWorst;
    }

    public void setLinePaymentDataMergeWorst(ArrayList<LinePaymentData> linePaymentDataMergeWorst) {
        this.linePaymentDataMergeWorst = linePaymentDataMergeWorst;
    }

    public ArrayList<LinePaymentData> getLinePaymentDataMergeMainOpt() {
        return linePaymentDataMergeMainOpt;
    }

    public void setLinePaymentDataMergeMainOpt(ArrayList<LinePaymentData> linePaymentDataMergeMainOpt) {
        this.linePaymentDataMergeMainOpt = linePaymentDataMergeMainOpt;
    }

    public ArrayList<LinePaymentData> getLinePaymentDataMergeWorstOpt() {
        return linePaymentDataMergeWorstOpt;
    }

    public void setLinePaymentDataMergeWorstOpt(ArrayList<LinePaymentData> linePaymentDataMergeWorstOpt) {
        this.linePaymentDataMergeWorstOpt = linePaymentDataMergeWorstOpt;
    }


    public void setTotalPaymentMain(double totalPaymentMain) {
        this.totalPaymentMain = totalPaymentMain;
    }

    public void setTotalPaymentWorst(double totalPaymentWorst) {
        this.totalPaymentWorst = totalPaymentWorst;
    }

    public void printRouteData() throws Exception {
        if(totalPaymentMain == 0) {
            return;
        }
        System.out.println("\n\nroutes data - " + name + ":");
        if (part1 != 0) {
            System.out.print("kalatz: sum - " + this.kalatzOpt.getPv() + ", rate - " + kalatzOpt.getRate() + ", nper - " + kalatzOpt.getNper());
        }
        if (part2 != 0) {
            System.out.print("\nkatz: sum - " + this.katzOpt.getPv() + ", rate - " + katzOpt.getRate() + ", nper - " + katzOpt.getNper());
        }
        if (part3 != 0) {
            System.out.print( "\nvariable5CPi: sum - " + this.var5CPIOpt.getPv() + ", rate - " + var5CPIOpt.getRate() + ", nper - " + var5CPIOpt.getNper());
        }
        if (part4 != 0) {
            System.out.print( "\nvar5Const: sum - " + this.var5ConstOpt.getPv() + ", rate - " + var5ConstOpt.getRate() + ", nper - " + var5ConstOpt.getNper());
        }
        System.out.print("\nprime: sum - " + this.primeOpt.getPv() + ", rate - " + primeOpt.getRate() + ", nper - " + primeOpt.getNper());
    }
    public void printData() throws Exception {
        if(totalPaymentMain == 0) {
            System.out.println("\n\n" + name + " - no solution");
            return;
        }
        System.out.printf("\n" + name + " - first month repayment - ");
        System.out.printf("%.2f" , this.firstMonthlyRepaymentOpt);
        System.out.printf("\n" + name + " - max month repayment main - ");
        System.out.printf("%.2f" , this.maxMonthRepaymentMainOpt);
        System.out.printf("\n" + name + " - total payment main - ");
        System.out.printf("%.2f" , this.totalPaymentMain);
        System.out.printf("\n" + name + " - total rate payment main - ");
        System.out.printf("%.2f" , totalPaymentMain - mortgage);
        System.out.printf("\n" + name + " - max month repayment worst - ");
        System.out.printf("%.2f" , this.maxMonthRepaymentWorstOpT);
        System.out.printf("\n" + name + " - total payment worst - ");
        System.out.printf("%.2f" , this.totalPaymentWorst);
        System.out.printf("\n" + name + " - total rate payment worst - ");
        System.out.printf("%.2f" , totalPaymentWorst - mortgage);
    }
}