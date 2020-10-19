import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Lists of selected mixes for export
 */
public class MixBestLists {

    private ArrayList<ExportData> top30;
    private ArrayList<ExportData> withoutKalatz;
    private ArrayList<ExportData> withoutKatz;
    private ArrayList<ExportData> withoutVar5CPI;
    private ArrayList<ExportData> withoutVar5Const;
    private ArrayList<ExportData> withoutVar5;
    private ArrayList<ExportData> lowMaxMonthRepayMain; //10 mixes have their lowest max return in the main scenario.
    private ArrayList<ExportData> lowMaxMonthRepayWorst; //10 mixes have their lowest max return in the worst scenario.
    private ArrayList<ExportData> summaryList;
    // 3 top30 sort by total payment
    // 3 top30 sort by max repay main scenario
    // 3 top30 sort by max repay worst scenario
    // 1 lowMaxMonthRepayWorst sort by max repay worst scenario


    public MixBestLists () {
        this.top30 = new ArrayList<>();
        this.withoutKalatz = new ArrayList<>();
        this.withoutKatz = new ArrayList<>();
        this.withoutVar5CPI = new ArrayList<>();
        this.withoutVar5Const = new ArrayList<>();
        this.withoutVar5 = new ArrayList<>();
        this.lowMaxMonthRepayMain = new ArrayList<>();
        this.lowMaxMonthRepayWorst = new ArrayList<>();
        this.summaryList = new ArrayList<>();

    }

    public void clearAllList() {
        top30.clear();
        withoutKalatz.clear();
        withoutKatz.clear();
        withoutVar5CPI.clear();
        withoutVar5Const.clear();
        withoutVar5.clear();
        lowMaxMonthRepayMain.clear();
        lowMaxMonthRepayWorst.clear();
        summaryList.clear();
    }

