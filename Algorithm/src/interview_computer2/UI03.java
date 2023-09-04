package interview_computer2;

import java.util.Scanner;

/**
 * 字符串中数字位置逆序，比如
 * a132bcd456efg，逆序后应该是a654bcd231efg
 *
 @author Alex
 @create 2023-08-20-14:54
 */
public class UI03 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入字符串: ");
        String input = scanner.nextLine();

        String reversed = reverseDigits2(input);
        System.out.println("逆序后的字符串: " + reversed);
    }


    public static String reverseDigits2(String input) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder digitBuilder = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                digitBuilder.append(c);
            }
        }

        String s = digitBuilder.reverse().toString();
        int j = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if(Character.isLetter(c)){
                stringBuilder.append(c);
            }

            if (Character.isDigit(c)) {
                stringBuilder.append(s.charAt(j));
                j++;
            }
        }


        return stringBuilder.toString();
    }


}
