/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knownerrorfinder;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author samsonaiyegbusi
 */
public final class EditMenuOptions {
    
    
    
    public static void copyToClipboard(JTable logTable, JTable keTable, JTable ukeTable){
        
        int[] keSelectedRows = keTable.getSelectedRows();
int[] ukeSelectedRows = ukeTable.getSelectedRows();
int[] logTableSelectedRows = logTable.getSelectedRows();

int keIndex = keTable.getSelectedRow();
int ukeIndex = ukeTable.getSelectedRow();
int logTableIndex = logTable.getSelectedRow();
        if (keSelectedRows.length != 0)
        {
            StringBuilder sb = new StringBuilder();
                    for (int i : keSelectedRows) {
                        int selectedColumn = 0;
                        String info = keTable.getValueAt(i, selectedColumn).toString();
                        sb.append(info).append("\n");
                    }
                    copy(sb.toString());
        }
        else if (keIndex != -1){
            String value = keTable.getValueAt(keIndex, 0).toString();
            copy(value);   
        }else if (ukeSelectedRows.length != 0)
        {
            StringBuilder sb = new StringBuilder();
                    for (int i : ukeSelectedRows) {
                        int selectedColumn = 0;
                        String info = ukeTable.getValueAt(i, selectedColumn).toString();
                        sb.append(info).append("\n");
                    }
                    copy(sb.toString());
        }
        else if (ukeIndex != -1){
            String value = keTable.getValueAt(ukeIndex, 0).toString();
            copy(value);   
        }else if (logTableSelectedRows.length != 0)
        {
            StringBuilder sb = new StringBuilder();
                    for (int i : logTableSelectedRows) {
                        int selectedColumn = 1;
                        String info = logTable.getValueAt(i, selectedColumn).toString();
                        sb.append(info).append("\n");
                    }
                    copy(sb.toString());
        }
        else if (logTableIndex != -1){
            String value = keTable.getValueAt(logTableIndex, 0).toString();
            copy(value);   
        }
        
        
    }
    
   private static void copy(String text){
        StringSelection stringSelection = new StringSelection(text.trim());
Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
clpbrd.setContents(stringSelection, null);
    }
   
   public static void paste( JTextField searchField){
    Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
    Transferable t = c.getContents(null);
    try {
        searchField.setText((String) t.getTransferData(DataFlavor.stringFlavor));
    } catch (Exception e){
        e.printStackTrace();
    }
}
   
   
   public static boolean clipboardHasContent(){
       Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
    Transferable t = c.getContents(null);
        if (t == null){
            return false;
        } else {
            return true;
        }
   }
   
   public static boolean isItemSelected(JTable logTable, JTable keTable, JTable ukeTable){
       
         int[] keSelectedRows = keTable.getSelectedRows();
int[] ukeSelectedRows = ukeTable.getSelectedRows();
int[] logTableSelectedRows = logTable.getSelectedRows();

int keIndex = keTable.getSelectedRow();
int ukeIndex = ukeTable.getSelectedRow();
int logTableIndex = logTable.getSelectedRow();
        if (keSelectedRows.length != 0)
        {
            return true;
        }
        else if (keIndex != -1){
           return true;
        }else if (ukeSelectedRows.length != 0)
        {
           return true;
        }
        else if (ukeIndex != -1){
            return true;
        }else if (logTableSelectedRows.length != 0)
        {
            return true;
        }
        else if (logTableIndex != -1){
            return true;
   }
    
        return false;
}
   
   public static void selectAll(JTable logTable, JTable keTable, JTable ukeTable){
       
       int keIndex = keTable.getSelectedRow();
int ukeIndex = ukeTable.getSelectedRow();
int logTableIndex = logTable.getSelectedRow();
        if (keIndex != -1){
            keTable.selectAll();
        }
        else if (ukeIndex != -1){
            ukeTable.selectAll();
        }
        else if (logTableIndex != -1){
            logTable.selectAll();
        }
        
        
   
   }
   
    public static void search(JTextField searchBox){
        searchBox.requestFocus();
    }
}