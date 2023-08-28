package usearray;

import java.util.Scanner;

/**
 @author Alex
 @create 2023-08-10-21:09
 */
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        String[] strs = s.split(" ");
        int n = Integer.parseInt(strs[0]);
        int target = Integer.parseInt(strs[1]);

        int[][] array1 = new int[n][n];
        int sum = n * n;
        int k=1,i=0,j=0;
        for (int m = 1; m <=sum ; m++) {
            if(k==1){
                if(j<n && array1[i][j]==0){
                    array1[i][j++] = m;
                    if(m==target){
                        System.out.println(++i +" "+ j);
                        return;
                    }
                }else {
                    k=2;
                    j--;
                    m--;
                    i++;
                    continue;
                }
            }
            if(k==2){
                if(i<n && array1[i][j]==0){
                    array1[i++][j] = m;
                    if(m==target){
                        System.out.println(i+" "+ ++j);
                        return;
                    }
                }else {
                    k=3;
                    i--;
                    m--;
                    j--;
                    continue;
                }
            }
            if(k==3){
                if(j>=0 && array1[i][j]==0){
                    array1[i][j--] = m;
                    if(m==target){
                        j = j+2;
                        System.out.println(++i+" "+j);
                        return;
                    }
                }else {
                    k=4;
                    i--;
                    m--;
                    j++;
                    continue;
                }
            }
            if(k==4){
                if(i>=0 && array1[i][j]==0){
                    array1[i--][j] = m;
                    if(m==target){
                        i=i+2;

                        System.out.println(i+" "+ ++j);
                        return;
                    }
                }else {
                    k=1;
                    i++;
                    m--;
                    j++;
                    continue;
                }
            }

        }

    }
}
