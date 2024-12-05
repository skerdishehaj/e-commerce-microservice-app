create table if not exists payment
(
    id                integer         not null primary key,
    amount            numeric(38, 2)  not null,
    payment_method    varchar(255)    not null,
    order_id          integer         not null,
    created_date      timestamp       not null default current_timestamp,
    last_modified_date timestamp      default current_timestamp
);

create sequence if not exists payment_seq increment by 50;