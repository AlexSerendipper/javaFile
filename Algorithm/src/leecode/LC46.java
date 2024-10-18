package leecode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 给定一个不含重复数字的数组 nums，返回其所有可能的全排列。你可以按任意顺序返回答案。
 *
 * 示例 1：
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 * 示例 2：
 *
 * 输入：nums = [0,1]
 * 输出：[[0,1],[1,0]]
 * 示例 3：
 *
 * 输入：nums = [1]
 * 输出：[[1]]
 *
 * 思路：回溯法：一种通过探索所有可能的候选解来找出所有的解的算法。如果候选解被确认不是一个解（或者至少不是最后一个解），
 *      回溯算法会通过在上一步进行一些变化抛弃该解，即回溯并且再次尝试。
 *
 * 我们可以将题目给定的 n个数的数组划分成左右两个部分，左边的表示已经填过的数，右边表示待填的数，
 * 我们在回溯的时候只要动态维护这个数组即可。具体来说，假设我们已经填到第first个位置，那么nums数组中
 * [0,first−1] 是已填过的数的集合，[first,n−1] 是待填的数的集合。我们肯定是尝试用[first,n−1]里的数去填第first个数，
 * 假设待填的数的下标为 i，那么填完以后我们将第 i 个数和第 first 个数交换，即
 * 能使得在填第 first+1 个数的时候 nums 数组的 [0,first] 部分为已填过的数，[first+1,n−1] 为待填的数，回溯的时候交换回来即能完成撤销操作。
 *
 * 举个简单的例子，假设我们有 [2,5,8,9,10]这 5个数要填入，已经填到第 3个位置，已经填了 [8,9]两个数，1
 * 那么这个数组目前为 [8,9|2,5,10]这样的状态，分隔符区分了左右两个部分。假设这个位置我们要填 10这个数，为了维护数组，我们将 2 和 10 交换，
 * 即能使得数组继续保持分隔符左边的数已经填过，右边的待填 [8,9,10|2,5]
 @author Alex
 @create 2024-03-21-12:05
 */
public class LC46 {
    public static void main(String[] args) {
        int[] arr = {2,5,8,9,10};
        List<List<Integer>> permute = new LC46().permute(arr);
    }
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();

        List<Integer> output = new ArrayList<Integer>();
        for (int num : nums) {
            output.add(num);
        }

        int n = nums.length;

        backtrack(n, output, res, 0);
        return res;
    }

    public void backtrack(int n, List<Integer> output, List<List<Integer>> res, int first) {
        // 递归中止条件，所有数都填完了
        if (first == n) {
            res.add(new ArrayList<Integer>(output));
        }

        for (int i = first; i < n; i++) {
            // 动态维护数组
            Collections.swap(output, first, i);
            // 继续递归填下一个数
            backtrack(n, output, res, first + 1);
            // 撤销操作，恢复一下，即回溯
            Collections.swap(output, first, i);
        }
    }

}
