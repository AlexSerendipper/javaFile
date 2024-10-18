package acm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * HJ41 称砝码
 * 描述
 * 现有n种砝码，重量互不相等，分别为 m1,m2,m3...mn ；
 * 每种砝码对应的数量为 x1,x2,x3...xn 。现在要用这些砝码去称物体的重量(放在同一侧)，问能称出多少种不同的重量。
 *
 *
 * 注：称重重量包括 0
 * 数据范围：每组输入数据满足1≤n≤10，1≤xi≤10

 * 输入描述：
 * 对于每组测试数据：
 * 第一行：n --- 砝码的种数(范围[1,10])
 * 第二行：m1 m2 m3 ... mn --- 每种砝码的重量(范围[1,2000])
 * 第三行：x1 x2 x3 .... xn --- 每种砝码对应的数量(范围[1,10])
 * 输出描述：
 * 利用给定的砝码可以称出的不同的重量数
 *
 * 示例输入：
 * 2
 * 1 2
 * 2 1
 *
 * 输出：
 * 5
 * 说明：
 * 可以表示出0，1，2，3，4五种重量。
 @author Alex
 @create 2024-03-10-12:10
 */
public class HJ41 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {  // 注意 while 处理多个 case
            HashSet<Integer> set = new HashSet<>();  // 存放所有可能的结果，不用担心重复问题
            set.add(0);  // 初始化为0
            int n = in.nextInt();  // 个数
            int[] w = new int[n];
            int[] nums = new int[n];

            for (int i = 0; i < n; i++) {
                w[i] = in.nextInt();  // 砝码的重量
            }

            for (int i = 0; i < n; i++) {
                nums[i] = in.nextInt();  // 砝码个数
            }

            for (int i = 0; i < n; i++) {  // 遍历砝码
                ArrayList<Integer> list = new ArrayList<>(set); // 取当前所有的结果✔✔✔
                for (int j = 1; j <= nums[i]; j++) {  // 遍历个数
                    for (int k = 0; k < list.size(); k++) {
                        set.add(list.get(k) + w[i] * j);
                    }
                }
            }
            System.out.println(set.size());
        }
    }
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        int n = sc.nextInt();  // 砝码种数
//
//        int[] arr1 = new int[n];  // 重量
//        int[] arr2 = new int[n];  // 数量
//
//        for (int i = 0; i < n; i++) {
//            arr1[i] = sc.nextInt();
//        }
//
//        for (int i = 0; i < n; i++) {
//            arr2[i] = sc.nextInt();
//        }
//
//        ArrayList<Integer> arr = new ArrayList<>();
//        HashSet<Integer> set = new HashSet<>();
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < arr2[i]; j++) {
//                arr.add(arr1[i]);
//            }
//        }
//
//        int temp = 0;
//        // 双指针遍历arr,这样会漏掉一些情况...
//        for (int i = 0; i < arr.size(); i++) {
//            temp = arr.get(i);
//            set.add(temp);
//            for (int j = i+1; j < arr.size(); j++) {
//                temp = temp + arr.get(j);
//                set.add(arr.get(i)+arr.get(j));
//                set.add(temp);
//            }
//        }
//        // 还有重量0，所以加一个
//        System.out.println(set.size()+1);
//    }
}
