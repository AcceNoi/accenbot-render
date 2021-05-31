package render;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * 自动缩放的url render
 * @author <a href="1339liu@gmail.com">Accen</a>
 *
 */
public class UrlScalebleRenderImage extends UrlRenderImage{

	public UrlScalebleRenderImage(URL uri) {
		super(uri);
	}
	private double scale;
	/**
	 * 设置标准宽后，按照宽定比例缩小
	 */
	@Override
	void afterSetStandardWidth(int standardWidth) {
		scale = (double)standardWidth/this.getWidth();
		BufferedImage scaledImage = new BufferedImage(standardWidth, (int)(getHeight()*scale), this.bufferImage.getType());
		Graphics2D g2d = scaledImage.createGraphics();
		g2d.drawImage(bufferImage
				, new AffineTransformOp(AffineTransform.getScaleInstance(scale, scale), null)
				, 0
				, 0);
		g2d.dispose();
		this.bufferImage = scaledImage;
	}

}
