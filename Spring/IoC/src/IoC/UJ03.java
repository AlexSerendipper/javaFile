package IoC;

import IoC.bean.Emp;
import IoC.service.UserService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 【基于xml的bean管理】part2:注入对象属性
 * （1）注入对象属性（外部bean）：如，在UserService中调用UserDao
 *      在UserService中创建UserDao类型属性，需创建 set 方法（因为property注入，本质上调用了set方法）
 *      配置xml。外部bean就体现在：在property属性中，使用了ref标签注入了其他bean对象
 ------------------------
 <!-- 4、service 和 dao 对象创建 -->
 <bean id="userService" class="IoC.service.UserService">
     <!--注入 userDao 对象
     name 属性：类里面属性名称
     ref 属性：创建userDao对象的别名(即bean标签id值) -->
     <property name="userDao" ref="userDaoImpl"></property>
 </bean>

 <bean id="userDaoImpl" class="IoC.dao.UserDaoImpl"></bean>
 -------------------------



 * (2) 注入对象属性（内部bean）: 如，使用spring表示Dept和Emp之间的关系
 *      一个部门有多个员工，一个员工属于一个部门
 *      配置xml。内部bean就体现在：在property中，直接创建一个其他bean对象
 ------------------------
 <bean id="emp" class="IoC.bean.Emp">
     <!--设置两个普通属性-->
     <property name="ename" value="lucy"></property>
     <property name="gender" value="女"></property>
     <!--设置对象类型属性-->
     <!--可以使用外部bean的方式，用bean标签创建Dept类，然后使用ref引入-->
     <property name="dept">
         <bean id="dept" class="IoC.bean.Dept">
             <property name="dname" value="安保部"></property>
         </bean>
     </property>
 </bean>

 ------------------------
 * (3) 级联赋值: 在一个bean对象中对另一个bean对象赋值（不常用）
 *      实际上，上述的 内部bean方法 以及 外部bean 方法，都能实现级联赋值
 *      此外，还有一种方法，首先必须在Emp类中，生成Dept类的get方法
 *                         然后配置xml文件，利用<property>标签进行级联赋值
 ------------------------
 <!--级联赋值-->
 <bean id="emp2" class="IoC.bean.Emp">
     <!--设置两个普通属性-->
     <property name="ename" value="lucy"></property>
     <property name="gender" value="女"></property>
     <!--级联赋值-->
     <property name="dept" ref="dept"></property>
     <property name="dept.dname" value="技术部"></property>
 </bean>
 <bean id="dept" class="IoC.bean.Dept">
     <!--设置了这一行，就属于是外部bean的级联赋值了-->
     <!--<property name="dname"></property>-->
 </bean>
 ------------------------

 @author Alex
 @create 2023-02-21-13:41
 */

public class UJ03 {
    // 注入属性（外部bean）
    @Test
    public void test(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("UJ03.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.add();
    }

    // 注入属性（内部bean）
    @Test
    public void test2(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("UJ03.xml");
        Emp emp = context.getBean("emp", Emp.class);
        System.out.println(emp);
    }

    // 级联赋值（其他方法）
    @Test
    public void test3(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("UJ03.xml");
        Emp emp = context.getBean("emp2", Emp.class);
        System.out.println(emp);
    }

}
