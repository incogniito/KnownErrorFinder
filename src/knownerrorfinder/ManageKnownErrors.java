/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knownerrorfinder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.example.knownerror.KnownError;

/**
 *
 * @author samsonaiyegbusi
 */
public class ManageKnownErrors {

    private MainFrame mf;
    private List<String> knownError;
    private List<KnownError> readErrors;

    public ManageKnownErrors(MainFrame mf) {
        this.mf = mf;

        knownError = new ArrayList();
        AccessDataFromXML accessXML = new AccessDataFromXML();
        readErrors = accessXML.retrieveKnownErrors();

        for (KnownError errors : readErrors) {
            knownError.add(errors.getName());
        }
    }

    public void openImportDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setName("Import Known Errors");
                fileChooser.setAcceptAllFileFilterUsed(false);

        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text File", "txt"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("CSV", "csv"));

        int returnVal = fileChooser.showOpenDialog(mf.getContentPane());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            int fileNameLength = file.getName().length();
            String fileNameExtension = file.getName().substring(fileNameLength - 3, fileNameLength);

            if (fileNameExtension.equals("txt")) {
                importKnownErrorsFromTxt(file);
            } else if (fileNameExtension.equals("csv")) {
                importKnownErrorsFromCSV(file);
            }

        }

    }

    public void openExportDialog() {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setName("Export Known Errors");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text File", "txt"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("CSV", "csv"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XML", "xml"));

        int returnVal = fileChooser.showSaveDialog(mf.getContentPane());
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            
            String filterExtension = fileChooser.getFileFilter().getDescription();
                String filePath = fileChooser.getSelectedFile().getPath();

            if (filterExtension.equalsIgnoreCase("Text File")) {
                exportKnownErrorsToTxt(filePath);
            } else if (filterExtension.equalsIgnoreCase("CSV")) {
                exportKnownErrorsToCSV(filePath);
            }else if (filterExtension.equalsIgnoreCase("XML")) {
                
                File file = new File(filePath + ".xml");

                
                exportKnownErrorsToXML(file);
            }

        }

    }

    private void importKnownErrorsFromTxt(File file) {

        List<String> errors = seperateByLines(file);
        ImportedErrorComparator knownErrorConfirmation = new ImportedErrorComparator();

        knownErrorConfirmation.populateImportedKnownErrorTable(errors);
        knownErrorConfirmation.populateExistingKnownErrorTable(knownError);
        knownErrorConfirmation.setVisible(true);

    }

    private void exportKnownErrorsToTxt(String fileName) {

        StringBuilder sb = new StringBuilder();
        for (KnownError errors : readErrors){
            String exception = errors.getName();
            String solution = errors.getSolution();
            
            String month = Integer.toString(errors.getDateAdded().getMonth());
            String year = Integer.toString(errors.getDateAdded().getYear());
            String day = Integer.toString(errors.getDateAdded().getDay());
            
            String date = day+"/"+month+"/"+year;
            
            int index = readErrors.indexOf(errors);
            if (index != (readErrors.size()-1))
            {
            sb.append(exception).append(", ").append(date).append(", ").append(solution).append("\n");
            } else {
                            sb.append(exception).append(", ").append(date).append(", ").append(solution);
            }     
        }
        
        
        try (FileWriter fw = new FileWriter(fileName + ".txt")) {
            fw.write(sb.toString());
        } catch (IOException ex) {
            Logger.getLogger(ManageKnownErrors.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void importKnownErrorsFromCSV(File file) {
        List<String> errors = seperateByCommas(file);
        ImportedErrorComparator knownErrorConfirmation = new ImportedErrorComparator();

        knownErrorConfirmation.populateImportedKnownErrorTable(errors);
        knownErrorConfirmation.populateExistingKnownErrorTable(knownError);
        knownErrorConfirmation.setVisible(true);
    }

    private void exportKnownErrorsToXML(File file){
                File source = new File(System.getProperty("user.dir") + "/src/Files/knownErrors.xml");

        try {
            Files.copy(source.toPath(), file.toPath());
        } catch (IOException ex) {
            Logger.getLogger(ManageKnownErrors.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void exportKnownErrorsToCSV(String fileName) {

           StringBuilder sb = new StringBuilder();
        for (KnownError errors : readErrors){
            String exception = errors.getName();
            String solution = errors.getSolution();
            
            String month = Integer.toString(errors.getDateAdded().getMonth());
            String year = Integer.toString(errors.getDateAdded().getYear());
            String day = Integer.toString(errors.getDateAdded().getDay());
            
            String date = day+"/"+month+"/"+year;
            
            int index = readErrors.indexOf(errors);
            if (index != (readErrors.size()-1))
            {
            sb.append(exception).append(", ").append(date).append(", ").append(solution).append("\n");
            } else {
                            sb.append(exception).append(", ").append(date).append(", ").append(solution);
            }     
        }
        
        
        try (FileWriter fw = new FileWriter(fileName + ".csv")) {
            fw.write(sb.toString());
        } catch (IOException ex) {
            Logger.getLogger(ManageKnownErrors.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private List<String> seperateByLines(File file) {

        //parses and obtains lines from file
        String line;
        List<String> txtLogs = new ArrayList();
        try {
            //Reads in file 
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            //iterates txt file while a line exists
            while ((line = reader.readLine()) != null) {

                txtLogs.add(line);

            }
            reader.close();
            return txtLogs;
        } catch (IOException e) {
            System.err.print(e);
        }
        return null;
    }

    private List<String> seperateByCommas(File file) {

        //parses and obtains lines from file
        String line;
        String seperator = ",";
        List<String> txtLogs = new ArrayList();
        try {
            //Reads in file 
            BufferedReader reader = new BufferedReader(new FileReader(file));
            //iterates txt file while a line exists
            while ((line = reader.readLine()) != null) {

                txtLogs.addAll(Arrays.asList(line.split(seperator)));

            }
            reader.close();
            return txtLogs;
        } catch (IOException e) {
            System.err.print(e);
        }
        return null;
    }

}
