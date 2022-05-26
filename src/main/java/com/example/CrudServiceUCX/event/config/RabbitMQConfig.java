package com.example.CrudServiceUCX.event.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private final static String queueName = "CrudService";
    private final static String topicName  = "user.request.by.first.name";
    private final static String routingKey = "CrudService_RoutingKEY";

    @Bean
    public Queue queue () {
        return new Queue(queueName);
    }

    @Bean
    public TopicExchange topicExchange () {
        return new TopicExchange(topicName);
    }

    @Bean
    public Binding binding (Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with(routingKey);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter () {
        return new Jackson2JsonMessageConverter();
    }
}
