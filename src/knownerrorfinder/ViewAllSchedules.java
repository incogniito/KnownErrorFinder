/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knownerrorfinder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import schedules.Schedule;

/**
 *
 * @author oluwakemiborisade
 */
public class ViewAllSchedules extends javax.swing.JFrame {

    public ViewAllSchedules() {
        initComponents();
        setTitle("Choose a Schedule");
        existingSchedules();
        populateTable();
    }
    DefaultTableModel scheduleModel;
    List<Schedule> currentSchedules;
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        displayTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        displayTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Schedules"
            }
        ){public boolean isCellEditable(int row, int column){return false;}}
    );
    displayTable.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            displayTableMouseClicked(evt);
        }
    });
    jScrollPane1.setViewportView(displayTable);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(19, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(19, Short.MAX_VALUE))
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void displayTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_displayTableMouseClicked
        
        
    }//GEN-LAST:event_displayTableMouseClicked

    
   
  //get names of existing schedules and add to the table rows .  
    public void  existingSchedules(){
      
        scheduleModel = (DefaultTableModel) displayTable.getModel();
             
        currentSchedules = AccessDataFromXML.retrieveSchedules();
          for (Schedule schedule : currentSchedules){
              
               
              Object[] o = new Object[1];
              o[0] = schedule.getName();
            scheduleModel.addRow(o);
          }
          
          

    }
    //open up schedular frame when schedule name is double clicked.
    public void populateTable(){
         displayTable.addMouseListener(new MouseAdapter(){
        //listen for when a row is doubleclicked 
           @Override
           public void mouseClicked(MouseEvent e) {
           if (e.getClickCount() == 2) {  
               int rowIndex = displayTable.getSelectedRow();
               String scheduleName = displayTable.getValueAt(rowIndex, 0).toString();
               for(Schedule schedule: currentSchedules){
                   
             //gets the schedule name and gets schedule details      
                if (schedule.getName().equalsIgnoreCase(scheduleName)){
                
                    Date scheduleTime = schedule.getScheduleTime().toGregorianCalendar().getTime();
                    List <String> scheduleDay = schedule.getScheduleDays().getScheduleDay();
                    List <String> folderPaths = schedule.getFolderPaths().getFolderPath();
                    
                    Scheduler updateFrame = new Scheduler(schedule.getId(), scheduleName, scheduleTime, scheduleDay, folderPaths);
                    updateFrame.setDefaultCloseOperation(Scheduler.DISPOSE_ON_CLOSE);
                    
                    updateFrame.setVisible(true);
                    
                    //close window after action has been completed 
                    WindowAdapter adapter = new WindowAdapter(){
                        @Override
                        public void windowClosed(WindowEvent e) {
                            dispose();
                        }
                        
                   
                    };
                    
                   updateFrame.addWindowListener(adapter);
                   updateFrame.addWindowFocusListener(adapter);
                 }  
               }     
    }
  }
       });
    }
  
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
            java.util.logging.Logger.getLogger(ViewAllSchedules.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewAllSchedules.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewAllSchedules.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewAllSchedules.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewAllSchedules().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable displayTable;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
