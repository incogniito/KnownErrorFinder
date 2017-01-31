/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knownerrorfinder;

import java.awt.PopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

/**
 *
 * @author samsonaiyegbusi
 */
public class CustomMouseListener extends MouseAdapter{
    
    private PopUpMenu menu;
    private JTable logTable;
    public CustomMouseListener(PopUpMenu menu, JTable logTable){
        this.menu = menu;
        this.logTable = logTable;
    }
    
    @Override
    public void mouseClicked (MouseEvent e)
    {
        
        //Determine if right button is clicked
        if(SwingUtilities.isRightMouseButton(e))
        {
            doPop(e);
        }
        
        
        
    }
    
    
    private void doPop(MouseEvent e){
        
        if (logTable.getSelectedRow() != -1){
                 menu.logTablePopupMenu(logTable).show(e.getComponent(), e.getX(), e.getY());
        }
        
        
    }
}
