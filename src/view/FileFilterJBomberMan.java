package view;

import java.io.File;
import javax.swing.filechooser.FileFilter;
/**
 * Thi is the personalized FileFilter I use in the DynaSlaveAccountManager 
 * to accept, into the JFileChooser, only file with ".jbm" extension.
 */
public class FileFilterJBomberMan extends FileFilter {

	@Override
	public boolean accept(File f) {
		if(f.isDirectory()) return true;
		String ext = getExtension(f);
		if(ext==null) return false;
		if(ext.equals("jbm")) return true;
		return false;
	}

	@Override
	public String getDescription() {
		return "File JBomberMan (*.jbm)";
	}
	/**
	 * Method to obtain the extension of a file name.
	 * @param f The file whose extension you want to know 
	 * @return requested extension String
	 */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}
