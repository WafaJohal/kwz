import java.io.File;

import javax.swing.filechooser.FileFilter;

	class ImageFilter extends FileFilter {
		// Accept all directories and all gif, jpg, tiff, or png files.
		public boolean accept(File f) {
			if (f.isDirectory()) {
				return true;
			}
			String extension = f.getName().toLowerCase();
			if (extension != null) {
				if (extension.endsWith("gif") || extension.endsWith("png")
						|| extension.endsWith("pnm")
						|| extension.endsWith("jpg")) {
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
		}


	}