package basicfunction;

import basicfunction.mappers.DeptMapper;
import basicfunction.mappers.DynamicSQLMapper;
import basicfunction.pojo.Dept;
import basicfunction.pojo.Emp;
import basicfunction.utils.GetMapperUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 【动态sql】详见DynamicSQLMapper.xml
 *  Mybatis框架的动态SQL技术是一种根据特定条件动态拼装SQL语句的功能，它存在的意义是为了解决拼接SQL语句的痛点问题。
 *   与#{}，&{}拼接字符串不同。动态sql主要解决的是拼接关键字。（场景：当用户输入一个筛选条件，如age=14, 需要将其高效的拼接在原来的sql语句后重新进行查询）
 *  要记住动态SQL是MyBatis提供的语法，并不是sql语法
 *
 * 【IF】
 *  if标签可通过test属性的表达式进行判断，若表达式的结果为true，则标签中的内容会执行；反之标签中的内容不会执行
 *  ✔✔通常会使用恒等式作为where的一个条件，因为其不影响之后的判断，并且能够更好的拼接之后的条件
-------------------------------
<select id="getEmpByCondition" resultType="Emp">
    select *
    from mybatis_emp where 1=1
    <if test="empName!='' and empName!=null "> and emp_name = #{empName}</if>
    <if test="age!='' and age!=null "> and age = #{age}</if>
    <if test="sex!='' and sex!=null "> and sex = #{sex}</if>
    <if test="email!='' and email!=null "> and email = #{email}</if>
</select>
-------------------------------
 *
 * 【WHERE】与恒等式实现的功能相同！！推荐使用恒等式
 *  where和if一般结合使用
 *  若where标签中的if条件都不满足，则where标签没有任何功能，即不会添加where关键字
 *   若where标签中，有if条件满足，则会自动添加where关键字，并将条件 前方的 多余的 and或者or去掉
 *  注意：where标签不能去掉条件后方多余的and，如 age = #{age} and
--------------------------------
<where>
    <if test="empName!='' and empName!=null "> and emp_name = #{empName}</if>
    <if test="age!='' and age!=null "> and age = #{age}</if>
    <if test="sex!='' and sex!=null "> and sex = #{sex}</if>
    <if test="email!='' and email!=null "> and email = #{email}</if>
</where>
--------------------------------
 *
 * 【TRIM】
 *  若TRIM标签中没有任何内容，则TRIM标签没有任何效果
 *  trim通常也和if结合使用，where作为trim的一种特殊情况✔
 *    prefix属性：在trim标签包裹的部分的 最前面 添加指定符号
 *    suffix属性：在trim标签包裹的部分的 最后面 添加指定符号
 *    prefixOverrides属性：前缀需要覆盖的内容，一般是第一个判断条件前面的多余的结构，如下
 *    suffixOverrides属性：为trim标签中，条件的 前面/后面 删除 多余的 内容
 --------------------------------
 // preprefixOverrides属性：当第一个name没有值的时候，这个时候sql语句就会是 select * from User where and age='',
 // 很明显这个sql语句语法存在问题。在这里prefixOverrides就起作用了，它会让前缀where覆盖掉第一个and。
 // 覆盖之后的是：select * from User where age='';
<select id='queryUser'>
    select * from User
    <trim prefix='where' prefixOverrides='and'>
        <if test="name != null and name != ''">
            name = #{name}
        </if>
        <if test="age !=null and age !=''">
            and age = #{age}
        </if>
    </trim>
<select>

// suffixOverrides属性与之类似，这里不多加叙述
<trim prefix="where" suffixOverrides="and|or">
     <if test="empName!='' and empName!=null "> emp_name = #{empName} and</if>
     <if test="age!='' and age!=null "> age = #{age} and </if>
     <if test="sex!='' and sex!=null "> sex = #{sex} and</if>
     <if test="email!='' and email!=null "> email = #{email} and</if>
</trim>
 --------------------------------

 * 【choose、when、otherwise】
 *  choose、when、otherwise 相当于 switch、case、default（并且每个case中默认带有break）
 *  因为每个case中默认带有break，所以利用该标签后，语句判断只会执行其中一个
--------------------------------
<choose>
    <when test="empName != '' and empName != null"> emp_name = #{empName} </when>
    <when test="age != '' and age != null"> age = #{age} </when>
    <when test="sex != '' and sex != null"> sex = #{sex} </when>
    <when test="email != '' and email != null"> email = #{email} </when>
    <otherwise>did=1</otherwise>
