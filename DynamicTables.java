import java.util.ArrayList;

/**
 * Tables that keep all route calculations calculated for reuse
 */
public class DynamicTables {

    private ArrayList<PvSplit> kalatzList;
    private ArrayList<PvSplit> katzList;
    private ArrayList<PvSplit> var5CpiList;
    private ArrayList<PvSplit> var5ConstList;
    private ArrayList<PvSplit> primeList;

    public DynamicTables() {
        this.kalatzList = new ArrayList<>();
        this.katzList = new ArrayList<>();
        this.var5CpiList = new ArrayList<>();
        this.var5ConstList = new ArrayList<>();
        this.primeList = new ArrayList<>();
    }

    public void clearList(Setting setting) {
        if(kalatzList.size() > setting.getMaximumKalatzPvSplit()) {
            kalatzList.remove( 0 );
        }
        if(katzList.size() > setting.getMaximumKatzPvSplit()) {
          katzList.remove( 0 );
        }
        if(var5CpiList.size() > setting.getMaximumVar5CpiPvSplit()) {
            var5CpiList.remove( 0 );
        }
        if(var5ConstList.size() > setting.getMaximumVar5ConstPvSplit()) {
            var5ConstList.remove( 0 );
        }
    }

    public void clearAll() {
        kalatzList.clear();
        katzList.clear();
        var5CpiList.clear();
        var5ConstList.clear();
        primeList.clear();
    }
    public ArrayList<PvSplit> getKalatzList() {
        return kalatzList;
    }

    public void setKalatzList(ArrayList<PvSplit> kalatzList) {
        this.kalatzList = kalatzList;
    }

    public ArrayList<PvSplit> getKatzList() {
        return katzList;
    }

    public void setKatzList(ArrayList<PvSplit> katzList) {
        this.katzList = katzList;
    }

    public ArrayList<PvSplit> getVar5CpiList() {
        return var5CpiList;
    }

    public void setVar5CpiList(ArrayList<PvSplit> var5CpiList) {
        this.var5CpiList = var5CpiList;
    }

    public ArrayList<PvSplit> getVar5ConstList() {
        return var5ConstList;
    }

    public void setVar5ConstList(ArrayList<PvSplit> var5ConstList) {
        this.var5ConstList = var5ConstList;
    }

    public ArrayList<PvSplit> getPrimeList() {
        return primeList;
    }

    public void setPrimeList(ArrayList<PvSplit> primeList) {
        this.primeList = primeList;
    }
}
