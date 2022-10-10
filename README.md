# desafio tds
 
Api de um encurtador de Urls desenvolvido com java e o framework Spring.
 
## Abordagem para solução do desafio
 
Para a realização do encurtamento das urls foi implementado uma função de codificação por base 62 que recebe um número inteiro e gera uma string em base62 correspondente. O uso dessa estratégia com a aplicação de um contador que incrementa cada vez que uma nova url encurtada é gerada consegue garantir que nunca 2 urls diferentes vão receber o mesmo mapeamento, garantindo a integridade dos dados. esse comportamento também pode ser aproveitado na necessidade de escalabilidade horizontal da aplicação, já que em um contexto onde vários servidores estarão usando o mesmo código para gerar os encurtamentos seria possível utilizar um serviço de orquestração entre eles que atribuiria para cada instância um range de inteiros diferentes, garantindo a consistência dos dados também entre os diversos processos simultâneos.
 
## Endpoints da Api
 
- (get) /:shortUrl
    - Endpoint que recebe um uma url encurtada e faz o redirecionamento para a url original
 
- (post) /
    - Endpoint que recebe uma URL e devolve para o cliente uma url encurtada correspondente a ele
 
- (get) /analytics
    - Endpoint que retorna alguma informações gerais de estatísticas da aplicação
 
- (get) /analytics/specific
    - Endpoint que recebe por parâmetros uma Url ou uma url encurtada e devolve algumas informações sobre ela
 
 
## modelagem de dados
Para o funcionamento do encurtamento foi optado por utilizar uma collection de banco de dados mongoDb qie vai armazenar o mapeamento de uma Url original para uma Url encurtada e alguma meta informações sobre cada mapeamento
- modelo da entidade UrlMap utilizada na aplicação:
```
{
    url: string, (indexed, unique)
    shortUrl: string,    (indexed, unique)
    creationDate: LocalDateTime,
    lastAccessDate: LocalDateTime,
    numberOfAccesses: int,
    numberOfCreationRequests: int,
}
```
## Fluxos de comportamento dos endpoints
 
- (get) /:shortUrl
    - Recebe uma string correspondente a uma short url
    - checa no banco de dados se existe algum shortId correspondete ao recebido
    - se encontrar no banco de dados:
        - Incrementa o valor de number of accesses do item encontrado
        - Atualiza o campo LastAccessDateTime do item encontrado
        - retorna para o usuário uma resposta 302 FOUND informando o redirecionamento para a url original
    - se não encontrar um docmento correspondente no banco:
        - retornar para o usuário 404 NOT FOUND
 
- (post) /
    - O endpoint recebe uma url nos parametros da requisição
    - Checa se a url recebida está no formato de uma url válida
    - se a url não for válida:
        - retorna para o usuário o erro 400 bad request informando que a url informada não é válida
    - se for uma url válida:
        - checa se já existe um documento no banco de dados com a url recebida
        - se já existir no banco de dados:
            - incrementa o campo numberOfCreationRequets do item
            - retorna o link encurtado para o usuário
        - se ainda não existir no banco de dados:
            - gera um novo shortUrl para a Url recebida
            - salva o novo item no banco de dados
            - retorna o link encurtado gerado para o cliente
 
 
- (get) /analytics
    - Faz as queries necessárias do banco de dados para montar a resposta
    - retorna um objeto Json com os dados buscados no banco de dados
 
- (get) /analytics/specific
    - pode receber uma url ou uma url encurtada pelos parâmetros da requisição, ou ambos(priorizar um deles)
    - se os dois parâmetros esperados estiverem vazios:
        - Retorna para o usuário erro 400 BAD REQUEST
    - se pelo menos um dos parâmetros:
        - pesquisa no banco de dados as informações do item:
        - se não encontrar o item no banco:
            - retorna 404 NOT FOUND
        - se encontrar o item:
            - armazena os campos que devem ser retornado em um objeto
            - retorna para o usuário as informações buscadas no banco em formato JSON