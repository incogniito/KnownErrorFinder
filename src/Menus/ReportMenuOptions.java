/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menus;

import Frames.MainFrame;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileView;
import Frames.KnownErrorFinder1;
import FileAccessors.KnownErrorFileChecker;
import Tables.LogTable;
import Tables.UnknownErrorTable;
import javax.swing.Action;
import javax.swing.ActionMap;

/**
 *
 * @author samsonaiyegbusi
 */
public class ReportMenuOptions {

    public ReportMenuOptions() {

    }

    public static void openReports(MainFrame mf, KnownErrorFinder1 kef, JTabbedPane featuresTabbedPane) {
        final File dirToLock = new File(System.getProperty("user.home") + "/KnownErrorFinderFiles/Reports/");
        JFileChooser fc = new JFileChooser(dirToLock);
        setOpenButtonState(fc, false);
List<String> str = new ArrayList();
        
       ActionMap am = fc.getActionMap();  
       for (Object ob : am.allKeys()){
           str.add(ob.toString());
       }
        
        
        fc.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(evt.getPropertyName())) {
                    String chosenFileName = evt.getNewValue().toString();

                    File file = new File(chosenFileName);
                    List<File> files = Arrays.asList(dirToLock.listFiles());
                    if (files.contains(file.getParentFile())) {
                        setOpenButtonState(fc, true);

                    } else {
                        setOpenButtonState(fc, false);
                        System.out.println(file.getName());
                    }
                }

                fc.repaint();
            }
        });

        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        
        int returnVal = fc.showOpenDialog(mf.getContentPane());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File folder = fc.getSelectedFile();

            File[] listOfFiles = folder.listFiles();
            List<String> filePaths = new ArrayList();

            for (File file : listOfFiles) {
                filePaths.add(file.getPath());
            }

            kef = new KnownErrorFinder1(filePaths);
            mf.addFinderInstances(kef);
            String parentPath = folder.getParent();
            String date = parentPath.substring(parentPath.length() - 10, parentPath.length());
            featuresTabbedPane.add(date + " | " + folder.getName(), kef.getContentPane());
        }

    }

    public static void deleteReports(MainFrame mf) throws IOException {

        final File dirToLock = new File(System.getProperty("user.home") + "/KnownErrorFinderFiles/Reports/");
        JFileChooser fc = new JFileChooser(dirToLock);

        //fc.setMultiSelectionEnabled(true);
        //check for when a file in selected 
        fc.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(evt.getPropertyName())) {
                    String chosenFileName = evt.getNewValue().toString();
                    File filee = new File(chosenFileName);

                    String getP = filee.getParent();
                    //check the right directory has been selected 
                    if (getP.contains((dirToLock.toString()))) {
                        setDeleteButtonState(fc, true);

                    } else {
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
            if (o == JOptionPane.YES_OPTION) {
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

    public static void export(MainFrame mf) {

        List<List<String>> fileContents = extractUnknownErrorLogs(mf.getSelectedFinder());
        //list of file names to associate with its corresponding filecontents
        List<String> fileNames = new ArrayList();

        JTabbedPane logFileTabPane = mf.getSelectedFinder().getLogTabbedPane();

        for (int i = 0; i < logFileTabPane.getTabCount(); i++) {

            String fileName = logFileTabPane.getTitleAt(i);
            fileNames.add(fileName);
        }

        //opens export dialog
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setName("Export Report");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fileChooser.showDialog(mf.getContentPane(), "Export");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getPath();

            for (String fileName : fileNames) {
                String tempFileName = filePath + "/" + fileName;
                int index = fileNames.indexOf(fileName);
                List<String> fileContent = fileContents.get(index);
                writeFiles(tempFileName, fileContent);
            }
            JOptionPane.showMessageDialog(null, "Unknown Error Logs Exported", "", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    //extracts unknown error lines from logs
    private static List<List<String>> extractUnknownErrorLogs(KnownErrorFinder1 finder) {
        //a List containing another list of strings which represent the contents that will be written to each file 
        List<List<String>> unknownErrorFiles = new ArrayList();

        int tabCount = finder.getLogTabbedPane().getTabCount();

        for (int i = 0; i < tabCount; i++) {
            List<String> unknownErrorLogs = new ArrayList();

            //each iterated tab count will ensure that setSelectedIndex is called which will therefore execute the change listener in the 
            //knownErrorFinder1 class file for the log table panel and therefore as each tab is changing the unknown error tables will have different contents
            //which will be used to obtain the necessary log lines from the corresponding log table
            finder.getLogTabbedPane().setSelectedIndex(i);

            //obtains the unknown error table
            UnknownErrorTable ukeTable = finder.getUkeTable();
            //obtains the log table
            LogTable logTable = finder.getLogTable();
            //gets the logs used for the table
            List<String> logs = logTable.getOpenedLog();

            for (int j = 0; j < ukeTable.getRowCount(); j++) {
                //iterates through the rows in the unknown error table and obtaining the unknown error
                String unknownError = ukeTable.getValueAt(j, 0).toString();
                for (String log : logs) {

                    String shortenedLog;
                    //looks for a shortend version of each line to improve the accuracy of the search
                    if (log.length() > 250) {
                        shortenedLog = log.substring(0, 250);
                    } else {
                        shortenedLog = log;
                    }

                    //adds log if it contains the unknown error
                    if (shortenedLog.contains(unknownError)) {
                        unknownErrorLogs.add(log);
                    }
                }
            }
            //incase old reports are opened and all the unknown errors have been added to known errors
            //they will not be exported to files. 
            if (!unknownErrorLogs.isEmpty()) {
                unknownErrorFiles.add(unknownErrorLogs);
            }
        }

        return unknownErrorFiles;
    }

    //writes log contents to file and exports it to the chosen file location
    private static void writeFiles(String fileName, List<String> fileContent) {

        Path file = Paths.get(fileName);

        try {
            Files.write(file, fileContent, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(KnownErrorFileChecker.class.getName()).log(Level.SEVERE, null, ex);
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
            throws IOException {
        //check the file is there, and its a directory
        if (file.exists()) {
            if (file.isDirectory()) {
                //delete directory if it is empty 
                if (file.list().length == 0) {
                    file.delete();
                    System.out.println("Directory is deleted : " + file.getAbsolutePath());
                } //list contents of directory if its not empty then delete
                else {
                    String files[] = file.list();
                    for (String temp : files) {
                        //construct file structure 
                        File fileDelete = new File(file.getPath(), temp);
                        //recursively delete
                        delete(fileDelete);
                    }
                    //check again if the directory is empty the delete
                    if (file.list().length == 0) {
                        file.delete();
                        System.out.println("Directory deleted : " + file.getAbsolutePath());
                    }
                }
            } else {
                //if its a file, then delete it
                file.delete();
                System.out.println("File deleted : " + file.getAbsolutePath());
            }
        }
    }
}