</choose>
--------------------------------

 * 【foreach】常用于我们接收到的数据为数组或集合的情况： 如 收到前端传来的id数组，使用该标签进行批量删除
 *  collection属性：设置要循环的数组或集合
 *  item属性：表示集合或数组中的每一个数据
 *  separator属性：设置循环体之间的分隔符（即item之间的分隔符）✔
 *                  如下即为: insert into mybatis_emp values
 *                            (null,#{emp.empName},#{emp.age},#{emp.sex},#{emp.email},null),
 *                            (null,#{emp.empName},#{emp.age},#{emp.sex},#{emp.email},null)
 *  open属性：设置foreach标签中的 内容的开始符
 *  close属性：设置foreach标签中的 内容的结束符
--------------------------------
<insert id="InsertEmpByCollection">
    insert into mybatis_emp values
    <foreach collection="emps" item="emp" separator=",">
        (null,#{emp.empName},#{emp.age},#{emp.sex},#{emp.email},null)
    </foreach>
</insert>
--------------------------------

 * 【sql】sql片段标签，可以记录一段公共sql片段，在使用的地方通过include标签进行引入
---------------------------------
<sql id="empColumns">
    eid,emp_name,age
</sql>

<select id="getEmpByEqual" resultType="Emp">
    select <include refid="empColumns"></include> from mybatis_emp
</select>
---------------------------------
 *
 *
 @author Alex
 @create 2023-03-13-15:47
 */
public class UBF09 {
    // 测试IF标签：恒等式 以及 测试sql标签
    @Test
    public void test1() throws IOException {
        DynamicSQLMapper mapper = GetMapperUtils.getMapper1(DynamicSQLMapper.class);
        Emp emp = new Emp(null, "张三", null, null, null);
        List<Emp> empByCondition = mapper.getEmpByEqual(emp);
        System.out.println(empByCondition);
    }

    // Where
    @Test
    public void test2() throws IOException {
        DynamicSQLMapper mapper = GetMapperUtils.getMapper1(DynamicSQLMapper.class);
        Emp emp = new Emp(null, "张三", null, null, null);
        List<Emp> empByCondition = mapper.getEmpByWhere(emp);
        System.out.println(empByCondition);
    }

    // trim
    @Test
    public void test3() throws IOException {
        DynamicSQLMapper mapper = GetMapperUtils.getMapper1(DynamicSQLMapper.class);
        Emp emp = new Emp(null, "张三", null, "男", null);

        List<Emp> empByCondition = mapper.getEmpByTrim(emp);
        System.out.println(empByCondition);
    }
    @Test
    public void test3_1() throws IOException {
        DynamicSQLMapper mapper = GetMapperUtils.getMapper1(DynamicSQLMapper.class);
        Emp emp = new Emp(1, null, 6, "女", null);
        int i = mapper.updateEmpByTrim(emp);
        System.out.println(i);
    }

    // choose
    @Test
    public void test4() throws IOException {
        DynamicSQLMapper mapper = GetMapperUtils.getMapper1(DynamicSQLMapper.class);
        Emp emp = new Emp(null, "", null, "男", null);
        List<Emp> empByCondition = mapper.getEmpByChoose(emp);
        System.out.println(empByCondition);
    }

    // 测试foreach批量删除
    @Test
    public void test5() throws IOException {
        DynamicSQLMapper mapper = GetMapperUtils.getMapper1(DynamicSQLMapper.class);
        int i = mapper.deleteEmpByArray(new Integer[]{11, 12, 13});
        System.out.println(i);
    }

    // 测试foreach批量添加
    @Test
    public void test6() throws IOException {
        DynamicSQLMapper mapper = GetMapperUtils.getMapper1(DynamicSQLMapper.class);
        Emp emp1 = new Emp(null, "aa", 12, "女", "qq@.com");
        Emp emp2 = new Emp(null, "bb", 22, "女", "qq@.com");
        Emp emp3 = new Emp(null, "cc", 33, "女", "qq@.com");
        List<Emp> list = Arrays.asList(emp1, emp2, emp3);
        int i = mapper.InsertEmpByCollection(list);
        System.out.println(i);
    }
}
