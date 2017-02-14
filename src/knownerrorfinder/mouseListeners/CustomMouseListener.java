/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knownerrorfinder.mouseListeners;

import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import knownerrorfinder.PopUpMenu;
import knownerrorfinder.ShowFullDetails;

/**
 *
 * @author samsonaiyegbusi
 */
public class CustomMouseListener extends MouseAdapter{
    
    private PopUpMenu menu;
    private JTable table;
    private JTable logsTable;
    private JTextField searchField;
    
    //constructor for Log table
    public CustomMouseListener(PopUpMenu menu, JTable logTable){
        this.menu = menu;
        this.logsTable = logTable;
    }
    
    //constructor for error tables
    public CustomMouseListener(PopUpMenu menu, JTable exceptionsTable, JTextField searchField ){
        this.menu = menu;
        this.table = exceptionsTable;
        this.searchField = searchField;
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
            } else {
                int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1 ){

                menu.unknownTablePopupMenu(table, searchField).show(e.getComponent(), e.getX(), e.getY());
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
