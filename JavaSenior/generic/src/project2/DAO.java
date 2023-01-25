package project2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 @author Alex
 @create 2022-12-12-21:27
 */
public class DAO<T> {
    Map<String, T> map = new HashMap<>();


    // 保存 T 类型的对象到 Map 成员变量中
    public void save(String id, T entity) {
        map.put(id, entity);
    }

    // 从 map 中获取 id 对应的对象
    public T get(String id) {
        T t = map.get(id);
        return t;
    }

    // 替换map中key为id的内容,改为entity对象
    public void update(String id, T entity) {
        if (!map.containsKey(id)) {
            System.out.println("不包含指定的id");
            return;
        }
        map.put(id, entity);
    }

    // 返回 map 中存放的所有 T 对象
    public List<T> list() {
        ArrayList<T> arrayList = new ArrayList<>();
        for (T value : map.values()) {
            arrayList.add(value);
        }
        return arrayList;
    }

    // 删除指定 id 对象
    public void delete(String id) {
        map.remove(id);
    }
}

