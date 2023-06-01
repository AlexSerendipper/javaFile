package usearray;

import org.junit.Test;

import java.util.Arrays;

/**
 *【数组中涉及到的常见算法】2
 * 1. 数组元素的赋值(杨辉三角、回形数等)
 * 2. 求数值型数组中元素的最大值、最小值、平均数、总和等
 * 3. 数组的复制、反转、查找(线性查找、二分法查找)
 * 4. 数组元素的排序算法
 *
 @author Alex
 @create 2023-01-09-16:13
 */
public class UA03 {
    // 数组元素的复制、翻转
    @Test
    public void test1() {
        // 1. 数组的复制，必须重新开辟内存空间
        int[] array1, array2, array3, array4;
        array1 = new int[]{2, 3, 5, 7, 11, 13, 17, 19};
        array2 = new int[array1.length];
        for (int i = 0; i < array1.length; i++) {
            array2[i] = array1[i];
        }
        System.out.println(Arrays.toString(array2));
        System.out.println("*****************");
        // 2. 数组的翻转1（生成新数组）
        array3 = new int[array1.length];
        for (int i = array1.length - 1; i >= 0; i--) {
            array3[7 - i] = array1[i];
        }
        System.out.println(Arrays.toString(array3));
        System.out.println("*****************");
        // 3. 数组的翻转2（不生成新数组）
        for (int i = 0; i < array1.length / 2; i++) { // 这里除以2刚好，可以带入值体会一下
            int temp = array1[i];
            array1[i] = array1[array1.length - i - 1];
            array1[array1.length - i - 1] = temp;
        }
        System.out.println(Arrays.toString(array1));
    }

    // 数组查找（线性查找就是一个个顺序查找，这里主要针对特殊查找方式）
    // 二分查找：二分法查找仅针对有序数组
    // 思路 1)首先找到中间的索引值   floor(5/2) = 2，对应值为3
    //      2)中间数与target对比   3 [5,9,12]
    //      3)再找到中间的索引  [5] 9 [12]
    // -----------------------------------------------
    // 给定一个n个元素有序的（升序）整型数组nums和一个目标值target，写一个函数搜索nums中的target，如果目标值存在返回下标，否则返回-1
    // 输入: nums = [-1,0,3,5,9,12], target = 9
    // 输出: 4
    // 输入: nums = [-1,0,3,5,9,12], target = 2
    // 输出: -1
    @Test
    public void test2() {
        int[] array2 = new int[]{2, 3, 5, 7, 11, 13, 17, 19};
        int left = 0, mid, right = array2.length - 1;
        int target = 2;  // 需要查找的目标值
        boolean flag = true;
        while (left <= right) {  // 这个小于等于主要是查找最后一个数的区别，如本题的12（自己验算）。开区间设置为<即可
            mid = (right + left) / 2;
            // 刚好在中间
            if (array2[mid] == target) {
                System.out.println("指定元素的位置为：" + mid);
                flag = false;
                break;
            }
            // 从右侧寻找
            else if (array2[mid] < target) {
                left = mid + 1;
            }
            // 从左侧寻找
            else if (array2[mid] > target) {
                right = mid - 1;
            }
        }
        if (flag == true) {
            System.out.println("没有找到哦~很抱歉");
        }
    }
}
