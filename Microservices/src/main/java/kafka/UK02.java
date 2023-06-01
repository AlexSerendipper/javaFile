package kafka;

/**
 * 【Kafka】
 *  简介：Kafka自称是一个分布式的流媒体平台，这是由于随着kafka的发展，它也能做一些除消息队列以外的工作（如日志收集、用户行为追踪、流式处理等）
 *         ✔消息队列一般有两种实现方式，一种的点对点的方式，类似与阻塞队列，一个产品只能由一个消费者消费
 *         ✔另一种方式是发布订阅的方式，生产者负责发布，产品可以由多个消费者消费，kafka使用的就是发布订阅的方式
 *  特点：高吞吐量、消息持久化(数据存硬盘中,对硬盘顺序读取的性能高于对内存的随机读取的性能)、高可靠性(分布式集群部署)、高扩展性
 *  常用术语：- Broker（kafka服务器，每一台服务器称为一个Broker）
 *             - Zookeeper（是一个独立的应用，kafka内置该应用，用来管理kafka集群）
 *             - Topic（生产者发布产品的位置，那个文件夹称为Topic）
 *             - Partition（分区，对Topic进行进一步分区生产者可以以多个线程写入数据，增强高并发能力。 如下图，数字代表产品，生产者每次生产是往队尾追加）
 *             --------------------------------------------------------------------------------------------------
 *             partition 0   0 1 2 3 4 5
 *             partition 1   0 1 2
 *             --------------------------------------------------------------------------------------------------
 *             - Offset（针对每一个分区，产品所在的索引的位置）
 *             - Leader Replica（主副本，类似于redis的master）
 *             - Follower Replica（次副本，类似于redis的slave）
 *
 * 【Kafka下载】http://kafka.apache.org，注意下载的tar包在windows下也能用
 *
 * 【Kafka使用1】了解，windows端
 * （1）zookeeper.properties配置：dataDir=D:/kafka/data/zookeeper,配置Zookeeper运行时会产生的一些的数据的存放位置
 *      server.properties配置：log.dirs=D:/kafka/data/kafka-logs配置kafka日志文件的存储位置
 * （2）cmd命令休止符，演示kafka使用
 *       启动zookeeper
 *      > cd d:
 *      > cd d:\kafka\kafka_2.12-2.3.0
 *      > bin\windows\zookeeper-server-start.bat config\zookeeper.properties     # windows中，以zookeeper.properties配置文件启动服务器
 *       启动 kafka
 *      > cd d:
 *      > cd d:\kafka\kafka_2.12-2.3.0
 *      > bin\windows\kafka-server-start.bat config\server.properties
 *       使用 1：生产者发布主题
 *      > cd d:
 *      > cd d:\kafka\kafka_2.12-2.3.0\bin\windows
 *      > kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic test
 *                    （创建主题topic: kafka默认服务器和端口为--bootstrap-server 9092，创建一个副本，一个分区，主题名为tests）
 *      > kafka-topics.bat --list --bootstrap-server localhost:9092                      # 查看当前服务器上的所有主题
 *      > kafka-console-producer.bat --broker-list localhost:9092 --topic test           # 以生产者的身份，向9092服务器的test主题，发布一条消息
 *       使用 2：消费者读取消息
 *      > cd d:
 *      > cd d:\kafka\kafka_2.12-2.3.0\bin\windows
 *      > kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning     # 以生产者的身份，向9092服务器的test主题，从头开始读取消息（此时生产者再次发布消息，消费者可以立马读取）
 *
 * 【Kafka使用2】linux端，推荐使用
 *  注意：需要提前安装好java环境，否则会报bin/kafka-run-class.sh: line 299: exec: java: not found错误（sudo apt install openjdk-8-jdk）
 * （1）zookeeper.properties配置：dataDir=/home/alex/opt/kafka_2.12-2.3.0/data/zookeeper，配置Zookeeper运行时会产生的一些的数据的存放位置
 *      server.properties配置：log.dirs=/home/alex/opt/kafka_2.12-2.3.0/data/kafka-logs，配置kafka日志文件的存储位置
 * （2）linux系统演示kafka使用
 *       启动zookeeper（于kafka文件夹下）
 *      > bin/zookeeper-server-start.sh config/zookeeper.properties      # 使用linux命令，以zookeeper.properties配置文件启动zookeeper
 *       启动 kafka
 *      > bin/kafka-server-start.sh  config/server.properties         # 使用linux命令，启动kafka服务器
 *
 @author Alex
 @create 2023-04-14-10:02
 */
public class UK02 {
}
