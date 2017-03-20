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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;
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
}
