package render;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public interface Render {
	/**
	 * 
	 * @param os
	 */
	public void render(OutputStream os);
	default public void render(File localFile) {
		try {
			render(new FileOutputStream(localFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
