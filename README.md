<div align="center" style={{ marginTop: 16 }}>
  <h2 style={{ margin: 0 }} align="center">
    Integração de Sistemas: Transformação de Arquivos Desnormalizados para JSON
  </h2>
</div>

## Sobre o projeto

### O que é?

Este projeto visa solucionar a demanda de integração entre dois sistemas distintos. Um dos sistemas é legado e possui arquivos de pedidos desnormalizados. A tarefa principal é transformar esses arquivos em um formato JSON normalizado, permitindo assim a integração e atendimento dos requisitos do novo sistema.

### Linguagem
Optei por utilizar Kotlin neste projeto devido às suas diversas vantagens que se alinham perfeitamente com os requisitos da arquitetura e objetivos de desenvolvimento. Kotlin é uma linguagem moderna, concisa e interoperável com Java, o que facilita a integração com bibliotecas e frameworks já existentes. Sua sintaxe clara e expressiva ajuda a reduzir erros de codificação, aumentando a produtividade dos desenvolvedores. Além disso, Kotlin oferece suporte robusto para programação funcional e orientada a objetos, tornando-o ideal para a implementação dos princípios de DDD, SOLID e Clean Architecture. A escolha de Kotlin permite construir uma aplicação robusta, de fácil manutenção e escalável.

### Arquitetura

A arquitetura do projeto é baseada nos conceitos de **DDD** (Domain Driven Design), **SOLID** e **Clean Architecture**. A escolha se deu por ser uma arquitetura que permite a criação de aplicações escaláveis, de fácil manutenção e que permite a criação de testes automatizados. Além disso, a arquitetura permite a criação de aplicações com baixo acoplamento e alta coesão.

## Tecnologias usadas no desenvolvimento da API

