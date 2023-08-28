package acm;

import java.util.Scanner;

/**
 * 开发一个坐标计算工具， A表示向左移动，D表示向右移动，W表示向上移动，S表示向下移动。从（0,0）点开始移动，从输入字符串里面读取一些坐标，并将最终输入结果输出到输出文件里面。
 *
 * 输入：
 * 合法坐标为A(或者D或者W或者S) + 数字（两位以内）
 * 坐标之间以;分隔。
 * 非法坐标点需要进行丢弃。如AA10;  A1A;  $%$;  YAD; 等。
 * 下面是一个简单的例子 如：
 * A10;S20;W10;D30;X;A1A;B10A11;;A10;
 *
 * 处理过程：
 * 起点（0,0）
 * +   A10   =  （-10,0）
 * +   S20   =  (-10,-20)
 * +   W10  =  (-10,-10)
 * +   D30  =  (20,-10)
 * +   x    =  无效
 * +   A1A   =  无效
 * +   B10A11   =  无效
 * +  一个空 不影响
 * +   A10  =  (10,-10)
 * 结果 （10， -10
 @author Alex
 @create 2023-07-17-15:19
 */
public class HJ17 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String str = in.nextLine();
        String[] strs = str.split(";");
        // 初始坐标
        int x = 0;
        int y = 0;
        for(String ss:strs){
            if(check(ss)){
                char c = ss.charAt(0);
                int n = Integer.parseInt(ss.substring(1,ss.length()));

                switch(c){
                    case 'A':
                        x -= n;
                        break;
                    case 'D':
                        x += n;
                        break;
                    case 'W':
                        y += n;
                        break;
                    case 'S':
                        y -= n;
                        break;
                }
            }else{
                // 不合法，直接跳过本次循环
                continue;
            }
        }
        System.out.println(x+","+y);
    }

    public static boolean check(String ss){
        // 判断输入的字符串是否符合规范
        if(ss.isEmpty()){
            return false;
        }
        String a = ss.substring(0,1);
        String b = ss.substring(1,ss.length());
        if(a.matches("[ADSW]") && b.matches("[0-9]{1,2}")){
            return true;
        }
        return false;
    }

}


