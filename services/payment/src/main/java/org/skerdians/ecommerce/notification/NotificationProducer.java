package org.skerdians.ecommerce.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skerdians.ecommerce.notification.dto.PaymentNotificationRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.messaging.Message;

import static org.skerdians.ecommerce.config.KafkaPaymentTopicConfig.PAYMENT_TOPIC;
import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {

    private final KafkaTemplate<String, PaymentNotificationRequest> kafkaTemplate;

    public void sendNotification(PaymentNotificationRequest request) {
        log.info("Sending notification for order <{}> with body <{}>", request.orderReference(), request);
        Message<PaymentNotificationRequest> message = MessageBuilder
                .withPayload(request)
                .setHeader(TOPIC, PAYMENT_TOPIC)
                .build();
        this.kafkaTemplate.send(message);
    }
}
