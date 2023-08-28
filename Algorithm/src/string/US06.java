package string;

/**
 * 实现 strStr() 函数。
 * 给定一个 haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如果不存在，则返回  -1。
 * 示例 1: 输入: haystack = "hello", needle = "ll" 输出: 2
 * 示例 2: 输入: haystack = "aaaaa", needle = "bba" 输出: -1
 * 说明: 当 needle 是空字符串时，我们应当返回什么值呢？这是一个在面试中很好的问题。 对于本题而言，当 needle 是空字符串时我们应当返回 0
 @author Alex
 @create 2023-06-29-9:18
 */
public class US06 {
    public static void main(String[] args) {
        // 方法：KMP
        String haystack = "aabaabaafa";
        String needle = "aabaaf";
        int i = new US06().strStr(haystack, needle);
        System.out.println(i);

    }

    // KMP算法：建议看视频吧
    // 前缀是指不包含最后一个字符 的 所有以第一个字符开头的连续子串。如aabaaf的前缀有a,aa,aab,aaba,aabaa
    // 后缀是指包含最后一个字符，不包含首字符的所有连续子串。如aabaaf的后缀有abaaf,baaf,aaf,af,f

    // 最长相等前后缀：指的是一个字符串，其 相等的 前后缀 中最长的长度（或是前后缀中相等的个数）
    // 如：'a'的最长相等前后缀为0。'aa'的最长相等前后缀为1。 'aaa'的最长相等前后缀为2。
    // 计算出所有子串的最长相等前后缀，即可组成前缀表，如'aabaaf'的前缀表为 010120

    // 匹配过程：难点，前一个字符的前缀表的数值是2， 所以把下标移动到下标2的位置继续比配。
    //           因为前缀表的数值是2，所以代表有一个与之相同的前缀aa，长度为2，接下来的匹配要从与之相同的前缀开始匹配，所以下标为2处开始匹配

    // next数组，很多KMP算法的时间都是使用next数组来做回退操作，本质上next数组就可以是前缀表，但是很多实现都是把前缀表统一减一（或右移一位，初始位置为-1）之后作为next数组。
    //                                                                                 （实际上整体右移，只不过是只需比较当前字符对应的前缀表的数值进行跳转即可）


    // （1）构造next数组
    // 其实就是计算模式串s，前缀表的过程。 主要有如下三步：
    // 初始化：定义两个指针i和j，j指向前缀末尾位置，i指向后缀末尾位置。。。✔✔注意,j不仅指向前缀末尾位置，而且表示i之前（包括i）这个子串的最长相等前后缀的长度！！！！！！！！！！！！！！,直接理解为表示i之前（包括i）这个子串的最长相等前后缀的长度就完事了
    // 处理前后缀不相同的情况
    // 处理前后缀相同的情况
    public int strStr(String haystack, String needle) {
        if (needle.length() == 0) return 0;
        int[] next = new int[needle.length()];
        getNext(next, needle);


        // i指向haystack ，j指向needle
        int j = 0;
        for (int i = 0; i < haystack.length(); i++) {
            // 如果字符不同，按照next数组则回退
            while (j > 0 && needle.charAt(j) != haystack.charAt(i)){
                j = next[j - 1];
            }
            // 字符相同，继续比较下一位
            if (needle.charAt(j) == haystack.charAt(i)){
                j++;
            }
            // 若j = needle.length()，说明找到了相同子串
            if (j == needle.length()){
                return i - needle.length() + 1;
            }
        }
        return -1;

    }

    private static void getNext(int[] next, String s) {
        // 初始化
        int j = 0;
        next[0] = 0;
        // 不相同：回退
        for (int i = 1; i <= s.length(); i++) {
            while(j>0 && s.charAt(i)!=s.charAt(j)){
                j = next[j-1];
            }

            // 相同
            if(s.charAt(i)==s.charAt(j)){
                j++;
            }

            next[i] = j;
        }
    }

    // 暴力解法
    public int strStr1(String haystack, String needle) {
        for (int i = 0; i < haystack.length(); i++) {
            // 若要取的长度超出了索引了，直接返回0即可
            if((i + needle.length())>haystack.length()){
                return -1;
            }

            // 取子串进行比较
            if(haystack.charAt(i) == needle.charAt(0)){
                String temp = haystack.substring(i, i + needle.length());
                if(temp.equals(needle)){
                    return i;
                };
            }
        }
        return -1;
    }
}
