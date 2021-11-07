--liquibase formatted sql

--changeset stan:init

create table account1
(
    id bigserial constraint account_pk primary key,
    amount int check (amount >=0),
    version int default 0
);


