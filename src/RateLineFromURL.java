/**
 * Distribution of interest data from the website according to the financing percentage.
 */
public class RateLineFromURL {
    private int years;
    private RankingRate percentUntil45;
    private RankingRate percentUntil60;
    private RankingRate percentAbove61;


    /**
     * constructor.
     */

    public RateLineFromURL(int years, RankingRate percentUntil45, RankingRate percentUntil60, RankingRate percentAbove61  ){
        this.years = years;
        this.percentUntil45 = percentUntil45;
        this.percentUntil60 = percentUntil60;
        this.percentAbove61 = percentAbove61;
    }


    public void setPercentAbove61(RankingRate percentAbove61) {
        this.percentAbove61 = percentAbove61;
    }
    public RankingRate getPercentUntil45() {
        return percentUntil45;
    }

    public RankingRate getPercentUntil60() {
        return percentUntil60;
    }

    public RankingRate getPercentAbove61() {
        return percentAbove61;
    }


    public int getYears() {
        return years;
    }

    public void setPercentUntil45(RankingRate percentUntil45) {
        this.percentUntil45 = percentUntil45;
    }

    public void setPercentUntil60(RankingRate percentUntil60) {
        this.percentUntil60 = percentUntil60;
    }

    public void setPercentUntil70(RankingRate percentAbove61) {
        this.percentAbove61 = percentAbove61;
    }

    public void setYears(int years) {
        this.years = years;
    }
}
