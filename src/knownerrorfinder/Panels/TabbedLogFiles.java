/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knownerrorfinder.Panels;

import java.awt.LayoutManager;
import java.util.List;
import javax.swing.JPanel;
import knownerrorfinder.Tables.KnownErrorTable;
import knownerrorfinder.Tables.LogTable;
import knownerrorfinder.Tables.UnknownErrorTable;

/**
 *
 * @author samsonaiyegbusi
 */
public class TabbedLogFiles extends JPanel{
    
    private KnownErrorTable keTable;
    private UnknownErrorTable ukeTable;
    private LogTable logTable;
    private List<String> logs;
    private javax.swing.GroupLayout knownTabLayout;
    
    public TabbedLogFiles(KnownErrorTable keTable, UnknownErrorTable ukeTable, LogTable logTable, List<String> logs){
        
        this.keTable = keTable;
        this.ukeTable = ukeTable;
        this.logs = logs;
        this.logTable = logTable;
        
       
                
                
    }
    
    public void updateTable(){
                logTable.populateLogsTable(logs, keTable);
    }

    @Override
    public void setLayout(LayoutManager mgr) {
        super.setLayout(mgr); //To change body of generated methods, choose Tools | Templates.
        
        
        
        
        
    }
    
    
    
    
}
