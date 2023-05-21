-- liquibase formatted sql

--changeset dsazonov: 1
CREATE TABLE notification_task(
    );

--changeset dsazonov: 2
CREATE INDEX datetime ON notification_task(datetime);