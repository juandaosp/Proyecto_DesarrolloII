package sarum;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.File;

/**
 *
 * @author hanfergc
 */
public class SelecionarArchivoTxt_1 extends javax.swing.filechooser.FileFilter{
    final static String txt= "txt";
    /** Creates a new instance of XMLFilter */
    public SelecionarArchivoTxt_1() {
    }

    public boolean accept(File f) {
         if (f.isDirectory()) {
            return true;
        }
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            String extension = s.substring(i+1).toLowerCase();
            if (txt.equals(extension)) {
                    return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public String getDescription() {
        return "Archivos .txt";
    }

}
