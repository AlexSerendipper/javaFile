package usebasicgrammar;

import org.junit.Test;

/**
 * 【程序流程控制】
 * 【一、顺序结构】程序从上到下逐行地执行，中间没有任何判断和跳转
 *
 * 【二、分支结构】根据条件，选择性地执行某段代码
 *     1）分支结构1。多用于判断条件是一个范围的情况✔当多个条件是“包含”关系时，请遵循“上小下大"原则✔
 *        若要省略了{}，若下面有多行语句，只能写一行!!!
 *        else if 为可选项
 *         if (条件表达式1) {
 *             语句1；
 *         } else if (条件表达式2)  {
 *             语句2；
 *          ....
 *         } else {
 *             // 上述条件都不成立执行此处代码
 *         }
 *      2）分支结构2。多用于判断条件是一个定值（只能用于六种类型，byte/short/char/int/枚举类型/string）✔
 *        常用一个变量作为表达式
 *        case只能声明定值，不能声明一个范围
 *        ✔如果某一个被匹配上case不写break，输出后会执行下一个case直到遇到break或是直接运行到程序末尾
 *        switch(表达式){
 *             case value1:
 *                 // 表达式 全等于 value1 时要执行的代码
 *                 break;
 *             case value2:
 *                 // 表达式 全等于 value2 时要执行的代码
 *                 break;
 *             default:
 *                 // 表达式 不等于任何一个 value 时要执行的代码
 *         }
 *      3）分支结构3。
 *        返回值为布尔值时，可以直接用作条件判断 (只要判断的对象不是空或者undefined都是true)
 *        obj1[name]。当该对象有该属性时，就执行该分支
 *         if(obj1[name]){
 *             // 语句1
 *         }
 *
 * 【三、循环结构】根据循环条件，重复性的执行某段代码。
 *      1）循环结构1:for循环
 *       初始化变量是声明的新的变量，条件表达式为终止的条件，操作表达式用于变量更新（递增或递减）
 *       执行顺序为：生成初始化变量 → 判断条件表达式 → 执行循环体 → 执行操作表达式 → 判断条件表达式..
 *       其中for(;;)可以建立一个无限循环
 *          for(初始化变量; 条件表达式; 操作表达式 ){
 *                  //循环体
 *           }
 *      2）循环结构2：while循环
 *       初始化变量定义在循环外
 *       多用于判断条件比较复杂的情况✔
 *       while(true)建立一个无限循环
 *         定义初始化变量
 *         while (条件表达式) {
 *                     // 操作表达式
 *                     // 循环体代码
 *        }
 *     3)循环结构3：do while循环
 *      先执行一次循环体，再去判断条件，如果条件为TRUE，则继续执行循环体
 *       定义初始化变量
 *       do {
 *            // 循环体
 *            // 操作表达式
 *        } while(条件表达式);
 *     4)循环结构4：增强for循环。jdk5.0新增了foreach循环(加强for循环)，用来遍历集合和数组
 *      把遍历的每一个值取出赋值给局部变量
 *      遍历集合时，底层仍调用Iterator完成操作。
 *         for(遍历的元素类型 局部变量:遍历对象){
 *              //循环体
 *         }
 *     5)循环结构5:循环嵌套、内部循环结束后跳到外边的循环
 *      for (外循环的初始; 外循环的条件; 外循环的操作表达式) {
 *          for (内循环的初始; 内循环的条件; 内循环的操作表达式) {
 *              //循环体
 *         }
 *      }
 *
 * 【特殊关键字的使用】
 *  1）break()
 *    break语句用于终止某个语句块的执行
 *    主要用于switch语句和循环语句中
 *    break语句出现在多层嵌套的语句块中时，默认退出最近一层循环
 *    break语句出现在多层嵌套的语句块中时，可以通过标签指明要终止的是哪一层语句块。见下方例子
 *  2）continue()
 *    continue只能使用在循环结构中
 *    continue语句用于跳过其所在循环语句块的一次执行，继续下一次循环
 *    continue语句出现在多层嵌套的语句块中时，默认退出最近一层循环的当此循环
 *    continue语句出现在多层嵌套的循环语句体中时，可以通过标签指明要跳过的是哪一层循环
 *  3)return
 *    它的功能是结束一个方法。当一个方法执行到一个return语句时，这个方法将被结束
 *    return直接结束整个方法，不管这个return处于多少层循环之内
 *
 *
 @author Alex
 @create 2023-01-09-14:30
 */
public class UBG03 {
    // 分支语句若要省略了{}，若下面有多行语句，只能写一行!!!
    @Test
    public void test1() {
        int x = 4;
        int y = 1;
        if (x > 2) {
            if (y > 2)
                System.out.println(x + y);
            System.out.println("atguigu");
        } else
            System.out.println("x is " + x);
    }

    ;

    // 循环中的特殊情况：初始化变量指定在外边，有两个操作表达式用逗号分开
    // 要熟记for循环的执行过程
    @Test
    public void test2() {
        int num = 1;
        for (System.out.print('a'); num <= 3; System.out.print("c"), num++) {
            System.out.print("b");
        }
    }

    // 通过标签指明要终止的是哪一层语句块
    @Test
    public void test3() {
        label1:
        for (int i = 0; i < 5; i++) {
            label2:
            for (int j = 0; j < 5; j++) {
                label3:
                for (int k = 0; k < 5; k++) {
                    if (k == 2) {
                        break label2;
                    }
                    System.out.println(k);
                }
            }
        }
    }

    // 编写程序从1循环到150，并在每行打印一个值，另外在每个3的倍数行上打印出“foo”,在每个5的倍数行上打印“biz”,在每个7的倍数行上打印输出“baz”
    @Test
    public void test4(){
        for(int i=1;i<=150;i++) {
            if(i%3==0) {
                System.out.println("foo");
                continue;
            }
            if(i%5==0) {
                System.out.println("biz");
                continue;
            }
            if(i%7==0) {
                System.out.println("baz");
                continue;
            }
            System.out.println(i);
        }
    }

    // 统计所有小于非负整数 n 的质数的数量。
    // 思路：
    // 质数又称为素数。一个大于1的自然数，除了1和它自身外，不能被其他自然数整除的数叫质数；否则称为合数。
    // 厄拉多塞筛法：先将2－N的各数放入表中，然后在2的上面画一个圆圈，然后划去2的其他倍数；第一个既未画圈又没有被划去的数是3，将它画圈，再划去3的其他倍数；现在既未画圈又没有被划去的第一个数 是5，将它画圈，并划去5的其他倍数……
    // 依次类推，一直到所有小于或等于N的各数都画了圈或划去为止。这时，表中画了圈的以及未划去的那些数正好就是小于N的素数。

    @Test
    public void test5(){
        countPrimes(100);
    }


    /**
     * 统计n以内的质数数量并输出
     * @param n
     * @return
     */
    public static int countPrimes(int n) {
        if(n < 3){
           return 0;
        }

        boolean [] dp = new boolean [n];  // 判断是否为数组，index为false的是质数
        dp[0] = true;  // 先把0和1标记为合数（非质数）, 【数组中默认值为false】
        dp[1] = true;
        int res = 0;

        for(int i = 2; i * i < n; i++){  // 遍历元素
            if(!dp[i]){  // 如果是质数，将它的整数倍都标记位合数
                for(long j = i * i; j < n; j += i)
                    dp[(int)j] = true;
            }
        }

        int ans = 0;
        for (int i = 0; i < dp.length; i++) {
            if(dp[i]==false){
                System.out.print(i+" ");
                ans++;
            }
        }
        return ans;
    }
}

