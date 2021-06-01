package render.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * 均值参考回溯法
 * @author <a href="1339liu@gmail.com">Accen</a>
 * @since 1.0
 */
public class AverageBacktraceDivide extends Divide {
	/**
	 * 保存降序排列后原始数据的下标
	 */
	private int[] sortedIndex;
	private int[] sortedValue;
	private boolean[] sortedUsage;//sortedValue的分配状态
	public AverageBacktraceDivide(int groupCount) {
		super(groupCount);
	}

	@Override
	public int[][] caculate() {
		int[][] forSortOriginArr = (int[][]) IntStream.range(0, origins.length)
									.mapToObj(index->new int[] {origins[index],index})
									.toArray(int[][]::new);
		Arrays.sort(forSortOriginArr, (o1,o2)->/*降序*/o2[0]-o1[0]);
		sortedIndex = new int[forSortOriginArr.length];
		sortedValue = new int[forSortOriginArr.length];
		IntStream.range(0, forSortOriginArr.length)
					.forEach(index->{
						sortedIndex[index] = forSortOriginArr[index][1];
						sortedValue[index] = forSortOriginArr[index][0];
					});
		sortedUsage = new boolean[forSortOriginArr.length];
		double average = (double)Arrays.stream(sortedValue).sum()/groupCount;
		int averageFmt = (int) Math.ceil(average);
		return (int[][]) IntStream.range(0, groupCount)
					.mapToObj(i->catchSumTo(averageFmt))
					.toArray(int[][]::new);
	}
	
	/**
	 * 从arr中获取一组数组，使其和==sum
	 * @param arr
	 * @param sum
	 * @return
	 */
	private int[] catchSumTo(int sum) {
		//本次抽取，已经使用过的元素
		boolean[] onceSortedUsage = Arrays.copyOf(sortedUsage, sortedUsage.length);
		int curSum = 0;
		ArrayList<Integer> rs = new ArrayList<>();
		int index = 0;
		while(true) {
			if(curSum == sum) {break;}
			if(index>=sortedValue.length) {
				if(rs.size()==0) {
					break;
				}else {
					//最后一个元素都用完了，还不符合sum，则回溯
					index = rs.remove(rs.size()-1);
					sortedUsage[index] = false;
					curSum -= sortedValue[index];
					index ++;
					continue;
				}
			}
			if(!sortedUsage[index]&&!onceSortedUsage[index]) {
				if(curSum +  sortedValue[index] <= sum) {
					rs.add(index);
					sortedUsage[index] = onceSortedUsage[index] = true;
					curSum += sortedValue[index];
					index++;
				}else {
					//如果加上当前数之后大于sum，则直接跳过
					index++;
				}
			}else {
				index++;
			}
		}
		
		if(curSum == sum) {
			//符合条件
			return rs.stream().mapToInt(i->sortedIndex[i]).toArray();
		}else {
			//不符合
			rs.forEach(i->sortedUsage[i]=false);
			if(sum-1>0) {
				return catchSumTo(sum-1);
			}else {
				return null;
			}
		}
	}

}
