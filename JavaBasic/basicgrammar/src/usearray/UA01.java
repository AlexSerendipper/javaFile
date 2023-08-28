package usearray;

import org.junit.Test;

import java.util.Arrays;

/**
 * 【数组概述】
 *  数组(Array)，是多个相同类型数据按一定顺序排列的集合，并使用一个名字命名，并通过编号的方式对这些数据进行统一管理。
 *  数组本身是引用数据类型，而数组中的元素可以是任何数据类型
 *  创建数组对象会在内存中开辟一整块连续的空间，而数组名中引用的是这块连续空间的首地址。
 *  数组的长度一旦确定，就不能修改
 *  我们可以直接通过下标(或索引)的方式调用指定位置的元素
 *  注意：java的索引值只有正数
 *
 * 【一维数组】
 *  1）声明：type var[] 或 type[] var；  声明数组时不能指定其长度
 *  2）初始化
 *      静态初始化（在定义数组的同时就为数组元素分配空间并赋值）。如int arr[] = new int[]{3, 9, 8};
 *      动态初始化（数组的动态赋值：只指定数组长度）。如 int[] arr = new int[2];
 *                                                    arr[0] = 3;
 *                                                    arr[1] = 9;
 *      ✔注意：静态初始化存在类型推断，可以省略new。如int[] array1 = {1,2,3,4,5}
 *  3）引用：数组名[数组元素下标]
 *  4）长度：array1.length
 *  5）默认初始化：一维数组元素的默认初始值
 *            byte 0
 *            short 0
 *            int 0
 *            long 0L
 *            float 0.0F
 *            double 0.0
 *            char 0 或写为:’\u0000’(表现为空)
 *            boolean false
 *            引用类型 null
 *  6）赋值。int array2 = array1 ✔所有的引用数据类型的变量名实际上储存的都是地址值，所以引用数据类型赋值操作更改的只是地址指针
 *
 * 【二维数组】二维数组可以看成是一维数组array1中的元素存储的是另一个一维数组array2。其实，从数组底层的运行机制来看，其实并没有多维数组。
 *  1）声明：int[][] array1 或者 int[] array1[]  或者 int array1[][]
 *  2）初始化
 *      静态初始化（在定义数组的同时就为数组元素分配空间并赋值）。如int[][] array1 = new int[][]{{1,2},{3,4},{5,6}}
 *      动态初始化1（数组的动态赋值：只指定二维数组个数）。如 int[][] arr = new int[2][];
 *                                                          arr[0] = new int[3]；
 *                                                          arr[1] = new int[1];
 *      动态初始化2（数组的动态赋值：指定二维数组个数和一维数组长度）。如string[][] array2 = new string[3][2]。二维数组中有3个一维数组。每一个一维数组中有2个元素
 *      ✔注意：静态初始化存在类型推断，可以省略new。如int[] array1 = {{1,2},{3,4},{5,6}}
 *  3）引用：数组名[行索引值][列索引值]
 *  4）长度1：array1.length：获取二维数组个数
 *     长度2：array1[1].length：获取一维数组长度
 *  5）默认初始化：外层数组初始值即为数组的地址值。内层就为一维数组，所以初始值同上
 *
 * 【Arrays工具类】常用方法如下
 *   Arrays.equals(int[] a,int[] b)            判断两个数组是否相等。
 *   Arrays.toString(int[] a)                  输出数组信息。
 *   Arrays.void fill(int[] a,int val)         将指定值覆盖整个数组。
 *   Arrays.void sort(array1)                  对数组进行排序（需要实现comparable接口）
 *   Arrays.int binarySearch(int[] a,int key)  对数组（需排序后）进行二分法检索指定值的索引
 *
 * 【数组使用中的常见异常】
 *   数组脚标越界异常(ArrayIndexOutOfBoundsException)
 *   空指针异常(NullPointerException)
 *
 @author Alex
 @create 2023-01-09-15:18
 */
public class UA01 {
    // array的常见用法
    @Test
    public void test1(){
        // 二维数组的遍历
        int[][] array3 = {{1,2},{3,4,5,6},{7,8}};
        for(int i=0;i<array3.length;i++) {
            for(int j=0;j<array3[i].length;j++) {
                System.out.print(array3[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("*****************");
        // 二维数组的默认值, 因为外层数组是引用数据类型，当里面没有值的时候初始值为null
        double[][] array4 = new double[3][];
        System.out.println(array4[1]);
    }

    // arrays工具类的使用
    @Test
    public void test2(){
        int[] array1 = new int[] {1,2,3,4};
        int[] array2 = new int[] {1,2,3,5};
        // 显示数组
        System.out.println(Arrays.toString(array2));
        int[] array3 = new int[]{-98,-21,0,32,33,43,64,76};
        // 二分法查找指定值
        System.out.println(Arrays.binarySearch(array3, 76));
    }


}
