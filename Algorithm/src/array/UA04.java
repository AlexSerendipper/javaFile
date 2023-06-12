package array;

/**
 * 5. 长度最小的子数组：给定一个含有 n 个正整数的数组和一个正整数 s ，找出该数组中满足其和 ≥ s 的长度最小的 连续 子数组，并返回其长度。如果不存在符合条件的子数组，返回 0。
 * 例：输入：s = 7, nums = [2,3,1,2,4,3] 输出：2 解释：子数组 [4,3] 是该条件下的长度最小的子数组。
 @author Alex
 @create 2023-06-07-14:44
 */
public class UA04 {
    public static void main(String[] args) {
        int[] nums = {2,3,1,2,4,3};
        int target = 7;
        int i = new UA04().minSubArrayLen2(target, nums);
        System.out.println(i);
    }

    // 1. 暴力解法: 两个for循环，然后不断的寻找符合条件的子序列（速度慢，暴力解法超时）
    public int minSubArrayLen(int target, int[] nums) {
        int length  = Integer.MAX_VALUE;  // 最终数组的长度
        for (int i = 0; i < nums.length; i++) {  // 外层指针指向子序列的起始位置
            int temp = 0;  // 子序列之和
            int count = 0;  // count表示子序列的长度

            for (int j = i; j < nums.length; j++) {  // 内层指针指向子序列的长度
                temp += nums[j];
                if(temp>=target){
                    count = j-i+1;  // 子序列的长度
                    length = count<length ? count : length;
                    break;
                }
            }
        }
        return length==Integer.MAX_VALUE?0:length;
    }


    // 2. 滑动窗口解法: 其实是双指针法的变种，外层控制父序列的的中止位置，内层求出满足条件的子序列
    public int minSubArrayLen2(int target, int[] nums) {
        int temp = 0;  // 滑动窗口内的值的大小
        int count = 0;  // count表示子序列的长度
        int begin = 0;  // 内层（子序列）的指针位置
        int length  = Integer.MAX_VALUE;  // 最终数组的长度

        for (int i = 0; i < nums.length; i++) {  // i为 父序列的起始位置
            temp += nums[i];
            while (temp>=target){
                count = i - begin + 1;
                length = count<length?count:length;
                temp -= nums[begin++];  // 最关键的部分，让temp始终存储的是滑动窗口内的数值和
            }
        }
        return length!=Integer.MAX_VALUE?length:0;
    }



}

