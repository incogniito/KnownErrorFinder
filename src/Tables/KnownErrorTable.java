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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import MouseListeners.CustomMouseListener;
import Menus.PopUpMenu;
import org.example.knownerror.KnownError;

/**
 *
 * @author samsonaiyegbusi
 */
public class KnownErrorTable extends JTable {

    private JTextField searchBox;
    private List<KnownError> knownErrors;
    private List<String> knownErrorHolder;
    private UnknownErrorTable ukeTable;

    //column names for log table
   private Object[] knownErrorColumnNames = {"Exception"};
    //creates model for log table
   private DefaultTableModel knownModel; 

    
    
    public KnownErrorTable(UnknownErrorTable ukeTable, JTextField searchBox, List<KnownError> knownErrors) {
        
        this.ukeTable = ukeTable;
        this.searchBox = searchBox;
        this.knownErrors = knownErrors;
        
        knownModel = new DefaultTableModel(new Object[0][0], knownErrorColumnNames) {

           
        //disables fields from being editable
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; //To change body of generated methods, choose Tools | Templates.
        }
    };
        
        knownErrorHolder = new ArrayList();


        setUp();
    }
    
    private void setUp()
    {
         //popup menus
        PopUpMenu popup = new PopUpMenu();
        //assigns popup menus to tables
        CustomMouseListener knownPopUpListener = new CustomMouseListener(popup, this, searchBox);
        this.addMouseListener(knownPopUpListener);
        
    }

    public void rePopulateErrorsTable(String log, List<KnownError> knownErrors) {
        this.knownErrors = knownErrors;
        //only proceeds if known errors exists
        if (knownErrors != null) {
            

        //uses regex to obtain words that have exception within its name

            Pattern p = Pattern.compile("[\\w]+Exception", Pattern.CASE_INSENSITIVE);
            //limits the search to only the first 250 characters.
            if (log.length() > 250) {
                log = log.substring(0, 250);
            }

            Matcher m = p.matcher(log);
            EXCEPTION_FINDER:
            while (m.find()) {
                String word = log.substring(m.start(), m.end());
                if (word.matches("[\\w]+Exception$")) {
                    //checks if word matches the regex (words ending with 'exception')
                    if (!knownErrorHolder.contains(word)) {
                        for (KnownError ke : knownErrors) {
                            if (word.equalsIgnoreCase(ke.getName())) {
                                Object[] o = new Object[1];

                                o[0] = ke.getName();
                                knownModel.addRow(o);
                                knownErrorHolder.add(word);
                                continue EXCEPTION_FINDER;

                            }
                        }

                        ukeTable.checkWordExistsInHolder(word);

                    }
                }
            }
            //sets unknown & known model to unknown table
            setModel(knownModel);
            ukeTable.setModel();
        } else {
            //if no known errors exist then just check for unknown errors
            ukeTable.populateUnknownErrorsTable(log);
        }

    }
    
     public void populateErrorsTable(String log) {
        //only proceeds if known errors exists
        if (knownErrors != null) {
            

        //uses regex to obtain words that have exception within its name

            Pattern p = Pattern.compile("[\\w]+Exception", Pattern.CASE_INSENSITIVE);
            //limits the search to only the first 250 characters.
            if (log.length() > 250) {
                log = log.substring(0, 250);
            }

            Matcher m = p.matcher(log);
            EXCEPTION_FINDER:
            while (m.find()) {
                String word = log.substring(m.start(), m.end());
                if (word.matches("[\\w]+Exception$")) {
                    //checks if word matches the regex (words ending with 'exception')
                    if (!knownErrorHolder.contains(word)) {
                        for (KnownError ke : knownErrors) {
                            if (word.equalsIgnoreCase(ke.getName())) {
                                Object[] o = new Object[1];

                                o[0] = ke.getName();
                                knownModel.addRow(o);
                                knownErrorHolder.add(word);
                                continue EXCEPTION_FINDER;

                            }
                        }

                        ukeTable.checkWordExistsInHolder(word);

                    }
                }
            }
            //sets unknown & known model to unknown table
            setModel(knownModel);
            ukeTable.setModel();
        } else {
            //if no known errors exist then just check for unknown errors
            ukeTable.populateUnknownErrorsTable(log);
        }

    }
    
    public void clearKnownErrorTable(){
        //empties table
        knownModel.setRowCount(0);
        knownErrorHolder.clear();
    }
    
}
