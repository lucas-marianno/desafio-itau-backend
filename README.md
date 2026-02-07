# Desafio Itaú Backend
Esta é a minha solução para o desafio de backend do Banco Itaú, que contempla 100% dos requisitos obrigatórios **e** opcionais.

Como o desafio estava bem estruturado e com objetivos claros, trabalhei usando a estratégia `TDD`: para cada requisito do projeto, primeiramente criei os testes que refletissem a regra; em seguida, implementava o requisito usando o teste como referência e objetivo. Eventualmente, quando encontrava `edge-cases`, os adicionava aos testes e, por sua vez, utilizava esses testes para garantir a qualidade do código durante o processo de implementação de novas funcionalidades ou até mesmo refatorações.

Como o desafio pedia que não fosse utilizado nenhum tipo de banco de dados e tudo fosse armazenado em memória, decidi criar uma classe chamada `TransactionRepository`. Ela armazena um objeto do tipo `Map<Long, Transaction>`, onde cada transação é guardada em sequência com um `Long` que atua como `primary_key`. Implementei também as funções de `CRUD`, de modo que essa classe pudesse emular em memória as funcionalidades de um banco de dados relacional, como o `H2` ou `PostgreSQL`.

Por se tratar de uma representação de um sistema bancário, deduzi que a precisão dos valores de cada transação fosse de importância sumária (assim como a veracidade e precisão das estatísticas). Deste modo, decidi armazenar todos os valores financeiros com o tipo `BigDecimal`, o que garante a exatidão dos valores sem a preocupação com erros do tipo `floating-point`.

Devido à minha decisão de utilizar o tipo `BigDecimal`, desenvolvi a classe `BigDecimalSummaryStatistics`. Ela se comporta de maneira idêntica à `DoubleSummaryStatistics`, porém garantindo precisão e evitando erros de ponto flutuante.

Ainda pensando na precisão dos dados, fiz uso do método de arredondamento de casas decimais `HALF_EVEN`, diminuindo assim erros cumulativos de arredondamento.

Um dos requisitos opcionais era a criação de um endpoint de saúde da aplicação. Decidi fazer uso da famosa stack `Prometheus + Grafana`, o que permitiu observar com facilidade as inúmeras métricas tanto da aplicação quanto do servidor em que ela está rodando — tudo isso através de um endpoint com interface gráfica amigável e dashboards.

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

Baixe esse repositório e execute na pasta raiz do projeto:
```bash
docker-compose up
```

## Como executar testes unitários e de integração
Você precisará ter o `JDK 25` instalado em sua máquina. O Maven wrapper já está incluso no projeto.

Baixe esse repositório e execute na raiz do projeto:
```bash
./mvnw test
```

Para executar e análisar os testes individualmente, abra o projeto na IDE ou editor de sua preferência e execute como de costume.
          
## Documentação da API
Primeramente, execute a aplicação e ela estará disponível em [http://localhost:8080/](http://localhost:8080/).

>Assim como já mencionado, esse projeto contempla 100% dos requisitos do desafio, portanto todos os endpoints são funcionais e são conforme listados no item `2.2` no arquivo `DESAFIO.md`. De qualquer modo, fica aqui um resumo dos endpoints principais.

###  Inserir transações
Insere uma transação no banco de dados  
endpoint: `POST` `/transacao`

exemplo de body: 
```json
{
    "valor": 123.45,
    "dataHora": "2020-08-07T12:34:56.789-03:00"
}
```

### Limpar transações
Apaga todas as transações do bando de dados.  
endpoint: `DELETE` `/transacao`

### Calcular estatísticas
Retorna as estatísticas das transações que ocorreram nos últimos 60 segundos (padrão)  
endpoint: `GET` `/estatistica`

Para outros intervalos de tempo, utilize o parâmetro `segundos=` + a quantidade de segundos desejada  
exemplo: `GET` `/estatistica?segundos=999999999`

## Endpoints opcionais (saúde e observabilidade)

### Receber dados prometheus
Recebe dados brutos diretamente do Micrometer-Prometheus  
endpoint: `GET` `/actuator/prometheus`

### Painel Grafana
O painel admin Grafana pode ser acessado em [http://localhost:3000/](http://localhost:3000/).

login:`admin`  
senha:`admin`

>A senha de acesso pode ser alterada em `docker-compose.yml`

Um dash-board contendo as estatísticas do spring-boot e métricas dos endpoints está disponível em [http://localhost:3000/d/OS7-NUiGz/spring-boot-statistics-and-endpoint-metrics](http://localhost:3000/d/OS7-NUiGz/spring-boot-statistics-and-endpoint-metrics)


