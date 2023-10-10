package interview_computer1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * 微众银行9.3号面试
 *
 * 酷酷的小明准备和小伙伴们展示他捏出来的超酷的橡皮泥士兵。在展示之前，小明发现有些棉皮泥士兵大小十分相似甚至相同，
 * 这让小明感觉不是很酷，因为小明想要他的橡皮泥作品都有自己的风格
 * 即使是大小也要有区别。小明的n个橡皮泥士兵的大小分别为a1,az....an，
 * 小明可以通过给某个士兵加一单位橡皮泥来使得其大小增加一单位。小明想知道如果他想要让所有的像皮泥士兵大小都不相同，
 * 至少雪要一共加多少单位橡皮泥。
 *
 * 样例输入
 * 5
 * 1 1 2 3 3
 * 样例输出
 * 5
 @author Alex
 @create 2023-09-03-19:36
 */
public class UI02 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] nums = new int[n];

        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        Arrays.sort(nums);
        HashSet<Integer> set = new HashSet<>();
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            int temp = nums[i];
            while(set.contains(temp)){
                temp++;
                count++;
            }
            set.add(temp);
        }
        System.out.println(count);

    }
}
