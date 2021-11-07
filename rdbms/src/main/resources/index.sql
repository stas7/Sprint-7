--liquibase formatted sql

--changeset stan:index

create index index_account ON account1(id);