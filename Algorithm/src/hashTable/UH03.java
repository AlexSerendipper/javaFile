package hashTable;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.REUtil;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.TreeMap;

/**
 * 快乐数：https://leetcode.cn/problems/happy-number/
 *
 * 编写一个算法来判断一个数 n 是不是快乐数。
 * 「快乐数」 定义为：
 * 对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和。
 * 然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。
 * 如果这个过程 结果为 1，那么这个数就是快乐数。
 * 如果 n 是 快乐数 就返回 true ；不是，则返回 false 。
 @author Alex
 @create 2023-06-21-7:37
 */
public class UH03 {

    public static void main(String[] args) {
        boolean happy = new UH03().isHappy(19);
    }

    // 这道题目使用哈希法，来判断这个sum是否重复出现，如果重复了就是return false， 否则一直找到sum为1为止。
    public boolean isHappy(int n) {
        HashSet<Integer> set = new HashSet<>();
        while(true){
            int sum = getSum(n);
            if(sum == 1 ){
                return true;
            }

            if(set.contains(sum)){
                return false;
            }
            set.add(sum);
            n = sum;
        }

    }

    // 求一个数各位上的平方和
    public static int getSum(int n){
        int sum = 0;
        while (n>0) {
            sum += (n % 10) * (n % 10);
            n /= 10;  // 注意：由于n为int类型，所以除法运算直接截位
        }
        return sum;
    }
}
