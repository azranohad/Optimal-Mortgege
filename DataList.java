
import java.util.ArrayList;
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
        Table360Factory table360Factory = new Table360Factory();
        this.cpiData = table360Factory.getForecastTable( "csvCPI.csv", 480 );
        this.primeData = table360Factory.getForecastTable( "csvPrime.csv", 480 );
        RateFactoryFromWeb katz = new RateFactoryFromWeb();
        this.katzTable = katz.rateSplit( "https://supermarker.themarker.com/Mortgage/CompareMortgage.aspx?Years=6&Product=3&SUM=1060000&Type=" );
        RateFactoryFromWeb kalatz = new RateFactoryFromWeb();
        this.kalatzTable = kalatz.rateSplit( "https://supermarker.themarker.com/Mortgage/CompareMortgage.aspx?Years=6&Product=4&SUM=1060000&Type=");
        RateFactoryFromWeb variable5CPI = new RateFactoryFromWeb();
        this.variable5CPITable = variable5CPI.rateSplit( "https://supermarker.themarker.com/Mortgage/CompareMortgage.aspx?Years=6&Product=6&SUM=1060000&Type=");
        RateFactoryFromWeb variable5Const = new RateFactoryFromWeb();
        this.variable5ConstTable = variable5Const.rateSplit( "https://supermarker.themarker.com/Mortgage/CompareMortgage.aspx?Years=6&Product=69&SUM=1060000&Type=");
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