    /**
     * Initializes the data for export from the mix
     * @param mix
     * @return new export data with data from mix.
     */
    public ExportData createExportData(MixBase mix) throws Exception {
        ExportData exportDate = new ExportData();


        exportDate.setProperty( (int) mix.getCustomerData().getProperty() );
        exportDate.setMortgage( (int) mix.getMortgage() );
        exportDate.setFirstMonthlyRepaymentDesirable( mix.getFirstMonthlyRepaymentDesirable() );
        exportDate.setMaxMonthlyRepaymentWorstDesirable( mix.getMaxMonthRepaymentWorstDesirable() );
        exportDate.setPrimeDecrease( mix.getPrimeDecrease() );
        exportDate.setPercentage();
        exportDate.setDiffNperFactor( mix.getCustomerData().getDiffNperFactor() );
        exportDate.setRateCustomer( mix.getRateCustomer() );
        exportDate.setMergeListMain( mix.getLinePaymentDataMergeMainOpt() );
        exportDate.setMergeListWorst( mix.getLinePaymentDataMergeWorstOpt() );


        exportDate.setAllTotalPaymentMain( mix.getTotalPaymentMain() );
        exportDate.setAllTotalPaymentWorst( mix.getTotalPaymentWorst() );
        exportDate.setAllRateTotalPaymentMain( mix.totalRatePaymentMain() );
        exportDate.setAllRateTotalPaymentWorst( mix.getTotalRatePaymentWorst() );
        exportDate.setMaxMonthRepayMain( mix.getMaxMonthRepaymentMainOpt() );
        exportDate.setMaxMonthRepayWorst( mix.getMaxMonthRepaymentWorstOpT() );
        exportDate.setAllFirstMonthRepayment( mix.getFirstMonthlyRepayment() );

        exportDate.setKalatz( mix.getKalatzOpt() );
        exportDate.setKalatzName( mix.getKalatzOpt().getName() );
        exportDate.setKalatzRate( mix.getKalatzOpt().getRate() );
        exportDate.setKalatzNper( mix.getKalatzOpt().getNper() );
        exportDate.setKalatzPv( mix.getKalatzOpt().getPv() );
        exportDate.setKalatzFirstMonthRepayment( mix.getKalatzOpt().firstMonthRepay() );
        exportDate.setKalatzProp( mix.getPart1() );
        exportDate.setKalatzTotalPaymentMain(mix.getKalatzOpt().getTotalPaymentMain()  );
        exportDate.setKalatzTotalPaymentRateKalatz( mix.getKalatzOpt().getTotalRatePaymentMain() );

        exportDate.setKatz( mix.getKatzOpt() );
        exportDate.setKatzName( mix.getKatzOpt().getName() );
        exportDate.setKatzRate( mix.getKatzOpt().getRate() );
        exportDate.setKatzNper( mix.getKatzOpt().getNper() );
        exportDate.setKatzPv( mix.getKatzOpt().getPv() );
        exportDate.setKatzFirstMonthRepayment( mix.getKatzOpt().firstMonthRepay() );
        exportDate.setKatzProp( mix.getPart2() );
        exportDate.setKatzTotalPaymentMain( mix.getKatzOpt().getTotalPaymentMain() );
        exportDate.setKatzTotalRateMain( mix.getKatzOpt().getTotalRatePaymentMain() );
        exportDate.setKatzTotalPaymentWorst( mix.getKatzOpt().getTotalPaymentWorst() );
        exportDate.setKatzTotalRateWorst( mix.getKatzOpt().getTotalRatePaymentWorst() );

        exportDate.setVar5CPI( mix.getVar5CPIOpt() );
        exportDate.setVar5CpiName( mix.getVar5CPIOpt().getName() );
        exportDate.setVar5CpiRate( mix.getVar5CPIOpt().getRate() );
        exportDate.setVar5CpiNper( mix.getVar5CPIOpt().getNper() );
        exportDate.setVar5CpiPv( mix.getVar5CPIOpt().getPv() );
        exportDate.setVar5CpiFirstMonthRepayment( mix.getVar5CPIOpt().firstMonthRepay() );
        exportDate.setVar5CpiProp( mix.getPart3() );
        exportDate.setVar5CpiTotalPaymentMain( mix.getVar5CPIOpt().getTotalPaymentMain() );
        exportDate.setVar5CpiTotalRateMain( mix.getVar5CPIOpt().getTotalRatePaymentMain() );
        exportDate.setVar5CpiTotalPaymentWorst( mix.getVar5CPIOpt().getTotalPaymentWorst() );
        exportDate.setVar5CpiTotalRateWorst( mix.getVar5CPIOpt().getTotalRatePaymentWorst() );

        exportDate.setVar5Const( mix.getVar5ConstOpt() );
        exportDate.setVar5ConstName( mix.getVar5ConstOpt().getName() );
        exportDate.setVar5ConstRate( mix.getVar5ConstOpt().getRate() );
        exportDate.setVar5ConstNper( mix.getVar5ConstOpt().getNper() );
        exportDate.setVar5ConstPv( mix.getVar5ConstOpt().getPv() );
        exportDate.setVar5ConstFirstMonthRepayment( mix.getVar5ConstOpt().firstMonthRepay() );
        exportDate.setVar5ConstProp( mix.getPart4() );
        exportDate.setVar5ConstTotalPaymentMain( mix.getVar5ConstOpt().getTotalPaymentMain() );
        exportDate.setVar5ConstTotalRateMain( mix.getVar5ConstOpt().getTotalRatePaymentMain() );
        exportDate.setVar5ConstTotalPaymentWorst( mix.getVar5ConstOpt().getTotalPaymentWorst() );
        exportDate.setVar5ConstTotalRateWorst( mix.getVar5ConstOpt().getTotalRatePaymentWorst() );

        exportDate.setPrime( mix.getPrimeOpt() );
        exportDate.setPrimeName( mix.getPrimeOpt().getName() );
        exportDate.setPrimeRate( mix.getPrimeOpt().getRate() );
        exportDate.setPrimeNper( mix.getPrimeOpt().getNper() );
        exportDate.setPrimePv( mix.getPrimeOpt().getPv() );
        exportDate.setPrimeFirstMonthRepayment( mix.getPrimeOpt().firstMonthRepay() );
        exportDate.setPrimeProp( mix.getPrimeOpt().getPv() / mix.getMortgage() );
        exportDate.setPrimeTotalPaymentMain( mix.getPrimeOpt().getTotalPaymentMain() );
        exportDate.setPrimeTotalRateMain( mix.getPrimeOpt().getTotalRatePaymentMain() );
        exportDate.setPrimeTotalPaymentWorst( mix.getPrimeOpt().getTotalPaymentWorst() );
        exportDate.setPrimeTotalRateWorst( mix.getPrimeOpt().getTotalRatePaymentWorst() );

        return exportDate;
    }

