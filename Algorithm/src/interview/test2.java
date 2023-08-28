package interview;

import java.util.Scanner;

/**
 @author Alex
 @create 2023-08-20-15:05
 */
public class test2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        while(n>0){
            String test = in.nextLine();
            System.out.println("test is :"+test );
            n--;
        }
    }

}
