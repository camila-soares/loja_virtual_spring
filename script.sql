--drop database lojavirtualDB;

--create database lojavirtualDB;

create sequence seq_acesso;

alter sequence seq_acesso owner to postgres;

create sequence seq_avaliacao_produto;

alter sequence seq_avaliacao_produto owner to postgres;

create sequence seq_categoria_produto;

alter sequence seq_categoria_produto owner to postgres;

create sequence seq_conta_pagar;

alter sequence seq_conta_pagar owner to postgres;

create sequence seq_conta_receber;

alter sequence seq_conta_receber owner to postgres;

create sequence seq_cupom_desconto;

alter sequence seq_cupom_desconto owner to postgres;

create sequence seq_endereco;

alter sequence seq_endereco owner to postgres;

create sequence seq_forma_pagamento;

alter sequence seq_forma_pagamento owner to postgres;

create sequence seq_imagem_produto;

alter sequence seq_imagem_produto owner to postgres;

create sequence seq_item_venda_loja;

alter sequence seq_item_venda_loja owner to postgres;

create sequence seq_marca_produto;

alter sequence seq_marca_produto owner to postgres;

create sequence seq_nota_fiscal_compra;

alter sequence seq_nota_fiscal_compra owner to postgres;

create sequence seq_nota_fiscal_venda;

alter sequence seq_nota_fiscal_venda owner to postgres;

create sequence seq_nota_item_produto;

alter sequence seq_nota_item_produto owner to postgres;

create sequence seq_pessoa;

alter sequence seq_pessoa owner to postgres;

create sequence seq_produto;

alter sequence seq_produto owner to postgres;

create sequence seq_status_rastreio;

alter sequence seq_status_rastreio owner to postgres;

create sequence seq_usuario;

alter sequence seq_usuario owner to postgres;

create sequence seq_venda_compra_loja_virtual;

alter sequence seq_venda_compra_loja_virtual owner to postgres;

create table acesso
(
    id        bigint       not null
        primary key,
    descricao varchar(255) not null
);

alter table acesso
    owner to postgres;

create table categoria_produto
(
    id             bigint       not null
        primary key,
    nome_descricao varchar(255) not null
);

alter table categoria_produto
    owner to postgres;

create table conta_pagar
(
    id                   bigint         not null
        primary key,
    descricao            varchar(255)   not null,
    dt_pagamento         date,
    dt_vencimento        date           not null,
    status               varchar(255)   not null
        constraint conta_pagar_status_check
            check ((status)::text = ANY
                   ((ARRAY ['COBRANCA'::character varying, 'VENCIDA'::character varying, 'ABERTA'::character varying, 'ALUGUEL'::character varying, 'FUNCIONARIO'::character varying, 'NEGOCIADA'::character varying, 'QUITADA'::character varying])::text[])),
    valor_desconto       numeric(38, 2),
    valor_total          numeric(38, 2) not null,
    pessoa_id            bigint         not null,
    pessoa_fornecedor_id bigint         not null
);

alter table conta_pagar
    owner to postgres;

create table conta_receber
(
    id             bigint         not null
        primary key,
    descricao      varchar(255)   not null,
    dt_pagamento   date,
    dt_vencimento  date           not null,
    status         varchar(255)   not null
        constraint conta_receber_status_check
            check ((status)::text = ANY
                   ((ARRAY ['CONBRANCA'::character varying, 'VENCIDA'::character varying, 'ABERTA'::character varying, 'QUITADA'::character varying])::text[])),
    valor_desconto numeric(38, 2),
    valor_total    numeric(38, 2) not null,
    pessoa_id      bigint         not null
);

alter table conta_receber
    owner to postgres;

create table cupom_desconto
(
    id                     bigint       not null
        primary key,
    cod_desc               varchar(255) not null,
    data_validade_cupom    date         not null,
    valor_porcent_desconto numeric(38, 2),
    valor_real_desconto    numeric(38, 2)
);

alter table cupom_desconto
    owner to postgres;

create table endereco
(
    id            bigint       not null
        primary key,
    bairro        varchar(255) not null,
    cep           varchar(255) not null,
    cidade        varchar(255) not null,
    complemento   varchar(255),
    logradouro    varchar(255) not null,
    numero        varchar(255) not null,
    tipo_endereco varchar(255) not null
        constraint endereco_tipo_endereco_check
            check ((tipo_endereco)::text = ANY
                   ((ARRAY ['COBRANCA'::character varying, 'ENTREGA'::character varying])::text[])),
    uf            varchar(255) not null,
    pessoa_id     bigint       not null
);

