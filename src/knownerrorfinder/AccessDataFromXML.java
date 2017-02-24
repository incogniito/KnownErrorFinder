/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knownerrorfinder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
