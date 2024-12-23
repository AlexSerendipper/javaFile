package leecode;

/**
 * 415. 字符串相加
 * 给定两个字符串形式的非负整数 num1 和num2 ，计算它们的和并同样以字符串形式返回。
 * 你不能使用任何內建的用于处理大整数的库（比如 BigInteger）， 也不能直接将输入的字符串转换为整数形式。
 *
 * 示例 1：
 * 输入：num1 = "11", num2 = "123"
 * 输出："134"
 * 示例 2：
 *
 * 输入：num1 = "456", num2 = "77"
 * 输出："533"
 * 示例 3：
 *
 * 输入：num1 = "0", num2 = "0"
 * 输出："0"
 *
 @author Alex
 @create 2024-03-27-20:04
 */
public class LC415 {
    public static void main(String[] args) {
        System.out.println(new LC415().addStrings("11","123"));
    }

    public String addStrings(String num1, String num2) {
        int i = num1.length() - 1, j = num2.length() - 1, add = 0;
        StringBuffer ans = new StringBuffer();
        while (i >= 0 || j >= 0 || add != 0) {
            int x = i >= 0 ? num1.charAt(i) - '0' : 0;  // 难点就在这，因为num1.charAt(i)-'0'实际上返回的就是一个数，但如果两个数字位数不同，直接返回0即可
            int y = j >= 0 ? num2.charAt(j) - '0' : 0;
            int result = x + y + add;
            ans.append(result % 10);  // 取个位数
            add = result / 10;  // 去除个位数，取剩下为进位
            i--;
            j--;
        }
        // 计算完以后的答案需要翻转过来
        ans.reverse();
        return ans.toString();
    }

}
