package render.algorithm;

import java.util.stream.IntStream;

/**
 * 按提供数组的顺序直接分组
 * @author <a href="1339liu@gmail.com">Accen</a>
 * @since 1.0
 */
public class SequenceDivide extends Divide {
	public static final String ASC = "asc";
	public static final String DESC = "desc";
	private String order;
	public SequenceDivide(int groupCount) {
		super(groupCount);
		this.order = ASC;
	}
	public SequenceDivide(int groupCount,String order) {
		super(groupCount);
		this.order = order;
	}
	
	@Override
	public int[][] caculate() {
		int[][] seqs = new int[groupCount][];
		int rem = this.origins.length%groupCount;
		int mod = this.origins.length/groupCount;
		IntStream.range(0, groupCount)
					.forEach(index->{
						seqs[index] = new int[mod+(rem>index?1:0)];
						IntStream.range(0, seqs[index].length)
									.forEach(index2->{
										int v = index2*groupCount+index;
										seqs[index][index2] = DESC.equalsIgnoreCase(this.order)?(this.origins.length-v):v;
									});
					});
		return seqs;
	}

}
