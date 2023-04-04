package IoC.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 @author Alex
 @create 2023-02-21-20:43
 */
public class Orders{
    private String oname;

    //无参数构造
    public Orders() {
        System.out.println("第一步 执行无参数构造创建 bean 实例");
    }

    // 私有属性的set方法
    public void setOname(String oname) {
        this.oname = oname;
        System.out.println("第二步 调用 set 方法设置属性值");
    }

    // 创建执行的初始化的方法
    public void initMethod() {
        System.out.println("第三步 执行初始化的方法");
    }

    // 创建执行的销毁的方法
    public void destroyMethod() {
        System.out.println("第五步 执行销毁的方法");
    }

}
