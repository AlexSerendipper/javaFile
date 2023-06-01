package usecollection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 【需求：在List内去除重复数字值，要求尽量简单】
 * @author Alex
 * @create 2022-12-06-13:15
 */
public class Q4 {

    public static List duplicateList(List list) {
        HashSet set = new HashSet();
        set.addAll(list);  // 就是arraylist和hashset两个数据都互通呗
        return new ArrayList(set);  // 就是arraylist和hashset两个数据都互通呗
    }
    public static void main(String[] args) {
        List list = new ArrayList();
        list.add(new Integer(1));
        list.add(new Integer(2));
        list.add(new Integer(2));
        list.add(new Integer(4));
        list.add(new Integer(4));
        List list2 = duplicateList(list);
        for (Object integer : list2) {
            System.out.println(integer);
        }
    }
}
