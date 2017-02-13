/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knownerrorfinder;

import com.jtattoo.plaf.aero.AeroLookAndFeel;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.jtattoo.plaf.mint.MintLookAndFeel;
import javax.swing.UIManager;

/**
 *
 * @author samsonaiyegbusi
 */
public class Theme {
    
    public static void main(String[] args)
    {
         try{
            UIManager.setLookAndFeel(new MintLookAndFeel());

            new KnownErrorFinder1().setVisible(true); 
        } catch(Exception e){
            
        }
    }
    
}
