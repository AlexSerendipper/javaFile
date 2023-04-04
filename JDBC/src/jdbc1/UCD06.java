package jdbc1;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * 【使用PreparedStatement实现批量数据操作】
 *  update和delete本身就具有批量操作的效果，这里的批量操作主要指批量插入
 *  方式一：使用statement操作（已经被淘汰）,每次循环都需要重新生成要给sql语句字符串
 *  方式二：使用PreparedStatement，只生成一个sql语句字符串，每次循环只修改占位符而已
 *  方式三：使用PreparedStatement+分批操作，
 *    ?rewriteBatchedStatements=true&useServerPrepStmts=false；        # MySQL5.7是默认关闭批处理的，需要将该语句写在配置文件的url后面
 *    useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true    # MySQL8.0是默认关闭批处理的，需要将该语句写在配置文件的url后面
 *    addBatch(String)：添加需要批量处理的SQL语句；
 *    executeBatch()：执行批量处理语句；
 *    clearBatch():清空缓存的数据
 *    connection.setAutoCommit(false); 默认情况下DML的增删改操作是会自动提交的，故默认每一个Batch提交一次。。我们可以先关闭自动提交，等全部sql执行后在一起提交
 *
 @author Alex
 @create 2023-01-30-18:20
 */
public class UCD06 {
    // 方式一:statement
    @Test
    public void test() throws Exception{
        Connection connection = UCD02.getConnection();
        Statement st = connection.createStatement();
        for (int i = 0; i < 20000; i++) {
            String sql = "insert into goods(name) value('name_"+i+"')";  // 可以看见每次循环都需要重新生成要给sql语句字符串
            st.execute(sql);
        }
        connection.close();
    }

    // 方式二：PreparedStatement
    @Test
    public void test2() throws Exception{
        Connection connection = UCD02.getConnection();
        String sql = "insert into goods(name) value(?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        for (int i = 0; i < 200; i++) {
            ps.setObject(1,"name_"+i);  // 可以看见，只生成一个sql语句字符串，每次循环只修改占位符而已
            ps.execute();
        }
        UCD03.closeResourse(connection,ps);
    }

    // ✔方式三：PreparedStatement+分批操作+设置不允许自动提交
    @Test
    public void test3() throws Exception{
        Connection connection = UCD02.getConnection();
        String sql = "insert into goods(name) value(?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        connection.setAutoCommit(false);
        for (int i = 0; i < 4000; i++) {
            ps.setObject(1,"name_"+i);
            // 攒sql
            ps.addBatch();
            // 每隔200清空一次
            if(i%200==0){
                ps.executeBatch();
                ps.clearBatch();
            }
        }
        connection.commit();
        UCD03.closeResourse(connection,ps);
    }
}
