package kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * 【springboot整合kafka】
 * （1）引入依赖
------------------
<!--kafka依赖-->
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
</dependency>
------------------
 * （2）配置kafka（application.properties）
 *   配置server
 *   配置consumer
------------------
# 配置默认的服务器（默认Broker地址）(配置本机的ip地址，windows端可以使用localhost，linux端要使用192.168.131.129)
spring.kafka.bootstrap-servers=192.168.131.129:9092
# 配置消费组id（可以不配置）
spring.kafka.consumer.group-id=community-consumer-group
# 配置是否自动提交消费者的偏移量
spring.kafka.consumer.enable-auto-commit=true
# 配置自动提交的频率（3000ms）
spring.kafka.consumer.auto-commit-interval=3000
------------------
 *
 * 【kafka使用】
 *   kafkaTemplate.send(topic,data);         # 生产者,发布指定topic的内容，主动发布，需要时发布
 *   @KafkaListener(topics={"test"})         # 消费者,接收指定topic的内容，被动接收，一直处于待接收状态 ~
 *
 *
 @author Alex
 @create 2023-04-14-11:12
 */


public class UK03 {
   /*
    @Autowired
    private KafkaProducer kafkaProducer;
    @Test
    public void test() throws Exception{
        // 生产者是主动发消息，消费者是一直在接消息的状态
        kafkaProducer.sendMessage("test","你好");
        kafkaProducer.sendMessage("test","在吗");
        Thread.sleep(1000*10);
    }
}

@Component
class KafkaProducer{
    @Autowired
    private KafkaTemplate kafkaTemplate;
    public void sendMessage(String topic,String content){
        kafkaTemplate.send(topic,content);
    }
}


@Component
class KafkaConsumer{
    @KafkaListener(topics={"test"})
    public void receiveMessage(ConsumerRecord record){
        System.out.println(record.value());
    }
    */
}
