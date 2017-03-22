/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knownerrorfinder;

import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileView;
import knownerrorfinder.Panels.KnownErrorFinder1;
import knownerrorfinder.Tables.LogTable;
import knownerrorfinder.Tables.UnknownErrorTable;

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
            mf.addFinderInstances(kef);
            String parentPath = folder.getParent();
            String date = parentPath.substring(parentPath.length() - 10, parentPath.length());
            featuresTabbedPane.add(date + " | " + folder.getName(), kef.getContentPane());
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
    
    public static void export(MainFrame mf){
        
        List<List<String>> fileContents = extractUnknownErrorLogs(mf.getSelectedFinder());
        List<String> fileNames = new ArrayList();
        
        
       JTabbedPane logFileTabPane =  mf.getSelectedFinder().getLogTabbedPane();
        
        for (int i = 0; i < logFileTabPane.getTabCount(); i++){
            
            String fileName = logFileTabPane.getTitleAt(i);
            fileNames.add(fileName);
        }
        
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setName("Export Report");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fileChooser.showDialog(mf.getContentPane(), "Export");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
                     String filePath = fileChooser.getSelectedFile().getPath();

            for (String fileName : fileNames){
                String tempFileName = filePath+"/"+fileName;
                int index = fileNames.indexOf(fileName);
                List<String> fileContent = fileContents.get(index);
                writeFiles(tempFileName, fileContent);
            }
                         JOptionPane.showMessageDialog(null, "Unknown Error Logs Exported","",JOptionPane.INFORMATION_MESSAGE);

        }
    }
    
    private static List<List<String>> extractUnknownErrorLogs( KnownErrorFinder1 finder){
        
                    List<List<String>> unknownErrorFiles = new ArrayList();

                    int tabCount = finder.getLogTabbedPane().getTabCount();
                    
        for (int i = 0; i < tabCount; i++){
                                List<String> unknownErrorLogs = new ArrayList();

            finder.getLogTabbedPane().setSelectedIndex(i);
            
            UnknownErrorTable ukeTable = finder.getUkeTable();
            LogTable logTable  = finder.getLogTable();
            List<String> logs = logTable.getOpenedLog();
            
            for (int j = 0; j < ukeTable.getRowCount(); j++){
                String unknownError = ukeTable.getValueAt(j, 0).toString();
                for(String log : logs){
                    
                    String shortenedLog;
                     if (log.length() > 250) {
                shortenedLog = log.substring(0, 250);
            } else{
                        shortenedLog = log;
                     }
                    
                    
                    if(shortenedLog.contains(unknownError)){
            unknownErrorLogs.add(log);
                    }
                }
            }
            if(!unknownErrorLogs.isEmpty()){
            unknownErrorFiles.add(unknownErrorLogs);
            }
        }
        
        return unknownErrorFiles;
    }
    
    
    private static void writeFiles(String fileName, List<String> fileContent){
        
                Path file = Paths.get(fileName);

       try {
            Files.write(file, fileContent, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(KnownErrorFileChecker.class.getName()).log(Level.SEVERE, null, ex);
        }    }
    
}
