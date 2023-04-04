package IoC;

import IoC.bean.Emp;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 【基于xml自动装配】实际生活中，基于xml的自动装配很少使用，主要使用基于注解的自动装配
 *  UJ02~04中的属性的注入方式属于手动装配
 *  自动装配：根据指定装配规则（属性名称或者属性类型），Spring自动将匹配的属性值进行注入
 *   其主要作用在于简化了 对象属性 的注入流程✔✔✔
 *  通过设置 bean标签的属性 autowire，配置自动装配，两个常用属性如下：
 *    byName根据属性名称注入，注入bean的id值必须 和 类属性名一样（如下，bean id为dept，需要注入到emp类中的属性名也为dept）
 *    byType根据属性类型注入，（如下，外部bean类型为Dept，emp bean自动找到该类进行注入）
 *                            （若此时还有一个Dept类型的bean，则根据类型注入的方式就会出错）
-------------------------------------
 <!--外部bean-->
 <bean id="emp" class="IoC.bean.Emp" autowire="byName">
     <property name="ename" value="lucy"></property>
     <property name="gender" value="女"></property>
 <!-- <property name="dept" ref="dept"> </property>-->
 </bean>

 <bean id="dept" class="IoC.bean.Dept">
     <property name="dname" value="安保部"></property>
 </bean>
 ---------------------------------------

 @author Alex
 @create 2023-02-22-15:02
 */
public class UJ05 {
    @Test
    public void test1(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("UJ05.xml");
        Emp emp = context.getBean("emp", Emp.class);
        System.out.println(emp);
    }
}
