package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method POST()
        url '/api/v1/payments'
        headers {
            contentType(applicationJson())
        }
        body([
            amount: 129.99,
            paymentMethod: 'PAYPAL',
            orderId: 2353,
            orderReference: 'MS-20251702',
            customer: [
                id: '676ae5ea20a3ae4d4038f9b2',
                firstName: 'Aaaa',
                lastName: 'Bbbb',
                email: 'aaaa@bbbb.com'
            ]
        ])
    }
    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body(1953)
    }
}