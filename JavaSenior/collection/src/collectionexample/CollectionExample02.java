package collectionexample;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 【list接口面试题，问输出】
 * @author Alex
 * @create 2022-12-05-9:29
 */
public class CollectionExample02 {
    @Test
    public void testListRemove() {
        List list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        updateList(list);
        System.out.println(list);
    }
    private static void updateList(List list) {
        list.remove(2);  // 其实想考察的是，remove方法可以根据指定对象删除，也可以指定索引删除
        // list.remove(new Integer(2));
    }
}
