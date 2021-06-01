package render.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.IntStream;

/**
 * 最小优先算法
 * @author <a href="1339liu@gmail.com">Accen</a>
 *
 */
public class MinimumFirstDivide extends Divide{

	public MinimumFirstDivide(int groupCount) {
		super(groupCount);
	}

	@Override
	public int[][] caculate() {
		ArrayList<LinkedList<Integer>> rs = new ArrayList<LinkedList<Integer>>(groupCount);
		IntStream.range(0, groupCount).forEach(i->rs.add(i, new LinkedList<>()));
		int[] rsSum = new int[groupCount];
		int minimumIndex = 0;
		for(int i = 0;i<this.origins.length;i++) {
			rs.get(minimumIndex).add(i);
			rsSum[minimumIndex] += this.origins[i];
			//更新minimumIndex
			minimumIndex = IntStream.range(0, groupCount).reduce((x,y)->rsSum[x]<=rsSum[y]?x:y).getAsInt();
		}
		return rs.stream().map(r->r.stream().mapToInt(i->i).toArray()).toArray(int[][]::new);
	}

}
