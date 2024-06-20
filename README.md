<div align="center" style={{ marginTop: 16 }}>
  <h2 style={{ margin: 0 }} align="center">
    Integração de Sistemas: Transformação de Arquivos Desnormalizados para JSON
  </h2>
</div>

## <a id="-sobre-o-projeto"></a> Sobre o projeto

### <a id="-o-que-é"></a> O que é?

Este projeto tem como objetivo resolver a demanda de integração entre dois sistemas distintos. Um dos sistemas é legado e possui arquivos de pedidos desnormalizados. A tarefa principal é transformar esses arquivos em um formato JSON normalizado, permitindo assim a integração e atendimento dos requisitos do novo sistema.

### <a id="️-linguagem"></a> Linguagem
Optei por utilizar Kotlin neste projeto devido às suas diversas vantagens que se alinham perfeitamente com os requisitos da arquitetura e objetivos de desenvolvimento. Kotlin é uma linguagem moderna, concisa e interoperável com Java, o que facilita a integração com bibliotecas e frameworks já existentes. Sua sintaxe clara e expressiva ajuda a reduzir erros de codificação, aumentando a produtividade dos desenvolvedores. Além disso, Kotlin oferece suporte robusto para programação funcional e orientada a objetos, tornando-o ideal para a implementação dos princípios de DDD, SOLID e Clean Architecture. A escolha de Kotlin permite construir uma aplicação robusta, de fácil manutenção e escalável.

### <a id="-arquitetura"></a> Arquitetura

A arquitetura do projeto é baseada nos conceitos de **DDD** (Domain Driven Design), **SOLID** e **Clean Architecture**. A escolha se deu por ser uma arquitetura que permite a criação de aplicações escaláveis, de fácil manutenção e que permite a criação de testes automatizados. Além disso, a arquitetura permite a criação de aplicações com baixo acoplamento e alta coesão.

## <a id="-tecnologias-usadas-na-api"></a> Tecnologias usadas na API

- [Kotlin](https://kotlinlang.org/) - Linguagem de programação moderna e concisa, totalmente interoperável com Java.
- [Spring](https://spring.io/) - Framework robusto para desenvolvimento de aplicações Java e Kotlin, oferecendo diversas funcionalidades.
- [Flyway](https://flywaydb.org/) - Ferramenta de migração de banco de dados que permite versionar e aplicar mudanças no esquema de forma controlada.
- [Postgres](https://www.postgresql.org/) - Sistema de gerenciamento de banco de dados relacional avançado e de código aberto.
- [Insomnia](https://insomnia.rest/) - Ferramenta para testar APIs, facilitando a criação, gestão e teste de requisições HTTP.

## <a id="-como-executar-o-projeto"></a> Como executar o projeto

### <a id="-pré-requisitos"></a> Pré-requisitos

Para executar o projeto, é necessário ter o Java 21 instalado.Seguem os passos para instalação e execução do projeto:
1. Instalação do Java
```bash
# Verifique se o Java já está instalado
$ java -version
```

2. Se o Java não estiver instalado, faça o download do [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) e siga as instruções de instalação para o seu sistema operacional.

### <a id="-clonando-o-repositório"></a> Passos para Executar o Projeto

```bash
# Clone o repositório
$ git clone https://github.com/laryssa-serra/desafio

# Acesse a pasta do projeto
$ cd desafio

# Instale as dependências do projeto:
$ ./gradlew build
```

### <a id="bootrun"></a> Inicie a aplicação:
```bash
$ ./gradlew bootRun
```
Pronto, a aplicação web está pronta para ser utilizada no endereço http://localhost:8080

## <a id="endpoints"></a> Endpoints

| Método  | Endpoint                 | Descrição                           | 
| ------- | -------------------      | ----------------------------------- | 
| POST    |   api/v1/orders/imports  | Import de arquivo                   |
| GET     |     api/v1/customers     | Busca de todos os customers na base |

## <a id="-como usar"></a> Como usar a aplicação
- Use o endpoint `POST /api/v1/orders/import` para importar o arquivo com o seguinte formato: `.txt`.
  
  Exemplo de como devem ser os dados do arquivo: data_1.txt
  
`
0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308
`
- Use o endpoint `GET /api/v1/customers` para listar todos os usuários.

 É possível utilizar alguns query params:

 - `order_id` -> Filtrar customers pelo order_id. Exemplo: `GET /api/v1/customers?order_id=27`

 - `date_form` -> Filtrar customers por uma data específica. Exemplo: `GET /api/v1/customers?date_from=2021-12-24`

 - `date_to` -> Filtrar customers por uma data final específica. Exemplo: `GET /api/v1/customers?date_to=2021-12-27`

 - `date_form` e `date_to` -> Filtrar customers em um intervalo de data.  Exemplo: `GET /api/v1/customers?date_from=2021-12-24&date_to=2021-12-27`
 
 - `date_form`, `date_to` e `order_id`, -> Filtrar customers com um order_id e intervalor de datas especificos. Exemplo: `GET /api/v1/customers?date_from=2021-12-24&date_to=2021-12-27&order_id=27`


## <a id="-testes"></a> Testes

### <a id="️-testes-unitários"></a> Testes unitários

Para executar os testes unitários, execute o seguinte comando:

```bash
# Execute os testes unitários
$ ./gradlew test
```
