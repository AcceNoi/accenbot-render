package render;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * 使用URL格式的renderImage
 * @author <a href="1339liu@gmail.com">Accen</a>
 */
public class UrlRenderImage extends RenderImage {
	BufferedImage bufferImage;
	public UrlRenderImage(URL uri) {
		try(InputStream is = uri.openStream();){
			bufferImage = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public int getWidth() {
		return bufferImage.getWidth();
	}

	@Override
	public int getHeight() {
		return bufferImage.getHeight();
	}

	@Override
	void afterSetStandardWidth(int standardWidth) {
	}

	@Override
	BufferedImage getBufferedImage() {
		return bufferImage;
	}

}
