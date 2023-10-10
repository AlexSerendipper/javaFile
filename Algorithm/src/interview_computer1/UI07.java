package interview_computer1;


import java.util.Scanner;

/**
 * 9.9美的笔试第二题：
 *
 * 有一个田子方格行数为N (通过输入传入)，列数不固定。
 * 将把加密的字符串按照如下规则填充到田子方格中，即从上到下，从下再到上蛇形对角线循环填充。
 * 最终加密的新字符为第-行第一个、第一行第二个...第二行第一个...第N行第X个所有字符按顺序组合的字符
 *
 * 如:
 * 输入: abcdefghijklmnopqrst 4加密结果为: agmsbfhlnrtceikoqdjp
 *
 * 示例为：a           g...
 *          b       f
 *            c   e
 *              d
 @author Alex
 @create 2023-09-09-14:44
 */

public class UI07 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        int N = sc.nextInt();
        char[] chars = str.toCharArray();

        // 添加到二维数组中
        char[][] cc = new char[N][str.length()*N];


        int flag = 1;
        int col = 1;
        for (int i = 0; i < str.length();i++) {
            if(i<N){
                // 第一列赋值到二维数组，不分方向
                cc[i][0] = str.charAt(i);
            }else {
                if(flag==1){
                    // 第二列，从下往上赋值
                    for (int j = N-2; j >= 0; j--) {
                        if(i==str.length()){
                            break;
                        }
                        cc[j][col] = str.charAt(i++);
                    }
                    col++;
                    i--;
                    flag = flag*-1;
                }else {
                    // 第三列，从上往下赋值
                    for (int j = 1; j <= N - 1; j++) {
                        if(i==str.length()){
                            break;
                        }
                        cc[j][col] = str.charAt(i++);
                    }
                    col++;
                    i--;
                    flag = flag * -1;
                }
            }
        }

        String ss = "";
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < cc[0].length; j++) {
                ss = ss + cc[i][j] + "";
            }
        }
        String sss = ss.replaceAll("[\u0000]", "");
        System.out.println(sss);
    }
}
