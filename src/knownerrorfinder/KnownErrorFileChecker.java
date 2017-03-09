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

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author samsonaiyegbusi
 */
public class KnownErrorFileChecker {
//checks if file exists

    public boolean checkIfFileExists() {
        File f = new File(System.getProperty("user.dir") + "/src/Files/knownErrors.xml");
        if (f.exists() && !f.isDirectory()) {
            return true;
        } else {
            return false;
        }
    }

    //creates an xml file in the Files package
    public void createNewKnownErrorFile() {
        List<String> lines = Arrays.asList("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");

        Path file = Paths.get(System.getProperty("user.dir") + "/src/Files/knownErrors.xml");
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(KnownErrorFileChecker.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //checks if file exists
    public boolean checkIfRecentHistoryExists() {
        File f = new File(System.getProperty("user.dir") + "/src/Files/recentHistory.txt");
        if (f.exists() && !f.isDirectory()) {
            return true;
        } else {
            return false;
        }
    }

    //creates an recent history file in the Files package
    public void createNewRecentHistoryFile() {
        List<String> lines = Arrays.asList("");

        Path file = Paths.get(System.getProperty("user.dir") + "/src/Files/recentHistory.txt");
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(KnownErrorFileChecker.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //updates recent history file
    public void updateRecentHistoryFile(List<String> lines) {

        Path file = Paths.get(System.getProperty("user.dir") + "/src/Files/recentHistory.txt");
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(KnownErrorFileChecker.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<String> readRecentHistoryFile() {
        List<String> history = new ArrayList();
        Path file = Paths.get(System.getProperty("user.dir") + "/src/Files/recentHistory.txt");

        try (Stream<String> stream = Files.lines(file)) {
            history = stream.collect(Collectors.toList());
            return history;
        } catch (IOException ex) {
           System.out.println(ex);
        }
        return null;
    }
    
    public boolean checkIfScheduleFileExists() {
        File f = new File(System.getProperty("user.dir") + "/src/Files/theSchedules.xml");
        if (f.exists() && !f.isDirectory()) {
            return true;
        } else {
            return false;
        }
    }

    //creates an xml file in the Files package
    public void createNewScheduleFile() {
        List<String> lines = Arrays.asList("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");

        Path file = Paths.get(System.getProperty("user.dir") + "/src/Files/theSchedules.xml");
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(KnownErrorFileChecker.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
