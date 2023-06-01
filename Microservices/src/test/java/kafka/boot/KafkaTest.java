package kafka.boot;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.AfterClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


/**
 @author Alex
 @create 2023-04-14-13:05
 */

@SpringBootTest
public class KafkaTest {
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
}