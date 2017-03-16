/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knownerrorfinder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTabbedPane;
import knownerrorfinder.Panels.CloseFeatureTabButton;
import knownerrorfinder.Panels.KnownErrorFinder1;
import org.example.knownerror.KnownError;
import schedules.Schedule;

/**
 *
 * @author samsonaiyegbusi
 */
public class ExecuteSchedule implements Runnable {

    private final Schedule schedule;
    private List<String> mostRecentScheduledFilePaths;
    private static KnownErrorFinder1 sKef;
    private static JTabbedPane sFeaturesTabbedPane;
    
    private Path scheduleParentPath;

    public ExecuteSchedule(Schedule schedule) {
        this.schedule = schedule;
        mostRecentScheduledFilePaths = new ArrayList();
        

    }

    protected static void initialiseComponents( KnownErrorFinder1 kef, JTabbedPane featuresTabbedPane){
        sKef = kef;
        sFeaturesTabbedPane = featuresTabbedPane;
    }
    @Override
    public void run() {

        while (true) {

            List<String> days = schedule.getScheduleDays().getScheduleDay();
            LocalTime time = schedule.getScheduleTime().toGregorianCalendar().toZonedDateTime().toLocalTime();
            String today = LocalDate.now().getDayOfWeek().toString();

            for (String day : days) {
                while (day.equalsIgnoreCase(today)) {
                    LocalTime currentTime = LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond());
                    if (!mostRecentScheduledFilePaths.isEmpty()){
                        for (String filePath : mostRecentScheduledFilePaths){
                            mostRecentScheduledFilePaths.remove(filePath);
                        }
                    }
                    if (time.equals(currentTime)) {
                        createFolder(false);
                        
sKef = new KnownErrorFinder1(mostRecentScheduledFilePaths);
         String parentPath =  scheduleParentPath.getParent().toString();
         String date = parentPath.substring(parentPath.length()-10, parentPath.length());
         sFeaturesTabbedPane.add(date +" | "+scheduleParentPath.getFileName().toString(), sKef.getContentPane());
                             sFeaturesTabbedPane.setTabComponentAt(sFeaturesTabbedPane.getTabCount() - 1, new CloseFeatureTabButton(sFeaturesTabbedPane));

                    } else {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                           // Logger.getLogger(ExecuteSchedule.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }

                }

            }
            try {
                Thread.sleep(43200000);
            } catch (InterruptedException ex) {
               // Logger.getLogger(ExecuteSchedule.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private void createFolder(Boolean identifier) {

        if (identifier == false) {
            Path path = Paths.get(System.getProperty("user.dir") + "/Reports/" + LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()));
            
            //if directory exists?
            if (!Files.exists(path)) {
                try {
                    Files.createDirectories(path);
                    createFolder(true);
                } catch (IOException e) {
                    //fail to create directory
                    e.printStackTrace();
                }
            } else {
                createFolder(true);
            }
        } else {
             scheduleParentPath = Paths.get(System.getProperty("user.dir") + "/Reports/" + LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()) + "/" + schedule.getName()+"/");
//if directory exists?
            if (!Files.exists(scheduleParentPath)) {
                try {
                    Files.createDirectories(scheduleParentPath);
                    List<String> folderPaths = schedule.getFolderPaths().getFolderPath();
                    for (String folderPath : folderPaths) {
                        File folder = new File(folderPath);
                        File[] listOfFiles = folder.listFiles();
                        copyContents(listOfFiles, scheduleParentPath);
                    }
                } catch (IOException e) {
                    //fail to create directory
                    e.printStackTrace();
                }
            } else {
                List<String> folderPaths = schedule.getFolderPaths().getFolderPath();
                for (String folderPath : folderPaths) {
                    File folder = new File(folderPath);
                    File[] listOfFiles = folder.listFiles();
                    copyContents(listOfFiles,  scheduleParentPath);
                }
            }

        }

    }
    
    private void copyContents(File[] sources, Path dest) {
        if (sources.length != 0) {
            for (File source : sources) {
                try {
                    String destFileNamePath = dest.toString()+"/"+source.getName();
                                Path schedulePath = Paths.get(destFileNamePath);

                                
                  if (checkIfContainsUnknownErrors(source)){
                      Files.copy(source.toPath(), schedulePath, StandardCopyOption.REPLACE_EXISTING);
                    mostRecentScheduledFilePaths.add(schedulePath.toString());
                  }              
                    
                } catch (IOException ex) {
                    Logger.getLogger(ExecuteSchedule.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }
    
    private boolean checkIfContainsUnknownErrors(File file){
        
        List<String> logs = KnownErrorFinder1.linesFromFile(file);
        
        List<KnownError> knownErrors = AccessDataFromXML.retrieveKnownErrors();
        List<String> tempErrorHolder = new ArrayList();
        
        if (knownErrors != null) {
            
                        for (KnownError ke : knownErrors) {
                            tempErrorHolder.add(ke.getName());
                        }      
                        //only proceeds if known errors exists
        
         for (String log : logs){
        //uses regex to obtain words that have exception within its name

            Pattern p = Pattern.compile("[\\w]+Exception", Pattern.CASE_INSENSITIVE);
            //limits the search to only the first 250 characters.
            if (log.length() > 250) {
                log = log.substring(0, 250);
            }

            Matcher m = p.matcher(log);
            EXCEPTION_FINDER:
            while (m.find()) {
                String word = log.substring(m.start(), m.end());
                if (word.matches("[\\w]+Exception$")) {
                    //checks if word matches the regex (words ending with 'exception')
                            if (!tempErrorHolder.contains(word)){
                                
                                return true;
                            
                        }
                    }
                }
            }
           
        } 
        return false;
        }
        
    
    }

    
    


