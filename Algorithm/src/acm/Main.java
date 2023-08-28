package acm;

import java.util.Scanner;

/**
 @author Alex
 @create 2023-08-26-9:02
 */
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        String s = in.nextLine();  // 小兴串
        if(s.length()!=n){
            return;
        }

        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if(i<s.length()-1){
                if(s.substring(i,i+2).equals("10")||s.substring(i,i+2).equals("01")){
                    count++;
                    i++;
                }else {
                    count++;
                }
            }else {
                count++;
            }

        }
        System.out.println(count);
    }
}
