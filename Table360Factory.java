import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads the prime interest rate prime data and estimates the pci from a csv file
 * and create list of ThreeLevel object.
 */
public class Table360Factory {
    private List<ThreeLevel> forecastTable;

    /**
     * Gets the file name and number of rows and produces the required list from it.
     */
    public List<ThreeLevel> getForecastTable(String fileName, int rows) throws Exception {
        this.forecastTable = new ArrayList<>();
        BufferedReader crunchifyBuffer = null;
        try {
            String crunchifyLine = null;
            crunchifyBuffer = new BufferedReader(new FileReader(fileName) );

            // How to read file in java line by line?
            for (int i = 0; i < rows; i++) {
                crunchifyLine = crunchifyBuffer.readLine();
                ThreeLevel lineTemp = new ThreeLevel();
                String[] arrOfStr = crunchifyLine.split(",", 4);
                lineTemp.setNper (Integer.parseInt(arrOfStr[0]));
                lineTemp.setMain(Double.parseDouble( arrOfStr[1].split( "%", 2 )[0]));
                lineTemp.setLenient(Double.parseDouble( arrOfStr[2].split( "%", 2 )[0]));
                lineTemp.setWorst(Double.parseDouble( arrOfStr[3].split( "%", 2 )[0]));
                forecastTable.add( lineTemp );
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
        return forecastTable;
    }
}

