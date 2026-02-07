# Desafio Itaú Backend
Essa é a minha solução para o desafio de backend do banco itaú que contempla 100% dos requisitos obrigatórios **E** opcionais.

Como o desafio estava bem estruturado e com objetivos claros. Trabalhei usando a estratégia `TDD`: Para cada requisito do projeto, eu primeiramente criei os testes que refletissem o requisito, então eu implementava o requisito usando o teste como referência e objetivo. Eventualmente, quando enccontrava `edge-cases`, os adicionava aos testes, e por sua vez utilizava os testes para garantir a qualidade do código durante o processo de implementação de novas funcionalidades ou até mesmo refatorações.

Como o desafio pedia que não fossem utilizados nenhum tipo de banco de dados e tudo fosse manualmente armazenado em memória. Decidi criar uma classe chamada `TransactionRepository` que armazena um objeto to tipo `Map<Long,Transaction>`, onde cada transação é armazenada em sequencia com um `Long` que atua como `private_key`. Implementei também as funções de `CRUD`, de modo que essa classe pudesse emular em memória as funcionalidades de um banco de dados relacional tipo `H2` ou `postgreSQL`.

Por se tratar de uma representação de um sistema bancário, deduzi que a precisão dos valores de cada transação fosse de importância sumária! (assim como a veracidade e precisão das estatísticas). Deste modo, decidi armazenar todos os valores financeiros com o tipo `BigDecimal`, o que garante a exatidão dos valores sem se preocupar com erros do tipo `floating-point`.

Devido a minha decisão de utilizar o tipo `BigDecimal`, eu fui obrigado a desenvolver `BigDecimalSummaryStatistics`. Que se comporta de maneira idêntica a `DoubleSummaryStatistics`, porém garantindo precisão e evitando erros `floating-point`.

Ainda pensando na precisão dos dados, fiz uso do método de arredondamento de casas decimais `HALF_EVEN`, diminuindo assim erros cumulativos de arredondamento.

Um dos requisitos opcionais era que fosse criado um endpoint de saúde da aplicação. Decidi fazer uso da famosa stack `Prometheus + Grafana`, o que permitiu com facilidade observar as inúmeras métricas tanto da aplicação, quanto métricas do servidor em que a aplicação está rodando. Tudo isso atraves de uma endpoint com interface gráfica amigável e dash-boards.

Para reduzir a verbosidade do cógigo, utilizei a ferramenta `Lombok` para gerar código `boiler-plate`.

Para facilitar o deploy da aplicação e garantir que funcionasse em qualquer contexto, fiz uso do `Docker` e `Docker Compose`.

## Tecnologias utilizadas:
- Java 25
- Maven
- Spring Boot 4.0.1
- Spring Boot WEB
- Spring Boot Actuator
- Micrometer-Prometheus
- Grafana
- Lombok
- Docker
- Docker Compose

## Como executar a aplicação
Você precisará ter instalado `docker` e `docker-compose` em sua máquina.

- Baixe esse repositório e execute na pasta raiz do projeto:
  ```bash
  docker-compose up
  ```

## Como executar testes unitários e de integração
Você precisará ter o `JDK 25` instalado em sua máquina. O Maven wrapper já está incluso no projeto.
- Baixe esse repositório e execute na raiz do projeto:
  ```bash
  ./mvnw test
  ```

Para executar e análisar os testes individualmente, abra o projeto na IDE ou editor de sua preferência e execute como de costume.

## Documentação da API
Primeramente, execute a aplicação e ela estará disponível em [http://localhost:8080/](http://localhost:8080/).

### Endpoints base
Assim como já mencionado, esse projeto contempla 100% dos requisitos do desafio, portanto todos os endpoints são funcionais e são conforme listados no item `2.2` do desafio. De qualquer modo, fica aqui um resumo dos endpoints principais.


#### Inserir transações
Insere uma transação no banco de dados

endpoint: `POST` `/transacao`
exemplo de body: 
```json
{
    "valor": 123.45,
    "dataHora": "2020-08-07T12:34:56.789-03:00"
}
```

#### Limpar transações
Apaga todas as transações do bando de dados.

endpoint: `DELETE` `/transacao`

#### Calcular estatísticas
Retorna as estatísticas das transações que ocorreram nos últimos 60 segundos (padrão)
endpoint: `GET` `/estatistica`

Para outros intervalos de tempo, utilize o parâmetro `segundos=` + a quantidade de segundos desejada
exemplo: `GET` `/estatistica?segundos=999999999`

### Endpoints opcionais (saúde e observabilidade)

#### Receber dados prometheus
Recebe dados brutos diretamente do Micrometer-Prometheus
endpoint: `GET` `/actuator/prometheus`

### Painel Grafana
O painel admin Grafana pode ser acessado em [http://localhost:3000/](http://localhost:3000/).

login:`admin`
senha:`admin`

>Observação: A senha de acesso pode ser alterada em `docker-compose.yml`

Um dash-board contendo as estatísticas do spring-boot e métricas dos endpoints está disponível em [http://localhost:3000/d/OS7-NUiGz/spring-boot-statistics-and-endpoint-metrics](http://localhost:3000/d/OS7-NUiGz/spring-boot-statistics-and-endpoint-metrics)