- [Kotlin](https://kotlinlang.org/) - Linguagem de programação moderna e concisa, totalmente interoperável com Java.
- [Spring](https://spring.io/) - Framework robusto para desenvolvimento de aplicações Java e Kotlin, oferecendo diversas funcionalidades.
- [Flyway](https://flywaydb.org/) - Ferramenta de migração de banco de dados que permite versionar e aplicar mudanças no esquema de forma controlada.
- [Postgres](https://www.postgresql.org/) - Sistema de gerenciamento de banco de dados relacional avançado e de código aberto.

## Como rodar a aplicação

### Pré-requisitos

#### Java
Para rodar a aplicação, é necessário ter no mínimo o JDK 21 instalado.
- Verificar Instalação do Java
```bash
# Verifique se o Java 21 (ou superior) já está instalado
$ java -version
```
Se a versão retornada for 21 ou superior, você está pronto para seguir para a próxima etapa. Caso contrário, siga a instrução abaixo para instalar o JDK 21.

- Download e Instalação do Java 21
   
Faça o download do JDK 21 a partir do [site oficial da Oracle](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) e siga as instruções de instalação para o seu sistema operacional.

#### Banco de Dados
- Configuração do Banco de Dados

Para rodar a aplicação, é necessário ter o banco de dados PostgreSQL disponível localmente com as seguintes configurações:

- Nome do banco de dados: `magazine_dev`
- Usuário: `postgres`
- Senha: `postgres`

<b>Instalação do PostgreSQL</b>

Caso ainda não tenha o PostgreSQL instalado, você pode instalá-lo de duas maneiras:

- <b>Instalação Local</b>:
Para instalar o PostgreSQL localmente, siga as instruções disponíveis no site oficial: [Instalar PostgreSQL](https://www.postgresql.org/download/).

- <b>Instalação via Docker</b>:
Se preferir usar Docker, execute o comando abaixo para criar um container chamado `magazine-challenge-postgresql` com o PostgreSQL configurado corretamente para utilizar a aplicação:
```bash
$ docker run --name magazine-challenge-postgresql -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=magazine_dev -p 5432:5432 -d postgres
```
 
- Para inicializar o sistema gerenciador de banco de dados (PostgreSQL):
```bash
$ docker start magazine-challenge-postgresql
```

### Build da aplicação

```bash
# Clone o repositório
$ git clone https://github.com/laryssa-serra/desafio

# Acesse a pasta do projeto
$ cd desafio

# Instale as dependências do projeto:
$ ./gradlew build
```

### Inicie a aplicação:
```bash
$ ./gradlew bootRun
```
Pronto, a aplicação web está pronta para ser utilizada no endereço http://localhost:8080

## <a id="endpoints"></a> Endpoints

| Método  | Endpoint                 | Descrição                           | 
| ------- | -------------------      | ----------------------------------- | 
| POST    |   api/v1/orders/imports  | Importa arquivo de pedidos          |
| GET     |   api/v1/customers       | Busca customers na base             |

## <a id="-como usar"></a> Como usar a aplicação

### Importar Arquivo

Use o endpoint `POST /api/v1/orders/imports` para importar um arquivo com o seguinte formato: .txt.
  
<b>Estrutura do Arquivo de Dados</b>
  
`
0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308
`

O arquivo de dados segue uma estrutura específica onde cada segmento de caracteres representa uma informação distinta. Abaixo está a descrição detalhada de cada segmento:

- `userId`: Os primeiros 10 caracteres representam o ID do cliente.
- `userName`: Os próximos 45 caracteres representam o nome do cliente.
- `orderId`: Os próximos 10 caracteres representam o ID do pedido.
- `productId`: Os próximos 10 caracteres representam o ID do produto.
- `value`: Os próximos 12 caracteres representam o valor do produto.
- `date`: Os próximos 8 caracteres representam a data da compra.

<b>Respostas da Requisição</b>

Ao realizar uma requisição para a API, você pode esperar os seguintes códigos de status HTTP como resposta:
- `Sucesso (201 Created)`: Indica que a requisição foi bem-sucedida e o recurso foi criado com sucesso.
- `Arquivo Já Importado (201 Created)`: Se o arquivo enviado na requisição já tiver sido importado anteriormente, ele não será importado novamente. No entanto, a resposta ainda retornará um status 201 Created para indicar que a operação foi recebida e processada corretamente.
- `Erro Interno do Servidor (500 Internal Server Error)`: Indica que houve um problema no servidor ao processar a requisição.

### Listar Customers

Use o endpoint `GET /api/v1/customers` para listar customers. É possível utilizar (e combinar) os query params:

 - `order_id` -> Filtrar customers pelo order_id. Exemplo: `GET /api/v1/customers?order_id=27`

 - `date_from` -> Filtrar customers por uma data inicial específica. Exemplo: `GET /api/v1/customers?date_from=2021-12-24`

 - `date_to` -> Filtrar customers por uma data final específica. Exemplo: `GET /api/v1/customers?date_to=2021-12-27`

<b>Funcionamento dos Filtros do Endpoint:</b>

Os filtros do endpoint são projetados para selecionar usuários com base em critérios específicos aplicados aos seus pedidos (orders). É importante notar que:

- Retorno Completo dos Pedidos: Os filtros não removem pedidos do customer. Em vez disso, seleciona o customer que possui pelo menos um pedido que atende aos critérios do filtro, retornando todos os seus pedidos, mesmo aqueles que não atendem aos critérios.

Exemplo de Funcionamento do Filtro:

Suponha que um usuário tenha os seguintes pedidos: order_id 1, 2, 3, 4. Se for utilizado o filtro order_id = 2, o comportamento será o seguinte:

`Usuário Selecionado`: O usuário será incluído no resultado porque possui um pedido (order_id = 2) que atende ao critério do filtro.

`Retorno Completo`: Todos os pedidos do usuário (1, 2, 3, 4) serão retornados, e não apenas o pedido que atende ao critério do filtro.

Esse mecanismo garante que os filtros ajudem a identificar usuários relevantes sem excluir informações adicionais sobre seus pedidos.

<b>Respostas do Endpoint</b>

Ao fazer uma requisição GET para o endpoint, você pode receber as seguintes respostas:
- `Sucesso (200 OK)`: Quando a requisição é bem-sucedida, o servidor retorna uma resposta com o status 200 OK. A resposta inclui os dados solicitados.
- `Erro Interno do Servidor (500 Internal Server Error)`: Se ocorrer um problema no servidor ao processar a requisição, uma resposta com o status 500 Internal Server Error será retornada. Isso indica que algo deu errado no lado do servidor.

## Testes

### Testes unitários com coverage

Para executar os testes unitários com coverage e gerar um relatório HTML, foram adicionadas as dependências do Kover no projeto.
```
# Executa os testes unitários com coverage e gera um relatório HTML
$ ./gradlew koverHtmlReport
```

Após a execução, o caminho para o relatório gerado será exibido no terminal. O relatório HTML fornece uma visualização detalhada da cobertura dos testes, ajudando a identificar áreas do código que necessitam de mais testes.
