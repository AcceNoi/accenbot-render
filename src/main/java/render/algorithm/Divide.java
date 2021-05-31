package render.algorithm;
/**
 * 分组的超类，需要实现不同的算法应该实现caculate以输出分组好的数据
 * @author <a href="1339liu@gmail.com">Accen</a>
 * @since 1.0
 */
public abstract class Divide {
	/**
	 * 分组数 >0
	 */
	protected int groupCount;
	/**
	 * 原始数据
	 */
	protected int[] origins;
	public Divide(int groupCount) {
		this.groupCount = groupCount;
	}
	public void acceptData(int[] origins) {
		this.origins = origins;
	}
	/**
	 * 进行分组
	 * @return origns一维为分组，二维为每个分组的元素在origin中的数组下表
	 */
	public abstract int[][] caculate();
	
}
