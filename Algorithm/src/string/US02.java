package string;



/**
 * 反转字符串 II：https://leetcode.cn/problems/reverse-string-ii/
 * 给定一个字符串 s 和一个整数 k，从字符串开头算起，每计数至 2k 个字符，就反转这 2k 字符中的前 k 个字符。
 *
 * 如果剩余字符少于 k 个，则将剩余字符全部反转。
 * 如果剩余字符小于 2k 但大于或等于 k 个，则反转前 k 个字符，其余字符保持原样。
 *
 *
 * 注意：2k只是计数！！！！！！！！！！！！！！！！！！不一定要存在2k个字符的。。。所以就算剩余字符大于2k，也是翻转前k个字符，实际上本题只有两种可能！！！！！！
 * 输入：s ="abcd"   k =3
 * 输出："abcd"
 *
 @author Alex
 @create 2023-06-26-9:25
 */

// 一些同学可能为了处理逻辑：每隔2k个字符的前k的字符，写了一堆逻辑代码或者再搞一个计数器，来统计2k，再统计前k个字符。
// 其实在遍历字符串的过程中，只要让i 每次移动 2k 就可以了，然后判断是否需要有反转的区间。
public class US02 {
    public static void main(String[] args) {
        String s = "abcdefghij";
        new US02().reverseStr(s,2);
    }

    public String reverseStr(String s, int k) {
        // 转换为char数组方便翻转
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i+=2*k) {
            // 就算剩余字符大于2k，也是翻转前k个字符（注意剩余字符长度为chars.length - i）
            if(chars.length - i >= k){
                reverseString(chars,i,i+k-1);
            // 否则 翻转剩余所有字符
            }else {
                reverseString(chars,i,chars.length-1);
            }
        }

        return new String(chars);
    }

    // 翻转数组的从i 到 j的区间
    public static void reverseString(char[] s, int i ,int j ) {
        for (; i < j; i++,j--) {
            char temp = s[i];
            s[i] = s[j];
            s[j] = temp;
        }
    }



    // 理解错了: 2k只是计数用的
    public String reverseStr1(String s, int k) {
        // 转换为char数组方便翻转
        char[] chars = s.toCharArray();
        // 计算出 剩余字符个数
        int rest = s.length() % (2*k);
        int point=0;
        for (int i = 0; i < s.length(); i+=(2*k)) {
            // 正常字符处理
            // 翻转其前k个字符
            if (i == 0){
                point = i;
                i = -1;
                continue;
            }

            reverseString(chars, point,point + k - 1);
            point = i + 1;

            // 剩余字符处理
            if(i == s.length() - rest - 1){
                if(rest<k){
                    reverseString(chars, i+1,s.length()-1);
                }
                if(rest<2*k && rest>=k){
                    reverseString(chars, i+1,i+k);
                }
            }
        }

        return new String(chars);
    }
}
