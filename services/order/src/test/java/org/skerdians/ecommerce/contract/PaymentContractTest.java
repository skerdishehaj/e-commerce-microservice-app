package org.skerdians.ecommerce.contract;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skerdians.ecommerce.customer.dto.CustomerResponse;
import org.skerdians.ecommerce.order.entity.PaymentMethod;
import org.skerdians.ecommerce.payment.dto.PaymentRequest;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@ActiveProfiles("qa")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureStubRunner(
        stubsMode = StubRunnerProperties.StubsMode.LOCAL,
        ids = "org.skerdians:payment:+:9396"
)
public class PaymentContractTest {

    @Test
    public void test() {
        RestTemplate restTemplate = new RestTemplate();
        PaymentRequest request = PaymentRequest.builder()
                .amount(new BigDecimal("129.99"))
                .paymentMethod(PaymentMethod.PAYPAL)
                .orderId(2353)
                .orderReference("MS-20251702")
                .customer(CustomerResponse.builder()
                        .id("676ae5ea20a3ae4d4038f9b2")
                        .firstName("Aaaa")
                        .lastName("Bbbb")
                        .email("aaaa@bbbb.com")
                        .build())
                .build();
        ResponseEntity<Integer> response = restTemplate.exchange(
                "http://localhost:9396/api/v1/payments", HttpMethod.POST, new HttpEntity<>(request),
                new ParameterizedTypeReference<Integer>() {
                });
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo(1953);
    }
}
