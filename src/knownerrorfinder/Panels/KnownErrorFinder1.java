/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knownerrorfinder.Panels;

import com.jtattoo.plaf.acryl.AcrylButtonUI;
import com.jtattoo.plaf.mint.MintButtonUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.RowFilter;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.synth.SynthLookAndFeel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import knownerrorfinder.AccessDataFromXML;
import knownerrorfinder.EditUnknownError;
import knownerrorfinder.KnownErrorFileChecker;
import knownerrorfinder.Panels.TabbedLogFiles;
import knownerrorfinder.Tables.KnownErrorTable;
import knownerrorfinder.Tables.LogTable;
import knownerrorfinder.Tables.UnknownErrorTable;
import org.example.knownerror.KnownError;

/**
 *
 * @author samsonaiyegbusi
 */
public class KnownErrorFinder1 extends javax.swing.JFrame {

    /**
     * Creates new form KnownErrorFinder1
     */
    public KnownErrorFinder1() {

        initComponents();

        //obtains known errors from xml
        retrieveKnownErrors();

        //Initialise error tables
        ukeTable = new UnknownErrorTable(searchBox);
        keTable = new KnownErrorTable(ukeTable, searchBox, knownErrors);

        //Adds table to panel
        //stops the labels that show the amount of entries found from being shown
        totalEntriesLabel.setVisible(false);
        specificEntryLabel.setVisible(false);

//        totalEntriesLabel.setText("");
//        specificEntryLabel.setText("");
        //Opens dialog to find an log file when application is opened
        openFile();
        JScrollPane ukeScrollPane = new JScrollPane(ukeTable);
        JScrollPane keScrollPane = new JScrollPane(keTable);

        //IMPORTANT adds a swinglayout 
        //tables will not show otherwise and button will not show underneath
        SpringLayout ktlayout = new SpringLayout();

        knownTab.setLayout(ktlayout);
        knownTab.add(keScrollPane);

        ktlayout.putConstraint(SpringLayout.WEST, keScrollPane, 0, SpringLayout.WEST, knownTab);
        ktlayout.putConstraint(SpringLayout.NORTH, ukeScrollPane, 0, SpringLayout.NORTH, knownTab);
        ktlayout.putConstraint(SpringLayout.EAST, knownTab, 0, SpringLayout.EAST, keScrollPane);
        ktlayout.putConstraint(SpringLayout.SOUTH, knownTab, 0, SpringLayout.SOUTH, keScrollPane);

        //IMPORTANT adds a swinglayout 
        //tables will not show otherwise and button will not show underneath
        SpringLayout uktlayout = new SpringLayout();

        unknownTab.setLayout(uktlayout);
        unknownTab.add(jButton1);
        unknownTab.add(ukeScrollPane);

        uktlayout.putConstraint(SpringLayout.WEST, ukeScrollPane, 0, SpringLayout.WEST, unknownTab);
        uktlayout.putConstraint(SpringLayout.NORTH, ukeScrollPane, 0, SpringLayout.NORTH, unknownTab);
        uktlayout.putConstraint(SpringLayout.WEST, jButton1, 0, SpringLayout.WEST, unknownTab);
        uktlayout.putConstraint(SpringLayout.NORTH, jButton1, 5, SpringLayout.SOUTH, ukeScrollPane);
        uktlayout.putConstraint(SpringLayout.EAST, unknownTab, 0, SpringLayout.EAST, ukeScrollPane);
        uktlayout.putConstraint(SpringLayout.SOUTH, unknownTab, 0, SpringLayout.SOUTH, jButton1);

        knownTab.add(keScrollPane);

        unknownTab.add(ukeScrollPane);

        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
                int index = sourceTabbedPane.getSelectedIndex();

                //clear tables
                keTable.clearKnownErrorTable();
                ukeTable.clearUnknownErrorTable();
                logTable.clearLogTable();

                //update tables
                logTable = logTables.get(index);
                logs = logTable.getOpenedLog();
                logTable.populateLogsTable(logs, keTable);

                //clear search field
                searchBox.setText("");
            }
        };

        logFileTabbedPane.addChangeListener(changeListener);

        //add logTable to panel        
        //adds listener to search box
        searchBoxListener();

    }

    public KnownErrorFinder1(String filename) {

        initComponents();

        //obtains known errors from xml
        retrieveKnownErrors();

        //Initialise error tables
        ukeTable = new UnknownErrorTable(searchBox);
        keTable = new KnownErrorTable(ukeTable, searchBox, knownErrors);

        //Adds table to panel
        //stops the labels that show the amount of entries found from being shown
        totalEntriesLabel.setVisible(false);
        specificEntryLabel.setVisible(false);

        //Opens dialog to find an log file when application is opened
        openFile(filename);
        JScrollPane ukeScrollPane = new JScrollPane(ukeTable);
        JScrollPane keScrollPane = new JScrollPane(keTable);

        //IMPORTANT adds a swinglayout 
        //tables will not show otherwise and button will not show underneath
        SpringLayout ktlayout = new SpringLayout();

        knownTab.setLayout(ktlayout);
        knownTab.add(keScrollPane);

        ktlayout.putConstraint(SpringLayout.WEST, keScrollPane, 0, SpringLayout.WEST, knownTab);
        ktlayout.putConstraint(SpringLayout.NORTH, ukeScrollPane, 0, SpringLayout.NORTH, knownTab);
        ktlayout.putConstraint(SpringLayout.EAST, knownTab, 0, SpringLayout.EAST, keScrollPane);
        ktlayout.putConstraint(SpringLayout.SOUTH, knownTab, 0, SpringLayout.SOUTH, keScrollPane);

        //IMPORTANT adds a swinglayout 
        //tables will not show otherwise and button will not show underneath
        SpringLayout layout = new SpringLayout();

        unknownTab.setLayout(layout);
        unknownTab.add(jButton1);
        unknownTab.add(ukeScrollPane);

        layout.putConstraint(SpringLayout.WEST, ukeScrollPane, 0, SpringLayout.WEST, unknownTab);
        layout.putConstraint(SpringLayout.NORTH, ukeScrollPane, 0, SpringLayout.NORTH, unknownTab);
        layout.putConstraint(SpringLayout.WEST, jButton1, 0, SpringLayout.WEST, knownTab);
        layout.putConstraint(SpringLayout.NORTH, jButton1, 5, SpringLayout.SOUTH, ukeScrollPane);
        layout.putConstraint(SpringLayout.EAST, unknownTab, 0, SpringLayout.EAST, ukeScrollPane);
        layout.putConstraint(SpringLayout.SOUTH, unknownTab, 0, SpringLayout.SOUTH, jButton1);

        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
                int index = sourceTabbedPane.getSelectedIndex();

                //clear tables
                keTable.clearKnownErrorTable();
                ukeTable.clearUnknownErrorTable();
                logTable.clearLogTable();

                //update tables
                logTable = logTables.get(index);
                logs = logTable.getOpenedLog();
                logTable.populateLogsTable(logs, keTable);

                //clear search field
                searchBox.setText("");
            }
        };

        logFileTabbedPane.addChangeListener(changeListener);

        //add logTable to panel        
        //adds listener to search box
        searchBoxListener();

    }

    private List<String> logs;
    private int totalEntriesFound = -1;
    private int currentEntry = 0;
    private int row = 0;
    private List<LogTable> logTables = new ArrayList();

    private String filePath;

    //used as a marker when the next and previous buttons have been pressed
    private int buttonCounter = 0;

    AccessDataFromXML knownErrorsFile = new AccessDataFromXML();
    List<KnownError> knownErrors;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        jTextField1 = new javax.swing.JTextField();
        searchBox = new javax.swing.JTextField();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        knownTab = new javax.swing.JPanel();
        unknownTab = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        specificEntryLabel = new javax.swing.JLabel();
        totalEntriesLabel = new javax.swing.JLabel();
        logFileTabbedPane = new javax.swing.JTabbedPane();

        fileChooser.setDialogTitle("Open Log File");
        fileChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileChooserActionPerformed(evt);
            }
        });

        jTextField1.setText("jTextField1");

        searchBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBoxActionPerformed(evt);
            }
        });
        searchBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchBoxKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout knownTabLayout = new javax.swing.GroupLayout(knownTab);
        knownTab.setLayout(knownTabLayout);
        knownTabLayout.setHorizontalGroup(
            knownTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 249, Short.MAX_VALUE)
        );
        knownTabLayout.setVerticalGroup(
            knownTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 318, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Known", knownTab);

        jButton1.setText("+");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout unknownTabLayout = new javax.swing.GroupLayout(unknownTab);
        unknownTab.setLayout(unknownTabLayout);
        unknownTabLayout.setHorizontalGroup(
            unknownTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(unknownTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(200, Short.MAX_VALUE))
        );
        unknownTabLayout.setVerticalGroup(
            unknownTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, unknownTabLayout.createSequentialGroup()
                .addGap(0, 295, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane3.addTab("Unknown", unknownTab);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/knownerrorfinder/icon-arrow-down-b-128.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/knownerrorfinder/icon-arrow-up-b-128.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        specificEntryLabel.setText("jLabel1");

        totalEntriesLabel.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(logFileTabbedPane)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(392, Short.MAX_VALUE)
                .addComponent(specificEntryLabel)
                .addGap(18, 18, 18)
                .addComponent(totalEntriesLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchBox, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchBox, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(specificEntryLabel)
                    .addComponent(totalEntriesLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                    .addComponent(logFileTabbedPane))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBoxActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_searchBoxActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        //opens new jframe enabling the ability to add the chosen unknown error to known errors
        EditUnknownError addToUnknownFrame = new EditUnknownError();

        //stores the index of the selected row
        int index = ukeTable.getSelectedRow();
        //stores value of selected cell
        String id = ukeTable.getValueAt(index, 0).toString();
        //Enables the name label to be predefined before jframe appears
        addToUnknownFrame.setName(id);
        //allows jframe to be repsonsive
        addToUnknownFrame.pack();
        //close only the opened frame
        addToUnknownFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //makes the jframe visible
        addToUnknownFrame.setVisible(true);

        //Listens to when jframe closes
        WindowAdapter adapter = new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                //checks if the save variable has been changed to true to identify if the unknown error has been saved
                if (addToUnknownFrame.saved == true) {
                    //clears the known and unknown error table so that it can be updated
                    keTable.clearKnownErrorTable();
                    ukeTable.clearUnknownErrorTable();
//                    knownErrorHolder.clear();
//                    unknownErrorHolder.clear();
                    //show dialog informing users the unknown error has been saved
                    JOptionPane.showMessageDialog(addToUnknownFrame, "The exception has been added to the list of Known Errors", "Exception Added", JOptionPane.OK_OPTION);
                    //obtain errors to from xml file
                    knownErrors = knownErrorsFile.retrieveKnownErrors();
                    //populates log table
                    logTable.populateLogsTable(logs, keTable);
                }
            }

        };
        //adds listener
        addToUnknownFrame.addWindowListener(adapter);
        addToUnknownFrame.addWindowFocusListener(adapter);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // notices that the up button has been pressed when button counter equals 1
        buttonCounter = 1;
        //goes to next entry when button is clicked
        iterateDown();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void searchBoxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchBoxKeyReleased
        //resets the row
        row = 0;
        //resets the current entry
        currentEntry = 0;
        //counts the entries found from the value in the search box
        countFoundEntries();
        //finds the first instance of the value
        iterateDown();

    }//GEN-LAST:event_searchBoxKeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        //goes to previous entry when button is clicked
        iterateUp();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void fileChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileChooserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fileChooserActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(KnownErrorFinder1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KnownErrorFinder1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KnownErrorFinder1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KnownErrorFinder1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                KnownErrorFinder1 kef = new KnownErrorFinder1();
                kef.setVisible(true);

            }
        });
    }
