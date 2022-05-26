package com.example.CrudServiceUCX.event.consumer;

import com.example.CrudServiceUCX.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMQConsumer {

    private final String queueName = "CrudService";

    @RabbitListener(queues = queueName)
    public void consumeMessageFromQueue (Message message) {
        log.info(message.getMessage());
    }
}
