package render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;

import render.algorithm.Divide;

public class DivideGroupsImageRender implements Render{
	/**
	 * 分组算法
	 */
	private Divide divide;
	/**
	 * 分组数
	 */
	private int groupCount;
	/**
	 * 水平留白
	 */
	private int horiWhite;
	/**
	 * 左边界留白
	 */
	private int lBoderWhite;
	/**
	 * 右边界留白
	 */
	private int rBoderWhite;
	/**
	 * 竖直留白
	 */
	private int vertWhite;
	/**
	 * 顶部留白
	 */
	private int tWhite;
	/**
	 * 底部留白
	 */
	private int bWhite;
	/**
	 * 原始图片
	 */
	private RenderImage[] originImages;
	/**
	 * 标准宽，即每幅RenderImage的最终宽度
	 */
	private int standardWidth;
	private StandardWidth standardWidthImp;
	
	private int width;
	private int height;
	
	public DivideGroupsImageRender(int groupCount,/*留白，需要远小于图片宽度和高度*/int unitWhite,Divide divide) {
		this.groupCount = groupCount;
		this.horiWhite = this.lBoderWhite = this.rBoderWhite = this.vertWhite = this.horiWhite = this.tWhite = this.bWhite = unitWhite;
		this.divide = divide;
		this.standardWidthImp = StandardWidth._MIN ;
	}

	public DivideGroupsImageRender(int groupCount,/*留白，需要远小于图片宽度和高度*/int unitWhite,Divide divide,StandardWidth standardWidthImp) {
		this.groupCount = groupCount;
		this.horiWhite = this.lBoderWhite = this.rBoderWhite = this.vertWhite = this.horiWhite = this.tWhite = this.bWhite = unitWhite;
		this.divide = divide;
		this.standardWidthImp = standardWidthImp;
	}
	/**
	 * 初始化原始图片数据
	 * @param originImages
	 */
	public void acceptImages(RenderImage[] originImages) {
		this.originImages = originImages;
		this.standardWidth = standardWidthImp.standardWidth(Arrays.stream(this.originImages).mapToInt(originImage->originImage.getWidth()).toArray());
		Arrays.stream(this.originImages).forEach(renderImage->renderImage.afterSetStandardWidth(this.standardWidth));
		divide.acceptData(Arrays.stream(this.originImages).mapToInt(originImage->(int)originImage.getHeight()).toArray());
	}
	@Override
	public void render(OutputStream os) {
		//根据标准宽计算图层宽
		this.width = this.lBoderWhite+this.rBoderWhite+this.horiWhite*(this.groupCount-1)+this.standardWidth*this.groupCount;
		//计算最高高度
		int[][] ca = divide.caculate();
		this.height = Arrays.stream(ca)
							.mapToInt(ca1->this.tWhite+this.bWhite+this.vertWhite*(ca1.length-1)
									+Arrays.stream(ca1).map(ca1Index->this.originImages[ca1Index].getHeight()).sum())
							.max()
							.getAsInt();
		
		//开始绘制
		BufferedImage wrapper = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D wrapperG = wrapper.createGraphics();
		wrapperG.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON);
		wrapperG.setColor(Color.BLACK);
		wrapperG.fillRect(0, 0, wrapper.getWidth(), wrapper.getHeight());
		
		for(int group = 0;group<ca.length;group++) {
			//第一层 分组
			int x = this.lBoderWhite + group*(this.standardWidth +this.horiWhite);
			int _y = this.tWhite;//分组初始y坐标
			for(int index = 0;index< ca[group].length;index++) {
				//第二层 每个分组的元素
				RenderImage renderImage = this.originImages[ca[group][index]];
				renderImage.beforeRenderMe(wrapperG);
				wrapperG.drawImage(renderImage.getBufferedImage(), null, x, _y);
				renderImage.afterRenderMe(wrapperG, x, _y);
				_y += this.originImages[ca[group][index]].getHeight()+this.vertWhite;//累加
			}
		}
		try {
			wrapperG.dispose();
			ImageIO.write(wrapper, "jpg", os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
