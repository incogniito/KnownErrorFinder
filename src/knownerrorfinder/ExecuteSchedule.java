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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import schedules.Schedule;

/**
 *
 * @author samsonaiyegbusi
 */
public class ExecuteSchedule implements Runnable {

    private final Schedule schedule;

    public ExecuteSchedule(Schedule schedule) {
        this.schedule = schedule;

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
                    if (time.equals(currentTime)) {
                        createFolder(false);

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
            Path schedulePath = Paths.get(System.getProperty("user.dir") + "/Reports/" + LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()) + "/" + schedule.getName()+"/");
//if directory exists?
            if (!Files.exists(schedulePath)) {
                try {
                    Files.createDirectories(schedulePath);
                    List<String> folderPaths = schedule.getFolderPaths().getFolderPath();
                    for (String folderPath : folderPaths) {
                        File folder = new File(folderPath);
                        File[] listOfFiles = folder.listFiles();
                        copyContents(listOfFiles, schedulePath);
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
                    copyContents(listOfFiles, schedulePath);
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

                    Files.copy(source.toPath(), schedulePath);
                } catch (IOException ex) {
                    Logger.getLogger(ExecuteSchedule.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

}