alter table endereco
    owner to postgres;

create table forma_pagamento
(
    id        bigint       not null
        primary key,
    descricao varchar(255) not null
);

alter table forma_pagamento
    owner to postgres;

create table marca_produto
(
    id             bigint       not null
        primary key,
    nome_descricao varchar(255) not null
);

alter table marca_produto
    owner to postgres;

create table nota_fiscal_compra
(
    id             bigint         not null
        primary key,
    data_compra    date           not null,
    descricao_obs  varchar(255),
    numero_nota    varchar(255)   not null,
    serie_nota     varchar(255)   not null,
    valor_desconto numeric(38, 2),
    valor_icms     numeric(38, 2) not null,
    valor_total    numeric(38, 2) not null,
    conta_pagar_id bigint         not null
        constraint conta_pagar_fk
            references conta_pagar,
    pessoa_id      bigint         not null
);

alter table nota_fiscal_compra
    owner to postgres;

create table nota_fiscal_venda
(
    id                           bigint       not null
        primary key,
    numero                       varchar(255) not null,
    pdf                          text         not null,
    serie                        varchar(255) not null,
    tipo                         varchar(255) not null,
    xml                          text         not null,
    venda_compra_loja_virtual_id bigint       not null
        constraint uklwo38kb0aaxboalphfjlan8qr
            unique
);

alter table nota_fiscal_venda
    owner to postgres;

create table pessoa_fisica
(
    id              bigint       not null
        primary key,
    email           varchar(255) not null,
    nome            varchar(255) not null,
    telefone        varchar(255) not null,
    cpf             varchar(255) not null,
    data_nascimento date
);

alter table pessoa_fisica
    owner to postgres;

create table pessoa_pj
(
    id            bigint       not null
        primary key,
    email         varchar(255) not null,
    nome          varchar(255) not null,
    telefone      varchar(255) not null,
    categoria     varchar(255),
    cnpj          varchar(255) not null,
    insc_estadual varchar(255) not null,
    insc_municial varchar(255),
    nome_fantasia varchar(255) not null,
    razao_social  varchar(255) not null
);

alter table pessoa_pj
    owner to postgres;

create table produto
(
    id                  bigint           not null
        primary key,
    alerta_qtde_estoque boolean,
    altura              double precision not null,
    clique              integer,
    descricao           text             not null,
    largura             double precision not null,
    link_you_tube       varchar(255),
    nome                varchar(255)     not null,
    peso                double precision not null,
    profundidade        double precision not null,
    qdt_estoque         integer          not null,
    qtde_alerta_estoque integer,
    tipo_unidade        varchar(255)     not null,
    valor_venda         numeric(38, 2)   not null
);

alter table produto
    owner to postgres;

create table avaliacao_produto
(
    id         bigint  not null
        primary key,
    descricao  varchar(255),
    nota       integer not null,
    pessoa_id  bigint  not null,
    produto_id bigint  not null
        constraint produto_fk
            references produto
);

alter table avaliacao_produto
    owner to postgres;

create table imagem_produto
(
    id              bigint not null
        primary key,
    image_miniatura text   not null,
    image_original  text   not null,
    produto_id      bigint not null
        constraint produto_fk
            references produto
);

alter table imagem_produto
    owner to postgres;

create table nota_item_produto
(
    id                    bigint           not null
        primary key,
    quantidade            double precision not null,
    nota_fiscal_compra_id bigint           not null
        constraint nota_fiscal_compra_fk
            references nota_fiscal_compra,
    produto_id            bigint           not null
        constraint produto_fk
            references produto
);

alter table nota_item_produto
    owner to postgres;

create table usuario
(
    id               bigint       not null
        primary key,
    data_atual_senha date         not null,
    login            varchar(255) not null
        constraint ukpm3f4m4fqv89oeeeac4tbe2f4
            unique,
    senha            varchar(255) not null,
    pessoa_id        bigint       not null
);

alter table usuario
    owner to postgres;

create table usuarios_acesso
(
    usuario_id bigint not null
        constraint usuario_fk
            references usuario,
    acesso_id  bigint not null
        constraint uk8bak9jswon2id2jbunuqlfl9e
            unique
        constraint acesso_fk
            references acesso,
    constraint unique_acesso_user
        unique (usuario_id, acesso_id)
);

alter table usuarios_acesso
    owner to postgres;

