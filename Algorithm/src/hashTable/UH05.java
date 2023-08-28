package hashTable;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 两数之和：https://leetcode.cn/problems/two-sum/
 *
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和 为目标值 target  的那 两个 整数，并返回它们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现！
 * 你可以按任意顺序返回答案。
 @author Alex
 @create 2023-06-21-7:55
 */
public class UH05 {
    @Test
    public void test(){
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0,3);
        map.put(1,2);
        map.put(2,4);
        System.out.println("map.get(3) = " + map.get(3));
        int[] nums = {3,2,4};
        int[] ints = new UH05().twoSum(nums, 6);
        System.out.println(Arrays.toString(ints));
    }


    // 在遍历数组的时候，只需要向map去查询是否有和目前遍历元素匹配的数值，
    // 如果有，就找到的匹配对，如果没有，
    // 就把目前遍历的元素放进map中，因为map存放的就是我们访问过的元素。
    public int[] twoSum(int[] nums, int target) {
        int[] indexs = new int[2];  // 用于存放索引
        HashMap<Integer, Integer> map = new HashMap<>();

        if(nums == null || nums.length == 0){
            return indexs;
        }

        for (int i = 0; i < nums.length; i++) {

            // 如果有
            int temp = target - nums[i];
            if(map.containsKey(temp)){
                indexs[0] = map.get(temp);
                indexs[1] = i;
                return indexs;
            }
            // 如果没有。注意：把数组中 元素的值作为map的key，元素的索引作为map的value
            map.put(nums[i],i);
        }
        return indexs;
    }
}
