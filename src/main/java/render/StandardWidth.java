package render;

import java.util.Arrays;

/**
 * 标准宽接口
 * @author <a href="1339liu@gmail.com">Accen</a>
 * @since 1.0
 */
public interface StandardWidth {
	StandardWidth _MIN = new MIN();
	StandardWidth _MAX = new MAX();
	StandardWidth _ARI_AVE = new ARI_AVE();
	/**
	 * 计算标准宽
	 * @param widths 样本宽
	 * @return 确定的标准宽
	 */
	public int standardWidth(int[] widths);
	/**
	 * 取最小值为标准宽
	 * @author <a href="1339liu@gmail.com">Accen</a>
	 *
	 */
	public class MIN implements StandardWidth{

		@Override
		public int standardWidth(int[] widths) {
			return Arrays.stream(widths).min().getAsInt();
		}
		
	}
	/**
	 * 取最大值为标准宽
	 * @author <a href="1339liu@gmail.com">Accen</a>
	 *
	 */
	public class MAX implements StandardWidth{
		
		@Override
		public int standardWidth(int[] widths) {
			return Arrays.stream(widths).max().getAsInt();
		}
		
	}
	
	/**
	 * 取算数平均为标准宽
	 * @author <a href="1339liu@gmail.com">Accen</a>
	 *
	 */
	public class ARI_AVE implements StandardWidth{

		@Override
		public int standardWidth(int[] widths) {
			return Arrays.stream(widths).sum()/widths.length;
		}
		
	}
	/**
	 * 固定标准宽
	 * @author <a href="1339liu@gmail.com">Accen</a>
	 *
	 */
	public class CONSTANT implements StandardWidth{
		private int constant;
		public CONSTANT(int constant) {this.constant = constant;}

		@Override
		public int standardWidth(int[] widths) {
			return constant;
		}
		
	}
	
}
