import java.util.ArrayList;

/**
 * divide according nper.
 */
public class NperSplit {

    private int nperKey;
    private ArrayList<BaseRoute> baseRouteList;

    public NperSplit(BaseRoute baseRoute) {
        this.nperKey = baseRoute.getNper();
        baseRouteList = new ArrayList<>();
        baseRouteList.add( baseRoute );
    }

    public int getNperKey() {
        return nperKey;
    }

    public void setNper(int nper) {
        this.nperKey = nperKey;
    }

    public ArrayList<BaseRoute> getBaseRouteList() {
        return baseRouteList;
    }

    public void setBaseRouteList(ArrayList<BaseRoute> baseRouteList) {
        this.baseRouteList = baseRouteList;
    }

}
