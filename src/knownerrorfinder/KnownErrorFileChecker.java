/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knownerrorfinder;

import java.io.File;
import java.io.IOException;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samsonaiyegbusi
 */
public class KnownErrorFileChecker {
//checks if file exists
    public boolean checkIfFileExists() {
        File f = new File(System.getProperty("user.dir")+"/src/Files/knownErrors.xml");
        if (f.exists() && !f.isDirectory()) {
            return true;
        } else {
            return false;
        }
    }
    //creates an xml file in the Files package
    public void createNewKnownErrorFile(){
      List<String> lines = Arrays.asList("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");

   Path file = Paths.get(System.getProperty("user.dir")+"/src/Files/knownErrors.xml");
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(KnownErrorFileChecker.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
