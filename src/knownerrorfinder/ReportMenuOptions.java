/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knownerrorfinder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileView;
import knownerrorfinder.Panels.KnownErrorFinder1;

/**
 *
 * @author samsonaiyegbusi
 */
public class ReportMenuOptions {

    public ReportMenuOptions() {

    }

    
    public static void openReports(MainFrame mf, KnownErrorFinder1 kef, JTabbedPane featuresTabbedPane ) {
        final File dirToLock = new File(System.getProperty("user.dir") + "/Reports/");
        JFileChooser fc = new JFileChooser(dirToLock);
        
        
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
int returnVal = fc.showOpenDialog(mf.getContentPane());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
        File folder = fc.getSelectedFile();
        
        File[] listOfFiles = folder.listFiles();
        List<String> filePaths = new ArrayList();
        
        
        for (File file : listOfFiles){
            filePaths.add(file.getPath());
        }
        
        
         kef = new KnownErrorFinder1(filePaths);
         String parentPath = folder.getParent();
         String date = parentPath.substring(parentPath.length()-10, parentPath.length());
         featuresTabbedPane.add(date +"|"+folder.getName(), kef.getContentPane());
    }

    }
}
