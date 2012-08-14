package kamoru.util.video;

import java.io.File;
import java.io.FileFilter;

public class FileFilterImpl implements FileFilter {

	private String[] filenames = null;
	private String[] extensions = null;
	private long fromLastModified = -1l;
	private long toLastModified = -1l;

	public FileFilterImpl(String[] filenames, String[] extensions,
			long fromLastModified, long toLastModified) {
		super();
		this.filenames = filenames;
		this.extensions = extensions;
		this.fromLastModified = fromLastModified;
		this.toLastModified = toLastModified;
	}

	@Override
	public boolean accept(File pathname) {
		boolean a = false, b = false, c = false, d = false;
		String filename = pathname.getName();
		long lastModified = pathname.lastModified();

		if(pathname.isDirectory()) {
			return true;
		} else {
			if (filenames == null) {
				a = true;
			} else {
				for (String name : filenames) {
					if (filename.indexOf(name) > -1) {
						a = true;
					}
				}
			}
			if (extensions == null) {
				b = true;
			} else {
				for (String ext : extensions) {
					if(filename.endsWith(ext)) {
						b = true;
					}
				}
			}
			if (fromLastModified <= 0l) {
				c = true;
			} else {
				if(fromLastModified <= lastModified) {
					c = true;
				}
			}
			if (toLastModified <= 0l) {
				d = true;
			} else {
				if(lastModified <= toLastModified) {
					d = true;
				}
			}
//			System.out.format("%s %s %s %s%n", a, b, c, d);
			return a && b && c && d;
		}
		
	}

}
