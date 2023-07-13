package string;

/**
 * 替换空格：请实现一个函数，把字符串 s 中的每个空格替换成"%20"。
 * 输入：s = "We are happy."
 * 输出："We%20are%20happy."
 *
 @author Alex
 @create 2023-06-27-13:36
 */
public class US03 {
    public static void main(String[] args) {
        String s = "We are happy.";
        String s1 = new US03().replaceSpace(s);
        System.out.println(s1);
    }

    // 法2：双指针法
    public String replaceSpace(String s) {
        if(s == null || s.length() == 0){
            return s;
        }

        StringBuilder sb = new StringBuilder();
        // 每碰到一个空格，就往sb中添加两个空格，最后把sb加到字符串s中，s即可拓展为最终的长度
        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i)==' '){
                sb.append("  ");
            }
        }
        // 若是没有空格直接返回
        if(sb.length() == 0){
            return s;
        }

        int left = s.length() - 1;  // 定义左指针，指向原字符串的末尾
        s = s + sb.toString();
        int right = s.length() - 1;  // 定义右指针，指向拓宽后新字符串的末尾
        char[] chars = s.toCharArray();

        while(left>=0){
            if(chars[left]==' '){
                chars[right--] = '0';
                chars[right--] = '2';
                chars[right] = '%';
            }else {
                chars[right] = chars[left];
            }

            left--;
            right--;
        }

        return new String(chars);
    }

    // 方法1：直接调用String类的函数包
    public String replaceSpace1(String s) {
        String s1 = s.replace(" ", "%20");
        return s1;
    }


}
