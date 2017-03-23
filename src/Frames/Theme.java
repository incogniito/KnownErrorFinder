/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames;

import Frames.MainFrame;
import Frames.KnownErrorFinder1;
import com.jtattoo.plaf.aero.AeroLookAndFeel;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.jtattoo.plaf.mint.MintLookAndFeel;
import java.util.Properties;
import javax.swing.UIManager;

/**
 *
 * @author samsonaiyegbusi
 */
public class Theme {
    //this Main is run to set the theme for the entire project
    public static void main(String[] args)
    {
         try{
             
              Properties props = new Properties();
  props.put("logoString", "");
  MintLookAndFeel.setCurrentTheme(props);
            UIManager.setLookAndFeel(new MintLookAndFeel());

            MainFrame frame = new MainFrame();
            frame.pack();
            frame.setVisible(true); 
        } catch(Exception e){
            System.out.println(e);
        }
    }
    
}
