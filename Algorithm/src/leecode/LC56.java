package leecode;

import java.util.*;

/**
 * 56. 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。
 * 请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。
 *
 * 示例 1：
 * 输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
 * 输出：[[1,6],[8,10],[15,18]]
 * 解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
 * 示例 2：
 * 输入：intervals = [[1,4],[4,5]]
 * 输出：[[1,5]]
 * 解释：区间 [1,4] 和 [4,5] 可被视为重叠区间。
 *
 * 思路
 * 如果我们按照区间的左端点排序，那么在排完序的列表中，可以合并的区间一定是连续的。如下所示
 * (1,9),(2，5),(19，20),(10，11),(12，20),(0，3),(0，1),(0，2)
 *                   ↓↓↓↓↓↓↓↓↓↓↓↓
 * (0,3),(0，1)(0，2),(1,9)(2，5),(10，11),(12，20),(19，20)
 *
 * 算法
 * 我们用数组 merged 存储最终的答案。
 * 首先，我们将列表中的区间按照左端点升序排序。然后我们将第一个区间加入 merged 数组中，并按顺序依次考虑之后的每个区间：
 * 如果当前区间的左端点在数组 merged 中最后一个区间的右端点之后，那么它们不会重合，我们可以直接将这个区间加入数组merged 的末尾；
 * 否则，它们重合，我们需要用当前区间的右端点更新数组 merged 中最后一个区间的右端点，将其置为二者的较大值。
 @author Alex
 @create 2024-03-21-11:45
 */
public class LC56 {
    public int[][] merge(int[][] intervals) {
        if (intervals.length == 0) {
            return new int[0][2];  // 异常处理。创建了一个二维整型数组，其外部数组长度为0，内部数组长度为2（但实际上没有内部数组存在）。
        }

        // 数组按照左区间进行排序
        Arrays.sort(intervals, new Comparator<int[]>() {
            public int compare(int[] interval1, int[] interval2) {
                return interval1[0] - interval2[0];
            }
        });

        List<int[]> merged = new ArrayList<int[]>();

        // 遍历每一个区间
        for (int i = 0; i < intervals.length; ++i) {
            int L = intervals[i][0], R = intervals[i][1];
            // 如果当前数组为空，抑或是当前merge列表中的最后的数组的右端点小于当前区间左端点
            // 直接加入merge数组末尾
            if (merged.size() == 0 || merged.get(merged.size() - 1)[1] < L) {
                merged.add(new int[]{L, R});
            } else {
                // 否则，更新当前merge列表中的最后的数组的右端点
                merged.get(merged.size() - 1)[1] = Math.max(merged.get(merged.size() - 1)[1], R);
            }
        }
        return merged.toArray(new int[merged.size()][]);
    }
}
