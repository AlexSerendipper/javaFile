package leecode;

/**
 *
 * 青蛙跳台阶问题：一只青蛙一次可以跳上1级台阶，也可以跳上2级台阶。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。
 * 这里用递归法来解释，最好的解法是动态规划法！
 @author Alex
 @create 2024-04-08-16:11
 */


// 在本题中，我们要 求解青蛙跳上一个 n 级的台阶总共有多少种跳法；我们知道青蛙一次只可以跳1级或2级的台阶，
// 那么在小蛙 跳上第n 级台阶的前一步时，小蛙 一定站在第n-1 级或第n-2 级台阶上。
// 所以如果设「青蛙跳上一个 n 级的台阶」共有 f(n) 种跳法；则我们可以得到其中的函数关系为 f(n) = f(n-1) + f(n-2)
public class LCR127 {
    public int trainWays(int num) {
        if(num == 1){
            return 1;
        }else if(num == 2)
        {
            return 2;
        }else {
            return trainWays(num-1)+trainWays(num-2);
        }
    }
}
