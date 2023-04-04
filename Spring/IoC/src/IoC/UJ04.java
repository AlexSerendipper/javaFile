package IoC;

import IoC.bean.Stu;
import IoC.service.UserService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 【基于xml的bean管理】part3:注入集合属性
 * （1）注入数组属性 <array>
 * （2）注入 List 集合类型属性<list>
 * （3）注入 Map 集合类型属性<map>
 * （4）注入 Set 集合类型属性<set>
 ------------------------
<!-- 集合类型属性注入-->
<bean id="stu" class="IoC.bean.Stu">
    <!--数组类型属性注入-->
    <property name="courses">
        <array>
            <value>java 课程</value>
            <value>数据库课程</value>
        </array>
    </property>
    <!--list 类型属性注入-->
    <property name="list">
        <list>
            <value>张三</value>
            <value>小三</value>
        </list>
    </property>
    <!--map 类型属性注入-->
    <property name="maps">
        <map>
            <entry key="JAVA" value="java"></entry>
            <entry key="PHP" value="php"></entry>
        </map>
    </property>
    <!--set 类型属性注入-->
    <property name="sets">
        <set>
            <value>MySQL</value>
            <value>Redis</value>
        </set>
    </property>
 </beans>
------------------------

 * 【集合的值为对象】
 *   利用外部bean的方式在value中进行赋值
 *
 * 【提取集合中注入的公共部分】增加代码的复用性
 * （1）在 spring 配置文件中引入名称空间util
 * （2）配置xml文件,利用<util>标签提取注入的公共部分
------------------------
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xmlns:util="http://www.springframework.org/schema/util"
xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                    http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd">
<!-- 添加util名称空间配置结束，把beans都改成util -->
<bean id="stu" class="IoC.bean.Stu">
     <!--注入 list 集合类型，值是Course对象，普通注入-->
     <property name="courseList">
         <list>
             <ref bean="course1"></ref>
             <ref bean="course2"></ref>
         </list>
     </property>
     <!--注入 list 集合类型，值是Course对象，公共注入-->
     <property name="courseList2" ref="bookList"> </property>
</bean>
<!--创建多个 course 对象-->
<bean id="course1" class="IoC.bean.Course">
    <property name="cname" value="Spring5 框架"></property>
</bean>
<bean id="course2" class="IoC.bean.Course">
    <property name="cname" value="MyBatis 框架"></property>
</bean>

<!-- 通过util标签，抽取注入的公共部分，因为注入的是course对象，所以用外部引用的方式 -->
<!-- 如果抽取的是普通List<String>，直接用value标签就行了 -->
<util:list id="bookList">
    <ref bean="course1"></ref>
    <ref bean="course2"></ref>
</util:list>
------------------------
 @author Alex
 @create 2023-02-21-14:36
 */
public class UJ04 {
    @Test
    public void test(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("UJ04.xml");
        Stu stu = context.getBean("stu", Stu.class);
        System.out.println(stu);
    }
}
