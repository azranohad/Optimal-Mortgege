
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;


public class DetailsMixScreen extends JFrame {


    private JPanel panel1;
    private JTable table1;
    private JTable table2;
    private JTextField numberPaymentText;
    private JTable table3;
    private JButton calcButton;
    private JLabel Label;
    private ExportData mixData;
    private int numOfRows;
    private ArrayList<BaseRoute> routes;
    DecimalFormat df2 = new DecimalFormat( "#.##" );
    DecimalFormat decimalFormat = new DecimalFormat( "#.##" );


    public int numOfRoute(ExportData mixData) {
        int counter = 0;
        double[] routes = {mixData.getKalatzPv(),
                mixData.getKatzPv(),
                mixData.getVar5CpiPv(),
                mixData.getVar5ConstPv(),
                mixData.getPrimePv()};
        for (int i = 0; i < routes.length; i++) {
            if (routes[i] != 0) {
                counter++;
            }
        }
        numOfRows = counter + 1;
        return counter;
    }

    public DetailsMixScreen(ExportData mixData, JTable generalTable) throws Exception {

        this.mixData = mixData;
        panel1 = new JPanel();
        numOfRoute( mixData );
        decimalFormat.setGroupingUsed( true );
        decimalFormat.setGroupingSize( 3 );

        routes = new ArrayList<>();
        routes.add( mixData.getKalatz() );
        routes.add( mixData.getKatz() );
        routes.add( mixData.getVar5CPI() );
        routes.add( mixData.getVar5Const() );
        routes.add( mixData.getPrime() );





        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );

        table2 = generalTable;
        JScrollPane tableGeneral = new JScrollPane( table2 );
        tableGeneral.setPreferredSize( new Dimension( 800, 40 ) );
        panel1.add( tableGeneral, BorderLayout.CENTER );

        createTable1();

        createDataPaymentTable(1);



        JScrollPane calcB = new JScrollPane(calcButton);
        panel1.add( calcB );
        calcB.set

        JScrollPane textField = new JScrollPane(numberPaymentText);
        panel1.add( textField );

        JScrollPane textLabel = new JScrollPane(Label);
        panel1.add( textLabel );

        this.add( panel1, BorderLayout.CENTER );
        this.setPreferredSize( new Dimension( 1200, 600 ) );


        this.setTitle( mixData.getMixName() + " Details" );
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.pack();
        this.setVisible( true );


