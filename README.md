<div align="center">
<img width="250" src="https://raw.githubusercontent.com/AcceNoi/accenbot/master/accenbot-logo-1.png" alt="logo">
<h2 id="quick-start"><a href="https://github.com/AcceNoi/accenbot-render">Accenbot Render</a></h2>
</div>

### 1.目的

本项目主要用作[Accenbot](https://github.com/AcceNoi/accenbot)中P站榜单绘制功能的抽象

### 2.说明

为了解决 **若干个元素的数组，分为若干组时，其分别求和后方差最小** 问题的算法，***实际只需要解决数组的分组***。l例如数组{1,3,11,9,25,4,7}，按顺序分组为{{0,3,6},{1,4,7},{2,5}}。

```java
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
```

继承此类，实现caculate方法。

### 3.计划

计划实现的算法：

- [x] 顺序分组（升序和降序）
- [ ] 动态规划分组
- [x] 均值参考回溯分组（原理是方差最小意味着更接近算数均值，所以在排序好的数组中，依次求和直到接近均值为止形成一个分组）
- [x] 瀑布流/最短优先分组（遍历数组，每次取和最小的分组放入新元素）
- [ ] ...

| 算法名   | 主要类名                                                     | 是否方差最小 | 时间复杂度 | 算法理解难度 |
| -------- | ------------------------------------------------------------ | ------------ | ---------- | ------------ |
| 顺序分组 | [SequenceDivide](https://github.com/AcceNoi/accenbot-render/blob/main/src/main/java/render/algorithm/SequenceDivide.java) | x           | O(n)       | o           |
|动态规划分组||-||o|
|均值参考回溯分组|[AverageBacktraceDivide](https://github.com/AcceNoi/accenbot-render/blob/main/src/main/java/render/algorithm/AverageBacktraceDivide.java)|-|O(n^m)|-|
|瀑布流/最短优先分组|[MinimumFirstDivide](https://github.com/AcceNoi/accenbot-render/blob/main/src/main/java/render/algorithm/MinimumFirstDivide.java)|x|O(n)|o|

>**m为分组数，n为原始数据个数*，m<<n
>
>×表示不关注或者难度高，
>
>-表示趋向关注或者难度一般，
>
>o表示关注或者难度低

### 4.感谢

各位可以按照自己的想法提issue或者fork pull代码，或者对计划实现的算法写自己的实现，然后按照3中的格式写明自己的算法基本情况。
