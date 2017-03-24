/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileAccessors;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTabbedPane;
import Panels.CloseFeatureTabButton;
import Frames.KnownErrorFinder1;
import Frames.MainFrame;
import org.example.knownerror.KnownError;
import schedules.Schedule;

/**
 *
 * @author samsonaiyegbusi
 */
public class ExecuteSchedule implements Runnable {

    private Schedule schedule;
    private List<String> mostRecentScheduledFilePaths;
    private KnownErrorFinder1 sKef;
    private JTabbedPane sFeaturesTabbedPane;

    private Path scheduleParentPath;

    public ExecuteSchedule(Schedule schedule, KnownErrorFinder1 kef, JTabbedPane featuresTabbedPane) {
        this.schedule = schedule;
        mostRecentScheduledFilePaths = new ArrayList();
        sKef = kef;
        sFeaturesTabbedPane = featuresTabbedPane;

    }
    private int counter = 0;

    @Override
    public void run() {

        while (true) {

            //obtains days and time from schedule and the current date
            List<String> days = schedule.getScheduleDays().getScheduleDay();
            String today = LocalDate.now().getDayOfWeek().toString();
            //checks if the days and timematch and clears the most Recent Scheduled FilePath list so that it can be reused for the
            //updated version of the filePaths if there is any
            for (String day : days) {
                while (day.equalsIgnoreCase(today)) {
                    //ensures any sudden changes to the time will be updated on all instances of the application    
                    for (Schedule _schedule : AccessDataFromXML.retrieveSchedules()) {

                        if (_schedule.getName().equalsIgnoreCase(schedule.getName())) {
                            schedule = _schedule;
                            if (!schedule.getScheduleDays().getScheduleDay().containsAll(days))
                            {
                                //obtains days from schedule and the current date in events of sudden updates to schedules
                                days = schedule.getScheduleDays().getScheduleDay();
                            }
                        }
                    }
                   
                    //obtains time from schedule and the current date in events of sudden updates to schedules
                    LocalTime time = schedule.getScheduleTime().toGregorianCalendar().toZonedDateTime().toLocalTime();
                    

                    LocalTime currentTime = LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond());

                    if (time.equals(currentTime)) {
                        if (!mostRecentScheduledFilePaths.isEmpty()) {
                            mostRecentScheduledFilePaths.clear();
                        }
                        //Creates files into the reports directory within the application
                        createFolder(false);

                        sKef = new KnownErrorFinder1(mostRecentScheduledFilePaths);
                        String parentPath = scheduleParentPath.getParent().toString();
                        String date = parentPath.substring(parentPath.length() - 10, parentPath.length());
                        try {

                            MainFrame.addFinderInstances(sKef);
                            sFeaturesTabbedPane.add(date + " | " + scheduleParentPath.getFileName().toString(), sKef.getContentPane());
                            counter++;
                            System.out.println(counter);
                        } catch (Exception e) {
                            System.out.print(e);
                        }
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

    //creates report
    private void createFolder(Boolean identifier) {

        //checks if a folder with the name of the current date is created, if not then it creates a one and if so
        //it then executed the method again this time creating a directory for the schedule
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
            scheduleParentPath = Paths.get(System.getProperty("user.dir") + "/Reports/" + LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()) + "/" + schedule.getName() + "/");
//if directory does not exist then create a new one, if so then copy the contents of the folder paths noted in the schedule into the reports folder
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
                    copyContents(listOfFiles, scheduleParentPath);
                }
            }

        }

    }

    //copies the contents of the file path into reports directory wihtin application
    private void copyContents(File[] sources, Path dest) {

        if (sources.length != 0) {
            for (File source : sources) {
                try {
                    String destFileNamePath = dest.toString() + "/" + source.getName();
                    Path schedulePath = Paths.get(destFileNamePath);

                    //checks if unknown errors exist so it can be added to directory, otherwise they will be omitted
                    if (checkIfContainsUnknownErrors(source)) {
                        Files.copy(source.toPath(), schedulePath, StandardCopyOption.REPLACE_EXISTING);
                        mostRecentScheduledFilePaths.add(schedulePath.toString());
                    }

                } catch (IOException ex) {
                    Logger.getLogger(ExecuteSchedule.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }

    //uses regex to parse logs with unknown errors in file
    private boolean checkIfContainsUnknownErrors(File file) {

        List<String> logs = KnownErrorFinder1.linesFromFile(file);

        List<KnownError> knownErrors = AccessDataFromXML.retrieveKnownErrors();
        List<String> tempErrorHolder = new ArrayList();

        if (knownErrors != null) {

            for (KnownError ke : knownErrors) {
                tempErrorHolder.add(ke.getName());
            }
            //only proceeds if known errors exists

            for (String log : logs) {
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
                        if (!tempErrorHolder.contains(word)) {

                            return true;

                        }
                    }
                }
            }

        }
        return false;
    }

}
