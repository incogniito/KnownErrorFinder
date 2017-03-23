/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menus;

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

    //copies a select row(s) and adds them to a string builder and calls the copy method to copy the string to the clipboard 
    public static void copyToClipboard(JTable logTable, JTable keTable, JTable ukeTable) {

        int[] keSelectedRows = keTable.getSelectedRows();
        int[] ukeSelectedRows = ukeTable.getSelectedRows();
        int[] logTableSelectedRows = logTable.getSelectedRows();

        int keIndex = keTable.getSelectedRow();
        int ukeIndex = ukeTable.getSelectedRow();
        int logTableIndex = logTable.getSelectedRow();
        if (keSelectedRows.length != 0) {
            StringBuilder sb = new StringBuilder();
            for (int i : keSelectedRows) {
                int selectedColumn = 0;
                String info = keTable.getValueAt(i, selectedColumn).toString();
                sb.append(info).append("\n");
            }
            copy(sb.toString());
        } else if (keIndex != -1) {
            String value = keTable.getValueAt(keIndex, 0).toString();
            copy(value);
        } else if (ukeSelectedRows.length != 0) {
            StringBuilder sb = new StringBuilder();
            for (int i : ukeSelectedRows) {
                int selectedColumn = 0;
                String info = ukeTable.getValueAt(i, selectedColumn).toString();
                sb.append(info).append("\n");
            }
            copy(sb.toString());
        } else if (ukeIndex != -1) {
            String value = keTable.getValueAt(ukeIndex, 0).toString();
            copy(value);
        } else if (logTableSelectedRows.length != 0) {
            StringBuilder sb = new StringBuilder();
            for (int i : logTableSelectedRows) {
                int selectedColumn = 1;
                String info = logTable.getValueAt(i, selectedColumn).toString();
                sb.append(info).append("\n");
            }
            copy(sb.toString());
        } else if (logTableIndex != -1) {
            String value = keTable.getValueAt(logTableIndex, 0).toString();
            copy(value);
        }

    }
//copies to clipboard
    private static void copy(String text) {
        StringSelection stringSelection = new StringSelection(text.trim());
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(stringSelection, null);
    }
//pastes from clipboard only to the Search box
    public static void paste(JTextField searchField) {
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable t = c.getContents(null);
        try {
            searchField.setText((String) t.getTransferData(DataFlavor.stringFlavor));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//checks if the clipboard has content
    public static boolean clipboardHasContent() {
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable t = c.getContents(null);
        if (t == null) {
            return false;
        } else {
            return true;
        }
    }

    //checks if any of the tables is selected so that it can prevent the copy action from being executed if none is selected
    public static boolean isItemSelected(JTable logTable, JTable keTable, JTable ukeTable) {

        int[] keSelectedRows = keTable.getSelectedRows();
        int[] ukeSelectedRows = ukeTable.getSelectedRows();
        int[] logTableSelectedRows = logTable.getSelectedRows();

        int keIndex = keTable.getSelectedRow();
        int ukeIndex = ukeTable.getSelectedRow();
        int logTableIndex = logTable.getSelectedRow();
        if (keSelectedRows.length != 0) {
            return true;
        } else if (keIndex != -1) {
            return true;
        } else if (ukeSelectedRows.length != 0) {
            return true;
        } else if (ukeIndex != -1) {
            return true;
        } else if (logTableSelectedRows.length != 0) {
            return true;
        } else if (logTableIndex != -1) {
            return true;
        }

        return false;
    }
//selects all rows in a selected table table 
    public static void selectAll(JTable logTable, JTable keTable, JTable ukeTable) {

        int keIndex = keTable.getSelectedRow();
        int ukeIndex = ukeTable.getSelectedRow();
        int logTableIndex = logTable.getSelectedRow();
        if (keIndex != -1) {
            keTable.selectAll();
        } else if (ukeIndex != -1) {
            ukeTable.selectAll();
        } else if (logTableIndex != -1) {
            logTable.selectAll();
        }

    }
//takes user to the search box
    public static void search(JTextField searchBox) {
        searchBox.requestFocus();
    }
}
