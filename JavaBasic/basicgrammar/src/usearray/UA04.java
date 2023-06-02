package usearray;

import org.junit.Test;

/**
 *【数组中涉及到的常见算法】3
 * 1. 数组元素的赋值(杨辉三角、回形数等)
 * 2. 求数值型数组中元素的最大值、最小值、平均数、总和等
 * 3. 数组的复制、反转、查找(线性查找、二分法查找)
 * 4. 数组元素的排序算法
 *
 *【数组元素的排序】
 *【如何衡量排序算法的优劣】
 * 时间复杂度：分析关键字的比较次数和记录的移动次数(因为有的时候排序的是对象，根据对象的属性来排序)
 * 空间复杂度：分析排序算法中需要多少辅助内存
 * 稳定性：若两个记录A和B的关键字值相等，但排序后A、B的先后次序保持不变，则称这种排序算法是稳定的
 *【排序算法汇总】28了解即可，34需要手写，其余知道名字即可
 * 选择排序:1直接选择排序、2堆排序
 * 交换排序:3冒泡排序、4快速排序（这个后面在搞，有点难，建议面试前突击一下）
 * 插入排序:5直接插入排序、6折半插入排序、7Shell排序
 * 8归并排序
 * 9桶式排序
 * 10基数排序
 *
 *
 @author Alex
 @create 2023-01-09-16:13
 */
public class UA04 {
    // 冒泡排序思想：
    // 1. 比较相邻的元素。如果第一个比第二个大（升序），就交换他们两个。
    // 2. 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。这步做完后，最后的元素会是最大的数。
    // 3. 针对所有的元素重复以上的步骤，除了最后一个。
    // 4. 持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较为止
    @Test
    public void test1() {
        // 1冒泡排序
        int temp;
        int[] array1 = new int[]{43, 32, 76, -98, 0, 64, 33, -21};
        for (int i = 0; i < array1.length - 1; i++) {  // i控制总共需要循环的次数
            for (int j = 0; j < array1.length - 1 - i; j++) {  // 关键点：每次相邻的两个比。。，这个是这样看的，假设4个数，i=0需要比3次，i=1需要比2两次，规律就是array1.length - 1 - i
                if (array1[j] > array1[j + 1]) {  // 交换两者顺序
                    temp = array1[j];
                    array1[j] = array1[j + 1];
                    array1[j + 1] = temp;
                }
            }
        }
        // 显示排序后数组
        for (int i = 0; i < array1.length; i++) {
            System.out.print(array1[i] + " ");
        }
    }
}