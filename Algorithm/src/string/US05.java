package string;

/**
 * 左旋转字符串：字符串的左旋转操作是把字符串前面的若干个字符转移到字符串的尾部。请定义一个函数实现字符串左旋转操作的功能。
 * 比如，输入字符串"abcdefg"和数字2，该函数将返回左旋转两位得到的结果"cdefgab"。
 @author Alex
 @create 2023-06-28-9:39
 */

public class US05 {
    // 直接拆分成两个字符串，然后拼接即可
    // 但是为了让本题更有意义，提升一下本题难度：不能申请额外空间，只能在本串上操作。
//    具体步骤为：
//    (1)反转区间为前n的子串
//    (2)反转区间为n到末尾的子串
//    (3)反转整个字符串
    public String reverseLeftWords(String s, int n) {
        StringBuilder sb = new StringBuilder(s);
        reverseStr(sb,0,n-1);
        reverseStr(sb,n,sb.length()-1);
        reverseStr(sb,0,sb.length()-1);
        return sb.toString();
    }

    private static void reverseStr(StringBuilder sb,int begin,int end){
        while(begin<end){
            char temp = sb.charAt(begin);
            sb.setCharAt(begin,sb.charAt(end));
            sb.setCharAt(end,temp);
            begin++;
            end--;
        }
    }
}
