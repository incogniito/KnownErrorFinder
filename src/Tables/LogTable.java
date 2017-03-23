/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import FileAccessors.AccessDataFromXML;
import MouseListeners.CustomMouseListener;
import Menus.PopUpMenu;
import org.example.knownerror.KnownError;

/**
 *
 * @author samsonaiyegbusi
 */
public class LogTable extends JTable {
    
    private List<String> logs = new ArrayList();
        //column names for log table
    private Object[] columnNames = {"No", "Message"};
    
    
   private DefaultTableModel model ;
       
    public LogTable(){        
        model = new DefaultTableModel(new Object[0][0], columnNames) {
        //disables fields from being editable
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; //To change body of generated methods, choose Tools | Templates.
        }
    };
      
        
    }
    
    public void populateLogsTable(List<String> logs, KnownErrorTable keTable) {

        int counter = 0;
        this.logs = logs;
        model.setRowCount(0);
 
        //looks for each log in the list of logs
        for (String log : logs) {
            Object[] o = new Object[2];
            counter++;

            o[0] = counter;
            o[1] = log;
            //adds each line to table model
            model.addRow(o);
            //populates known and unknown errors table with every line
            keTable.populateErrorsTable(log);
        }
        
        //assigns model to table
        setModel(model);
        //popup menus
        PopUpMenu popup = new PopUpMenu();
        //assigns popup menus to tables
        CustomMouseListener popUpListener = new CustomMouseListener(popup, this);
        addMouseListener(popUpListener);
    }
    
    public void rePopulateLogsTable(List<String> logs, KnownErrorTable keTable, List<KnownError> knownErrors) {

        int counter = 0;
        this.logs = logs;
        model.setRowCount(0);
 
        //looks for each log in the list of logs
        for (String log : logs) {
            Object[] o = new Object[2];
            counter++;

            o[0] = counter;
            o[1] = log;
            //adds each line to table model
            model.addRow(o);
            //populates known and unknown errors table with every line
            keTable.rePopulateErrorsTable(log, knownErrors);
        }
        
        //assigns model to table
        setModel(model);
        //popup menus
        PopUpMenu popup = new PopUpMenu();
        //assigns popup menus to tables
        CustomMouseListener popUpListener = new CustomMouseListener(popup, this);
        addMouseListener(popUpListener);
    }
    
    public List<String> getOpenedLog(){
        //return logs in current log file
        return logs;
    }
     public void clearLogTable(){
         //clears table
         model.setRowCount(0);
         
     }

     public void setColumnSize(JTable table){
                columnModel.getColumn(0).setPreferredWidth(15);
                columnModel.getColumn(1).setPreferredWidth(200);

         
     }
     
    
    
}
