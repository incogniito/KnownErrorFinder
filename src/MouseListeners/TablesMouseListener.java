/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MouseListeners;

import FileAccessors.AccessDataFromXML;
import Frames.AddToKnownError;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import Frames.KnownErrorFinder1;
import Frames.MainFrame;
import Menus.PopUpMenu;
import Frames.ShowFullDetails;
import javax.xml.datatype.XMLGregorianCalendar;
import org.example.knownerror.KnownError;

/**
 *
 * @author samsonaiyegbusi
 */
public class TablesMouseListener extends MouseAdapter{
    
    private PopUpMenu menu;
    private JTable table;
    private JTable logsTable;
    private JTextField searchField;
    private KnownErrorFinder1 finder;
    
    //constructor for Log table
    public TablesMouseListener(PopUpMenu menu, JTable logTable){
        this.menu = menu;
        this.logsTable = logTable;
    }
    
    //constructor for error tables
    public TablesMouseListener(PopUpMenu menu, JTable exceptionsTable, JTextField searchField, KnownErrorFinder1 finder ){
        this.menu = menu;
        this.table = exceptionsTable;
        this.searchField = searchField;
        this.finder = finder;
    }
    
    
    @Override
    public void mouseClicked (MouseEvent e)
    {
        
        //Determine if right button is clicked
        if(SwingUtilities.isRightMouseButton(e))
        {
            
            doPop(e);
            
        }
        //checks for double click
        if (e.getClickCount() == 2)
        {
            if (logsTable != null)
            {
                showDetails();
            } //checks for single click
        } else if (e.getClickCount() == 1)
        {
             if (table != null){
              findOccurences();
            } 
        }    
    }
    
    
    private void doPop(MouseEvent e){
        

            if(logsTable != null){
                int selectedRow = logsTable.getSelectedRow();
                    if (selectedRow != -1 )
                    {
                 menu.logTablePopupMenu(logsTable).show(e.getComponent(), e.getX(), e.getY());
                    }
            }else if(table != null){
                
                int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1 )
                    {
                                         menu.keTablePopupMenu(table, finder).show(e.getComponent(), e.getX(), e.getY());

                        
                            }
                        }
                        
                    }
            
        
        
        
    
    //opens another jframe to give a larger view of the selected field(s)
    private void showDetails(){
        
        ShowFullDetails details = new ShowFullDetails();
                    int[] selectedRows = logsTable.getSelectedRows();
                    
                    if(selectedRows.length != 0)
                    {
                        StringBuilder sb = new StringBuilder();
                        for(int i : selectedRows)
                        {
                             int selectedColumn = 1;
                            String info = logsTable.getValueAt(i, selectedColumn).toString();
                            sb.append(info).append("\n");
                        }
                        details.fillDetails(sb.toString());
                    } else {
                    
                    int selectedRow = logsTable.getSelectedRow();
                    int selectedColumn = logsTable.getSelectedColumn();
                    if (selectedRow != -1 || selectedColumn != -1)
                    {
                    String info = logsTable.getValueAt(selectedRow, selectedColumn).toString();
                    
                    details.fillDetails(info);
                    }
                    }
                    details.setVisible(true);
    }
    
    
    
    private void findOccurences(){
       int rowIndex = table.getSelectedRow();
       if (rowIndex != -1)
       {
       String valueAtRow = table.getValueAt(rowIndex, 0).toString();
              searchField.setText(valueAtRow);
       }
    }
}
