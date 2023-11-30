/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tools;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Admin
 */
public class DirLib {
    public static String disPath() throws IOException{
        File thisFile = new File(".");
        String s = thisFile.getCanonicalPath();
        return s;
    }
    
    public static String srcPath() throws IOException{
        String s = DirLib.disPath();
        s += "/src";
        return s;
    }
    
    public static String clsPath() throws IOException{
        String s = DirLib.disPath();
        s += "/build/classes";
        return s;
    }
    
    public static String imgPath() throws IOException{
        String s = DirLib.srcPath();
        s += "/Database/IMG";
        return s;
    }
}
