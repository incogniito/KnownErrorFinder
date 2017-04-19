/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menus;

import FileAccessors.AccessDataFromXML;
import Frames.AddToKnownError;
import Frames.ShowFullDetails;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import Frames.KnownErrorFinder1;
import javax.xml.datatype.XMLGregorianCalendar;
import org.example.knownerror.KnownError;
/**
 *
 * @author samsonaiyegbusi
 */
public class PopUpMenu {

    public PopUpMenu() {

    }
//pop up menu for only the logs table
    public JPopupMenu logTablePopupMenu(JTable logTable) {

        //Create Pop up Items
        JPopupMenu logTablePopup = new JPopupMenu();
        JMenuItem view = new JMenuItem("View");

        //When View is clicked
        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowFullDetails details = new ShowFullDetails();
                int[] selectedRows = logTable.getSelectedRows();

                if (selectedRows.length != 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int i : selectedRows) {
                        int selectedColumn = 1;
                        String info = logTable.getValueAt(i, selectedColumn).toString();
                        sb.append(info).append("\n");
                    }
                    details.fillDetails(sb.toString());
                } else {

                    int selectedRow = logTable.getSelectedRow();
                    int selectedColumn = logTable.getSelectedColumn();
                    String info = logTable.getValueAt(selectedRow, selectedColumn).toString();

                    details.fillDetails(info);
                }
                details.setVisible(true);
            }

        });

        logTablePopup.add(view);

        return logTablePopup;
    }
    
  //pop up menu for only the logs table
    public JPopupMenu keTablePopupMenu(JTable table, KnownErrorFinder1 finder) {

        //Create Pop up Items
        JPopupMenu logTablePopup = new JPopupMenu();
        JMenuItem view = new JMenuItem("View Details");

        //When View is clicked
        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                                int selectedRow = table.getSelectedRow();

                String exceptionName = table.getValueAt(selectedRow, 0).toString();
                        for(KnownError error : AccessDataFromXML.retrieveKnownErrors()){
                            
                            if (error.getName().equalsIgnoreCase(exceptionName)){
                                
                                XMLGregorianCalendar dateAdded = error.getDateAdded();
                                String solution = error.getSolution();
                                AddToKnownError ake = new AddToKnownError(exceptionName, dateAdded, solution);
                                finder.addWindowListenerToAddKEFrame(ake);
                                ake.setVisible(true);
            }
}
            }
        });

        logTablePopup.add(view);

        return logTablePopup;
    }

}
