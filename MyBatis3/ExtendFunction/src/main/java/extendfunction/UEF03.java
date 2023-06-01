package extendfunction;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import extendfunction.mappers.EmpMapper;
import extendfunction.pojo.Emp;
import extendfunction.utils.GetMapperUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

/**
 * 【分页插件使用】
 * （1）添加相关依赖
-------------------------
<!-- https://mvnrepository.com/artifact/com.github.pagehelper/pagehelper -->
<!-- 分页插件依赖 -->
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper</artifactId>
    <version>5.2.0</version>
</dependency>
<!-- 实测下来好像是低版本比较好用啊 -->
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
    <version>1.2.3</version>
</dependency>
-------------------------
 * （2）MyBatis的 核心配置文件 中配置插件
-------------------------
<plugins>
    <!--设置分页插件1-->
    <plugin interceptor="com.github.pagehelper.PageInterceptor"></plugin>
</plugins>

# pagehelper 集成在springboot中使用application.properties配置
# helper-dialect 配置使用哪种数据库语言
pagehelper.helper-dialect=mysql
# reasonable 配置分页参数合理化功能，默认是false。
# 启用合理化时，如果pageNum<1会查询第一页，如果pageNum>总页数会查询最后一页；
# 禁用合理化时，如果pageNum<1或pageNum>总页数会返回空数据。
pagehelper.reasonable=true
-------------------------
 * （3）分页插件的使用
 *   Page<Object> page = PageHelper.startPage(int pageNum, int pageSize)                      # 在查询功能之前使用开启分页功能
 *                                                                                            # 在执行查询时，会按照当前页页码pageNum, 以及每页显示的条数pageSize 输出结果
 *   Page{count=,pageNum=,pageSize=,startRow=,endRow=,total=,pages=,reasonable=,pageSizeZero=}    # 查询后，分页后的 相关的数据会封装在page对象中
 *   PageInfo<T> pageInfo = new PageInfo<>(List<T> list, int navigatePages)                       # 查询后，可以使用pageInfo获取分页相关的所有数据
 *                                           # list：分页之后的数据。navigatePages：导航分页的页码数
 *                                            希望导航栏分页的页码数，顾名思义。当设置为5时，正常显示为（当前页码-2 当前页码 当前页码+2）
 *                                            当然，若总共10页，访问第1 2页时，一定显示的是1 2 3 4 5。访问第9 10页时，一定显示的是6 7 8 9 10
 *  (4)常用数据
 *   list:当前页数据
 *   pageNum：当前页的页码
 *   pageSize：每页显示的条数
 *   size：当前页显示的真实条数（主要针对最后一页）
 *   total：总记录数
 *   pages：总页数
 *   startRow：从第几行开始
 *   endRow: 到第几行结束
 *   prePage：上一页的页码
 *   nextPage：下一页的页码
 *   isFirstPage/isLastPage：是否为第一页/最后一页
 *   hasPreviousPage/hasNextPage：是否存在上一页/下一页
 *   navigatePages：导航分页的页码数，即我们设置导航栏显示分页的页码数。如5，如果数据只有三页，那只会显示三页
 *   navigatepageNums：当前导航分页的页码，即根据查询的数据，显示当前导航栏应显示的页码。如[1,2,3,4,5]
 *   navigateFirstPage: 导航分页开始页码，即navigatepageNums[1]。如 1
 *   navigateLastPage: 导航分页结束页码，即navigatepageNums[end]。如 5
 *
 @author Alex
 @create 2023-03-16-11:21
 */
public class UEF03 {
    /**
     * 通常分页功能都是通过 limit：index pageSize实现
     * index: 当前页的起始索引
     * pageSize: 每页显示的条数
     * pageNum：当前页的页码
     * pageSize：每页显示的条数
     * index = （pageNum-1）*pageSize                       # 这个很容易，起始索引为0，假设3页，每页显示5条数据，那第三页的第一条的索引是10，就是上一页的最后一条数据的个数，即（3-1）* 5
     * @throws Exception
     */
    @Test
    public void test1() throws IOException {
        EmpMapper mapper = GetMapperUtils.getMapper1(EmpMapper.class);
        Page<Object> page = PageHelper.startPage(3, 2);
        List<Emp> emps = mapper.selectByExample(null);
        for(Emp emp:emps){
            System.out.println(emp);
        }
        System.out.println(page);
        System.out.println("****************************");
        PageInfo<Emp> pageInfo = new PageInfo<>(emps, 3);
        System.out.println(pageInfo);

    }
}
