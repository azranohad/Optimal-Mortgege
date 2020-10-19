
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class SummaryList extends JFrame {
    private JPanel panel1;
    private JTable table1;
    private JTable table2;
    private ArrayList<ActionListener> action;


    public SummaryList( ArrayList<ExportData> summaryTable ) {


        panel1 = new JPanel();
        action = new ArrayList<>();

        DecimalFormat df = new DecimalFormat( "#" );
        DecimalFormat df1 = new DecimalFormat( "#.#" );
        DecimalFormat df2 = new DecimalFormat( "#.##" );
        DecimalFormat decimalFormat = new DecimalFormat( "#.##" );
        decimalFormat.setGroupingUsed( true );
        decimalFormat.setGroupingSize( 3 );


        String[] columnsMortgage = new String[]{
                "תאריך", "שווי נכס", "סכום ההלוואה", "אחוז מימון", "החזר חודשי התחלתי", "החזר חודשי מקס'"
        };

        ExportData general = summaryTable.get( 0 );
        Object[][] dataMortgage =

                {
                        {LocalDate.now(),decimalFormat.format( (int) general.getProperty() ) + " ₪",
                                decimalFormat.format( (int) general.getMortgage() ) + " ₪",
                                df2.format( (general.getPercentage()) * 100 ) + "%",
                                decimalFormat.format( (int) general.getFirstMonthlyRepaymentDesirable()) + " ₪",
                                decimalFormat.format( (int)  general.getMaxMonthlyRepaymentWorstDesirable()) + " ₪",
                                df2.format(   general.getPrimeDecrease() ) + "%"
                        }
                };
        DefaultTableModel tableDataMort = new DefaultTableModel(dataMortgage, columnsMortgage);
        table2 = new JTable( tableDataMort );

        //headers for the table
        String[] columns = new String[]{
                "Mix Name", "First Repay", "Total Payment Main", "Total Interest Payment Main", "Max Month Repay Main", "Refund ratio % Main", "Increase in refund % Main",
                "Total Payment Worst", "Total Interest Payment Worst", "Max Month Repay Worst", "Refund ratio % Worst", "Increase in refund % Worst",
                "CPI-linked %", "Variable %", "long route", "Details"
        };


        //actual data for the table in a 2d array
        Object[][] data = new Object[summaryTable.size()][16];
        for (int i = 0; i < summaryTable.size(); i++) {

                ExportData dataMix = summaryTable.get( i );

                data[i][0] = "Mix " + (i + 1); //Mix Name
                dataMix.setMixName( (String) data[i][0] );
                data[i][1] = decimalFormat.format( (int) dataMix.getAllFirstMonthRepayment() ) + " ₪"; //First Repay
                data[i][2] = decimalFormat.format( (int) dataMix.getAllTotalPaymentMain() ) + " ₪"; //Total Payment
                data[i][3] = decimalFormat.format( (int) dataMix.getAllRateTotalPaymentMain() ) + " ₪"; //Total Interest Payment
                data[i][4] = decimalFormat.format( (int) dataMix.getMaxMonthRepayMain() ) + " ₪"; // Max Month Repay
                data[i][5] = df2.format( (dataMix.getAllTotalPaymentMain() / dataMix.getMortgage()) * 100 ) + "%"; //Refund ratio %
                data[i][6] = df2.format( (dataMix.getMaxMonthRepayMain() / dataMix.getAllFirstMonthRepayment()) * 100 ) + "%"; //Increase in refund %
                data[i][7] = decimalFormat.format( (int) dataMix.getAllTotalPaymentWorst() ) + " ₪"; //Total Payment worst
                data[i][8] = decimalFormat.format( (int) dataMix.getAllRateTotalPaymentWorst() ) + " ₪"; //Total Interest Payment worst
                data[i][9] = decimalFormat.format( (int) dataMix.getMaxMonthRepayWorst() ) + " ₪"; //Max Month Repay worst
                data[i][10] = df2.format( (dataMix.getAllTotalPaymentWorst() / dataMix.getMortgage()) * 100 ) + "%"; //Refund ratio %
                data[i][11] = df2.format( (dataMix.getMaxMonthRepayWorst() / dataMix.getAllFirstMonthRepayment()) * 100 ) + "%"; //Increase in refund %
                data[i][12] = (df2.format( ((dataMix.getKatzPv() + dataMix.getVar5CpiPv()) / dataMix.getMortgage()) * 100 ) + "%"); //CPI-linked %
                data[i][13] = (df2.format( ((dataMix.getVar5CpiPv() + dataMix.getVar5ConstPv()) / dataMix.getMortgage()) * 100 ) + "%"); //Variable %
                data[i][14] = Math.max( dataMix.getKalatzNper(), (Math.max( dataMix.getKatzNper(),
                        Math.max( dataMix.getVar5ConstNper(), Math.max( dataMix.getVar5ConstNper(),
                                dataMix.getPrimeNper() ) ) )) ) / 12; //long route

            int finalI = i;
            Action details = new AbstractAction()
            {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater( new Runnable() {
                        @Override
                        public void run() {
                            try {
                                DetailsMixScreen detailsMixScreen = new DetailsMixScreen(dataMix, table2);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }
                    } );
                }
            } ;
                action.add( details );
            }

            DefaultTableModel model = new DefaultTableModel(data, columns);
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


        ButtonColumn buttonColumn = new ButtonColumn(table1, action , 15);
        buttonColumn.setMnemonic( KeyEvent.VK_D);

        //create table with data
        table1.setRowHeight( 25 );
        table1.getColumnModel().getColumn( 2 ).setPreferredWidth( 120 );
        table1.getColumnModel().getColumn( 3 ).setPreferredWidth( 120 );
        table1.getColumnModel().getColumn( 7 ).setPreferredWidth( 120 );
        table1.getColumnModel().getColumn( 8 ).setPreferredWidth( 120 );

        table1.getTableHeader().setBackground( Color.lightGray );


        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );



        JScrollPane tableGeneral = new JScrollPane( table2 );
        tableGeneral.setPreferredSize( new Dimension( 800, 40 ) );
        panel1.add( tableGeneral, BorderLayout.CENTER );

        JScrollPane tablePane = new JScrollPane( table1 );
        tablePane.setPreferredSize( new Dimension( 1024, 600 ) );
        panel1.add( tablePane, BorderLayout.CENTER );

        this.add( panel1, BorderLayout.CENTER );
        this.setPreferredSize( new Dimension( 1200, 600 ) );

        /*        this.add(tablePane, BorderLayout.CENTER);*/

        this.setTitle( "Summary Table" );
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.pack();
        this.setVisible( true );
    }
}