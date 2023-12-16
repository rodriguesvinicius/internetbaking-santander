# Santander Internet Banking API

## Descrição

Este projeto é uma API de Internet Banking desenvolvida em Java com Spring Boot, seguindo os princípios de Clean Architecture. Criado como desafio do Santander, a aplicação permite que os usuários realizem operações bancárias básicas, como criar uma conta, adicionar ou retirar dinheiro.

## Funcionalidades

- **Registro de Conta**: Os usuários podem criar uma conta no Internet Banking.

- **Movimentação Financeira**: Realize operações bancárias, como adicionar ou retirar dinheiro da conta.

## Clean Architecture

O projeto segue os princípios da Clean Architecture, organizando o código em camadas para facilitar a manutenção, teste e evolução do sistema. As camadas incluem:

- **Entidades**: Representações de objetos de negócio.

- **Casos de Uso**: Regras de negócio e lógica de aplicação.

- **Controladores (Controllers)**: Exposição da API REST.

- **Serviços**: Implementação da lógica de aplicação.

- **Repositórios**: Acesso aos dados.

## Tecnologias Utilizadas

- Spring Boot: Framework Java para o desenvolvimento de aplicativos web.
- Banco de Dados Relacional (por exemplo, MySQL): Armazenamento seguro de informações do usuário.

## Pré-requisitos

- Java Development Kit (JDK)
- Maven (ou outra ferramenta de construção)
- Banco de Dados configurado (por exemplo, MySQL)

## Configuração

1. Clone este repositório:

    ```bash
    git clone https://github.com/rodriguesvinicius/santander-internet-banking-api.git
    ```

2. Importe o projeto em sua IDE Java.

3. Configure as dependências, incluindo as informações do banco de dados, no arquivo de configuração.

## Utilização

1. Execute a aplicação.

2. Utilize as APIs expostas para criar contas e realizar movimentações financeiras.

## Contribuindo

Sinta-se à vontade para contribuir com melhorias, correções de bugs ou adição de novas funcionalidades. Abra uma issue ou envie um pull request para discutir e colaborar.

Esperamos que o Santander Internet Banking API seja útil e atenda aos requisitos de desafio propostos. Em caso de dúvidas ou sugestões, entre em contato conosco!
