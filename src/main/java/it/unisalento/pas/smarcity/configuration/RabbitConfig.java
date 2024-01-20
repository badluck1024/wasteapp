package it.unisalento.pas.smarcity.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue throwWasteTriggerQueue() { return new Queue("throwWasteTriggerQueue", true); }

    @Bean
    public Queue requestPaymentsQueue() { return new Queue("requestPaymentsQueue", true); }

    @Bean
    public Queue updateOfficeBillQueue() { return new Queue("updateOfficeBillQueue", true); }

    @Bean
    public Queue updateOfficeRecordsQueue() { return new Queue("updateOfficeRecordsQueue", true); }

    @Bean
    public TopicExchange throwWasteTriggerExchange() {
        return new TopicExchange("throwWasteTriggerExchange");
    }

    @Bean
    public TopicExchange requestPaymentsExchange() {
        return new TopicExchange("requestPaymentsExchange");
    }

    @Bean
    public TopicExchange updateOfficeBillExchange() {
        return new TopicExchange("updateOfficeBillExchange");
    }

    @Bean
    public TopicExchange updateOfficeRecordsExchange() {
        return new TopicExchange("updateOfficeRecordsExchange");
    }

    @Bean
    public Binding throwWasteTriggerBinding(Queue throwWasteTriggerQueue, TopicExchange throwWasteTriggerExchange) {
        return BindingBuilder.bind(throwWasteTriggerQueue).to(throwWasteTriggerExchange).with("throwWasteTriggerKey");
    }

    @Bean
    public Binding requestPaymentsBinding(Queue requestPaymentsQueue, TopicExchange requestPaymentsExchange) {
        return BindingBuilder.bind(requestPaymentsQueue).to(requestPaymentsExchange).with("requestPaymentsKey");
    }

    @Bean
    public Binding updateOfficeBillBinding(Queue updateOfficeBillQueue, TopicExchange updateOfficeBillExchange) {
        return BindingBuilder.bind(updateOfficeBillQueue).to(updateOfficeBillExchange).with("updateOfficeBillKey");
    }

    @Bean
    public Binding updateOfficeRecordsBinding(Queue updateOfficeRecordsQueue, TopicExchange updateOfficeRecordsExchange) {
        return BindingBuilder.bind(updateOfficeRecordsQueue).to(updateOfficeRecordsExchange).with("updateOfficeRecordsKey");
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }
}
