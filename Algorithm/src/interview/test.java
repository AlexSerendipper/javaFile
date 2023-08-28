package interview;

import java.util.Scanner;

/**
 @author Alex
 @create 2023-08-20-14:54
 */
public class test {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int flag = 0;
        int R=0;
        int C=0;

        while(in.hasNextLine()){
            if (flag==0){
                String s = in.nextLine();
                String[] str = s.split(" ");
                R = Integer.parseInt(str[0]);
                C = Integer.parseInt(str[1]);
                flag++;
            }

            if(flag<=R){

            }else {
                break;
            }
        }


    }
}
