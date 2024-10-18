package acm;

import java.util.Scanner;

/**
 * HJ39：判断两个IP是否属于同一子网
 *
 @author Alex
 @create 2024-03-07-13:28
 */
public class HJ39 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String mask = sc.nextLine();
        String ip1 = sc.nextLine();
        String ip2 = sc.nextLine();

        String[] strs1 = mask.split("\\.");
        String fir = strs1[0];
        String end = strs1[strs1.length - 1];
        // 格式判断
        if(!mask.matches("\\b((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}((25[0-5]|2[0-4]\\d|[01]?\\d\\d?))\\b")){
            System.out.println(1);
            return;
        }
        if(!ip1.matches("\\b((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}((25[0-5]|2[0-4]\\d|[01]?\\d\\d?))\\b")){
            System.out.println(1);
            return;
        }
        if(!ip2.matches("\\b((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}((25[0-5]|2[0-4]\\d|[01]?\\d\\d?))\\b")){
            System.out.println(1);
            return;
        }
        if(!fir.equals("255") || !end.equals("0")){
            System.out.println(1);
            return;
        }

        // 按位相与
        String[] strs2 = ip1.split("\\.");
        String[] strs3 = ip2.split("\\.");
        boolean flag = true;
        for (int i = 0; i < strs2.length; i++) {
            int mk = Integer.parseInt(strs1[i]);
            int ip11 = Integer.parseInt(strs2[i]);
            int ip22 = Integer.parseInt(strs3[i]);
            Integer temp1 = Integer.parseInt(Integer.toBinaryString(ip11));
            Integer temp2 = Integer.parseInt(Integer.toBinaryString(ip22));

            // 按位相与的结果
            int ip111 = temp1 & mk;
            int ip222 = temp2 & mk;
            if(ip111!=ip222){
                flag = false;
            }
        }

        if(flag==true){
            System.out.println(0);
            return;
        }

        if(flag==false){
            System.out.println(2);
            return;
        }

    }
}