create table venda_compra_loja_virtual
(
    id                   bigint         not null
        primary key,
    data_entrega         date           not null,
    data_venda           date           not null,
    dia_entrega          integer,
    valor_desconto       numeric(38, 2),
    valor_frete          numeric(38, 2) not null,
    valor_total          numeric(38, 2) not null,
    cupom_desconto_id    bigint
        constraint cupom_desconto_fk
            references cupom_desconto,
    endereco_cobranca_id bigint         not null
        constraint endereco_cobranca_fk
            references endereco,
    endereco_entrega_id  bigint         not null
        constraint endereco_entrega_fk
            references endereco,
    forma_pagamento_id   bigint         not null
        constraint forma_pagamento_fk
            references forma_pagamento,
    nota_fiscal_venda_id bigint         not null
        constraint ukc30agr0qfwruqaoymp4chk2h1
            unique
        constraint nota_fiscal_venda_fk
            references nota_fiscal_venda,
    pessoa_id            bigint         not null
);

alter table venda_compra_loja_virtual
    owner to postgres;

create table item_venda_loja
(
    id                           bigint           not null
        primary key,
    quantidade                   double precision not null,
    produto_id                   bigint           not null
        constraint produto_fk
            references produto,
    venda_compra_loja_virtual_id bigint           not null
        constraint venda_compra_loja_virtual_fk
            references venda_compra_loja_virtual
);

alter table item_venda_loja
    owner to postgres;

alter table nota_fiscal_venda
    add constraint venda_compra_loja_virtual_fk
        foreign key (venda_compra_loja_virtual_id) references venda_compra_loja_virtual;

create table status_rastreio
(
    id                           bigint not null
        primary key,
    centro_distribuicao          varchar(255),
    cidade                       varchar(255),
    estado                       varchar(255),
    status                       varchar(255),
    venda_compra_loja_virtual_id bigint not null
        constraint venda_compra_loja_virtual_fk
            references venda_compra_loja_virtual
);

alter table status_rastreio
    owner to postgres;

create function validachave() returns trigger
    language plpgsql
as
$$

declare existe INTEGER;

begin
    existe = (select count(1) from pessoa_fisica where id = new.pessoa_id);
    if(existe <= 0 ) then
        existe = (select count(1) from pessoa_pj where id = new.pessoa_id);
        if( existe <= 0) then
            raise exception 'Não foi encontrado  o ID ou PK da pessoa para realizar a associação';
        end if;
    end if;
    return new;
end;
$$;

alter function validachave() owner to postgres;

create trigger validarpessoainser
    before insert or update
    on avaliacao_produto
    for each row
execute procedure validachave();

create trigger validarpessoainser
    before insert or update
    on conta_pagar
    for each row
execute procedure validachave();

create trigger validarpessoainser
    before insert or update
    on conta_receber
    for each row
execute procedure validachave();

create trigger validarpessoainser
    before insert or update
    on endereco
    for each row
execute procedure validachave();

create trigger validarpessoainser
    before insert or update
    on nota_fiscal_compra
    for each row
execute procedure validachave();

create trigger validarpessoainser
    before insert or update
    on usuario
    for each row
execute procedure validachave();

create trigger validarpessoainser
    before insert or update
    on venda_compra_loja_virtual
    for each row
execute procedure validachave();

create function validachavefornecedor() returns trigger
    language plpgsql
as
$$

declare existe INTEGER;

begin
    existe = (select count(1) from pessoa_fisica where id = new.pessoa_fornecedor_id);
    if(existe <= 0 ) then
        existe = (select count(1) from pessoa_pj where id = new.pessoa_fornecedor_id);
        if( existe <= 0) then
            raise exception 'Não foi encontrado  o ID ou PK da pessoa para realizar a associação';
        end if;
    end if;
    return new;
end;
$$;

alter function validachavefornecedor() owner to postgres;

create trigger validarpessoainserforn
    before insert or update
    on avaliacao_produto
    for each row
execute procedure validachavefornecedor();

create trigger validarpessoainserforn
    before insert or update
    on conta_pagar
    for each row
execute procedure validachavefornecedor();

create trigger validarpessoainserforn
    before insert or update
    on conta_receber
    for each row
execute procedure validachavefornecedor();

create trigger validarpessoainserforn
    before insert or update
    on endereco
    for each row
execute procedure validachavefornecedor();

create trigger validarpessoainserforn
    before insert or update
    on nota_fiscal_compra
    for each row
execute procedure validachavefornecedor();

create trigger validarpessoainserforn
    before insert or update
    on usuario
    for each row
execute procedure validachavefornecedor();

create trigger validarpessoainserforn
    before insert or update
    on venda_compra_loja_virtual
    for each row
execute procedure validachavefornecedor();
