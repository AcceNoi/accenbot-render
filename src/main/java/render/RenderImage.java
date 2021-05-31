package render;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * 绘制的基本图片元素
 * @author <a href="1339liu@gmail.com">Accen</a>
 * @since 1.0
 */
public abstract class RenderImage {

	public abstract int getWidth();
	public abstract int getHeight();
	protected void beforeRenderMe(Graphics2D g) {
		
	}
	protected void afterRenderMe(Graphics2D g,int x,int y) {
		
	}
	
	/**
	 * 在Render接受原始图片数据，设置玩标准宽之后，执行此方法，可以通过此方法来放大缩小图片
	 * @param width
	 */
	abstract void afterSetStandardWidth(int width);
	abstract BufferedImage getBufferedImage();
}
