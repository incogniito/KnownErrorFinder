/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knownerrorfinder;

import java.awt.PopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

/**
 *
 * @author samsonaiyegbusi
 */
public class CustomMouseListener extends MouseAdapter{
    
    private PopUpMenu menu;
    private JTable table;
    private JTable logsTable;
    
    
    public CustomMouseListener(PopUpMenu menu, JTable logTable){
        this.menu = menu;
        this.table = logTable;
    }
    
    
    public CustomMouseListener(PopUpMenu menu, JTable exceptionsTable, JTable logTable){
        this.menu = menu;
        this.table = exceptionsTable;
        this.logsTable = logTable;
    }
    
    
    @Override
    public void mouseClicked (MouseEvent e)
    {
        
        //Determine if right button is clicked
        if(SwingUtilities.isRightMouseButton(e))
        {
            doPop(e);
        }
        
        if (e.getClickCount() == 2)
        {
            if (table.getSelectedColumnCount()>1)
            {
                showDetails();
            } 
        } else if (e.getClickCount() == 1)
        {
            if (table.getSelectedColumnCount()>1)
            {
            } else {
                findUnknownOccurences();
            }
        }
        
        
        
    }
    
    
    private void doPop(MouseEvent e){
        
        if (table.getSelectedRow() != -1){
            if(table.getColumnCount() > 1){
                 menu.logTablePopupMenu(table).show(e.getComponent(), e.getX(), e.getY());
            } else {
                
                
            }
        }
        
        
    }
    
    private void showDetails(){
        
        ShowFullDetails details = new ShowFullDetails();
                    int[] selectedRows = table.getSelectedRows();
                    
                    if(selectedRows.length != 0)
                    {
                        StringBuilder sb = new StringBuilder();
                        for(int i : selectedRows)
                        {
                             int selectedColumn = 1;
                            String info = table.getValueAt(i, selectedColumn).toString();
                            sb.append(info).append("\n");
                        }
                        details.fillDetails(sb.toString());
                    } else {
                    
                    int selectedRow = table.getSelectedRow();
                    int selectedColumn = table.getSelectedColumn();
                    String info = table.getValueAt(selectedRow, selectedColumn).toString();
                    
                    details.fillDetails(info);
                    }
                    details.setVisible(true);
    }
    
    
    
    private void findUnknownOccurences(){
       int rowIndex = table.getSelectedRow();
       String valueAtRow = table.getValueAt(rowIndex, 0).toString();
       
       
    }
}
