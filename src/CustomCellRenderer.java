import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomCellRenderer extends DefaultTableCellRenderer {

    /**
     * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(JTable, Object, boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {

        Component rendererComp = super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, column);

        // Very important to handle selected items (render them inversely colored)

        int rowTotal = -1;
        String headerColumn = (String) table.getColumnModel().getColumn( column ).getHeaderValue();

        if ((headerColumn == "Total Payment Main") ||
                (headerColumn == "Total Interest Payment Main") ||
                (headerColumn == "Max Month Repay Main")||
                (headerColumn == "Refund ratio % Main")||
                (headerColumn == "Increase in refund % Main") ||
                (headerColumn == "יתרת הקרן - עיקרי")||
                (headerColumn == "תשלום חודשי - עיקרי")||
                (headerColumn == "ע''ח הקרן עיקרי")||
                (headerColumn == "ע''ח הריבית עיקרי")||
                (headerColumn == "סה''כ שולם עד כה עיקרי")) {
            rendererComp.setBackground(Color.yellow);
            rendererComp.setForeground(Color.BLACK);
        } else if ((headerColumn == "Total Payment Worst") ||
                (headerColumn == "Total Interest Payment Worst") ||
                (headerColumn == "Max Month Repay Worst")||
                (headerColumn == "Refund ratio % Worst")||
                (headerColumn == "Increase in refund % Worst")||
                (headerColumn == "יתרת הקרן - מחמיר")||
                (headerColumn == "תשלום חודשי - מחמיר")||
                (headerColumn == "ע''ח הקרן מחמיר")||
                (headerColumn == "ע''ח הריבית מחמיר")||
                (headerColumn == "סה''כ שולם עד כה מחמיר")) {
            rendererComp.setBackground( Color.RED );
            rendererComp.setForeground( Color.BLACK );
        } else {
            rendererComp.setBackground(Color.white);
            rendererComp.setForeground(Color.black);
        }

        return rendererComp;
    }
}