    /**
     * puts the mix in the appropriate lists
     * @param mix
     * @throws Exception
     */
    public void sortMixToTheList (MixBase mix) throws Exception {

        createExportData( mix );

        if (mix.getPart1() == 0) {
            withoutKalatz.add( createExportData( mix ) );
            if (withoutKalatz.size() > 10) {
                withoutKalatz.sort( Comparator.comparingDouble( ExportData::getAllTotalPaymentMain ) );
                withoutKalatz.remove( 10 );
            }
        }
        if (mix.getPart2() == 0) {
            withoutKatz.add( createExportData( mix ) );
            if (withoutKatz.size() > 10) {
                withoutKatz.sort( Comparator.comparingDouble( ExportData::getAllTotalPaymentMain ) );
                withoutKatz.remove( 10 );
            }
        }
        if (mix.getPart3() == 0) {
            withoutVar5CPI.add( createExportData( mix ) );
            if (withoutVar5CPI.size() > 10) {
                withoutVar5CPI.sort( Comparator.comparingDouble( ExportData::getAllTotalPaymentMain ) );
                withoutVar5CPI.remove( 10 );
            }
        }
        if (mix.getPart4() == 0) {
            withoutVar5Const.add( createExportData( mix ) );
            if (withoutVar5Const.size() > 10) {
                withoutVar5Const.sort( Comparator.comparingDouble( ExportData::getAllTotalPaymentMain ) );
                withoutVar5Const.remove( 10 );
            }
        }
        if ((mix.getPart3() == 0) && (mix.getPart4() == 0)) {
            withoutVar5.add( createExportData( mix ) );
            if (withoutVar5.size() > 10) {
                withoutVar5.sort( Comparator.comparingDouble( ExportData::getAllTotalPaymentMain ) );
                withoutVar5.remove( 10 );
            }
        }

        lowMaxMonthRepayMain.add( createExportData( mix ) );
        if (lowMaxMonthRepayMain.size() > 10) {
            lowMaxMonthRepayMain.sort( Comparator.comparingDouble( ExportData::getMaxMonthRepayMain ) );
            lowMaxMonthRepayMain.remove( 10 );
        }
        lowMaxMonthRepayWorst.add( createExportData( mix ) );
        if (lowMaxMonthRepayWorst.size() > 10) {
            lowMaxMonthRepayWorst.sort( Comparator.comparingDouble( ExportData::getMaxMonthRepayMain ) );
            lowMaxMonthRepayWorst.remove( 10 );
        }

        top30.add( createExportData( mix ) );
        if (top30.size() > 30) {
            top30.sort( Comparator.comparingDouble( ExportData::getAllTotalPaymentMain ) );
            top30.remove( 30 );
        }
    }

