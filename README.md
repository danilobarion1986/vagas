# Considerações gerais

Esta API foi feita seguindo os critérios especificados. Os principais guias utilizados aqui foram simplicidade e manutenibilidade. Escreví o código inteiramente em inglês para evitar conflitos entre as palavras da própria linguagem Java e da API. Desta forma, todo código ou documentação escrita para o desenvolvedor está em inglês, e aquilo que é da API propriamente dita está em português (de acordo com a especificação). 

## Considerações sobre o problema de localização

Existe um pacote na solução chamado br.com.vagas.domain.tsp . TSP significa Travelling Salesman Problem, ou Problema do Caixeiro Viajante. Este é um problema NP-Difícil onde existem várias soluções disponíveis na Internet. No entanto, considerei os seguintes fatores:

- Tempo delimitado para entrega da solução;
- Simplicidade da solução; 
- Apresentar um código facilmente inteligível.

Assim sendo, resolví o problema da forma que me pareceu a mais simples (e também da mesma forma como eu resolveria se não tivesse o auxílio de um computador), ou seja, de forma recursiva. O código segue as opções disponíveis a partir de um ponto de partida e vai desta forma elaborando possíveis caminhos até o destino final. Uma vez estabelecidos os caminhos, calcula o custo de cada um e devolve aquele que tem o menor custo. Esta implementação está disponível na classe br.com.vagas.domain.tsp.StandardTSPEngine . 

Aproveitei e criei alguns decorators para a classe. Um dos requisitos do programa era o de realizar os cálculos de acordo com uma tabela de ranges, disponível na descrição do problema. Assim sendo, criei uma nova classe chamada br.com.vagas.domain.tsp.RangeOfValuesTSPEngine , que pode encapsular uma implementação da interface br.com.vagas.domain.tsp.TSPEngine (a classe mencionada anteriormente, StandardTSPEngine, é uma delas). Quando invocada, esta classe chama a classe subjacente para realizar o cálculo da distância e então, transforma o resultado de acordo com a tabela de ranges. 

Além disso, criei uma terceira implementação que realiza o cache dos cálculos. Assim sendo, quando duas invocações são feitas para o cálculo da distância entre ponto A e ponto B, então o sistema recupera do cache, ao invés de recalcular tudo. Acabei não utilizando esta implementação, mas está disponível de qualquer forma. 

## Considerações sobre a API

Como mencionado anteriormente, procurei escrever o código todo em inglês e deixar somente a API, propriamente dita, em português. Assim sendo, criei um pacote chamado br.com.vagas.ws.serialization onde estão presentes vários DTO's que realizam a tarefa de lidar com as particularidades dos web services. Além disso, todos os endpoints são aderentes à técnica HATEOAS - assim sendo, todos retornam links para os recursos criados e alguns outros extras. 

No pacote br.com.vagas.ws estão os web services propriamente ditos. Procurei implementá-los da maneira mais reduzida possível, concentrando alguns detalhes na classe br.com.vagas.ws.Services . Esta classe contém algumas facilidades para manipulação dos DTO's, tais como mapeamento do ID (mais detalhes adiante) e inclusão dos links HATEOAS. Além disso, é nesta classe que está presente a constante que determina a versão corrente da API. Considero que, se utilizo contêiners para servir as API's, então caso eu tenha uma modificação da API eu posso ter dois contêiners rodando em paralelo com versões diferentes da API e incluir regras no balanceador de carga para decidir para qual contêiner as requisições devem ser direcionadas.

Quanto aos ID's, entrei num pequeno dilema (recorrente em praticamente todas as implementações de API's). Servir os ID's como inteiros tem um problema fundamental de exposição dos dados internos: quem estiver interessado em números sobre o vagas.com.br poderia realizar uma requisição numa data 01/01/2019 e, depois , na data 31/01/2019. A partir da diferença de ID's , essa pessoa saberia então quantas novas vagas foram criadas no site neste período, trazendo esta exposição.

O ideal, neste caso, seria colocar um GUID como ID ou alguma outra forma não-sequencial. No entanto, o problema com estes é que a testabilidade é muito mais difícil (na construção dos testes integrados, assumí em alguns pontos que o ID seria "1" ou "2" - coisa que eu não poderia fazer caso os ID's fossem GUID's).

## Considerações sobre as regras de negócio

Procurei concentrar as regras num pacote br.com.vagas.domain , além dos seus sub pacotes. A idéia aqui foi ficar o mais próximo possível do Domain Driven Design.

