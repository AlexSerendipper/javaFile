package elasticsearch;

/**
 * 【Elasticsearch 简介】Elasticsearch简称ES
 *  Elasticsearch是一个 分布式的（多台服务器集群式部署）、restful风格的搜索引擎。
 *   支持对各种类型数据的检索，而且搜索速度快，提供实时的搜索服务。
 *   由于采用分布式部署，该引擎可以很容易的实现水平扩展（加服务器），每秒可以处理PB级的海量数据
 *  要想使用该搜索引擎，要把数据库的数据在存一份到该引擎中，从这个角度来看，可以把Elasticsearch看作是一种特殊的数据库
 *  ✔Elasticsearch默认开启两个端口，9200端口负责http访问，9200端口负责TCP访问
 *
 * 【Elasticsearch 常见术语】与 mysql 横向对比
 *  索引 ==> 对应 mysql的数据库
 *  类型 ==> 对应 mysql的数据表
 *  文档（通常采用json格式） ==> 对应 mysql的行
 *  字段 ==> 对应 mysql的列（字段）
 *  ✔✔ 注意！！在Elasticsearch6.0以上的版本中，取消了类型的概念，一个索引就对应着一张表！！所有类型都设置为_doc作为占位符
 *  集群 ==> 类似与redis集群，多台Elasticsearch服务器的组合 构成一个集群
 *  节点 ==> 集群中的每一台服务器 称为一个节点
 *  分片 ==> 数据往索引（数据表）中存储时，可以拆分成多个分片进行存储，大大增加系统的并发能力
 *  副本 ==> 副本是对分片的备份，一个分片可以有多个副本
 *
 * 【Elasticsearch 下载与配置】
 *  建议下载当前spring自动版本仲裁推荐的Elasticsearch版本
 * (0) Elasticsearch启动时，默认需要262144的虚拟内容（否则启动时可能会报错）
 *      vim sysctl.conf                        # 以管理员权限打开sysctl.conf文件，该文件处于/etc目录下
 *      vm.max_map_count=655360                # 打开后在文件的最后一行加上这句话
 *      sysctl -p                              # 在/etc目录下使用该指令使文件生效
 *（1）修改 config 目录下的 elasticsearch.yml文件
 *      cluster.name: Community                # 设置集群的名字
 *      path.data: /home/alex/opt/elasticsearch-7.6.2-linux-x86_64/elasticsearch-7.6.2/elasticsearchData/data          # 配置Elasticsearch的文件存放路径
 *      path.logs: /home/alex/opt/elasticsearch-7.6.2-linux-x86_64/elasticsearch-7.6.2/elasticsearchData/logs          # 配置Elasticsearch的目录存放路径
 *      discovery.seed_hosts: ["192.168.131.130"]                          # 在约第69行新增该行，指定集群的主机端口
 *      cluster.initial_master_nodes: ["master"]                           # 在约第73行新增该行，指定启动时初始化的参与选主的node
 *      network.host: 192.168.131.130          # 把network.host的值修改为本机的IP地址
 *      http.cors.enabled: true                # 在elasticsearch.yml文件的最后增加如下两行，开启跨域访问功能（restful风格的访问）
 *       http.cors.allow-origin: "*"
 *（2）安装中文分词插件（默认Elasticsearch只支持英文分词）（https://github.com/medcl/elasticsearch-analysis-ik/releases/tag/v7.6.2）
 *      到github上安装对应版本的ik插件，并将其解压到 Elasticsearch目录的plugins目录的ik目录下！（注意：只能解压到该目录，并且是把压缩包内的所有文件，复制到我们自己创建的ik目录下）
 *        /home/alex/opt/elasticsearch-7.6.2-linux-x86_64/elasticsearch-7.6.2/plugins/ik
 *      IKAnalyzer.cfg.xml                     # 随着时代的发展，当出现新的词汇，用户可以在此处配置新词，让ik插件学会分词
 *（3）启动服务(启动目录下)（注意：Elasticsearch的启动至少要保证3g的虚拟机内存，否则可能会卡死~）
 *      bin/elasticsearch
 *
 * 【Elasticsearch 使用】通过bin目录下的命令
 * （1）原始请求
 *      curl -X GET "localhost:9200/_cat/health?v"    # 使用该命令，查看当前 Elasticsearch集群的健康状况
 *      curl -X GET "localhost:9200/_cat/nodes?v"     # 使用该命令，查看当前节点的状态
 *      curl -X GET "localhost:9200/_cat/indices?v"   # 查看索引
 *      curl -X PUT "localhost:9200/索引名"           # 创建指定索引名的索引
 *      curl -X DELETE "localhost:9200/索引名"        # 删除指定索引名的索引
 * （2）restful请求
 *      192.168.131.130:9200/_cat/health?v           # 同样可以使用postman，get请求实现restful风格的访问
 *      192.168.131.130:9200/test                    # 使用postman的 delete 请求，删除指定的索引
 *      192.168.131.130:9200/test/_doc/1             # 插入/修改数据：使用postman的 put 请求，/test为索引名，如果不存在指定索引，会自动创建
 *                                                                                           /_doc为占位符，无特殊含义。1为插入数据的id
 *                                                                                           注意，传入的数据，以请求体的raw-Json格式传入（如果两次传入数据不同，相当于是修改）
 *      192.168.131.130:9200/test/_doc/2             # 取出数据：使用postman的 get 请求获取指定id值的内容
 *      192.168.131.130:9200/test/_doc/3             # 删除数据：使用postman的 delete 请求删除指定id值的内容
 * （3）Elasticsearch查询
 *      192.168.131.130:9200/test/_search?q=title:互联网       # 查询关键字，当我们在索引中存入了不同id值的数据，可以根据关键字进行查询，_search?q=title:互联网表示寻找title中含有互联网 的内容
 *      192.168.131.130:9200/test/_search?q=content:运营实习   # 查询关键字，查找内容中含有 实习或运营（自动分词） 的内容
 *      192.168.131.130:9200/test/_search                      # 查询关键字，当有复杂的查询条件，需要以请求体的raw-Json格式传入。。不传入请求体则查询所有数据
                                                                                                            -----------------------
                                                                                                            {
                                                                                                                "query":{
                                                                                                                    "multi_match":{                            # 多条件查询
                                                                                                                        "query":"互联网",                      # 查询的关键字
                                                                                                                        "fields":["title","content"]           # 查询的范围，即从title和content中进行查询
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                            -----------------------
 *
 *
 @author Alex
 @create 2023-04-16-14:09
 */
public class UE01 {
}
