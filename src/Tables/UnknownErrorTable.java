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

/**
 *
 * @author samsonaiyegbusi
 */
public class UnknownErrorTable extends JTable {
    
   private JTextField searchBox;
   private List<String> unknownErrorHolder;
   
       //column names for log table
   private Object[] unknownErrorColumnNames = {"Exception"};
    //creates model for log table
    private DefaultTableModel unknownModel; 


    public UnknownErrorTable(JTextField searchBox) {
        
        this.searchBox = searchBox;
        
        unknownErrorHolder = new ArrayList();
        
        unknownModel = new DefaultTableModel(new Object[0][0], unknownErrorColumnNames) {
       
        //disables fields from being editable
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; //To change body of generated methods, choose Tools | Templates.
        }
    };
        
        setUp();
    }
    
    private void setUp()
    {
         //popup menus
        PopUpMenu popup = new PopUpMenu();
        //assigns popup menus to tables
        CustomMouseListener unknownPopUpListener = new CustomMouseListener(popup, this, searchBox);
        this.addMouseListener(unknownPopUpListener);
        
    }
    
    public void checkWordExistsInHolder(String word){
        //checks if the first character to ensure that the word is an actual exception
                if(Character.isUpperCase(word.charAt(0)))
                {

        if (!unknownErrorHolder.contains(word)) {
                            Object[] o = new Object[1];

                            o[0] = word;
                            unknownModel.addRow(o);
                            unknownErrorHolder.add(word);
                        }

                }
    }
    
    public void setModel(){
        this.setModel(unknownModel);
    }
    
    public void populateUnknownErrorsTable(String log) {
        //uses regex to obtain words that have exception within its name
        Pattern p = Pattern.compile("[\\w]+Exception", Pattern.CASE_INSENSITIVE);


        
        if (log.length() > 250) {
            log = log.substring(0, 250);
        }

        Matcher m = p.matcher(log);
        while (m.find()) {
            String word = log.substring(m.start(), m.end());
            //checks if word matches the regex (words ending with 'exception')
            if (word.matches("[\\w]+Exception$")) {
                //checks if the first character to ensure that the word is an actual exception
                if(Character.isUpperCase(word.charAt(0)))
                {
                if (!unknownErrorHolder.contains(word)) {
                    Object[] o = new Object[1];

                    o[0] = word;
                    unknownModel.addRow(o);
                    unknownErrorHolder.add(word);
                }
                }
            }
        }
        //sets unknown model to unknown table
        this.setModel(unknownModel);
    }

     public void clearUnknownErrorTable(){
         //empties table
        unknownModel.setRowCount(0);
        unknownErrorHolder.clear();
    }
 
}
