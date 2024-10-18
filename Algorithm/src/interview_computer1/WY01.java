package interview_computer1;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 网易雷火测试开发：输入为顺序输入的id，找到缺失的id
 *
 * 输入:
[10005, 10006, 10008, 10009, 10010, 10011, 10012, 10015]


 * 输出:[10007, 10013, 10014]
 *
 *
 @author Alex
 @create 2024-03-27-15:26
 */
public class WY01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int first = 0;
        int end = 0;
        int pre = 0;
        ArrayList<Integer> arr = new ArrayList<>();
        while (sc.hasNext()) { // 注意 while 处理多个 case
            String a = sc.next();
            if(a.matches("^\\[\\d+\\,")){
                first = Integer.parseInt(a.substring(1,a.length()-1));
                pre = first;
            }else if(a.matches("\\d+\\]")){
                end = Integer.parseInt(a.substring(0,a.length()-1));
                for (int i = pre; i < end-1; i++) {
                    arr.add(i+1);
                }
                break;
            }else {
                int temp = Integer.parseInt(a.substring(0,a.length()-1));
                if(temp==pre+1){
                    pre++;
                }else {
                    pre++;
                    arr.add(pre);
                    pre=temp;
                }
            }
        }
        Object[] objects = arr.toArray();
        for (int i = 0; i < objects.length; i++) {
            if (i == 0) {
                System.out.print("["+ objects[i]+", ");
            }else if(i==objects.length-1){
                System.out.print(objects[i]+"]");
            }else{
                System.out.print(objects[i]+", ");
            }
        }
    }
}
