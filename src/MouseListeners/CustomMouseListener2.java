/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MouseListeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JTable;
import Frames.ViewAllSchedules;

/**
 *
 * @author oluwakemiborisade
 */
public class CustomMouseListener2 extends MouseAdapter{
   
    
    public CustomMouseListener2(JFrame AllSchedulesTable){
        //this.displayTable = displayTable;
        
    }
    @Override
     public void mouseClicked (MouseEvent e)
    {   ViewAllSchedules editTable = new ViewAllSchedules();
        if (e.getClickCount() == 2)
        {
              editTable.existingSchedules();
             System.out.println("double clicked");
            
        }
    }
     
}

