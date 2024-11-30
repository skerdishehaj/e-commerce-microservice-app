package org.skerdians.ecommerce.payment.service;

import lombok.RequiredArgsConstructor;
import org.skerdians.ecommerce.notification.NotificationProducer;
import org.skerdians.ecommerce.notification.dto.PaymentNotificationRequest;
import org.skerdians.ecommerce.payment.dto.PaymentRequest;
import org.skerdians.ecommerce.payment.entity.Payment;
import org.skerdians.ecommerce.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;

    public Integer createPayment(PaymentRequest request) {
        Payment payment = this.repository.save(this.mapper.toPayment(request));

        this.notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        request.orderReference(),
                        request.amount(),
                        request.paymentMethod(),
                        request.customer().firstName(),
                        request.customer().lastName(),
                        request.customer().email()
                )
        );
        return payment.getId();
    }
}