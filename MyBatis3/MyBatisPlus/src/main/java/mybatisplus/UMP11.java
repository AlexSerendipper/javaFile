package mybatisplus;

/**
 * 【MyBatisX】MyBatisX插件用法：https://baomidou.com/pages/ba5b24/
 *  MyBatis-Plus为我们提供了强大的mapper和service模板，能够大大的提高开发效率
 *   但是在真正开发过程中，MyBatis-Plus并不能为我们解决所有问题，例如一些复杂的SQL，多表联查，我们就需要自己去编写代码和SQL语句，
 *   这个时候可以使用MyBatisX插件, MyBatisX插件是一款基于 IDEA 的快速开发插件，为效率而生。
 *   （实际上mybatisplus让我们在单表查询下不用再写sql语句，MyBatisX能让我们在更复杂的情况下也不用写sql）✔
 *
 * 【MyBatisX常用功能】
 * 【快速定位功能】
 *  能够快速定位到mapper接口以及mapper接口所对应的映射文件（！点击小鸟~）
 *
 * 【代码生成器功能】jdbctemplate_book
 * （1）idea中右侧database,添加mysql数据源 (配置name（当前连接名字）、user、password、database)
 * （2）右键我们需要操作的表，选择MybatisX-Generator（配置内容与之前代码生成器中配置的相同）
 *      module path          设置当前工程的具体路径， 如，D:\IdeaWorkspace\MyBatis3
 *      base path            设置当前工程的根路径，如，src/main/java
 *      base package         设置 生成文件（mapper、pojo等） 的包路径，如mabatisplus
 *      relative package     设置生成的实体类的包名，如pojo
 *      ignore field prefix  设置忽略的属性前缀。如t_
 *      ignore table prefix  设置忽略的表前缀。如mybatisplus_ (即mybatisplus_user 对应的实体类为 User)
 * （3）生成选项
 *      annotation           设置使用mybatis-plus3的注解
 *      option               设置使用comment(注释)，lombok(自动生成实体类的@data)
 *      template             设置使用mybatis-plus3的模板
 *
 * 【MyBatisX快速生成CRUD】非常好用
 *  根据见名知意原则，在mapper接口中，输入方法名。
 *   如insertSelective，即选择性添加，然后ctrl+1, 提示选择Generate Mybatis Sql✔。。 可以自动在mapper映射文件中生成对应的sql
 *   如updateAgeAndSexByUid
 *   如selectAgeAndSexBetween
 *   如selectAllOrderByAgeDesc
 *
 @author Alex
 @create 2023-03-28-15:43
 */
public class UMP11 {
}
