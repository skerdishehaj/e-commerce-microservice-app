create table if not exists category
(
    id          integer      not null primary key,
    name        varchar(255) not null,
    description varchar(255) not null
);

create table if not exists product
(
    id                 integer          not null primary key,
    name               varchar(255)     not null,
    description        varchar(255)     not null,
    available_quantity double precision not null,
    price              numeric(38, 2),
    category_id        integer          not null,
    foreign key (category_id) references category (id)
);

create sequence if not exists category_seq increment by 50;
create sequence if not exists product_seq increment by 50;
