--liquibase formatted sql

--changeset stan:insert

insert into account1(amount, version) values (100500,0);
insert into account1(amount, version) values (0,0);