
import org.apache.poi.ss.formula.functions.Column;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseMotionAdapter;

public class Gui extends JFrame {
    private JPanel mainPanel;
    private JButton CalculateBotton;
    private JTextField propertyText;
    private JTextField mortgageText;
    private JTextField firstMonthlyRepaymentText;
    private JTextField maxMonthlyRepaymentWorstText;
    private JTextField rateCustomerText;
    private JTextField diffNperFactorText;
    private JTextField primeDecreaseText;
    private JCheckBox setDefaultCheckBox;
    private JTextField sizeOfJumpText;
    private JTextField minimumKalatzText;
    private JTextField maximumVar5Text;
    private JTextField maximumCPIText;
    private JCheckBox defaultSettingCheckBox;
    private JButton summaryTableButton;
    private JButton top30Button;
    private JButton top30SortMaxButton;
    private CustomerData customerData;
    private Setting setting;
    private ScenarioGenerator scenarioGenerator;

    public Gui(String title) throws Exception {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE  );
        this.setContentPane( mainPanel );
        this.pack();
        DataList dataList = new DataList();
        scenarioGenerator = null;

        CalculateBotton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (defaultSettingCheckBox.isSelected() == true) {
                    setting = new Setting( 0.05, 0.1, 0.4, 0.6 , 0.7);
                } else {

                    double sizeOfJump = Double.parseDouble( sizeOfJumpText.getText() );
                    double minimumKalatz = Double.parseDouble(minimumKalatzText.getText() );
                    double maximumVar5 = Double.parseDouble( maximumVar5Text.getText());
                    double maximumCpi = Double.parseDouble( maximumCPIText.getText() );


                    setting = new Setting(sizeOfJump, minimumKalatz , maximumVar5 ,  maximumCpi, 0.7);
                }

                if (setDefaultCheckBox.isSelected() == true) {
                    customerData = new CustomerData( 2500000,1200000, 6000, 9500, 3, 0.5, 0.3 );
                } else {
                    int property = (int) (Double.parseDouble( propertyText.getText() ));
                    int mortgage = (int) (Double.parseDouble( mortgageText.getText() ));
                    double firstMonthlyRepayment = Double.parseDouble( firstMonthlyRepaymentText.getText() );
                    double maxMonthlyRepaymentWorst = Double.parseDouble( maxMonthlyRepaymentWorstText.getText() );
                    double primeDecrease = Double.parseDouble( primeDecreaseText.getText() );
                    double diffNperFactor = Double.parseDouble( diffNperFactorText.getText() );
                    int rateCustomer = (int) (Double.parseDouble( rateCustomerText.getText() )); // 1-6, 1 is better.


                    customerData = new CustomerData( property, mortgage, firstMonthlyRepayment, maxMonthlyRepaymentWorst, rateCustomer, primeDecrease, diffNperFactor  );
                }
                Constraints constraints = new Constraints( customerData, setting );
/*
                ScenarioGenerator allRoute = null;
*/
                try {
                    scenarioGenerator = new ScenarioGenerator( constraints);
                    scenarioGenerator.result5Routes(dataList);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        } );

        setDefaultCheckBox.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDefaultCheckBox.setSelected( true );
            }
        } );
        defaultSettingCheckBox.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                defaultSettingCheckBox.setSelected( true );
            }
        } );
        summaryTableButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (scenarioGenerator.getMixBestLists().getSummaryList().size() == 0) {
                    System.out.println("the summary list is empty");
                    return;
                } else {
                    SwingUtilities.invokeLater( new Runnable() {
                        @Override
                        public void run() {
                            SummaryList summaryList = new SummaryList(scenarioGenerator.getMixBestLists().getSummaryList());
                        }
                    } );
                }
            }
        } );
        top30Button.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (scenarioGenerator.getMixBestLists().getTop30().size() == 0) {
                    System.out.println("the Top 30 list is empty");
                    return;
                } else {
                    SwingUtilities.invokeLater( new Runnable() {
                        @Override
                        public void run() {
                            SummaryList summaryList = new SummaryList( scenarioGenerator.getMixBestLists().getTop30() );
                        }
                    } );
                }
            }
        } );
        top30SortMaxButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (scenarioGenerator.getMixBestLists().getLowMaxMonthRepayMain().size() == 0) {
                    System.out.println("the list is empty");
                    return;
                } else {
                    SwingUtilities.invokeLater( new Runnable() {
                        @Override
                        public void run() {
                            SummaryList summaryList = new SummaryList( scenarioGenerator.getMixBestLists().getLowMaxMonthRepayMain() );
                        }
                    } );
                }
            }
        } );
    }
    public static void main(String[] args) throws Exception {
        JFrame frame = new Gui( "Mortgage Calculator" );
        frame.setVisible( true );
    }

}