    /**
     * Produces the summary table.
     */
    public void createSummaryList() {
        if (top30.size() != 0) {
            top30.sort( Comparator.comparingDouble( ExportData::getAllTotalPaymentMain ) );
            for(int i = 0; i < 3; i++) {
                try{
                    summaryList.add( top30.get( i ) );
                } catch (Exception e) {
                    System.out.println("the size top30 list is" + top30.size());
                }
            }
            top30.sort( Comparator.comparingDouble( ExportData::getMaxMonthRepayMain ) );
            for(int i = 0; i < 3; i++) {
                try{
                    summaryList.add( top30.get( i ) );
                } catch (Exception e) {
                    System.out.println("the size top30 list is" + top30.size());
                }
            }
            top30.sort( Comparator.comparingDouble( ExportData::getMaxMonthRepayWorst ) );
            for(int i = 0; i < 3; i++) {
                try{
                    summaryList.add( top30.get( i ) );
                } catch (Exception e) {
                    System.out.println("the size top30 list is" + top30.size());
                }
            }
        }
        if(lowMaxMonthRepayWorst.size() != 0) {
            lowMaxMonthRepayWorst.sort( Comparator.comparingDouble( ExportData::getMaxMonthRepayWorst ) );
            summaryList.add( lowMaxMonthRepayWorst.get( 0 ) );
        }
    }

    /**
     * Exports the tables to csv files.
     */
    public void createAllCsvFiles(Constraints constraints) throws IOException {
        if (top30.size() != 0) {
            top30.sort( Comparator.comparingDouble( ExportData::getAllTotalPaymentMain ) );
            outputToCsv( top30, "top 30", constraints );
        }
        if (withoutKalatz.size() != 0) {
            withoutKalatz.sort( Comparator.comparingDouble( ExportData::getAllTotalPaymentMain ) );
            outputToCsv( withoutKalatz, "withoutKalatz", constraints );
        }
        if (withoutKatz.size() != 0) {
            withoutKatz.sort( Comparator.comparingDouble( ExportData::getAllTotalPaymentMain ) );
            outputToCsv( withoutKatz, "withoutKatz", constraints );
        }
        if (withoutVar5CPI.size() != 0) {
            withoutVar5CPI.sort( Comparator.comparingDouble( ExportData::getAllTotalPaymentMain ) );
            outputToCsv( withoutVar5CPI, "withoutVar5CPI", constraints );
        }
        if (withoutVar5Const.size() != 0) {
            withoutVar5Const.sort( Comparator.comparingDouble( ExportData::getAllTotalPaymentMain ) );
            outputToCsv( withoutVar5Const, "withoutVar5Const", constraints );
        }
        if (withoutVar5.size() != 0) {
            withoutVar5.sort( Comparator.comparingDouble( ExportData::getAllTotalPaymentMain ) );
            outputToCsv( withoutVar5, "withoutVar5", constraints );
        }
        if (lowMaxMonthRepayMain.size() != 0) {
            lowMaxMonthRepayMain.sort( Comparator.comparingDouble( ExportData::getMaxMonthRepayMain ) );
            outputToCsv( lowMaxMonthRepayMain, "lowMaxMonthRepayMain", constraints );
        }
        if (lowMaxMonthRepayWorst.size() != 0) {
            lowMaxMonthRepayWorst.sort( Comparator.comparingDouble( ExportData::getMaxMonthRepayWorst ) );
            outputToCsv( lowMaxMonthRepayWorst, "lowMaxMonthRepayWorst", constraints );
        }

        createSummaryList();
        if (summaryList.size() != 0) {
            outputToCsv( summaryList, "toexcel", constraints );
        }
    }

