package usegeneric;

import java.util.*;

/**
 *
 * 【泛型嵌套的举例】
 @author Alex
 @create 2022-12-12-20:56
 */
public class UseGeneric06 {
    public static void main(String[] args) {
        HashMap<String, ArrayList<Citizen>> map = new HashMap<String, ArrayList<Citizen>>();

        ArrayList<Citizen> list = new ArrayList<Citizen>();
        list.add(new Citizen("刘恺威"));
        list.add(new Citizen("杨幂"));
        list.add(new Citizen("小糯米"));
        map.put("刘恺威", list);
        // 这里就是泛型的嵌套
        Set<Map.Entry<String, ArrayList<Citizen>>> entrySet = map.entrySet();

        Iterator<Map.Entry<String, ArrayList<Citizen>>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ArrayList<Citizen>> entry = iterator.next();
            String key = entry.getKey();
            ArrayList<Citizen> value = entry.getValue();
            System.out.println("户主：" + key);
            System.out.println("家庭成员：" + value);
        }
    }
}
class Citizen{
    String name;

    public Citizen(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Citizen{" +
                "name='" + name + '\'' +
                '}';
    }
}