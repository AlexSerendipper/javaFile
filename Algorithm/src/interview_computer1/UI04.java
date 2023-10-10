package interview_computer1;

import java.util.*;

/**
 * 携程23/9/7。测试开发工程师
 *
 * 游游拿到了一个字符矩阵，她想知道有多少个三角形满足以下条件:
 * 1.三角形的三个顶点分别是y、o、u字符。
 * 2.三角形为直角三角形，且两个直角边一个为水平、另一个为垂直。
 * 输入：
 * 2 3
 * you
 * our
 *
 * 输出：3
 *
 *
 *
 @author Alex
 @create 2023-09-07-19:07
 */

// 超时，其实纵向的遍历处是可以少写一层的~
public class UI04 {
    public static void main(String[] args) {
        // 三个点，代表一个位置，不重复即可
        // 分别横向寻找，纵向寻找。找到就放到set中
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();  // 行
        int m = sc.nextInt();  // 列
        char[][] chars = new char[n][m];
        int[][] idx = new int[n][m];

        // 字符矩阵和对应的索引
        for (int i = 0; i < n; i++) {
            String s = sc.next();
            for (int j = 0; j < s.length(); j++) {
                chars[i][j] = s.charAt(j);
                idx[i][j] = i*m + j;
            }
        }

        HashSet<int[]> set1 = new HashSet<>();

        // 开始找矩形
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                HashSet<Character> set2 = new HashSet<>();
                set2.add('y');
                set2.add('o');
                set2.add('u');
                if(set2.contains(chars[i][j])){
                    set2.remove(chars[i][j]);  // 1、移除了一个y/o/u，以y为例
                }else {
                    continue;
                }

                // 横向遍历剩余两种可能即可
                for (int k = 0; k < m; k++) {
                    if(set2.contains(chars[i][k])){  // 2、移除了一个元素，以o为例
                        set2.remove(chars[i][k]);
                    }else {
                        continue;
                    }
                    // 纵向遍历
                    for (int l = 0; l < n; l++) {
                        if(set2.contains(chars[l][j])) {
                            int[] temp = new int[3];
                            temp[0] = idx[i][j];
                            temp[1] = idx[i][k];
                            temp[2] = idx[l][j];
                            Arrays.sort(temp);
                            set1.add(temp);
                        }
                    }
                    set2.add(chars[i][k]);  // 3、遍历后要把这个元素加回来
                }
            }
        }
        System.out.println(set1.size());
    }
}
