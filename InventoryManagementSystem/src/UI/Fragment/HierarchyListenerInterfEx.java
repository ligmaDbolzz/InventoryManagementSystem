 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.Fragment;

/**
 *
 * @author Admin
 */
import java.awt.event.*;
import javax.swing.JComponent;

public class HierarchyListenerInterfEx implements HierarchyListener{
     //to be override
    public void hierarchyChanged(HierarchyEvent e){
        JComponent cpn = (JComponent) e.getSource();
        if  ((HierarchyEvent.SHOWING_CHANGED & e.getChangeFlags()) != 0
            && cpn.isShowing()){
             //do
        }
    }
}
