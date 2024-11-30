## Technologies
- Microservices, Spring Boot 3, Spring Cloud

## Problem

- As a business owner in the e-commerce sector, I am currently operating without the aid of any digital solution.

## About business

- My product line consists of a variety of items, each identified by a unique code and accompanied by a detailed
  description

## Process

- Customers interact with my business by placing orders from this list of products.
- I identify my customers using their first name, last name, email and address.

## Payment

- Each customer transaction involves a specific payment method.

## Follow up

- Upon completion of a successful payment transaction, I take the responsibility to inform the customer via an email,
  confirming the success of their payment or conversely, notifying them of any payment failures.

## Business growth

- In a bid to streamline operations and promote growth within my business, I am looking to invest in the development of
  a dedicated application.

## Goal

- This application will serve to simplify my business processes and contribute significantly to the overall efficiency and
  scalability of my online venture.

## After more discussions with business owner

- Application should be resilient to failures, if one component goes down it does not bring the entire system to halt (Resilience)
- Application should be scalable to handle increasing traffic and workload demands. (Scalability)
- As a business owner I aim for rapid development and deployment of new features and updates to stay competitive in the
  market. (Agile)
- As a business owner I require comprehensive monitoring and debugging capabilities to track the performance of my
  application and identify potential issues. (Observability)
- Microservices architecture is the best fit for my application as it allows for the independent development and
  deployment of services. (Microservices)
- DDD (Domain Driven Design) is the best fit for my application as it allows for the independent development and
  deployment of services. (Microservices)
  - Customer domain : Customer, Address
  - Order domain : Order, OrderLine
  - Product domain : Product, Category
  - Payment domain : Payment
  - Notification domain : Notification

---

## Database

### Customer

| Column Name | Data Type | Description |
|-------------|-----------|-------------|
| id          | INTEGER   | Primary Key |
| address_id  | INTEGER   | Foreign Key |
| firstname   | VARCHAR   |             |
| lastname    | VARCHAR   |             |
| email       | VARCHAR   |             |

### Address

| Column Name | Data Type | Description |
|-------------|-----------|-------------|
| id          | INTEGER   | Primary Key |
| street      | VARCHAR   |             |
| city        | VARCHAR   |             |
| state       | VARCHAR   |             |
| zip         | VARCHAR   |             |

### Category

| Column Name | Data Type | Description |
|-------------|-----------|-------------|
| id          | INTEGER   | Primary Key |
| name        | VARCHAR   |             |
| description | TEXT      |             |

### Product

| Column Name | Data Type | Description |
|-------------|-----------|-------------|
| id          | INTEGER   | Primary Key |
| category_id | INTEGER   | Foreign Key |
| name        | VARCHAR   |             |
| description | TEXT      |             |
| price       | DECIMAL   |             |

### Order

| Column Name | Data Type | Description |
|-------------|-----------|-------------|
| id          | INTEGER   | Primary Key |
| customer_id | INTEGER   | Foreign Key |
| order_date  | DATE      |             |
| status      | VARCHAR   |             |

### OrderLine

| Column Name | Data Type | Description |
|-------------|-----------|-------------|
| id          | INTEGER   | Primary Key |
| order_id    | INTEGER   | Foreign Key |
| product_id  | INTEGER   | Foreign Key |
| quantity    | INTEGER   |             |
| price       | DECIMAL   |             |

### Payment

| Column Name | Data Type | Description |
|-------------|-----------|-------------|
| id          | INTEGER   | Primary Key |
| order_id    | INTEGER   | Foreign Key |
| reference   | VARCHAR   |             |
| status      | VARCHAR   |             |
| amount      | DECIMAL   |             |

### Notification

| Column Name | Data Type | Description |
|-------------|-----------|-------------|
| id          | INTEGER   | Primary Key |
| payment_id  | INTEGER   | Foreign Key |
| order_id    | INTEGER   | Foreign Key |
| sender      | VARCHAR   |             |
| recipient   | VARCHAR   |             |
| content     | TEXT      |             |
| date        | DATE      |             |

---

### Relationships:

1. **Customer and Address**:
    - Relationship: One-to-Many (1:1)
    - Description: A Customer can have one Addresses and an Address is associated with only one Customer.
    - Edge: Source (`Customer`), Target (`Address`), Cardinality (1:1)

2. **Customer and Order**:
    - Relationship: One-to-Many (1:M)
    - Description: A Customer can place multiple Orders, but an Order is placed by only one Customer.
    - Edge: Source (`Customer`), Target (`Order`), Cardinality (1:M)

3. **Order and Product**:
    - Relationship: Many-to-Many (M:M)
    - Description: An Order references many Products and a Product can appear in multiple Orders.
    - Edge: Source (`Order`), Target (`Product`), Cardinality (M:M)

3.1. **Order and OrderLine**:

- Relationship: One-to-Many (1:M)
- Description: An Order can have multiple OrderLines, but an OrderLine belongs to only one Order.
- Edge: Source (`Order`), Target (`OrderLine`), Cardinality (1:M)

3.2. **OrderLine and Product**:

- Relationship: One-to-Many (1:M)
- Description: An OrderLine references one Product, but a Product can appear in multiple OrderLines.
- Edge: Source (`OrderLine`), Target (`Product`), Cardinality (1:M)

4. **Product and Category**:
    - Relationship: One-to-Many (1:M)
    - Description: A Product belongs to one Category, but a Category can have multiple Products.
    - Edge: Source (`Product`), Target (`Category`), Cardinality (1:M)

5. **Order and Payment**:
    - Relationship: One-to-Many (1:1)
    - Description: An Order can have one Payments and a Payment is associated with only one Order.
    - Edge: Source (`Order`), Target (`Payment`), Cardinality (1:1)

6. **Order and Notification**:
    - Relationship: One-to-Many (1:1)
    - Description: An Order can have one Notification and a Notification is associated with only one Order.
    - Edge: Source (`Order`), Target (`Notification`), Cardinality (1:1)

7. **Payment and Notification**:
    - Relationship: One-to-Many (1:1)
    - Description: A Payment can have one Notification and a Notification is associated with only one Payment.
    - Edge: Source (`Payment`), Target (`Notification`), Cardinality (1:1)

---

## Folder structure

- `services`: Contains all services and microservices of the application

---

### Why are we starting with `config-server` and `discovery-server`?

- We start by implementing the `config-server` because we want to store all the configurations for the microservices
  that we will be creating in the future.
- And then we need the `discovery-server` so when starting the application we want to automatically register all the
  microservices to our discovery server.

#### Discovery Server

- `config client` dependency that we are using with the `discovery-server` with help us connect to the config server and
  fetch all configurations of micro-services
- The `eureka-server` dependency is for the only purpose so the microservice to be a discovery server
- Discovery server will connect to the config server and it will try to fetch the configurations using the application
  name specified in the application.ylm file
    - This is why Application Name of the discovery server is **_Important_** because it must match the name of the
      configuration file in the config server 
---

- In each microservice we will need the `config client` dependency to connect to the `config server` and fetch the configurations for the microservice`
- We will also need the `eureka client` dependency to register the microservice to the `discovery server`