        calcButton.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int numOfPayment = (int) Double.parseDouble( numberPaymentText.getText() );
                createDataPaymentTable(numOfPayment);
            }
        } );
    }

    public void createTable1() throws Exception {
        //headers for the table
        String[] columns = new String[]{
                "Route Name", "Percentage Of Pv", "Rate", "Nper", "Pv", "First Repay",
                "Total Payment Main", "Total Interest Payment Main", "Max Month Repay Main", "Refund ratio % Main", "Increase in refund % Main",
                "Total Payment Worst", "Total Interest Payment Worst", "Max Month Repay Worst", "Refund ratio % Worst", "Increase in refund % Worst"
        };

        //actual data for the table in a 2d array
        Object[][] data = new Object[numOfRows][16];
        int i = 0;
        for (BaseRoute route : routes) {
            if (route.getPv() != 0) {
                data[i][0] = route.getName(); //route Name
                data[i][1] = df2.format( (route.getPv() / mixData.getMortgage()) * 100 ) + "%"; //Percentage Of Pv %
                data[i][2] = df2.format( (route.getRate()) ) + "%"; //Rate %
                data[i][3] = decimalFormat.format( route.getNper() ); //Nper.
                data[i][4] = decimalFormat.format( route.getPv() ) + " ₪"; //Pv.
                data[i][5] = decimalFormat.format( (int) route.firstMonthRepay() ) + " ₪"; //first repay.

                //main
                data[i][6] = decimalFormat.format( (int) route.getTotalPaymentMain() ) + " ₪"; //Total Payment Main
                data[i][7] = decimalFormat.format( (int) route.getTotalRatePaymentMain() ) + " ₪"; //Total Interest Payment Main
                data[i][8] = decimalFormat.format( (int) route.getMaxMonthRepaymentMain() ) + " ₪"; // Max Month Repay Main
                data[i][9] = df2.format( (route.getTotalPaymentMain() / route.getPv()) * 100 ) + "%"; //Refund ratio Main %
                data[i][10] = df2.format( ((route.getMaxMonthRepaymentMain() / route.firstMonthRepay()) * 100) - 100 ) + "%"; //Increase in refund Main %

                //worst
                data[i][11] = decimalFormat.format( (int) route.getTotalPaymentWorst() ) + " ₪"; //Total Payment Worst
                data[i][12] = decimalFormat.format( (int) route.getTotalRatePaymentWorst() ) + " ₪"; //Total Interest Payment Worst
                data[i][13] = decimalFormat.format( (int) route.getMaxMonthRepaymentWorst() ) + " ₪"; // Max Month Repay Worst
                data[i][14] = df2.format( (route.getTotalPaymentWorst() / route.getPv()) * 100 ) + "%"; //Refund ratio Worst %
                data[i][15] = df2.format( ((route.getMaxMonthRepaymentWorst() / route.firstMonthRepay()) * 100) - 100 ) + "%"; //Increase in refund Worst %
                i++;
            }
        }

        /**
         * total line
         */
        data[numOfRows - 1][0] = "Total";
        data[numOfRows - 1][4] = mixData.getMortgage() + " ₪";

        data[numOfRows - 1][5] = decimalFormat.format( (int) mixData.getMergeListMain().get( 0 ).getMonthlyRepayment() ) + " ₪"; //first repay Main.
        data[numOfRows - 1][6] = decimalFormat.format( (int) mixData.getAllTotalPaymentMain() ) + " ₪"; //Total Payment Main.
        data[numOfRows - 1][7] = decimalFormat.format( (int) mixData.getAllRateTotalPaymentMain() ) + " ₪"; //Total Interest Payment Main.
        data[numOfRows - 1][8] = decimalFormat.format( (int) mixData.getMaxMonthRepayMain() ) + " ₪"; //Max Month Repay Main.
        data[numOfRows - 1][9] = df2.format( (mixData.getAllTotalPaymentMain() / mixData.getMortgage()) * 100 ) + "%"; //Refund ratio  % Main
        data[numOfRows - 1][10] = df2.format( ((mixData.getMaxMonthRepayMain() / mixData.getAllFirstMonthRepayment()) * 100) - 100 ) + "%"; //Increase in refund  % Main

        //worst
        data[numOfRows - 1][11] = decimalFormat.format( (int) mixData.getAllTotalPaymentWorst() ) + " ₪"; //Total Payment Worst
        data[numOfRows - 1][12] = decimalFormat.format( (int) mixData.getAllRateTotalPaymentWorst() ) + " ₪"; //Total Interest Payment Worst
        data[numOfRows - 1][13] = decimalFormat.format( (int) mixData.getMaxMonthRepayWorst() ) + " ₪"; // Max Month Repay Worst
        data[numOfRows - 1][14] = df2.format( (mixData.getAllTotalPaymentWorst() / mixData.getMortgage()) * 100 ) + "%"; //Refund ratio Worst %
        data[numOfRows - 1][15] = df2.format( ((mixData.getMaxMonthRepayWorst() / mixData.getAllFirstMonthRepayment()) * 100) - 100 ) + "%"; //Increase in refund Worst %

        DefaultTableModel model = new DefaultTableModel( data, columns );
        final CustomCellRenderer renderer = new CustomCellRenderer();
        table1 = new JTable( model ) {

            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                return renderer;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                return getValueAt( 0, column ).getClass();
            }

        };


        //create table with data
        table1.setRowHeight( 25 );
        table1.getColumnModel().getColumn( 1 ).setPreferredWidth( 50 );
        table1.getColumnModel().getColumn( 2 ).setPreferredWidth( 70 );
        table1.getColumnModel().getColumn( 3 ).setPreferredWidth( 50 );
        table1.getColumnModel().getColumn( 4 ).setPreferredWidth( 110 );
        table1.getColumnModel().getColumn( 5 ).setPreferredWidth( 80 );
        table1.getColumnModel().getColumn( 6 ).setPreferredWidth( 110 );
        table1.getColumnModel().getColumn( 7 ).setPreferredWidth( 100 );
        table1.getColumnModel().getColumn( 8 ).setPreferredWidth( 80 );
        table1.getColumnModel().getColumn( 9 ).setPreferredWidth( 80 );
        table1.getColumnModel().getColumn( 10 ).setPreferredWidth( 80 );
        table1.getColumnModel().getColumn( 11 ).setPreferredWidth( 100 );
        table1.getColumnModel().getColumn( 12 ).setPreferredWidth( 100 );
        table1.getColumnModel().getColumn( 13 ).setPreferredWidth( 80 );
        table1.getColumnModel().getColumn( 14 ).setPreferredWidth( 80 );
        table1.getColumnModel().getColumn( 15 ).setPreferredWidth( 80 );

        table1.getTableHeader().setBackground( Color.lightGray );

        JScrollPane tablePane = new JScrollPane( table1 );
        tablePane.setPreferredSize( new Dimension( 1000, 200 ) );
        panel1.add( tablePane, BorderLayout.CENTER );

    }
    public void createDataPaymentTable(int numOfPayment) {

        //headers for the table
        String[] columns = new String[]{
                "מסלול", "יתרת הקרן - עיקרי", "תשלום חודשי - עיקרי", "ע''ח הקרן עיקרי", "ע''ח הריבית עיקרי", "סה''כ שולם עד כה עיקרי",
                "יתרת הקרן - מחמיר", "תשלום חודשי - מחמיר", "ע''ח הקרן מחמיר", "ע''ח הריבית מחמיר", "סה''כ שולם עד כה מחמיר"
        };
        /**
         * payment line
         */
        //actual data for the table in a 2d array
        Object[][] data = new Object[numOfRows][11];
        int i = 0;

        for (BaseRoute route : routes) {
            ArrayList<LinePaymentData> routeListMain = route.getLinePaymentDataListMain();
            ArrayList<LinePaymentData> routeListWorst = route.getLinePaymentDataListWorst();

            if (route.getPv() != 0) {
                data[i][0] = route.getName(); //route Name
                //main
                data[i][1] = decimalFormat.format( (int) routeListMain.get( numOfPayment - 1 ).getFundPv() ) + " ₪"; //fund pv main.
                data[i][2] = decimalFormat.format( (int) routeListMain.get( numOfPayment - 1 ).getMonthlyRepayment() ) + " ₪"; //monthly repay main.
                data[i][3] = decimalFormat.format( (int) routeListMain.get( numOfPayment - 1 ).getFundPayment() ) + " ₪"; //fund payment main.
                data[i][4] = decimalFormat.format( (int) routeListMain.get( numOfPayment - 1 ).getRatePayment() ) + " ₪"; //rate pv main.
                data[i][5] = decimalFormat.format( (int) routeListMain.get( numOfPayment - 1 ).getSumPayment() ) + " ₪"; //sum payment main.

                //worst
                data[i][6] = decimalFormat.format( (int) routeListWorst.get( numOfPayment - 1 ).getFundPv() ) + " ₪"; //fund pv worst.
                data[i][7] = decimalFormat.format( (int) routeListWorst.get( numOfPayment - 1 ).getMonthlyRepayment() ) + " ₪"; //monthly repay worst.
                data[i][8] = decimalFormat.format( (int) routeListWorst.get( numOfPayment - 1 ).getFundPayment() ) + " ₪"; //fund payment worst.
                data[i][9] = decimalFormat.format( (int) routeListWorst.get( numOfPayment - 1 ).getRatePayment() ) + " ₪"; //rate pv worst.
                data[i][10] = decimalFormat.format( (int) routeListWorst.get( numOfPayment - 1 ).getSumPayment() ) + " ₪"; //sum payment worst.
                i++;
            }
        }

        /**
         * total line
         */
        ArrayList<LinePaymentData> mergeListMain = mixData.getMergeListMain();
        ArrayList<LinePaymentData> mergeListWorst = mixData.getMergeListWorst();
        data[numOfRows - 1][0] = "Total";

        //main
        data[numOfRows - 1][1] = decimalFormat.format( (int) mergeListMain.get( numOfPayment - 1 ).getFundPv() ) + " ₪"; //fund pv main.
        data[numOfRows - 1][2] = decimalFormat.format( (int) mergeListMain.get( numOfPayment - 1 ).getMonthlyRepayment() ) + " ₪"; //monthly repay main.
        data[numOfRows - 1][3] = decimalFormat.format( (int) mergeListMain.get( numOfPayment - 1 ).getFundPayment() ) + " ₪"; //fund payment main.
        data[numOfRows - 1][4] = decimalFormat.format( (int) mergeListMain.get( numOfPayment - 1 ).getRatePayment() ) + " ₪"; //rate pv main.
        data[numOfRows - 1][5] = decimalFormat.format( (int) mergeListMain.get( numOfPayment - 1 ).getSumPayment() ) + " ₪"; //sum payment main.

        //worst
        data[numOfRows - 1][6] = decimalFormat.format( (int) mergeListWorst.get( numOfPayment - 1 ).getFundPv() ) + " ₪"; //fund pv worst.
        data[numOfRows - 1][7] = decimalFormat.format( (int) mergeListWorst.get( numOfPayment - 1 ).getMonthlyRepayment() ) + " ₪"; //monthly repay worst.
        data[numOfRows - 1][8] = decimalFormat.format( (int) mergeListWorst.get( numOfPayment - 1 ).getFundPayment() ) + " ₪"; //fund payment worst.
        data[numOfRows - 1][9] = decimalFormat.format( (int) mergeListWorst.get( numOfPayment - 1 ).getRatePayment() ) + " ₪"; //rate pv worst.
        data[numOfRows - 1][10] = decimalFormat.format( (int) mergeListWorst.get( numOfPayment - 1 ).getSumPayment() ) + " ₪"; //sum payment worst.

        DefaultTableModel tableDataMort = new DefaultTableModel(data, columns);
        final CustomCellRenderer renderer = new CustomCellRenderer();
        table3 = new JTable( tableDataMort ) {

            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                return renderer;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                return getValueAt( 0, column ).getClass();
            }

        };

        table3.setRowHeight( 25 );
        table3.getColumnModel().getColumn( 0 ).setPreferredWidth( 50 );
        table3.getColumnModel().getColumn( 1 ).setPreferredWidth( 70 );
        table3.getColumnModel().getColumn( 2 ).setPreferredWidth( 50 );
        table3.getColumnModel().getColumn( 3 ).setPreferredWidth( 50 );
        table3.getColumnModel().getColumn( 4 ).setPreferredWidth( 50 );
        table3.getColumnModel().getColumn( 5 ).setPreferredWidth( 70 );
        table3.getColumnModel().getColumn( 6 ).setPreferredWidth( 70 );
        table3.getColumnModel().getColumn( 7 ).setPreferredWidth( 50 );
        table3.getColumnModel().getColumn( 8 ).setPreferredWidth( 50 );
        table3.getColumnModel().getColumn( 9 ).setPreferredWidth( 50 );
        table3.getColumnModel().getColumn( 10 ).setPreferredWidth( 70 );

        table3.getTableHeader().setBackground( Color.lightGray );

        JScrollPane table3General = new JScrollPane( table3 );
        table3General.setPreferredSize( new Dimension( 800, 200 ) );
        panel1.add( table3General, BorderLayout.CENTER );

    }
}
