package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Request for purchasing a product"
    request {
        method POST()
        url '/api/v1/products/purchase'
        headers {
            contentType(applicationJson())
        }
        body([
                [
                        productId: 101,
                        quantity : 1
                ]
        ])
    }
    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body([
                [
                        productId  : 101,
                        name       : "Gaming Keyboard 1",
                        description: "Backlit gaming keyboard with customizable keys",
                        price      : 129.99,
                        quantity   : 1.0
                ]
        ])
    }
}