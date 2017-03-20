/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knownerrorfinder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.example.knownerror.AllKnownErrors;
import org.example.knownerror.KnownError;
import schedules.Folders;
import schedules.Schedule;
import schedules.ScheduledDays;
import schedules.Schedules;


/**
 *
 * @author samsonaiyegbusi
 */
public class AccessDataFromXML {

    //unmarshals data from xml file
    public List<KnownError> retrieveKnownErrors() {
        
        AllKnownErrors knownErrorList = new AllKnownErrors();
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(knownErrorList.getClass().getPackage().getName());
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            knownErrorList = (AllKnownErrors) unmarshaller.unmarshal(new java.io.File(System.getProperty("user.dir") + "/src/Files/knownErrors.xml")); //NOI18N
            //flights = (FlightLists) unmarshaller.unmarshal(new java.io.File("/Volumes/WININSTALL/Service Centric and cloud computing/Travel_agencyWS/src/java/com/cw/travelagencyWS/Flights.xml")); //NOI18N
            List<KnownError> allErrors = knownErrorList.getErrors();
            return allErrors;

        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            System.out.println(ex);
        }
        return null;
    }

    //Marshals data from application into xml file
    public void newError(String name, Date date, String solution) {
        AllKnownErrors errorsInFile = new AllKnownErrors();
        List<KnownError> currentKnownErrors = errorsInFile.getErrors() ;
        

        List<KnownError> existingErrors = retrieveKnownErrors();
        KnownError newKnownError = null;
        if (existingErrors != null) {
            currentKnownErrors.addAll(existingErrors);
                
                    newKnownError.setName(name);
                    newKnownError.setSolution(solution);

                    GregorianCalendar dateAdded = new GregorianCalendar();
                    dateAdded.setTime(date);
                    XMLGregorianCalendar xmlDateAdded = null;
                    try {
                        xmlDateAdded = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(dateAdded.get(Calendar.YEAR), dateAdded.get(Calendar.MONTH) + 1, dateAdded.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
                    } catch (DatatypeConfigurationException ex) {
                        Logger.getLogger(AccessDataFromXML.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    newKnownError.setDateAdded(xmlDateAdded);
                    currentKnownErrors.add(newKnownError);
                
            
        } else {

            newKnownError.setName(name);
            newKnownError.setSolution(solution);

            GregorianCalendar dateAdded = new GregorianCalendar();
            dateAdded.setTime(date);
            XMLGregorianCalendar xmlDateAdded = null;
            try {
                xmlDateAdded = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(dateAdded.get(Calendar.YEAR), dateAdded.get(Calendar.MONTH) + 1, dateAdded.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
            } catch (DatatypeConfigurationException ex) {
                Logger.getLogger(AccessDataFromXML.class.getName()).log(Level.SEVERE, null, ex);
            }

            newKnownError.setDateAdded(xmlDateAdded);
            currentKnownErrors.add(newKnownError);

        }
            try {
                OutputStream os = new FileOutputStream(System.getProperty("user.dir") + "/src/Files/knownErrors.xml");
                javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(newKnownError.getClass().getPackage().getName());
                javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();           //^^^^^^^^^^
                marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
                marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                marshaller.marshal(errorsInFile, os);
                                   //^^^^^^^^^^s
            } catch (javax.xml.bind.JAXBException ex) {
                // XXXTODO Handle exception
                System.out.print(ex); //NOI18N
            } catch (FileNotFoundException e) {
                java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, e); //NOI18N
            }

        }
    
   //unmarshals data from xml file
    
    public List<Schedule> retrieveSchedules() {
        Schedules scheduleList = new Schedules();
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(scheduleList.getClass().getPackage().getName());
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            scheduleList = (Schedules) unmarshaller.unmarshal(new java.io.File(System.getProperty("user.dir") + "/src/Files/theSchedules.xml")); //NOI18N
            //flights = (FlightLists) unmarshaller.unmarshal(new java.io.File("/Volumes/WININSTALL/Service Centric and cloud computing/Travel_agencyWS/src/java/com/cw/travelagencyWS/Flights.xml")); //NOI18N
            List<Schedule> allSchedules = scheduleList.getSchedules();
                   
            return allSchedules;

        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            System.out.println(ex);
        }
        return null;
    }
     
    
  //create new schedule to marshal into xml file   
  public void newSchedule(String name, Date scheduleTime, List <String> scheduleDay, List<String> folderPaths ) {
        Schedules schedulesObj = new Schedules();
        List<Schedule> currentSchedule = schedulesObj.getSchedules();
        Folders folderP = new Folders();
        ScheduledDays daysChosen = new ScheduledDays();
        
        List<String> currentFolderPaths = folderP.getFolderPath();
        List<String> currentDays = daysChosen.getScheduleDay();
        List<Schedule> existingSchedules = retrieveSchedules();
        
        Schedule newSchedule = new Schedule();
        
        //check if schedules exist and add element details to new schedule 
        if (existingSchedules != null) {
            
                    currentSchedule.addAll(existingSchedules);
                    currentFolderPaths.addAll(folderPaths);
                    currentDays.addAll(scheduleDay);
                    int size = existingSchedules.size();
                    newSchedule.setId(size);
                    newSchedule.setName(name);
                    newSchedule.setFolderPaths(folderP);
                    newSchedule.setScheduleDays(daysChosen);
                    GregorianCalendar timeSelected = new GregorianCalendar();
                    timeSelected.setTime(scheduleTime);    
                    
        //convert date to gregorian           
                    XMLGregorianCalendar xmlTimeSelected = null;
                    try {
                        xmlTimeSelected = DatatypeFactory.newInstance().newXMLGregorianCalendarTime(timeSelected.get(Calendar.HOUR), timeSelected.get(Calendar.MINUTE), timeSelected.get(Calendar.SECOND), DatatypeConstants.FIELD_UNDEFINED);
                    } catch (DatatypeConfigurationException ex) {
                        Logger.getLogger(AccessDataFromXML.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    newSchedule.setScheduleTime(xmlTimeSelected);
                    currentSchedule.add(newSchedule);
                
            
        } else {
                    newSchedule.setId(0);
                    currentFolderPaths.addAll(folderPaths);
                    currentDays.addAll(scheduleDay);
                    newSchedule.setName(name);
                    newSchedule.setFolderPaths(folderP);
                    newSchedule.setScheduleDays(daysChosen);
                    GregorianCalendar timeSelected = new GregorianCalendar();
                    timeSelected.setTime(scheduleTime);                   
                    
                    //convert selected time to xml gregorian time
                    XMLGregorianCalendar xmlTimeSelected = null;
                    try {
                        xmlTimeSelected = DatatypeFactory.newInstance().newXMLGregorianCalendarTime(timeSelected.get(Calendar.HOUR), timeSelected.get(Calendar.MINUTE), timeSelected.get(Calendar.SECOND), DatatypeConstants.FIELD_UNDEFINED);
                    } catch (DatatypeConfigurationException ex) {
                        Logger.getLogger(AccessDataFromXML.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    newSchedule.setScheduleTime(xmlTimeSelected);
                    currentSchedule.add(newSchedule);

        }
            //marshalling data from app to xml document
            try {
                OutputStream os = new FileOutputStream(System.getProperty("user.dir") + "/src/Files/theSchedules.xml");
                javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(newSchedule.getClass().getPackage().getName());
                javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();           
                marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
                marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                marshaller.marshal(schedulesObj, os);
                                   
            } catch (javax.xml.bind.JAXBException ex) {
                // XXXTODO Handle exception
                System.out.print(ex); //NOI18N
            } catch (FileNotFoundException e) {
                java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, e); //NOI18N
            }

        }
        //update existing schedules 
    public void updateSchedules(int scheduleID, String name, Date scheduleTime, List <String> scheduleDay, List<String> folderPaths){
        
        Schedules schedulesObj = new Schedules();
        List<Schedule> currentSchedule = schedulesObj.getSchedules();
        Folders folderP = new Folders();
        ScheduledDays daysChosen = new ScheduledDays();
        
        List<String> currentFolderPaths = folderP.getFolderPath();
        List<String> currentDays = daysChosen.getScheduleDay();
        List<Schedule> existingSchedules = retrieveSchedules();
        
        Schedule schedule = new Schedule();
        
        currentSchedule.addAll(existingSchedules);
        currentFolderPaths.addAll(folderPaths);
        currentDays.addAll(scheduleDay);
        
        //check schedule ID before saving updated schedule
        for(Schedule schedules: currentSchedule){
            if (schedules.getId() == scheduleID){
                
                currentSchedule.get(scheduleID).setName(name);
                currentSchedule.get(scheduleID).setFolderPaths(folderP);
                currentSchedule.get(scheduleID).setScheduleDays(daysChosen);
                GregorianCalendar timeSelected = new GregorianCalendar();
                timeSelected.setTime(scheduleTime);                   
                    
                    XMLGregorianCalendar xmlTimeSelected = null;
                    try {
                        xmlTimeSelected = DatatypeFactory.newInstance().newXMLGregorianCalendarTime(timeSelected.get(Calendar.HOUR), timeSelected.get(Calendar.MINUTE), timeSelected.get(Calendar.SECOND), DatatypeConstants.FIELD_UNDEFINED);
                    } catch (DatatypeConfigurationException ex) {
                        Logger.getLogger(AccessDataFromXML.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    currentSchedule.get(scheduleID).setScheduleTime(xmlTimeSelected);
            }
        }
        //marshal data from app to xml file
         try {
                OutputStream os = new FileOutputStream(System.getProperty("user.dir") + "/src/Files/theSchedules.xml");
                javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(schedule.getClass().getPackage().getName());
                javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();           
                marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
                marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                marshaller.marshal(schedulesObj, os);
                                   
            } catch (javax.xml.bind.JAXBException ex) {
                // XXXTODO Handle exception
                System.out.print(ex); //NOI18N
            } catch (FileNotFoundException e) {
                java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, e); //NOI18N
            }
    }
    public void newErrors(List<String> errors){
        
        AllKnownErrors errorsInFile = new AllKnownErrors();
        List<KnownError> currentKnownErrors = errorsInFile.getErrors() ;
        

        List<KnownError> existingErrors = retrieveKnownErrors();
        KnownError newKnownError = null;
        
        if (existingErrors != null) {
            
            currentKnownErrors.addAll(existingErrors);
            
            
            IMPORTED_ERRORS_ITER: for(String importedErrors : errors){
                
                for (KnownError ke : currentKnownErrors){
                    
                    if (ke.getName().equalsIgnoreCase(importedErrors))
                    {
                        continue IMPORTED_ERRORS_ITER;
                    }
                    
                }
                
                newKnownError = new KnownError();
                
                    newKnownError.setName(importedErrors);
                    newKnownError.setSolution("");

                    GregorianCalendar dateAdded = new GregorianCalendar();
                    dateAdded.setTime(new Date());
                    XMLGregorianCalendar xmlDateAdded = null;
                    try {
                        xmlDateAdded = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(dateAdded.get(Calendar.YEAR), dateAdded.get(Calendar.MONTH) + 1, dateAdded.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
                    } catch (DatatypeConfigurationException ex) {
                        Logger.getLogger(AccessDataFromXML.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    newKnownError.setDateAdded(xmlDateAdded);
                    currentKnownErrors.add(newKnownError);
            }
            
        } else {

             for(String str : errors){
                newKnownError = new KnownError();
                    newKnownError.setName(str);
                    newKnownError.setSolution("");

                    GregorianCalendar dateAdded = new GregorianCalendar();
                    dateAdded.setTime(new Date());
                    XMLGregorianCalendar xmlDateAdded = null;
                    try {
                        xmlDateAdded = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(dateAdded.get(Calendar.YEAR), dateAdded.get(Calendar.MONTH) + 1, dateAdded.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
                    } catch (DatatypeConfigurationException ex) {
                        Logger.getLogger(AccessDataFromXML.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    newKnownError.setDateAdded(xmlDateAdded);
                    currentKnownErrors.add(newKnownError);
            }

        }
        
        try {
                OutputStream os = new FileOutputStream(System.getProperty("user.dir") + "/src/Files/knownErrors.xml");
                javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(newKnownError.getClass().getPackage().getName());
                javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
                marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
                marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                marshaller.marshal(errorsInFile, os);
            } catch (javax.xml.bind.JAXBException ex) {
                // XXXTODO Handle exception
                System.out.print(ex); //NOI18N
            } catch (FileNotFoundException e) {
                java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, e); //NOI18N
            }  
    }
}
