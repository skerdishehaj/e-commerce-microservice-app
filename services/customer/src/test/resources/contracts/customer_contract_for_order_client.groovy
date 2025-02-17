package contracts

import org.springframework.cloud.contract.spec.Contract


Contract.make {

    description("Get customer by id contract for order client")

    request {
        url '/api/v1/customers/1'
        method GET()
    }
    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body(
                "id": "1",
                "firstName": "John",
                "lastName": "Doe",
                "email": "johndoe@email.com",
                "address": [
                        "street"     : "123 Main St",
                        "houseNumber": "1",
                        "zipCode"    : "12345"
                ]
        )
    }
}