create table if not exists customer_order
(
    id                 integer        not null primary key,
    reference          varchar(255)   not null,
    total_amount       numeric(38, 2) not null,
    payment_method     varchar(255)   not null,
    customer_id        varchar(255)   not null,
    created_at         timestamp      not null,
    last_modified_date timestamp
);

create table if not exists customer_line
(
    id         integer          not null primary key,
    order_id   integer          not null,
    product_id integer          not null,
    quantity   double precision not null,
    foreign key (order_id) references customer_order (id)
);

create sequence if not exists customer_order_seq increment by 50;
create sequence if not exists customer_line_seq increment by 50;
