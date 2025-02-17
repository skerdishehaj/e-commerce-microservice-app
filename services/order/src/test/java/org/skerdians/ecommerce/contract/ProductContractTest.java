package org.skerdians.ecommerce.contract;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skerdians.ecommerce.product.dto.PurchaseRequest;
import org.skerdians.ecommerce.product.dto.PurchaseResponse;
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

import java.util.Collections;
import java.util.List;

@ActiveProfiles("qa")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureStubRunner(
        stubsMode = StubRunnerProperties.StubsMode.LOCAL,
        ids = "org.skerdians:product:+:9395"
)
public class ProductContractTest {

    @Test
    public void test() {
        RestTemplate restTemplate = new RestTemplate();
        List<PurchaseRequest> request = Collections.singletonList(new PurchaseRequest(101, 1));
        ResponseEntity<List<PurchaseResponse>> response = restTemplate.exchange(
                "http://localhost:9395/api/v1/products/purchase", HttpMethod.POST, new HttpEntity<>(request),
                new ParameterizedTypeReference<List<PurchaseResponse>>() {
                });
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotEmpty();
        Assertions.assertThat(response.getBody().get(0).productId()).isEqualTo(101);
        Assertions.assertThat(response.getBody().get(0).quantity()).isEqualTo(1);
    }
}
