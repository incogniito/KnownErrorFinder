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
import knownerrors.KnownError;
import knownerrors.KnownErrors;

/**
 *
 * @author samsonaiyegbusi
 */
public class AccessDataFromXML {
    


public List<KnownError> retrieveKnownErrors() {
        KnownErrors knownErrorList = new KnownErrors();
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(knownErrorList.getClass().getPackage().getName());
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            knownErrorList = (KnownErrors) unmarshaller.unmarshal(new java.io.File(System.getProperty("user.dir")+"/src/Files/knownErrors.xml")); //NOI18N
            //flights = (FlightLists) unmarshaller.unmarshal(new java.io.File("/Volumes/WININSTALL/Service Centric and cloud computing/Travel_agencyWS/src/java/com/cw/travelagencyWS/Flights.xml")); //NOI18N
            List<KnownError> allErrors = knownErrorList.getErrors();
            return allErrors;

        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            System.out.println(ex);
        }
        return null;
    }



    public void newError(String name, Date date, String solution) {
        KnownErrors errorsInFile = new KnownErrors();
        List<KnownError> currentKnownErrors = errorsInFile.getErrors();

        
        List<KnownError> existingErrors = retrieveKnownErrors();
        KnownError newKnownError = new KnownError();

        for (KnownError currentError : existingErrors) {
            if (currentError.getName().equalsIgnoreCase(name)) {
                currentKnownErrors.clear();
                break;
            } else {
                newKnownError.setName(name);
                newKnownError.setSolution(solution);
                
                GregorianCalendar dateAdded = new GregorianCalendar();
        dateAdded.setTime(date);
       XMLGregorianCalendar xmlDateAdded = null;
                try {
                    xmlDateAdded = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(dateAdded.get(Calendar.YEAR), dateAdded.get(Calendar.MONTH)+1, dateAdded.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
                } catch (DatatypeConfigurationException ex) {
                    Logger.getLogger(AccessDataFromXML.class.getName()).log(Level.SEVERE, null, ex);
                }

                newKnownError.setDateAdded(xmlDateAdded);
                currentKnownErrors.add(currentError);
            }
        }

        if (currentKnownErrors.size() > 0) {
            currentKnownErrors.add(newKnownError);

            try {
                OutputStream os = new FileOutputStream(System.getProperty("user.dir")+"/src/Files/knownErrors.xml");
                javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(newKnownError.getClass().getPackage().getName());
                javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
                marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
                marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                marshaller.marshal(currentKnownErrors, os);
            } catch (javax.xml.bind.JAXBException ex) {
                // XXXTODO Handle exception
                java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
            } catch (FileNotFoundException e) {
                java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, e); //NOI18N

            }

        }
    }


}