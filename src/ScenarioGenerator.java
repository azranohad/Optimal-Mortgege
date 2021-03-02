import java.util.Comparator;

/**
 * The execution plan of all scenarios.
 */

public class ScenarioGenerator {
    private final Constraints constraints;
    private final CustomerData customerData;
    private final Setting setting;
    private MixBestLists mixBestLists;
    private final double epsilon = Math.pow( 10, -4 );
    private double partKalatz;
    private double partKatz;
    private double partVar5Cpi;
    private double partVar5Const;


    /**
     * constructor.
     */
    public ScenarioGenerator(Constraints constraints) throws Exception {
        this.constraints = constraints;
        this.customerData = constraints.getCustomerData();
        this.setting = constraints.getSetting();
    }

    /**
     * Test spatula for all 5 routs.
     * @param dataList
     * @throws Exception
     */
    public void result5Routes(DataList dataList) throws Exception {
        long start = System.currentTimeMillis();
        DynamicTables dynamicTables = new DynamicTables();
        double lastpartKalatz = 0;

        this.mixBestLists = new MixBestLists();
        double epsilon  = Math.pow( 10, -4 );
        int iterator = 0;
        //kalatz.
        for (partKalatz = setting.getMinimumKalatz(); partKalatz <= setting.getMaximumNonPrime(); partKalatz = partKalatz + setting.getSizeOfJump()) {
            //katz.
            for (partKatz = 0 ; partKatz <= setting.getMaximumNonPrime(); partKatz = partKatz + setting.getSizeOfJump()) {
                //var5Cpi.
                for (partVar5Cpi = 0; partVar5Cpi <= setting.getMaximumNonPrime(); partVar5Cpi = partVar5Cpi + setting.getSizeOfJump() ) {
                    //var5Const.
                    for (partVar5Const = 0 ; partVar5Const <= setting.getMaximumNonPrime(); partVar5Const = partVar5Const + setting.getSizeOfJump()) {
                        if (Math.abs ((partKalatz + partKatz + partVar5Cpi + partVar5Const) - 0.7) > epsilon) {
                            continue;
                        } else if ((partVar5Cpi + partVar5Const) > setting.getMaximumVar5() + epsilon) {
                            continue;
                        } else if ((partVar5Cpi + partKatz) > setting.getMaximumCPI() + epsilon) {
                            continue;
                        }
                        iterator++;
                        CustomerData customerDataTemp = new CustomerData( customerData );
                        customerDataTemp.setRateCustomer( improveRateCustomer());
                        Constraints constraints1 = new Constraints( customerDataTemp, setting );
                        MixBase mix = new MixBase( "mixTemp", constraints1,
                                partKalatz, partKatz, partVar5Cpi, partVar5Const, customerData.getPrimeDecrease(), dataList);
                        mix.optimizationAllRoute(dynamicTables);
                        if (mix.isConditions()){
                            mixBestLists.sortMixToTheList( mix );
                        }
                        clearMemoryList( lastpartKalatz, dynamicTables );
                        dynamicTables.clearList(setting);
                        lastpartKalatz = partKalatz;
                    }
                }
            }
        }
        System.out.println(iterator);
        mixBestLists.createAllCsvFiles(constraints);
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed);
/*
        clearEnd( dynamicTables, mixBestLists );
*/
    }

    /**
     * Clears the track calculation memory while running so as not to flood the memory.
     * @param lastpartKalatz
     * @param dynamicTables
     */
    public void clearMemoryList (double lastpartKalatz, DynamicTables dynamicTables) {
        /**
         * Deletes the memory of the previous run on the rout kalatz.
         */
        if (Math.abs( partKalatz - lastpartKalatz ) > epsilon) {
           dynamicTables.getKalatzList().remove( 0 );
            /**
             * These loops check if there is an amount that we may no
             * longer need later (because it is large) and delete it.
             */

            dynamicTables.getKatzList().sort( Comparator.comparingDouble( PvSplit::getPartKey ) );
            while ((dynamicTables.getKatzList().size() != 0) && (partKalatz + dynamicTables.getKatzList().get( dynamicTables.getKatzList().size() - 1 )
                    .getPartKey() > setting.getMaximumNonPrime()) ) {
                dynamicTables.getKatzList().remove( dynamicTables.getKatzList().size() - 1 );
            }

            dynamicTables.getVar5CpiList().sort( Comparator.comparingDouble( PvSplit::getPartKey ) );
            while ( (dynamicTables.getVar5CpiList().size() != 0) && (partKalatz + dynamicTables.getVar5CpiList().get( dynamicTables.getVar5CpiList().size() - 1 )
                    .getPartKey() > setting.getMaximumNonPrime())) {
                dynamicTables.getVar5CpiList().remove( dynamicTables.getVar5CpiList().size() - 1);
            }

            dynamicTables.getVar5ConstList().sort( Comparator.comparingDouble( PvSplit::getPartKey ) );
            while ((dynamicTables.getVar5ConstList().size() != 0) && (partKalatz + dynamicTables.getVar5ConstList().get( dynamicTables.getVar5ConstList().size() - 1 )
                    .getPartKey() > setting.getMaximumNonPrime())) {
                dynamicTables.getVar5ConstList().remove( dynamicTables.getVar5ConstList().size() - 1);
            }
        }
    }


    /**
     * clear all list at the end of the calculation run.
     * @param dynamicTables
     * @param mixBestLists
     */
    public void clearEnd(DynamicTables dynamicTables, MixBestLists mixBestLists) {
        dynamicTables.clearAll();
        mixBestLists.clearAllList();
    }

    /**
     * Raises the customer level as the percentage
     * of more dangerous routes increases.
     * @return new rate customer.
     */

    public int improveRateCustomer() {
        int rateCustomerUpdate = customerData.getRateCustomer();
        double var5Part = partVar5Const + partVar5Cpi;

        if ((partVar5Cpi >= 0.1) && (partVar5Cpi < 0.3)) {
            rateCustomerUpdate = customerData.getRateCustomer() - 1;
        } else if ((partVar5Cpi >= 0.3) && (partVar5Cpi < 0.5)) {
            rateCustomerUpdate = customerData.getRateCustomer() - 2;
        } else if (partVar5Cpi >= 0.5) {
            rateCustomerUpdate = customerData.getRateCustomer() - 3;
        } else if ((partVar5Const >= 0.2) && (partVar5Const < 0.4)) {
            rateCustomerUpdate = customerData.getRateCustomer() - 1;
        } else if (var5Part >= 0.2) {
            rateCustomerUpdate = customerData.getRateCustomer() - 1;
        }
        if (partKatz >= 0.4) {
            rateCustomerUpdate = customerData.getRateCustomer() - 2;
        } else if (partKatz >= 0.15) {
            rateCustomerUpdate = customerData.getRateCustomer() - 1;
        }
        if (partKalatz > 0.44) {
            rateCustomerUpdate = customerData.getRateCustomer() + 2;
        } else if (partKalatz > 0.34) {
            rateCustomerUpdate = customerData.getRateCustomer() + 1;
        }
        if (rateCustomerUpdate < 1) {
            rateCustomerUpdate = 1;
        }
        return rateCustomerUpdate;
    }

    public Constraints getConstraints() {
        return constraints;
    }

    public CustomerData getCustomerData() {
        return customerData;
    }

    public Setting getSetting() {
        return setting;
    }

    public MixBestLists getMixBestLists() {
        return mixBestLists;
    }

    public void setMixBestLists(MixBestLists mixBestLists) {
        this.mixBestLists = mixBestLists;
    }

    public double getEpsilon() {
        return epsilon;
    }

    public double getPartKalatz() {
        return partKalatz;
    }

    public void setPartKalatz(double partKalatz) {
        this.partKalatz = partKalatz;
    }

    public double getPartKatz() {
        return partKatz;
    }

    public void setPartKatz(double partKatz) {
        this.partKatz = partKatz;
    }

    public double getPartVar5Cpi() {
        return partVar5Cpi;
    }

    public void setPartVar5Cpi(double partVar5Cpi) {
        this.partVar5Cpi = partVar5Cpi;
    }

    public double getPartVar5Const() {
        return partVar5Const;
    }

    public void setPartVar5Const(double partVar5Const) {
        this.partVar5Const = partVar5Const;
    }
}
