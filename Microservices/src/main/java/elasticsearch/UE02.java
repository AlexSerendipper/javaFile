package elasticsearch;

import elasticsearch.pojo.DiscussPost;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 【Springboot整合Elasticsearch】
 * （1）引入依赖
-----------------
<!--Elasticsearch依赖-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
</dependency>
-----------------
 * （2）application.properties配置
-----------------
# Elasticsearch properties
# 连接超时时间
spring.elasticsearch.rest.connection-timeout=1s
# 连接用户名（我没有配置用户名和密码，就不设置了）spring.elasticsearch.rest.username=community
# 连接密码 spring.elasticsearch.rest.password=
# 读取超时时间
spring.elasticsearch.rest.read-timeout=30s
# es接口地址，多个用逗号隔开（新版本直接监听es的http访问端口!）
spring.elasticsearch.rest.uris=http://192.168.131.130:9200
-----------------
 *
 * 【Elasticsearch使用】
 * （0）开启扫描 搜索引擎注解
 *      @EnableElasticsearchRepositories(basePackages = "community.dao")
 * （1）在与sql表的实体类上使用@Document注解，该表中的数据将自动同步到 elasticsearch中！ 并设置分区数shards和副本数replicas
 *      @Document(indexName = "dicusspost",shards = 6,replicas = 3)
 * （2）在对应的字段上使用对应的注解，声明存储到elasticsearch中的数据类型
 *      @Id                                # 主键
 *      @Field(type=FieldType.Integer)     # 整数类型
 *      @Field(index = true, type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")  # String类型（需要被搜索的字段）
 *       analyzer: 存储时的解析器和搜索时的解析器，实际上在索引中存值时，如 '互联网校招' ，我们希望elasticsearch能将其拆分成更多的词存入 如'互联、校招、联网等',
 *       用这些词来关联这句话，当输入关键词后能出现该词条！故使用"ik_max_word"
 *       searchAnalyzer：搜索解析器，当我们搜索时，如我们搜索 '互联网校招' ，我们肯定是只想搜索与 '互联网' '校招' 有关的内容，所以要使用更加聪明的分词方式，故使用"ik_smart"
 *       type：设置数据类型
 *       index：设置字段是否索引，默认是true，如果是false则该字段不能被查询
 *      @Field(type = FieldType.Date,format = DateFormat.date_hour_minute)    # 日期类型
 *        注意，✔字段使用的时间类型import java.time.LocalDate/LocalDateTime;✔如果使用旧版的java.util.Date会报错
 *         format：注意设置时必须设置日期格式，否则报错✔而且目前好像只能设置这个date_hour_minute....
 * （3）创建dao层接口 实现ElasticsearchRepository接口（该接口内置了多种CRUD操作方法），指定处理的 实体类型 以及 主键的类型
 *      @Repository
 *      public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost,Integer> {}
 * （4）自动注入 实现ElasticsearchRepository接口 即可使用
 *      ElasticsearchRepository.save(mapper.select())            # 增：将mapper查询的一条结果插入ElasticSearch中
 *                                                                 # 改：同样的，修改也是使用sava, 只需要传入修改后的实体，Elasticsearch将根据主键完成修改✔
 *      ElasticsearchRepository.saveAll(mapper.select())         # 增：将mapper查询的多条结果一起插入ElasticSearch中
 *      discussPostRepository.deleteById(ID);                    # 删：根据主键的ID值删除数据
 *      discussPostRepository.deleteAll();                       # 删：删除所有数据
 *      discussPostRepository.findAll();                         # 查：查询所有数据
 *
 *  【Elasticsearch搜索功能】
 *  （1）构建搜索条件
-----------------------
NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.multiMatchQuery("互联网寒冬", "title", "content"))       // 设置关键词，以及搜索的字段
    .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))                   // 搜索结果按照type,score.createTime降序排序
    .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
    .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
    .withPageable(PageRequest.of(0, 10))                                              // 设置分页搜索条件，设置当前页(从0开始) 以及 每页显示多少条数据
    .withHighlightFields(                                                             // 设置高亮显示条件，为匹配到的数据前后加标签
        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
    )
.build();
-----------------------
 * （2）搜索1
-----------------------
Page<DiscussPost> page = ElasticsearchRepository.search(searchQuery);    // 得到命中的分页page对象
    System.out.println(page.getTotalPages());                // 查询命中页数
    System.out.println(page.getTotalElements());            // 查询命中总数
    System.out.println(page.getNumber());                   // 查询当前页数
    System.out.println(page.getSize());                     // 查询当前页总记录数
    for (DiscussPost post : page) {                         // 遍历命中的所有数据
        System.out.println(post);
}
-----------------------
 * （3）搜索2（返回高亮显示数据）
 *   ElasticsearchRepository.search() 底层还是调用了 elasticsearchRestTemplate, 查到的数据包含了高亮数据和普通命中数据，但是并没有返回高亮数据，所以要直接使用elasticsearchRestTemplate
-----------------------
// 得到命中数据类 SearchHits， 内含命中的总数 totalHits，命中的具体信息列表 searchHits
// 命中的具体信息列表 searchHits 中的每一个元素都是一个 SearchHit 对象
// SearchHit 对象的 content属性，即为查询到的对象（discussPost）
// SearchHit对象的 highlightFields 属性，存储map数据（key为搜索的字段名,这里是title/content, value为添加高亮标签后的title/content的内容）
// 故 通过设置 SearchHit.content.title/content，实现返回高亮文本数据
SearchHits<DiscussPost> search = elasticsearchRestTemplate.search(searchQuery, DiscussPost.class);
List<SearchHit<DiscussPost>> searchHits = search.getSearchHits();
// 设置接收返回数据集合
List<DiscussPost> discussPosts = new ArrayList<>();
for (SearchHit<DiscussPost> searchHit : searchHits) {
        Map<String, List<String>> highLightFields = searchHit.getHighlightFields();
        // 用高亮数据替换原有数据
        searchHit.getContent().setTitle(highLightFields.get("title") == null ? searchHit.getContent().getTitle() : highLightFields.get("title").get(0));
        searchHit.getContent().setContent(highLightFields.get("content") == null ? searchHit.getContent().getContent() : highLightFields.get("content").get(0));
        // 把discusspost对象放到集合中
        discussPosts.add(searchHit.getContent());
    }
    System.out.println("命中的总数为：" + discussPosts.size());
    for (DiscussPost discussPost : discussPosts) {
        System.out.println(discussPost);  // 输出高亮显示数据的discusspost对象
    }
-----------------------
 @author Alex
 @create 2023-04-18-10:00
 */
public class UE02 {}

