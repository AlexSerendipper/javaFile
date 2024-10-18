package interview_computer1;

import java.util.HashSet;
import java.util.Scanner;

/**
 * DJI-组合及对比-使用在测试开发工程师岗卷A
 * 时间限制： 3000MS
 * 内存限制： 589824KB
 * 题目描述：
 * 已知：三个字符串变量x、y、z，其中，x由小写字母组成，y由数字字符组成，z由大写字母组成。
 *
 * 问题：1： x、y、z三个变量中各取1个字符组合成新字符串，有多少种组合？（组合不能重复）
 *       2： x中取2个字符组合成新字符串，y、z亦如此，总计有多少种组合？（2个字符不能重复）
 *
 * 示例: x='aa'
 *       y='12'
 *       z = 'ABC'
 *
 * 则：问题1组合为6种：a1A a2A a1B a2B a1C a2C
 *     问题2组合为4种：12 AB BC AC
 @author Alex
 @create 2023-08-06-20:07
 */

public class DJ1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashSet<String> set = new HashSet<>();
        String xx = sc.next();
        String yy = sc.next();
        String zz = sc.next();
        char[] x = xx.toCharArray();
        char[] y = yy.toCharArray();
        char[] z = zz.toCharArray();

        for(int i=0;i<x.length;i++){
            for(int j=0;j<y.length;j++){
                for(int k=0;k<z.length;k++){
                    char[] temps = new char[3];
                    StringBuilder sb = new StringBuilder();
                    sb.append(xx.charAt(i));
                    sb.append(yy.charAt(j));
                    sb.append(zz.charAt(k));
                    String temp = sb.toString();
                    set.add(temp);
                }
            }
        }
        System.out.println(set.size());

        int i1 = getNum(xx);
        int i2 = getNum(yy);
        int i3 = getNum(zz);
        System.out.println(i1+i2+i3);
    }


    public static int getNum(String a){
        HashSet<String> set = new HashSet<>();
        // 遍历字符串xx中所有的字符可能
        for(int i=0;i<a.length();i++){
            for (int j = 0; j < a.length(); j++) {
                if(i==j){
                    continue;
                }

                StringBuilder sb = new StringBuilder();
                // 如果添加的两个元素不相等
                if(a.charAt(i)!=a.charAt(j)){
                    sb.append(a.charAt(i));
                    sb.append(a.charAt(j));
                }

                String temp = sb.toString();
                // 并且翻转后在set中也不存在，则添加到set中
                String reverseTemp = sb.reverse().toString();
                if(!set.contains(reverseTemp) && !temp.equals("")){
                    set.add(temp);
                }
            }

        }
        return set.size();
    }
}
