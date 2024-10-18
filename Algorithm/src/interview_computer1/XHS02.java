package interview_computer1;

import java.util.*;

/**
 * 小红书3.18
 * 我们假定一个用户搜索的关键词是他感兴趣的，现在请你基于这个前提设计一个小红书购物的推荐算法。
 * 该算法的核心思想如下:首先给定一个商品清单，其中有每个商品所包含的关键词属性，然后给出用户最近搜索过的一些关键词，
 * 请你将包含用户搜索过的更多关键词的商品排在用户目录的前面。对于包含关键词数量相同的商品，我们按默认顺序排序，也就是说按输入给定的顺序优先级。
 *
 * 输入描述
 * 第一行输入一个正整数n,q，代表商品数量、用户搜索的关键词数星,
 * 第二行输入q个互不相同的、仅由小写字母组成的字符串，代表用户搜索过的关键词，
 * 接下来的2*n行，每两行描述一个商品。
 * 第一行输入一个仅由小写字母组成的字符串name和一个正整数m。
 * 代表商品的名称和商品包含的关键词属性数量。第二行输入m个互不相同的、仅由小写字母组成的字符串，代表每个商品的属性。
 * 保证所有字符串长度不超过 20，所有商品的名称互不相同
 *
 * 输出描述
 * 输出n行，每行一个字符串，代表用户主页中显示的商品名称。
 *
 * 样例输入
 * 2 5
 * red book game music sigma
 * mozart 3
 * book classic music
 * arcaea 4
 * red music game hard
 *
 * 样例输出
 * arcaea
 * mozart
 *
 * 提示
 * arcaea 这个商品包含了用户搜索的 3个关键词，而 mozart 只包含了2个
 @author Alex
 @create 2024-03-17-20:05
 */
// ac9%，听说是因为要根据输入给定的顺序优先级进行排序，我这里其实已经针对这个进行了treemap的修改了
// 我看别人说是要用Arrays.sort(List)，这种默认稳定排序才能ac100%
public class XHS02 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int q = sc.nextInt();

        // map存储搜索关键词以及次数
        HashMap<String, Integer> map1 = new HashMap<>();
        for (int i = 0; i < q; i++) {
            map1.put(sc.next(),0);
        }

        //map2记录商品及其匹配的次数
//        Comparator<Integer> rs = Collections.reverseOrder();
        TreeMap<String , Integer> map2 = new TreeMap<>();

        // 这里有问题，因为count可能相同啊，不能用count作为键
        for (int i = 0; i < n; i++) {
            String goodName = sc.next();
            int m = sc.nextInt();
            int count = 0;
            for (int k = 0; k < m; k++) {
                if(map1.containsKey(sc.next())){
                    count++;
                }
            }
            map2.put(goodName,count);
        }

        ArrayList<Map.Entry<String, Integer>> entries = new ArrayList<>(map2.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return -o1.getValue().compareTo(o2.getValue());
            }
        });


        for(Map.Entry<String, Integer> entry:entries){
            System.out.println(entry.getKey());
        }

    }
}
