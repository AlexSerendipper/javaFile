package usegeneric;

import java.util.List;

/**
 * 【泛型类的具体使用情景举例】
 @author Alex
 @create 2022-12-12-16:26
 */
public class UseGeneric04 {
    public static void main(String[] args) {
        CustomerDAO cDAO = new CustomerDAO();
        // 这样只能操作特定的表拉~
        cDAO.add(new Customer());
    }

}


// DAO:date(base) access object，对数据库进行的基本操作（共性操作）
// 通用的增删改查方法都定义在DAQ中，数据库中的每张表都对应着java的一个类
// 对不同的类要进行增删改查操作，所以需要用到泛型
class DAO<T> {
    // 添加一条记录
    public void add(T t) {
    }

    // 删除一条记录
    public boolean remove(int index) {
        return false;
    }

    // 修改一条记录
    public void update(int index, T t) {
    }

    // 查询一条记录
    public T getIndex(int index) {
        return null;
    }

    // 查询多条记录
    public List<T> getList(int index) {
        return null;
    }

    // 泛型方法，举例：获取表中一共有多少条记录（返回Long） 或是 获取表中员工的入职时间（返回Date）
    public <E> E getValue() {
        return null;
    }
}

// 此类对应数据库中的customer表
class Customer {
}


// 针对处理customer表的DAQ，就能保证输入的数据都是正确的拉
class CustomerDAO extends DAO<Customer> {
}