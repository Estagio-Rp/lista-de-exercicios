<h1 align="center">Super Extra - Sistema de Loja em Java</h1>

<p align="center">
  Projeto em Java com PostgreSQL, JDBC e Maven, refatorado em camadas inspiradas na Clean Architecture.
</p>

---

## Sobre o projeto

Sistema de loja desenvolvido em Java para praticar operações CRUD, integração com banco de dados e refatoração de código.

O sistema funciona via console e permite gerenciar:

- Produtos
- Cidades
- Endereços
- Clientes

O projeto foi refatorado para separar melhor as responsabilidades entre as camadas de controller, use case, service, DTO, DAO e DAOImpl.

---

## Tecnologias utilizadas

<div align="center">

![Java](https://img.shields.io/badge/%20Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/%20Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/%20PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/%20IntelliJ%20IDEA-000000?style=for-the-badge&logo=intellijidea&logoColor=white)
![Git Extensions](https://img.shields.io/badge/%20Git%20Extensions-4B4B4B?style=for-the-badge)
![GitHub](https://img.shields.io/badge/%20GitHub-181717?style=for-the-badge&logo=github&logoColor=white)

</div>

---

## Funcionalidades

### Produtos

- Cadastrar produto
- Listar produtos
- Editar produto
- Excluir produto

### Cidades

- Cadastrar cidade
- Listar cidades
- Editar cidade
- Excluir cidade

### Endereços

- Cadastrar endereço
- Listar endereços
- Editar endereço
- Excluir endereço

### Clientes

- Cadastrar cliente
- Listar clientes
- Editar cliente
- Excluir cliente

---

## Estrutura em camadas

O projeto foi organizado seguindo uma estrutura inspirada na Clean Architecture:

```text
Main
  ↓
Controller
  ↓
UseCase
  ↓
Service
  ↓
DAO
  ↓
DAOImpl
  ↓
Banco de Dados
```

## Estrutura de pacotes
```text
super_extra/
├── src/
│   └── main/
│       ├── java/
│       │   └── br/com/rpinfo/analuisa/
│       │       ├── adapter/
│       │       │   └── rest/
│       │       │       └── controller/
│       │       ├── application/
│       │       │   ├── dto/
│       │       │   ├── service/
│       │       │   └── usecase/
│       │       ├── domain/
│       │       │   ├── exceptions/
│       │       │   ├── model/entity/
│       │       │   └── repositories/
│       │       ├── shared/
│       │       ├── Conexao.java
│       │       └── Main.java
│       └── resources/
│           └── application.properties
├── sql/
└── pom.xml
```

## Menu principal

```text
=== LOJA - MENU PRINCIPAL ===
1. Gerenciar Produtos
2. Gerenciar Cidades
3. Gerenciar Endereços
4. Gerenciar Clientes
0. Sair
Escolha uma opção:
```
---

## Banco de dados
O projeto utiliza **PostgreSQL**.

As tabelas principais são:

- produtos
- cidades
- enderecos
- clientes