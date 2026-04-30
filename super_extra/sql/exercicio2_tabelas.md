CREATE TABLE cidades(
    id NUMERIC PRIMARY KEY,
    nome VARCHAR(50),
    uf VARCHAR(2)
);

CREATE TABLE enderecos(
    id NUMERIC PRIMARY KEY,
    cep VARCHAR(10),
    rua VARCHAR(100),
    numero NUMERIC,
    complemento VARCHAR(100),
    bairro VARCHAR(100),
    cida_id NUMERIC,
    CONSTRAINT fk_enderecos_cidades
    FOREIGN KEY(cida_id)
    REFERENCES cidades(id)
);

CREATE TABLE clientes(
    id NUMERIC PRIMARY KEY,
    nome VARCHAR(50),
    email VARCHAR(100),
    cpf VARCHAR(11),
    telefone VARCHAR(11),
    data_cadastro TIMESTAMP,
    ende_id NUMERIC,
    CONSTRAINT fk_clientes_enderecos
    FOREIGN KEY (ende_id)
    REFERENCES enderecos(id)
);