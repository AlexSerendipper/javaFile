package mybatisplus;

import mybatisplus.pojo.User;
import mybatisplus.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * 【IService】
 *  MyBatisPlus提供了一个通用的 Service 接口 IService<T>，泛型T为对应的实体类对象, 进一步封装 CRUD✔✔
 *  IService采用不同的前缀命名方式，避免与Mapper层进行混淆✔
 *    sava 添加                 （mapper层为 insert）
 *    remove 删除               （mapper层为 delete）
 *    savaOrUpdate 添加+修改    （如果传入id就是修改，没传入就是添加）
 *    update 修改               （mapper层也为update）
 *    get 查询单行              （mapper层为 select）
 *    list 查询集合             （mapper层为 select）
 *    page 分页
 *
 * 【IService的使用】
 *  MyBatis-Plus提供了 IService 的具体实现类 ServiceImpl，提供了 IService 中基础功能的实现
 *    public class ServiceImpl<M extends BaseMapper<T>, T> implements IService<T>
 *    其中M为对应的mapper接口，T为对应的实体类对象✔
 *  但通常仅靠 ServiceImpl 无法满足业务需求。故推荐使用流程如下：
 *  （1）自定义 UserService接口，实现 IService<T> 接口
 *  （2）自定义 UserServiceImpl 接口实现类, 继承 ServiceImpl 并实现 UserService接口
 *       此时即可实现对IService基本功能的调用，又可以使用自己扩展的功能
 *
 * 【常用方法汇总】
 *  Service.list()                               # 查询所有数据
 * 
 @author Alex
 @create 2023-03-25-13:49
 */
public class UMP03 {
    @Autowired
    UserService userService;

    // 测试通用service输出总记录数
    @Test
    public void test1(){
        long count = userService.count();
        System.out.println(count);
    }

    // 测试通用service进行批量添加操作,返回值表示是否操作成功
    // 这个在basicMapper中就没有批量添加的方法
    @Test
    public void test2(){
        ArrayList<User> users = new ArrayList<>();
        users.add(new User(6L,"zzj",18,"642671525@qq.com"));
        users.add(new User(7L,"lzy",18,"642671525@qq.com"));
        users.add(new User(8L,"hjy",18,"642671525@qq.com"));
        users.add(new User(9L,"hyq",18,"642671525@qq.com"));
        boolean result = userService.saveBatch(users);
        System.out.println(result);
    }
}
