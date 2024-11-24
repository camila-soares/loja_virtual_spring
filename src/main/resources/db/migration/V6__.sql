CREATE SEQUENCE IF NOT EXISTS seq_usuario START WITH 1 INCREMENT BY 1;

CREATE TABLE usuario
(
    id               BIGINT       NOT NULL,
    login            VARCHAR(255) NOT NULL,
    senha            VARCHAR(255) NOT NULL,
    data_atual_senha date         NOT NULL,
    pessoa_id        BIGINT       NOT NULL,
    empresa_id       BIGINT       NOT NULL,
    CONSTRAINT pk_usuario PRIMARY KEY (id)
);

CREATE TABLE usuarios_acesso
(
    acesso_id  BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL
);

ALTER TABLE usuario
    ADD CONSTRAINT uc_usuario_login UNIQUE (login);

ALTER TABLE usuarios_acesso
    ADD CONSTRAINT unique_acesso_user UNIQUE (usuario_id, acesso_id);

ALTER TABLE usuarios_acesso
    ADD CONSTRAINT usuario_fk FOREIGN KEY (usuario_id) REFERENCES usuario (id);

ALTER TABLE usuarios_acesso
    ADD CONSTRAINT usuario_fkoSbND9 FOREIGN KEY (acesso_id) REFERENCES acesso (id);