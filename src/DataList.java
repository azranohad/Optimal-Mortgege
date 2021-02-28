
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * the class all of tah table rate and cpi.
 */
public class DataList {
    private List<ThreeLevel> cpiData;
    private List<ThreeLevel> primeData;
    private ArrayList<RateLineFromURL> katzTable;
    private ArrayList<RateLineFromURL> kalatzTable;
    private ArrayList<RateLineFromURL> variable5CPITable;
    private ArrayList<RateLineFromURL> variable5ConstTable;

    public DataList(List<ThreeLevel> cpiData, ArrayList<RateLineFromURL> katzTable,
                    ArrayList<RateLineFromURL> kalatzTable,ArrayList<RateLineFromURL> variable5CPITable, ArrayList<RateLineFromURL> variable5ConstTable, List<ThreeLevel> primeData ) {
        this.cpiData = cpiData;
        this.kalatzTable = kalatzTable;
        this.katzTable = katzTable;
        this.primeData = primeData;
        this.variable5CPITable = variable5CPITable;
        this.variable5ConstTable = variable5ConstTable;
    }

    /**
     * constructor. create all table from csv files or from web.
     * @throws Exception
     */
    public DataList() throws Exception {

        //try the update rate list from web
        try{
            Table360Factory table360Factory = new Table360Factory();
            this.cpiData = table360Factory.getForecastTable( "csvCPI.csv", 480 );
            this.primeData = table360Factory.getForecastTable( "csvPrime.csv", 480 );
            RateFactoryFromWeb katz = new RateFactoryFromWeb();
            this.katzTable = katz.rateSplit( "https://www.supermarker.themarker.com/Mortgage/CompareMortgage.aspx?Years=6&Product=3&SUM=1060000&Type=" );
            RateFactoryFromWeb kalatz = new RateFactoryFromWeb();
            this.kalatzTable = kalatz.rateSplit( "https://www.supermarker.themarker.com/Mortgage/CompareMortgage.aspx?Years=6&Product=4&SUM=1060000&Type=");
            RateFactoryFromWeb variable5CPI = new RateFactoryFromWeb();
            this.variable5CPITable = variable5CPI.rateSplit( "https://www.supermarker.themarker.com/Mortgage/CompareMortgage.aspx?Years=6&Product=6&SUM=1060000&Type=");
            RateFactoryFromWeb variable5Const = new RateFactoryFromWeb();
            this.variable5ConstTable = variable5Const.rateSplit( "https://www.supermarker.themarker.com/Mortgage/CompareMortgage.aspx?Years=6&Product=69&SUM=1060000&Type=");
            outputRateListToCsv( katzTable, "KatzRate");
            outputRateListToCsv( kalatzTable, "KalatzRate");
            outputRateListToCsv( variable5ConstTable, "var5NonCPI_Rate");
            outputRateListToCsv( variable5CPITable, "var5CPI_Rate");
        } catch (Exception e) {
            //if update the list is not working
            System.out.println("the rate list is not update");
            this.katzTable = createListFormCSV_File( "KatzRate.csv", 25 );
            this.kalatzTable = createListFormCSV_File( "KalatzRate.csv", 26 );
            this.variable5ConstTable = createListFormCSV_File( "var5NonCPI_Rate.csv", 1 );
            this.variable5CPITable = createListFormCSV_File( "var5CPI_Rate.csv", 1 );
        }
    }
    public ArrayList<RateLineFromURL> createListFormCSV_File (String file_name, int rows) {
        ArrayList<RateLineFromURL> list_return = new ArrayList<RateLineFromURL>();
        BufferedReader crunchifyBuffer = null;
        try {
            String crunchifyLine = null;
            crunchifyBuffer = new BufferedReader(new FileReader(file_name) );
            // How to read file in java line by line?
            for (int i = 0; i < rows; i++) {
                crunchifyLine = crunchifyBuffer.readLine();
                String[] arrOfStr = crunchifyLine.split(",", 19);
                int years = (int) Double.parseDouble( arrOfStr[0]);
                RankingRate percentUntil45 = new RankingRate(years,
                        Double.parseDouble(arrOfStr[1]),
                        Double.parseDouble(arrOfStr[2]),
                        Double.parseDouble(arrOfStr[3]),
                        Double.parseDouble(arrOfStr[4]),
                        Double.parseDouble(arrOfStr[5]),
                        Double.parseDouble(arrOfStr[6]));
                RankingRate percentUntil60 = new RankingRate(years,
                        Double.parseDouble(arrOfStr[7]),
                        Double.parseDouble(arrOfStr[8]),
                        Double.parseDouble(arrOfStr[9]),
                        Double.parseDouble(arrOfStr[10]),
                        Double.parseDouble(arrOfStr[11]),
                        Double.parseDouble(arrOfStr[12]));
                RankingRate percentUntil75 = new RankingRate(years,
                        Double.parseDouble(arrOfStr[13]),
                        Double.parseDouble(arrOfStr[14]),
                        Double.parseDouble(arrOfStr[15]),
                        Double.parseDouble(arrOfStr[16]),
                        Double.parseDouble(arrOfStr[17]),
                        Double.parseDouble(arrOfStr[18]));
                RateLineFromURL rateLineFromURL = new RateLineFromURL( years ,
                        percentUntil45,percentUntil60 , percentUntil75 );
                list_return.add( rateLineFromURL );
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (crunchifyBuffer != null) crunchifyBuffer.close();
            } catch (IOException crunchifyException) {
                crunchifyException.printStackTrace();
            }
        }
        return list_return;
    }
    public void outputRateListToCsv(ArrayList<RateLineFromURL> rate, String fileName) throws IOException {
        fileName = fileName + ".csv";

        // create a writer
        BufferedWriter writer = Files.newBufferedWriter( Paths.get( fileName ) );
        try {
            //table of rate each line: years, 0-45% rate 1-6, 45-60% rate 1-6, 60-75% rate 1-6.
            List<List<String>> rateLine;
            for (RateLineFromURL rateList : rate ) {
                String years = Double.toString(rateList.getPercentUntil45().getYears());
                String rate1_until45 = Double.toString( rateList.getPercentUntil45().getRate1());
                String rate2_until45 = Double.toString( rateList.getPercentUntil45().getRate2());
                String rate3_until45 = Double.toString( rateList.getPercentUntil45().getRate3());
                String rate4_until45 = Double.toString( rateList.getPercentUntil45().getRate4());
                String rate5_until45 = Double.toString( rateList.getPercentUntil45().getRate5());
                String rate6_until45 = Double.toString( rateList.getPercentUntil45().getRate6());
                String rate1_until60 = Double.toString( rateList.getPercentUntil60().getRate1());
                String rate2_until60 = Double.toString( rateList.getPercentUntil60().getRate2());
                String rate3_until60 = Double.toString( rateList.getPercentUntil60().getRate3());
                String rate4_until60 = Double.toString( rateList.getPercentUntil60().getRate4());
                String rate5_until60 = Double.toString( rateList.getPercentUntil60().getRate5());
                String rate6_until60 = Double.toString( rateList.getPercentUntil60().getRate6());
                String rate1_until75 = Double.toString( rateList.getPercentAbove61().getRate1());
                String rate2_until75 = Double.toString( rateList.getPercentAbove61().getRate2());
                String rate3_until75 = Double.toString( rateList.getPercentAbove61().getRate3());
                String rate4_until75 = Double.toString( rateList.getPercentAbove61().getRate4());
                String rate5_until75 = Double.toString( rateList.getPercentAbove61().getRate5());
                String rate6_until75 = Double.toString( rateList.getPercentAbove61().getRate6());

                rateLine = Arrays.asList(
                        Arrays.asList( years, rate1_until45, rate2_until45, rate3_until45, rate4_until45, rate5_until45, rate6_until45,
                                rate1_until60,rate2_until60, rate3_until60 ,rate4_until60 ,rate5_until60 ,rate6_until60
                                ,rate1_until75 , rate2_until75, rate3_until75 , rate4_until75 , rate5_until75,rate6_until75 ));
                for (List<String> toCsv : rateLine) {
                    writer.write( String.join( ",", toCsv));
                    writer.newLine();
                }
            }
            //close the writer
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void setCpiData(List<ThreeLevel> cpiData) {
        this.cpiData = cpiData;
    }

    public void setKatzTable(ArrayList<RateLineFromURL> katzTable) {
        this.katzTable = katzTable;
    }

    public void setKalatzTable(ArrayList<RateLineFromURL> kalatzTable) {
        this.kalatzTable = kalatzTable;
    }

    public void setPrimeData(List<ThreeLevel> primeData) {
        this.primeData = primeData;
    }

    public void setVariable5CPITable(ArrayList<RateLineFromURL> variable5CPITable) {
        this.variable5CPITable = variable5CPITable;
    }

    public void setVariable5ConstTable(ArrayList<RateLineFromURL> variable5ConstTable) {
        this.variable5ConstTable = variable5ConstTable;
    }

    public ArrayList<RateLineFromURL> getKalatzTable() {
        return kalatzTable;
    }

    public ArrayList<RateLineFromURL> getKatzTable() {
        return katzTable;
    }

    public List<ThreeLevel> getCpiData() {
        return cpiData;
    }

    public List<ThreeLevel> getPrimeData() {
        return primeData;
    }

    public ArrayList<RateLineFromURL> getVariable5CPITable() {
        return variable5CPITable;
    }

    public ArrayList<RateLineFromURL> getVariable5ConstTable() {
        return variable5ConstTable;
    }
}
