import java.util.ArrayList;
/**
 * divide according pv.
 */
public class PvSplit {

    private double pvKey;
    private double partKey;
    private ArrayList<NperSplit> pvCommon;

    public PvSplit(BaseRoute baseRoute) {
        this.pvKey = baseRoute.getPv();
        this.partKey = pvKey / baseRoute.getCustomerData().getMortgage();
        this.pvCommon = new ArrayList<>();
        pvCommon.add( new NperSplit( baseRoute ) );

    }

    public double getPartKey() {
        return partKey;
    }

    public void setPartKey(double partKey) {
        this.partKey = partKey;
    }

    public double getPvKey() {
        return pvKey;
    }

    public void setPvKey(double pvKey) {
        this.pvKey = pvKey;
    }

    public ArrayList<NperSplit> getPvCommon() {
        return pvCommon;
    }

    public void setPvCommon(ArrayList<NperSplit> pvCommon) {
        this.pvCommon = pvCommon;
    }
}
