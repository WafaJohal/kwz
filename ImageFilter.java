//package components;

import java.io.File;
import javax.swing.filechooser.*;

/* ImageFilter.java is used by FileChooserDemo2.java. */
public abstract class ImageFilter extends FileFilter {
	// Accept all directories and all gif, jpg, tiff, or png files.
	String[] extensions;
	  
	  public ImageFilter(){
		  extensions = new String[]{"gif", "jpg", "pnm", "png"};
	  }

	  public ImageFilter(String[] exts, String descr) {
	    // Clone and lowercase the extensions
	    extensions = new String[exts.length];
	    for (int i = exts.length - 1; i >= 0; i--) {
	      extensions[i] = exts[i].toLowerCase();
	    }
	    
	  }

	  public boolean accept(File f) {
	    // We always allow directories, regardless of their extension
	    if (f.isDirectory()) { return true; }

	    // Ok, itвЂ™s a regular file, so check the extension
	    String name = f.getName().toLowerCase();
	    for (int i = extensions.length - 1; i >= 0; i--) {
	      if (name.endsWith(extensions[i])) {
	        return true;
	      }
	    }
	    return false;
	  }
/*
	public boolean acceptb(File f) {
		if (f.isDirectory()) {
			return true;
		}
		String extension[] = ;
		if (extension != null) {
			if (extension.equals(UtilsIm.tiff) || extension.equals(UtilsIm.tif)
					|| extension.equals(UtilsIm.gif)
					|| extension.equals(UtilsIm.jpeg)
					|| extension.equals(UtilsIm.jpg)
					|| extension.equals(UtilsIm.png)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	// The description of this filter
	public String getDescription() {
		return "Just Images";
	}*/
}
