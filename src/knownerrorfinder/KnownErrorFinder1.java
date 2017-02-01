/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knownerrorfinder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JPopupMenu;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import knownerrors.*;

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
        knownErrorFileCheck();
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
          
            List<String> logs = linesFromFile(file);
            populateLogsTable(logs);
            
            
        }
    }
    
    String filePath;
    List<String> unknownErrorHolder = new ArrayList();
    Object[] columnNames = {"No","Message"};
    DefaultTableModel model = new DefaultTableModel(new Object[0][0], columnNames){
      
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; //To change body of generated methods, choose Tools | Templates.
        }
      }
;
    
    Object[] unknownErrorColumnNames = {"Exception"};
    DefaultTableModel unknownModel = new DefaultTableModel(new Object[0][0], unknownErrorColumnNames){
      
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; //To change body of generated methods, choose Tools | Templates.
        }
      }
;
    
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
        openButton = new javax.swing.JButton();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        knownTab = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        knownTable = new javax.swing.JTable();
        unknownTab = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        unknownTable = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        logTable = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        fileChooser.setDialogTitle("Open Log File");

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("KNOWN ERROR FINDER");

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

        openButton.setText("Open Log File");
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });

        knownTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Title 1"
            }
        ));
        jScrollPane3.setViewportView(knownTable);

        javax.swing.GroupLayout knownTabLayout = new javax.swing.GroupLayout(knownTab);
        knownTab.setLayout(knownTabLayout);
        knownTabLayout.setHorizontalGroup(
            knownTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(knownTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(14, 14, 14))
        );
        knownTabLayout.setVerticalGroup(
            knownTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(knownTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Known", knownTab);

        jButton1.setText("+");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        unknownTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Title 1"
            }
        ));
        jScrollPane2.setViewportView(unknownTable);

        javax.swing.GroupLayout unknownTabLayout = new javax.swing.GroupLayout(unknownTab);
        unknownTab.setLayout(unknownTabLayout);
        unknownTabLayout.setHorizontalGroup(
            unknownTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(unknownTabLayout.createSequentialGroup()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 202, Short.MAX_VALUE))
            .addGroup(unknownTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        unknownTabLayout.setVerticalGroup(
            unknownTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, unknownTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Unknown", unknownTab);

        logTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                logTableKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(logTable);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/knownerrorfinder/icon-arrow-down-b-128.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/knownerrorfinder/icon-arrow-up-b-128.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(searchBox, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(openButton)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchBox, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(openButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane3)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBoxActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_searchBoxActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
          
        int row = unknownTable.getSelectedRow();
         KnownErrors hey = new KnownErrors();
         
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
          
            List<String> logs = linesFromFile(file);
            populateLogsTable(logs);
            
            
        }


               
    }//GEN-LAST:event_openButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void searchBoxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchBoxKeyReleased
    
       String query=searchBox.getText().toLowerCase();
      
       filterData(query);
    }//GEN-LAST:event_searchBoxKeyReleased

    private void logTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_logTableKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_logTableKeyPressed

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
                new KnownErrorFinder1().setVisible(true);
                
            }
        });
    }
    
    private List<String> linesFromFile(File file){
        String line;
        List<String> logs = new ArrayList();
        try{
            
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            
            while((line = reader.readLine()) != null)
            {
                if(line.startsWith("\t"))
                {
                    sb.append(line).append("\n\t");
                continue;
                } else if (line.startsWith("    ")){
                    sb.append(line).append("\n");
                    continue;
                }
                if (sb.toString().equalsIgnoreCase(""))
                {
                logs.add(line);
                }
                else{
                    String concat = logs.get(logs.size()-1).concat("\n" + sb.toString());
                    logs.set(logs.size()-1, concat);
                    sb.delete(0, sb.length());
                    logs.add(line);
                        }
            }
            reader.close();
            return logs;
        } catch(IOException e)
        {
            System.err.print(e);
        }
        return null;
    }
    
    private void populateLogsTable(List<String> logs){
           
        int counter = 0;
        
        if (model.getRowCount() > 0)
        {
      model.setRowCount(0);
        }
        for (String log: logs) {
            Object[] o = new Object[2];
            counter++;
            
            o[0] = counter;
            o[1] = log;
            model.addRow(o);
            populateUnknownErrorsTable(log);
        }
        logTable.setModel(model);
        //logTable.setEditingRow(ERROR);
        
        PopUpMenu popup = new PopUpMenu();
         
        
        CustomMouseListener popUpListener = new CustomMouseListener(popup, logTable);
        logTable.addMouseListener(popUpListener);
        //unknown
                
    }
    
    private void knownErrorFileCheck(){
        KnownErrorFileChecker checks = new KnownErrorFileChecker();
        if(checks.checkIfFileExists())
        {
            System.out.println("it exists");
        } else{
            checks.createNewKnownErrorFile();
            
        }
    }
    private void filterData(String query){
        
        TableRowSorter<DefaultTableModel> tr=new TableRowSorter<>(model);
        
        logTable.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter("(?i)"+query));
        
    }
    
    private void populateUnknownErrorsTable(String log){
        
        Pattern p = Pattern.compile("[\\w]+Exception", Pattern.CASE_INSENSITIVE);
        
        if(log.length() > 250)
        {
        log = log.substring(0, 250);
        } 
        
        
        Matcher m = p.matcher(log);
        while(m.find()){
            String word = log.substring(m.start(), m.end());
            if (word.matches("[\\w]+Exception$")){
                if(!unknownErrorHolder.contains(word))
                {
            Object[] o = new Object[1];
            
            o[0] = word;
            unknownModel.addRow(o);
            unknownErrorHolder.add(word);
            }
           }
        }
        unknownTable.setModel(unknownModel);
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel knownTab;
    private javax.swing.JTable knownTable;
    private javax.swing.JTable logTable;
    private javax.swing.JButton openButton;
    private javax.swing.JTextField searchBox;
    private javax.swing.JPanel unknownTab;
    private javax.swing.JTable unknownTable;
    // End of variables declaration//GEN-END:variables
    }