//parses and obtains lines from file

    private List<String> linesFromFile(File file) {
        String line;
        List<String> txtLogs = new ArrayList();
        try {
            //Reads in file 
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            //iterates txt file while a line exists
            while ((line = reader.readLine()) != null) {
                //checks for lines that start with a tab
                if (line.startsWith("\t")) {
                    sb.append(line).append("\n\t");
                    continue;
                    //checks for lines that start with a few spaces
                } else if (line.startsWith("    ")) {
                    sb.append(line).append("\n");
                    continue;
                }//add lines to list when there isn't a concatanated string 
                if (sb.toString().equalsIgnoreCase("")) {
                    txtLogs.add(line);
                } else {
                    //when there is a concatanated string, they are added in a row as one row with its predecessor
                    //to reduce column size whilst still maintaining clarity

                    String concat = txtLogs.get(txtLogs.size() - 1).concat("\n" + sb.toString());
                    txtLogs.set(txtLogs.size() - 1, concat);
                    //clear the string builer
                    sb.delete(0, sb.length());
                    //adds line to list
                    txtLogs.add(line);
                }
            }
            reader.close();
            return txtLogs;
        } catch (IOException e) {
            System.err.print(e);
        }
        return null;
    }

    private void iterateDown() {

        int counter = 0;
        String query = searchBox.getText();
        // sends user back to the top when they have found the maximum amount of entries
        if (currentEntry == totalEntriesFound) {
            row = 0;
            currentEntry = 0;
        } else if (totalEntriesLabel.getText().equalsIgnoreCase("not found")) {
            //does nothing when nothing is found
        }       //iterates down through table
        for (; row < logTable.getRowCount(); row++) {
            String next = logTable.getValueAt(row, 1).toString().toLowerCase();
            String lowerCaseQuery = query.toLowerCase();

            if (next.contains(lowerCaseQuery)) {
                counter++;
                //checks that only the next direct iteration is only used
                if (counter == 1) {
                    //selects the next entry and displays it
                    logTable.setRowSelectionInterval(row, row);
                    logTable.convertRowIndexToView(row);
                    logTable.scrollRectToVisible(logTable.getCellRect(row, 1, true));
                    currentEntry++;
                    updateCurrentEntry();
                    row++;
                    break;
                }
                //System.out.println("found");

            }
        }
    }

    private void iterateUp() {
        int counter = 0;
        String query = searchBox.getText();
        // stops the user from going backwards from the first entry
        if (currentEntry == 1) {
            row = 0;
        } else if (totalEntriesLabel.getText().equalsIgnoreCase("not found")) {
            //does nothing when nothing is found
        } else {        //iterates up through table
            for (; row < logTable.getRowCount(); row--) {
                //skips the first 2 iterations because the iterateDown method executes a final increment to row before this
                //button is pressed, thus, the first iteration will look at the next entry, which is skipped, then the second
                //will look at the current entry which will be skipped to then look at the previous entry.
                if (buttonCounter == 1) {
                    buttonCounter++;
                    continue;
                }
                if (buttonCounter == 2) {
                    //resets button counter
                    buttonCounter = 0;
                    continue;
                }

                String previous = logTable.getValueAt(row, 1).toString().toLowerCase();
                String lowerCaseQuery = query.toLowerCase();

                if (previous.contains(lowerCaseQuery)) {
                    counter++;
                    //checks that only the previous direct iteration is only used
                    if (counter == 1) {
                        //selects the previous entry and displays it
                        logTable.setRowSelectionInterval(row, row);
                        logTable.convertRowIndexToView(row);
                        logTable.scrollRectToVisible(logTable.getCellRect(row, 1, true));
                        currentEntry--;
                        updateCurrentEntry();
                        row--;
                        break;
                    }
                }

            }
        }
    }

    // counts the total number of found entries
    public void countFoundEntries() {
        int counter = 0;
        String query = searchBox.getText();

        for (int i = 0; i < logTable.getRowCount(); i++) {
            String next = logTable.getValueAt(i, 1).toString().toLowerCase();
            String lowerCaseQuery = query.toLowerCase();
            if (next.contains(lowerCaseQuery)) {
                counter++;
            }
            if (query.equalsIgnoreCase("")) {
                totalEntriesLabel.setVisible(false);
                //totalEntriesLabel.setText("");

            } else {
                if (counter == 0) {
                    totalEntriesLabel.setText("Not Found");
                    totalEntriesFound = -1;
                    updateCurrentEntry();

                } else {
                    totalEntriesLabel.setText(Integer.toString(counter) + " entries");
                    totalEntriesFound = counter;
                    totalEntriesLabel.setVisible(true);
                }
            }
        }
    }

    //updates the label that shows the current entry
    private void updateCurrentEntry() {

        String query = searchBox.getText();
        if (query.equalsIgnoreCase("")) {
            specificEntryLabel.setVisible(false);
            //specificEntryLabel.setText("");
        } else if (totalEntriesFound == -1) {
            specificEntryLabel.setVisible(false);
            //specificEntryLabel.setText("");
        } else {
            specificEntryLabel.setText(Integer.toString(currentEntry) + " of");
            specificEntryLabel.setVisible(true);
        }
    }

    //opens file
    public void openFile() {

        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            //populates the logtable with the lines from the file
            logs = linesFromFile(file);

            logTable = new LogTable();
            String name = fileChooser.getSelectedFile().getName();
            filePath = fileChooser.getSelectedFile().getPath();
            TabbedLogFiles newPanel = new TabbedLogFiles(keTable, ukeTable, logTable, logs);

            //sets name of tab
            newPanel.setName(name);

            //adds panel to tab
            logFileTabbedPane.add(newPanel);

            //adds table to scroll pane
            JScrollPane scrollPane = new JScrollPane(logTable);
            scrollPane.setSize(newPanel.getWidth(), newPanel.getHeight());

            //populates table
            newPanel.updateTable();
            addLogTable(logTable);

            //adds a box layout
            newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.LINE_AXIS));
            searchBox.setText("");

            
            //adds scrollable table to panel
            newPanel.add(scrollPane);
            
            totalEntriesLabel.setVisible(false);
        specificEntryLabel.setVisible(false);
        }

    }

    //opens file
    public void openFile(String recentFile) {

        File file = new File(recentFile);
        //populates the logtable with the lines from the file
        logs = linesFromFile(file);

        logTable = new LogTable();
        
        String name = file.getName();
        filePath = recentFile;
        TabbedLogFiles newPanel = new TabbedLogFiles(keTable, ukeTable, logTable, logs);

        //sets name of tab
        newPanel.setName(name);

        //adds panel to tab
        logFileTabbedPane.add(newPanel);

        //adds table to scroll pane
        JScrollPane scrollPane = new JScrollPane(logTable);
        scrollPane.setSize(newPanel.getWidth(), newPanel.getHeight());

        //populates table
        newPanel.updateTable();
        logTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        addLogTable(logTable);

        //adds a box layout
        newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.LINE_AXIS));
        searchBox.setText("");
        //adds scrollable table to panel
        newPanel.add(scrollPane);
        
        totalEntriesLabel.setVisible(false);
        specificEntryLabel.setVisible(false);
    }

    private void retrieveKnownErrors() {
        knownErrors = knownErrorsFile.retrieveKnownErrors();
    }

    //adds a listener to the search box
    private void searchBoxListener() {
        searchBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            //once data as been entered in this field from key input or when automatically when clicking a known/unknown
            //error the number of entries and the first occurence will be updated and found. 
            @Override
            public void insertUpdate(DocumentEvent e) {
                row = 0;
                currentEntry = 0;
                countFoundEntries();
                iterateDown();
            }
        });
    }

    private void addLogTable(LogTable logTable) {
        logTables.add(logTable);
    }

    private void removeLogTable(int position) {
        logTables.remove(position);

    }

    public String getFilePath() {
        return filePath;
    }

    private LogTable logTable;
    private KnownErrorTable keTable;
    private UnknownErrorTable ukeTable;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel knownTab;
    private javax.swing.JTabbedPane logFileTabbedPane;
    private javax.swing.JTextField searchBox;
    private javax.swing.JLabel specificEntryLabel;
    private javax.swing.JLabel totalEntriesLabel;
    private javax.swing.JPanel unknownTab;
    // End of variables declaration//GEN-END:variables
}