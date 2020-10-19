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
    private double part1;
    private double part2;
    private double part3;
    private double part4;


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
        DynamicTables dynamicTables = new DynamicTables();
        double lastPart1 = 0;

        this.mixBestLists = new MixBestLists();
        double epsilon  = Math.pow( 10, -4 );
        int iterator = 0;
        //kalatz.
        for (part1 = setting.getMinimumKalatz(); part1 <= setting.getMaximumNonPrime(); part1 = part1 + setting.getSizeOfJump()) {
            //katz.
            for (part2 = 0 ; part2 <= setting.getMaximumNonPrime(); part2 = part2 + setting.getSizeOfJump()) {
                //var5Cpi.
                for (part3 = 0; part3 <= setting.getMaximumNonPrime(); part3 = part3 + setting.getSizeOfJump() ) {
                    //var5Const.
                    for (part4 = 0 ; part4 <= setting.getMaximumNonPrime(); part4 = part4 + setting.getSizeOfJump()) {
                        if (Math.abs ((part1 + part2 + part3 + part4) - 0.7) > epsilon) {
                            continue;
                        } else if ((part3 + part4) > setting.getMaximumVar5() + epsilon) {
                            continue;
                        } else if ((part3 + part2) > setting.getMaximumCPI() + epsilon) {
                            continue;
                        }
                        iterator++;
                        CustomerData customerDataTemp = new CustomerData( customerData );
                        customerDataTemp.setRateCustomer( improveRateCustomer( part3, part4, part2 ) );
                        Constraints constraints1 = new Constraints( customerDataTemp, setting );
                        MixBase mix = new MixBase( "mixTemp", constraints1,
                                part1, part2, part3, part4, customerData.getPrimeDecrease(), dataList);
                        mix.optimizationAllRoute(dynamicTables);
                        if (mix.isConditions()){
                            mixBestLists.sortMixToTheList( mix );
                        }
                        clearMemoryList( lastPart1, dynamicTables );
                        dynamicTables.clearList(setting);
                        lastPart1 = part1;
                    }
                }
            }
        }
        System.out.println(iterator);
        mixBestLists.createAllCsvFiles(constraints);
/*
        clearEnd( dynamicTables, mixBestLists );
*/
    }

    /**
     * Clears the track calculation memory while running so as not to flood the memory.
     * @param lastPart1
     * @param dynamicTables
     */
    public void clearMemoryList (double lastPart1, DynamicTables dynamicTables) {
        /**
         * Deletes the memory of the previous run on the rout kalatz.
         */
        if (Math.abs( part1 - lastPart1 ) > epsilon) {
           dynamicTables.getKalatzList().remove( 0 );
            /**
             * These loops check if there is an amount that we may no
             * longer need later (because it is large) and delete it.
             */

            dynamicTables.getKatzList().sort( Comparator.comparingDouble( PvSplit::getPartKey ) );
            while ((dynamicTables.getKatzList().size() != 0) && (part1 + dynamicTables.getKatzList().get( dynamicTables.getKatzList().size() - 1 )
                    .getPartKey() > setting.getMaximumNonPrime()) ) {
                dynamicTables.getKatzList().remove( dynamicTables.getKatzList().size() - 1 );
            }

            dynamicTables.getVar5CpiList().sort( Comparator.comparingDouble( PvSplit::getPartKey ) );
            while ( (dynamicTables.getVar5CpiList().size() != 0) && (part1 + dynamicTables.getVar5CpiList().get( dynamicTables.getVar5CpiList().size() - 1 )
                    .getPartKey() > setting.getMaximumNonPrime())) {
                dynamicTables.getVar5CpiList().remove( dynamicTables.getVar5CpiList().size() - 1);
            }

            dynamicTables.getVar5ConstList().sort( Comparator.comparingDouble( PvSplit::getPartKey ) );
            while ((dynamicTables.getVar5ConstList().size() != 0) && (part1 + dynamicTables.getVar5ConstList().get( dynamicTables.getVar5ConstList().size() - 1 )
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

    public int improveRateCustomer(double var5CpiPart, double var5ConstPart, double katzPart ) {
        int rateCustomerUpdate = customerData.getRateCustomer();
        double var5Part = var5ConstPart + var5CpiPart;

        if ((var5CpiPart >= 0.1) && (var5CpiPart < 0.3)) {
            rateCustomerUpdate = customerData.getRateCustomer() - 1;
        } else if ((var5CpiPart >= 0.3) && (var5CpiPart < 0.5)) {
            rateCustomerUpdate = customerData.getRateCustomer() - 2;
        } else if (var5CpiPart >= 0.5) {
            rateCustomerUpdate = customerData.getRateCustomer() - 3;
        } else if ((var5ConstPart >= 0.2) && (var5ConstPart < 0.4)) {
            rateCustomerUpdate = customerData.getRateCustomer() - 1;
        } else if (var5Part >= 0.2) {
            rateCustomerUpdate = customerData.getRateCustomer() - 1;
        } else if (katzPart >= 0.3) {
            rateCustomerUpdate = customerData.getRateCustomer() - 1;
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

    public double getPart1() {
        return part1;
    }

    public void setPart1(double part1) {
        this.part1 = part1;
    }

    public double getPart2() {
        return part2;
    }

    public void setPart2(double part2) {
        this.part2 = part2;
    }

    public double getPart3() {
        return part3;
    }

    public void setPart3(double part3) {
        this.part3 = part3;
    }

    public double getPart4() {
        return part4;
    }

    public void setPart4(double part4) {
        this.part4 = part4;
    }
}