As entidades (mapeamento para o banco de dados) aqui presentes realizam a tarefa de conter anotações de validação (API Java) e também algumas particularidades JSON. Este último foi feito também em nome da simplicidade: os DTO's presentes no pacote de serialização de dados encapsulam estas entidades para apresentação para o cliente. Assim sendo, não encontrei justificativa para realizar, digamos, uma transposição de dados de e para as entidades. Caso fosse necessário, poderia utilizar algum framework como Apache Commons BeanUtils, MapStruct ou Dozer. Mas não ví necessidade neste momento. 

Este pacote contém uma classe chamada HRService. É esta classe responsável por concentrar as chamadas para os repositórios das entidades e também por algumas regras de negócio. Por exemplo, é nela que é realizada a chamada para a implementação de TSPEngine. O conceito aqui seria de ter uma classe fiel ao conceito de serviços do DDD.

No entanto, uma pequena quebra ao modelo do DDD aqui são as interfaces presentes no pacote br.com.vagas.domain.repositories. A idéia destas interfaces seria conter algo mais próximo do conceito de repositórios do DDD; no entanto, como a lógica de negócios presentes nelas são extremamente simples, elas se parecem mais com os antigos DAO's do que com repositórios propriamente ditos. São escolhas; elas poderiam evoluir de alguma forma para serem repositórios um dia. 

## Testabilidade

Incluí testes tanto unitários quanto de integração. Os testes de integração são executados pela API do Spring Boot para isso, e eles realizam, por exemplo, a inclusão de vagas, pessoas e candidaturas. Daí então o teste realiza o rankeamento e verifica se a ordenação, por exemplo, está correta. 

Procurei não ser muito "purista" aqui também, ou seja, onde não há lógica de negócios, também não há testes. Não realizei testes de getters e setters, por exemplo, pois acredito que isto só poluiria o código. Talvez uma ferramenta de avaliação de cobertura de testes possa impactar neste ponto. 

Por fim, vale observar que costumo colocar os testes nos mesmos pacotes onde as classes a serem testadas estão (ou ao menos o ponto de entrada, nos casos dos testes de integração). Considero que isto ajuda a realizar a localização destas classes. 

Para fins de testes / documentação, também incluí uma collection do Postman no código. Esta collection contém, além dos exemplos de requisição, alguns testes simples para verificação dos dados e cabeçalhos Location. No entanto, estes testes são bem pouco extensivos, e a intenção da collection é mais auxiliar no uso das API's. 

## Execução 

Incluí dois scripts bash na raiz do código para auxiliar a execução. 

O primeiro deles é chamado *build* . Este script realiza o empacotamento do JAR em um *fat jar* (obrigado, Spring Boot!) e executa os testes de integração. Depois, ele insere o *fat jar* recém construído num contêiner Docker, de acordo com o Dockerfile presente também na raiz do projeto. 

Depois de construído o contêiner, o próximo script a ser executado é o *run*. Este script cria um contêiner com o banco de dados (ou apenas inicializa o contêiner, caso este script já tenha sido executado antes). Depois, ele pára e destrói o contêiner da aplicação (caso já tenha sido executado antes. Senão, apenas ignora estes passos) e reinicializa, realizando o link do contêiner da aplicação com o do banco de dados (criei um perfil específico no projeto para que isto funcione).


## Considerações finais

Conforme colocado no teste, eu me divertí bastante realizando este código :) Demorei um tanto para produção porque no começo avaliei algumas implementações diferentes do algoritmo do TSP, especialmente algoritmos genéticos e busca tabu. Conforme fui olhando, pensei no meu tempo e na simplicidade da solução. Joguei tudo fora e escreví meu próprio algoritmo :) Talvez não seja o mais performático, mas atende o problema e, no fim das contas, pensei em muita coisa daquilo que aprendí trabalhando em *startups*: o importante é primeiro ter o MVP, agregar valor. Conforme o problema for escalando, nós refazemos de forma a comportar melhor as novas necessidades. Foi daí que surgiu a idéia de criar o sistema já com interfaces, e a idéia provou ser boa pois posso ir agregando comportamentos diferentes à vontade - como a questão de tabelar os resultados e incluir num cache. 

Uma outra coisa que resolví incluir (mais por minha própria consciência!) foi o liquibase. Não conseguiria admitir produzir um projeto sem gerenciamento da versão do banco de dados. Mais uma vez, o Spring Boot também vem a calhar pois já tem uma integração nativa.

Agradeço muito a oportunidade!
