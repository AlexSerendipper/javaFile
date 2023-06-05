package array;

/**
 @author Alex
 @create 2023-06-05-16:38
 */
import java.util.Arrays;

/**
 * 合并两个有序数组！
 * https://www.nowcoder.com/practice/89865d4375634fc484f3a24b7fe65665?tpId=117&&tqId=34943&rp=1&ru=/ta/job-code-high&qru=/ta/job-code-high/question-ranking
 *
 @author Alex
 @create 2023-06-02-16:46
 */
public class UA00 {
    // 方法一
    // A 和 B 中初始的元素数目分别为 m 和 n，，所以后台会自动输入m和n
    // 后台程序会预先将A扩容为[4,5,6,0,0,0]
    // 且后台会自动将合并后的数组 A 的内容打印出来
    public void merge1(int A[], int m, int B[], int n) {
        System.arraycopy(B, 0, A, m, n);
        Arrays.sort(A);
    }

    // 方法二：分别比较两个数组，由于都是有序数组，直接从第一个开始比较，把较小的数放入新数组，最后将新数组赋值给A
    public void merger2(int A[], int m, int B[], int n){
        int[]res = new int[m+n];
        int i = 0;
        int j = 0;
        int f = 0;

        while(i<m && j<n){
            if(A[i] > B[j]){
                res[f++] = B[j++];
            }else{
                res[f++] = A[i++];
            }
        }

        while (i < m) {
            res[f++] = A[i++];
        }

        while (j < n) {
            res[f++] = B[j++];
        }

        for (int k = 0; k < res.length; k++) {
            A[k] = res[k];
        }

    }


}
