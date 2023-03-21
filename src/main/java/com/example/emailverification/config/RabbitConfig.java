package com.example.emailverification.config;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
import org.springframework.retry.interceptor.RetryInterceptorBuilder;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

/**
 * @project: emailVerification
 * @author: Sarvar55
 */
@Configuration
@AllArgsConstructor
public class RabbitConfig implements RabbitListenerConfigurer {

    private CachingConnectionFactory cachingConnectionFactory;

    @Bean
    public Queue createUserRegistrationQueue() {
        return QueueBuilder.durable("q.user-registration")
                .withArgument("x-dead-letter-exchange", "x.registration-failure")
                .withArgument("x-dead-letter-routing-key", "fall-back")
                .build();
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(Jackson2JsonMessageConverter jsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public Declarables createPostRegistartionSchema() {

        return new Declarables(
                new FanoutExchange("x.post-registration"),
                new Queue("q.send-email"),
                new Queue("q.send-sms"),
                new Binding("q.send-email", Binding.DestinationType.QUEUE, "x.post-registration", "send-email", null),
                new Binding("q.send-sms", Binding.DestinationType.QUEUE, "x.post-registration", "send-sms", null));

    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }


    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, cachingConnectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        factory.setAdviceChain(retryInterceptor());
        return factory;
    }

    @Bean
    public Declarables createDeadLetterSchema() {
        return new Declarables(
                new DirectExchange("x.registration-failure"),
                new Queue("q.fall-back-registration"),
                new Binding("q.fall-back-registration", Binding.DestinationType.QUEUE, "x.registration-failure", "fall-back", null)
        );
    }

    @Bean
    public RetryOperationsInterceptor retryInterceptor() {
        return RetryInterceptorBuilder.stateless()
                .maxAttempts(3)
                .backOffOptions(2000, 2.0, 100000)
                .build();
    }
}