    /**
     * Generates a csv file from a list of export mixes data
     * @param exportDataList
     * @param fileName
     * @param constraints
     * @throws IOException
     */
    public void outputToCsv(ArrayList<ExportData> exportDataList, String fileName, Constraints constraints) throws IOException {

        fileName = fileName + ".csv";

        // create a writer
        BufferedWriter writer = Files.newBufferedWriter( Paths.get( fileName ) );

        String propertyString = Double.toString( exportDataList.get( 0 ).getProperty() );
        String mortgageString = Double.toString( exportDataList.get( 0 ).getMortgage() );
        String percentageString = Double.toString( exportDataList.get( 0 ).getPercentage() );
        String firstMonthlyRepaymentDesirableString = Double.toString( exportDataList.get( 0 ).getFirstMonthlyRepaymentDesirable() );
        String maxMonthRepaymentWorstDesirable = Double.toString( exportDataList.get( 0 ).getMaxMonthlyRepaymentWorstDesirable() );
        String primeDecreaseString = Double.toString( exportDataList.get( 0 ).getPrimeDecrease() );
        String diffNperFactorString = Double.toString( exportDataList.get( 0 ).getDiffNperFactor() );
        String rateCustomerString = Double.toString( constraints.getCustomerData().getRateCustomer() );
        String sizeOfJumpString = Double.toString( constraints.getSetting().getSizeOfJump());
        String minimumKalatzString = Double.toString( constraints.getSetting().getMinimumKalatz() );
        String maximumVar5String = Double.toString( constraints.getSetting().getMaximumVar5() );
        String maximumCPIString = Double.toString( constraints.getSetting().getMaximumCPI() );

        //constraints data
        List<String> dataMortgage = Arrays.asList( propertyString, mortgageString, percentageString,
                firstMonthlyRepaymentDesirableString, maxMonthRepaymentWorstDesirable,primeDecreaseString ,
                diffNperFactorString ,rateCustomerString, sizeOfJumpString, minimumKalatzString, maximumVar5String, maximumCPIString   );

        try {
            writer.write( "property,mortgage,percent fin,first repay, max repay worst, primeDecrease, diffNperFactor, rate customer," +
                    " size of jump, min kalatz, max var, max cpi " );
            writer.newLine();
            writer.write( String.join( ",", dataMortgage ) );
            writer.newLine();
            writer.newLine();
            //the mix data.
            writer.write("first repay , all payment main ,allRateTotalPaymentMain , max repay main, all payment worst, allRateTotalPaymentWorst, max repay worst," +
                    " kalatz rate, kalatz nper, kalatz pv, kalatz prop," +
                    "katz rate, katz nper, katz pv, katz prop, " +
                    "var 5 Cpi rate, var 5 Cpi nper, var 5 Cpi pv, var 5 Cpi prop," +
                    "var 5 Const rate, var 5 Const nper, var 5 Const pv, var 5 Const prop," +
                    "prime rate, prime nper, prime pv , prime prop," +
                    "rate customer mix," +
                    "first repay kalatz, total payment kalatz , total rate kalatz," +
                    "first repay katz, total payment katz main, total rate katz main , total payment katz worst,total rate katz worst ," +
                    "first repay var5cpi, total payment var5cpi main, total rate var5cpi main , total payment var5cpi worst,total rate var5cpi worst ," +
                    "first repay var5const, total payment var5const main, total rate var5const main , total payment var5const worst,total rate var5const worst ," +
                    "first repay prime, total payment prime main, total rate prime main , total payment prime worst,total rate prime worst" );
            writer.newLine();

            List<List<String>> mixs;
            for(ExportData data : exportDataList) {

                //convert the data mix to string.
                String allTotalPaymentMain = Double.toString( data.getAllTotalPaymentMain() );
                String allRateTotalPaymentMain = Double.toString( data.getAllRateTotalPaymentMain());
                String allTotalPaymentWorst = Double.toString( data.getAllTotalPaymentWorst());
                String allRateTotalPaymentWorst = Double.toString( data.getAllRateTotalPaymentWorst());
                String maxMonthRepayMain = Double.toString( data.getMaxMonthRepayMain());
                String maxMonthRepayWorst = Double.toString( data.getMaxMonthRepayWorst());
                String allFirstMonthRepayment = Double.toString( data.getAllFirstMonthRepayment());
                String rateCustomerMix = Double.toString( data.getRateCustomer());

                String kalatzRate = Double.toString( data.getKalatzRate());
                String kalatzNper = Double.toString( data.getKalatzNper());
                String kalatzPv = Double.toString( data.getKalatzPv());
                String kalatzFirstMonthRepayment = Double.toString( data.getKalatzFirstMonthRepayment());
                String kalatzProp = Double.toString( data.getKalatzProp());
                String kalatzTotalPaymentKalatz = Double.toString( data.getKalatzTotalPaymentKalatz());
                String kalatzTotalRateKalatz = Double.toString( data.getKalatzTotalRateKalatz());

                String katzRate = Double.toString( data.getKatzRate());
                String katzNper = Double.toString( data.getKatzNper());
                String katzPv = Double.toString( data.getKatzPv());
                String katzFirstMonthRepayment = Double.toString( data.getKatzFirstMonthRepayment());
                String katzProp = Double.toString( data.getKatzProp());
                String katzTotalPaymentMain = Double.toString( data.getKatzTotalPaymentMain());
                String katzTotalRateMain = Double.toString( data.getKatzTotalRateMain());
                String katzTotalPaymentWorst = Double.toString( data.getKatzTotalPaymentMain());
                String katzTotalRateWorst = Double.toString( data.getKatzTotalRateWorst());

                String var5CpiRate = Double.toString( data.getVar5CpiRate());
                String var5CpiNper = Double.toString( data.getVar5CpiNper());
                String var5CpiPv = Double.toString( data.getVar5CpiPv());
                String var5CpiFirstMonthRepayment = Double.toString( data.getVar5CpiFirstMonthRepayment());
                String var5CpiProp = Double.toString( data.getVar5CpiProp());
                String var5CpiTotalPaymentMain = Double.toString( data.getVar5CpiTotalPaymentMain());
                String var5CpiTotalRateMain = Double.toString( data.getVar5CpiTotalRateMain());
                String var5CpiTotalPaymentWorst = Double.toString( data.getVar5CpiTotalPaymentWorst());
                String var5CpiTotalRateWorst = Double.toString( data.getVar5CpiTotalRateWorst());

                String var5ConstRate = Double.toString( data.getVar5ConstRate());
                String var5ConstNper = Double.toString( data.getVar5ConstNper());
                String var5ConstPv = Double.toString( data.getVar5ConstPv());
                String var5ConstFirstMonthRepayment = Double.toString( data.getVarConstFirstMonthRepayment());
                String var5ConstProp = Double.toString( data.getVar5ConstProp());
                String var5ConstTotalPaymentMain = Double.toString( data.getVar5ConstTotalPaymentMain());
                String var5ConstTotalRateMain = Double.toString( data.getVar5ConstTotalRateMain());
                String var5ConstTotalPaymentWorst = Double.toString( data.getVar5ConstTotalPaymentWorst());
                String var5ConstTotalRateWorst = Double.toString( data.getVar5ConstTotalRateWorst());

                String primeRate = Double.toString( data.getPrimeDecrease() * (-1));
                String primeNper = Double.toString( data.getPrimeNper());
                String primePv = Double.toString( data.getPrimePv());
                String primeFirstMonthRepayment = Double.toString( data.getPrimeFirstMonthRepayment());
                String primeProp = Double.toString( data.getPrimeProp());
                String primeTotalPaymentMain = Double.toString( data.getPrimeTotalPaymentMain());
                String primeTotalRateMain = Double.toString( data.getPrimeTotalRateMain());
                String primeTotalPaymentWorst = Double.toString( data.getPrimeTotalPaymentWorst());
                String primeTotalRateWorst = Double.toString( data.getPrimeTotalRateWorst());

                //input the data string to list to write.
                mixs = Arrays.asList(
                        Arrays.asList( allFirstMonthRepayment, allTotalPaymentMain, allRateTotalPaymentMain, maxMonthRepayMain,
                                allTotalPaymentWorst, allRateTotalPaymentWorst, maxMonthRepayWorst,
                                kalatzRate, kalatzNper, kalatzPv, kalatzProp,
                                katzRate, katzNper, katzPv, katzProp,
                                var5CpiRate, var5CpiNper, var5CpiPv, var5CpiProp,
                                var5ConstRate, var5ConstNper, var5ConstPv, var5ConstProp,
                                primeRate, primeNper, primePv, primeProp,
                                rateCustomerMix,
                                kalatzFirstMonthRepayment, kalatzTotalPaymentKalatz, kalatzTotalRateKalatz,
                                katzFirstMonthRepayment, katzTotalPaymentMain, katzTotalRateMain, katzTotalPaymentWorst,katzTotalRateWorst,
                                var5CpiFirstMonthRepayment, var5CpiTotalPaymentMain, var5CpiTotalRateMain, var5CpiTotalPaymentWorst,var5CpiTotalRateWorst,
                                var5ConstFirstMonthRepayment, var5ConstTotalPaymentMain, var5ConstTotalRateMain, var5ConstTotalPaymentWorst,var5ConstTotalRateWorst,
                                primeFirstMonthRepayment, primeTotalPaymentMain, primeTotalRateMain, primeTotalPaymentWorst,primeTotalRateWorst) );
                // write header mix


                // write all mixs
                for (List<String> mixxx : mixs) {
                    writer.write( String.join( ",", mixxx ) );
                    writer.newLine();
                }
            }

            //close the writer
            writer.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public ArrayList<ExportData> getLowMaxMonthRepayMain() {
        return lowMaxMonthRepayMain;
    }

    public void setLowMaxMonthRepayMain(ArrayList<ExportData> lowMaxMonthRepayMain) {
        this.lowMaxMonthRepayMain = lowMaxMonthRepayMain;
    }

    public ArrayList<ExportData> getLowMaxMonthRepayWorst() {
        return lowMaxMonthRepayWorst;
    }

    public void setLowMaxMonthRepayWorst(ArrayList<ExportData> lowMaxMonthRepayWorst) {
        this.lowMaxMonthRepayWorst = lowMaxMonthRepayWorst;
    }

    public ArrayList<ExportData> getSummaryList() {
        return summaryList;
    }

    public void setSummaryList(ArrayList<ExportData> summaryList) {
        this.summaryList = summaryList;
    }

    public ArrayList<ExportData> getTop30() {
        return top30;
    }

    public void setTop30(ArrayList<ExportData> top30) {
        this.top30 = top30;
    }

    public ArrayList<ExportData> getWithoutKalatz() {
        return withoutKalatz;
    }

    public void setWithoutKalatz(ArrayList<ExportData> withoutKalatz) {
        this.withoutKalatz = withoutKalatz;
    }

    public ArrayList<ExportData> getWithoutKatz() {
        return withoutKatz;
    }

    public void setWithoutKatz(ArrayList<ExportData> withoutKatz) {
        this.withoutKatz = withoutKatz;
    }

    public ArrayList<ExportData> getWithoutVar5CPI() {
        return withoutVar5CPI;
    }

    public void setWithoutVar5CPI(ArrayList<ExportData> withoutVar5CPI) {
        this.withoutVar5CPI = withoutVar5CPI;
    }

    public ArrayList<ExportData> getWithoutVar5Const() {
        return withoutVar5Const;
    }

    public void setWithoutVar5Const(ArrayList<ExportData> withoutVar5Const) {
        this.withoutVar5Const = withoutVar5Const;
    }

    public ArrayList<ExportData> getWithoutVar5() {
        return withoutVar5;
    }

    public void setWithoutVar5(ArrayList<ExportData> withoutVar5) {
        this.withoutVar5 = withoutVar5;
    }
}
