package com.service.rocketMQ;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class Listener {
    public static void start() throws Exception{
        // CustomChatGpt customChatGpt = new CustomChatGpt();
        // System.out.println(customChatGptDemo.getAnswer("你好！"));
        Responser responser = new Responser();
        responser.sendAnswer("你好", 1);
        // 创建消费者实例
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("getQuestion_group", true);
        // 设置NameServer地址
        consumer.setNamesrvAddr("localhost:9876");
        // 订阅消息
        consumer.subscribe("question_topic", MessageSelector.byTag("tagA"));
        // 注册消息监听器
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    String info = new String(msg.getBody(), StandardCharsets.UTF_8);
                    JSONObject jsonObject = JSON.parseObject(info);
                    String question = jsonObject.getString("question");
                    System.out.printf("Received message: %s%n", question);
                    String answer = "好的";
                    JSONObject response = new JSONObject();
                    response.put("sessionID", jsonObject.get("sessionID"));
                    response.put("answer", answer);
                    try{
                        responser.sendAnswer(response.toJSONString(), 0);
                    }
                    catch (Exception e){
                        System.out.println(e);
                        System.out.println("ChatGPT回答为空！！");
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动消费者
        consumer.start();
        System.out.println("Consumer started");
        // 关闭消费者
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            consumer.shutdown();
        }));
    }
}
