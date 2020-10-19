/**
 * The constraints of the mix.
 */
public class Setting {

    private double sizeOfJump;
    private double minimumKalatz;
    private double maximumVar5;
    private double maximumCPI;
    private double maximumNonPrime;

    private int maximumKalatzPvSplit;
    private int maximumKatzPvSplit;
    private int maximumVar5CpiPvSplit;
    private int maximumVar5ConstPvSplit;

    private int maximumKalatzNperSplit;
    private int maximumKatzNperSplit;
    private int maximumVar5CpiNperSplit;
    private int maximumVar5ConstNperSplit;
    private int maximumPrimeNperSplit;

    public Setting(double sizeOfJump, double minimumKalatz, double maximumVar5, double maximumCPI, double maximumNonPrime) {
        this.sizeOfJump = sizeOfJump;
        this.minimumKalatz = minimumKalatz;
        this.maximumVar5 = maximumVar5;
        this.maximumCPI = maximumCPI;
        this.maximumNonPrime = maximumNonPrime;
        maximumKalatzPvSplit = 1;
        maximumKatzPvSplit = 11;
        maximumVar5CpiPvSplit = 11;
        maximumVar5ConstPvSplit = 11;

        maximumKalatzNperSplit = 23;
        maximumKatzNperSplit = 23;
        maximumVar5CpiNperSplit = 23;
        maximumVar5ConstNperSplit = 23;
        maximumPrimeNperSplit = 23;
    }

    public double getSizeOfJump() {
        return sizeOfJump;
    }

    public void setSizeOfJump(double sizeOfJump) {
        this.sizeOfJump = sizeOfJump;
    }

    public double getMinimumKalatz() {
        return minimumKalatz;
    }

    public void setMinimumKalatz(double minimumKalatz) {
        this.minimumKalatz = minimumKalatz;
    }

    public void setMaximumNonPrime(double maximumNonPrime) {
        this.maximumNonPrime = maximumNonPrime;
    }

    public double getMaximumVar5() {
        return maximumVar5;
    }

    public void setMaximumVar5(double maximumVar5) {
        this.maximumVar5 = maximumVar5;
    }

    public double getMaximumCPI() {
        return maximumCPI;
    }

    public double getMaximumNonPrime() {
        return maximumNonPrime;
    }

    public void setMaximumCPI(double maximumCPI) {
        this.maximumCPI = maximumCPI;
    }

    public int getMaximumKalatzPvSplit() {
        return maximumKalatzPvSplit;
    }

    public void setMaximumKalatzPvSplit(int maximumKalatzPvSplit) {
        this.maximumKalatzPvSplit = maximumKalatzPvSplit;
    }

    public int getMaximumKatzPvSplit() {
        return maximumKatzPvSplit;
    }

    public void setMaximumKatzPvSplit(int maximumKatzPvSplit) {
        this.maximumKatzPvSplit = maximumKatzPvSplit;
    }

    public int getMaximumVar5CpiPvSplit() {
        return maximumVar5CpiPvSplit;
    }

    public void setMaximumVar5CpiPvSplit(int maximumVar5CpiPvSplit) {
        this.maximumVar5CpiPvSplit = maximumVar5CpiPvSplit;
    }

    public int getMaximumVar5ConstPvSplit() {
        return maximumVar5ConstPvSplit;
    }

    public void setMaximumVar5ConstPvSplit(int maximumVar5ConstPvSplit) {
        this.maximumVar5ConstPvSplit = maximumVar5ConstPvSplit;
    }

    public int getMaximumKalatzNperSplit() {
        return maximumKalatzNperSplit;
    }

    public void setMaximumKalatzNperSplit(int maximumKalatzNperSplit) {
        this.maximumKalatzNperSplit = maximumKalatzNperSplit;
    }

    public int getMaximumKatzNperSplit() {
        return maximumKatzNperSplit;
    }

    public void setMaximumKatzNperSplit(int maximumKatzNperSplit) {
        this.maximumKatzNperSplit = maximumKatzNperSplit;
    }

    public int getMaximumVar5CpiNperSplit() {
        return maximumVar5CpiNperSplit;
    }

    public void setMaximumVar5CpiNperSplit(int maximumVar5CpiNperSplit) {
        this.maximumVar5CpiNperSplit = maximumVar5CpiNperSplit;
    }

    public int getMaximumVar5ConstNperSplit() {
        return maximumVar5ConstNperSplit;
    }

    public void setMaximumVar5ConstNperSplit(int maximumVar5ConstNperSplit) {
        this.maximumVar5ConstNperSplit = maximumVar5ConstNperSplit;
    }

    public int getMaximumPrimeNperSplit() {
        return maximumPrimeNperSplit;
    }

    public void setMaximumPrimeNperSplit(int maximumPrimeNperSplit) {
        this.maximumPrimeNperSplit = maximumPrimeNperSplit;
    }
}
