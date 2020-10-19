
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Divides the source page of the web page and extracts the interest rates for each route.
 */

public class RateFactoryFromWeb {
    private ArrayList<RateLineFromURL> rateLineFromURLList;

    /**
     * constructor, list table of rates.
     */
    public RateFactoryFromWeb() {
        this.rateLineFromURLList = new ArrayList<RateLineFromURL>();
    }

    /**
     * Connects all the lines of the web page to a single string
     * and extracts the section containing the interest rates.
     * @param URL the web page.
     * @return A string containing the interest.
     * @throws IOException
     */
    public String getTextURL(String URL) throws IOException {
        java.net.URL url = new URL(URL);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String text = null;
        try
        {
            InputStream in = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                result.append(line);
                text = text + line;
            }
        }
        finally
        {
            urlConnection.disconnect();
        }
        String[] arrOfStr = text.split("<div class=\"divTableRowInterestRates\">", 2);
        String[] arrOfStr2 = arrOfStr[1].split( "<h3", 2 );
        return arrOfStr2[0];
    }

    /**
     * Returns a table that divides the required interest rate range to 5 years and 6 levels each year.
     * @param rateMin
     * @param rateMax
     * @return
     */
    public double[][] rateArrayPerYear(double rateMin, double rateMax) {
        double[][] rateArrayYear = new double[5][6];
        double differenceRate = rateMax - rateMin;
        double jumpRate = 1.0 / 9.0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                rateArrayYear[i][j] = rateMin + (((j * jumpRate) + (i * jumpRate)) * differenceRate  );
            }
        }
        return rateArrayYear;
    }

    /**
     * Returns a list that divides the required interest rate range into 6 levels
     * @param rateMin
     * @param rateMax
     * @return
     */
    public double[] rate30Array(double rateMin, double rateMax) {
        double[] rateArray = new double[6];
        rateArray[0] = rateMin;
        rateArray[1] = ((rateMax - rateMin) * 0.2) + rateMin;
        rateArray[2] = ((rateMax - rateMin) * 0.4) + rateMin;
        rateArray[3] = ((rateMax - rateMin) * 0.6) + rateMin;
        rateArray[4] = ((rateMax - rateMin) * 0.8) + rateMin;
        rateArray[5] = rateMax;
        return rateArray;
    }

    /**
     * Returns the list of interest rates.
     * @param URL
     * @return
     * @throws IOException
     */
    public ArrayList<RateLineFromURL> rateSplit(String URL) throws IOException {
        String text = getTextURL( URL );
        //Filters the irrelevant passages from the text.
        text = text.replaceAll( "div", "" );
        text = text.replaceAll( "<", "" );
        text = text.replaceAll( ">", "" );
        text = text.replaceAll( "/", "" );
        text = text.replaceAll( "עד", "" );
        text = text.replaceAll( "משתנה צמודה כל 5 שנים", "" );
        text = text.replaceAll( "משתנה כל 5 שנים לא צמודה", "" );
        text = text.replaceAll( "TableRowInterestRates", "" );
        text = text.replaceAll( "class=", "" );
        text = text.replaceAll( " '' ", "" );
        text = text.replace( "2.2.85", "2.85" ); // temp, bug in web page.


        /**
         * Count the number of divisions by years.
         */
        String textTemp = text;
        String word = "שנים";
        int numOfSplit = 0;
        while (textTemp.contains( word )) {
            textTemp = textTemp.replaceFirst( word, "" );
            numOfSplit++;
        }

        /**
         * Returns an array of strings divided into each segment of years
         */
        String[] arrOfRate = text.split( "שנים", numOfSplit + 1 );

        /**
         * For each line of interest, filter, divide and convert to double.
         */
        for (int i = 1; i < numOfSplit + 1; i++) {
            String[] untilYearsTemp = arrOfRate[i].split( "%", 7 );
            arrOfRate[i] = arrOfRate[i].replaceAll( "[^0-9.]", " " );

            double rateMin45Temp = Double.parseDouble( untilYearsTemp[0] );
            double rateMax45Temp = Double.parseDouble( untilYearsTemp[1] );
            double rateMin60Temp = Double.parseDouble( untilYearsTemp[2] );
            double rateMax60Temp = Double.parseDouble( untilYearsTemp[3] );
            double rateMin75Temp = Double.parseDouble( untilYearsTemp[4] );
            double rateMax75Temp = Double.parseDouble( untilYearsTemp[5] );

            RankingRate percentUntil45Temp;
            RankingRate percentUntil60Temp;
            RankingRate percentAbove61Temp;


            /**
             * only kalatz table is 6 split. the last row is 30 years.
             * if num of split is 1, so there is no division by number of years
             */
            if ((i == 6) || (numOfSplit == 1)) {
                int years = 0;
                if (i == 6){
                    years = 30;
                } else if (numOfSplit == 1) {
                    years = 1;
                }
                double[] rankingRate45 = rate30Array( rateMin45Temp, rateMax45Temp );
                double[] rankingRate60 = rate30Array( rateMin60Temp, rateMax60Temp );
                double[] rankingRate75 = rate30Array( rateMin75Temp, rateMax75Temp );
                percentUntil45Temp = new RankingRate( years, rankingRate45[0], rankingRate45[1], rankingRate45[2], rankingRate45[3], rankingRate45[4], rankingRate45[5] );
                percentUntil60Temp = new RankingRate( years, rankingRate60[0], rankingRate60[1], rankingRate60[2], rankingRate60[3], rankingRate60[4], rankingRate60[5] );
                percentAbove61Temp = new RankingRate( years, rankingRate75[0], rankingRate75[1], rankingRate75[2], rankingRate75[3], rankingRate75[4], rankingRate75[5] );

                RateLineFromURL rateLineYearsTemp = new RateLineFromURL( percentAbove61Temp.getYears(), percentUntil45Temp, percentUntil60Temp, percentAbove61Temp );
                rateLineFromURLList.add( rateLineYearsTemp );
            } else {
                /**
                 *  Each row contains a range of 5 years,
                 *  divider according the logic in "rateArrayPerYear".
                 */
                double[][] rankingTable45 = rateArrayPerYear( rateMin45Temp, rateMax45Temp );
                double[][] rankingTable60 = rateArrayPerYear( rateMin60Temp, rateMax60Temp );
                double[][] rankingAbove61 = rateArrayPerYear( rateMin75Temp, rateMax75Temp );
                /**
                 * Each row contains a range of 5 years.
                 */
                for (int j = 0; j < 5; j++) {

                    percentUntil45Temp = new RankingRate( (((i) * 5) + j), rankingTable45[j][0], rankingTable45[j][1],
                            rankingTable45[j][2], rankingTable45[j][3], rankingTable45[j][4], rankingTable45[j][5] );
                    percentUntil60Temp = new RankingRate( (((i) * 5) + j), rankingTable60[j][0], rankingTable60[j][1],
                            rankingTable60[j][2], rankingTable60[j][3], rankingTable60[j][4], rankingTable60[j][5] );
                    percentAbove61Temp = new RankingRate( (((i) * 5) + j), rankingAbove61[j][0], rankingAbove61[j][1],
                            rankingAbove61[j][2], rankingAbove61[j][3], rankingAbove61[j][4], rankingAbove61[j][5] );
                    RateLineFromURL rateLineYearsTemp = new RateLineFromURL( percentUntil45Temp.getYears(), percentUntil45Temp, percentUntil60Temp, percentAbove61Temp );
                    rateLineFromURLList.add( rateLineYearsTemp );
                }
            }
        }
        return rateLineFromURLList;
    }
}
