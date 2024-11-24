CREATE SEQUENCE IF NOT EXISTS seq_conta_receber START WITH 1 INCREMENT BY 1;

CREATE TABLE conta_receber
(
    id             BIGINT       NOT NULL,
    descricao      VARCHAR(255) NOT NULL,
    status         VARCHAR(255) NOT NULL,
    dt_vencimento  date         NOT NULL,
    dt_pagamento   date,
    valor_total    DECIMAL      NOT NULL,
    valor_desconto DECIMAL,
    pessoa_id      BIGINT       NOT NULL,
    empresa_id     BIGINT       NOT NULL,
    CONSTRAINT pk_conta_receber PRIMARY KEY (id)
);