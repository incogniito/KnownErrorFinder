/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knownerrorfinder;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileView;
import knownerrorfinder.Panels.KnownErrorFinder1;

/**
 *
 * @author samsonaiyegbusi
 */
public class ReportMenuOptions {

    public ReportMenuOptions() {

    }
    public static void openReports(MainFrame mf, KnownErrorFinder1 kef, JTabbedPane featuresTabbedPane) {
        final File dirToLock = new File(System.getProperty("user.dir") + "/Reports/");
        JFileChooser fc = new JFileChooser(dirToLock);
                        setOpenButtonState(fc, false);

        fc.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(evt.getPropertyName())) {
                    String chosenFileName = evt.getNewValue().toString();
                    File file = new File(chosenFileName) ;
                    List<File> files = Arrays.asList( dirToLock.listFiles());
                    if (files.contains(file.getParentFile())) {
                        setOpenButtonState(fc, true);

                    } else  {
                        setOpenButtonState(fc, false);
                        System.out.println(file.getName());
                    }
                }

                fc.repaint();
            }
        });

        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = fc.showOpenDialog(mf.getContentPane());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File folder = fc.getSelectedFile();

            File[] listOfFiles = folder.listFiles();
            List<String> filePaths = new ArrayList();

            for (File file : listOfFiles) {
                filePaths.add(file.getPath());
            }

            kef = new KnownErrorFinder1(filePaths);
            String parentPath = folder.getParent();
            String date = parentPath.substring(parentPath.length() - 10, parentPath.length());
            featuresTabbedPane.add(date + " | " + folder.getName(), kef.getContentPane());
        }

    }
    
 public static void deleteReports(MainFrame mf) throws IOException {
            
        final File dirToLock = new File(System.getProperty("user.dir") + "/Reports/");
        JFileChooser fc = new JFileChooser(dirToLock);
          
        //fc.setMultiSelectionEnabled(true);
        //check for when a file in selected 
        fc.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(evt.getPropertyName())) {
                    String chosenFileName = evt.getNewValue().toString();
                    File filee = new File(chosenFileName) ;
                   
                    String getP = filee.getParent();
                    //check the right directory has been selected 
                    if (getP.contains((dirToLock.toString()))) {
                      setDeleteButtonState(fc, true);
                      
                    } else  {
                        setDeleteButtonState(fc, false);
                        System.out.println(filee.getName());
                    }
                }
                fc.repaint();
            }
        });
        
        //allow files and directory to be added 
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = fc.showDialog(mf, "Delete");
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            
            File file = fc.getSelectedFile();
            //ask user for confirmation
            int o = JOptionPane.showConfirmDialog(mf, "Are you sure you want to delete this ?", "Confirm", JOptionPane.YES_NO_OPTION);
            if(o == JOptionPane.YES_OPTION){
                delete(file);
            }
        }
 }

          
      
    public static void setOpenButtonState(Container c, boolean flag) {
        int len = c.getComponentCount();
        for (int i = 0; i < len; i++) {
            Component comp = c.getComponent(i);

            if (comp instanceof JButton) {
                JButton b = (JButton) comp;

                if ("Open".equals(b.getText())) {
                    b.setEnabled(flag);
                }

            } else if (comp instanceof Container) {
                setOpenButtonState((Container) comp, flag);
            }
        }
    }
    //disable and enable the delete button
    public static void setDeleteButtonState(Container cont, boolean flag) {
        int len = cont.getComponentCount();
        for (int i = 0; i < len; i++) {
            Component comp = cont.getComponent(i);

            if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                if ("Delete".equals(btn.getText())) {
                    btn.setEnabled(flag);
                }

            } else if (comp instanceof Container) {
                setDeleteButtonState((Container) comp, flag);
            }
        }
    }
    
    //delete the directory or file
    public static void delete(File file)
          throws IOException{
        //check the file is there, and its a directory
           if(file.exists()){
               if(file.isDirectory()){
                        //delete directory if it is empty 
                    if(file.list().length == 0){
                        file.delete();
                            System.out.println("Directory is deleted : " + file.getAbsolutePath());
                    }
                        //list contents of directory if its not empty then delete
                    else{
                        String files[] = file.list();
                        for (String temp : files){
                            //construct file structure 
                            File fileDelete = new File(file.getPath(), temp);
                            //recursively delete
                            delete(fileDelete);
                            }
                        //check again if the directory is empty the delete
                        if(file.list().length == 0){
                            file.delete();
                            System.out.println("Directory deleted : " + file.getAbsolutePath());
                        }
                    }
               }else{
                   //if its a file, then delete it
                   file.delete();
                   System.out.println("File deleted : " + file.getAbsolutePath());
               }
           }
    }
}
  
    
             
        
          
      
      

    

