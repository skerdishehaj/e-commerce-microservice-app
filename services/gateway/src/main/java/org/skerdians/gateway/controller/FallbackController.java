package org.skerdians.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping("/fallback")
@RestController
public class FallbackController {
    @RequestMapping("/contactSupport")
    public Mono<String> contactSupport() {
        return Mono.just("Please contact support team at: skerdians@fti.edu.al");
    }
}
