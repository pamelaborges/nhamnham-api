Api do serviço de cardápio
=======
# nhamnham-api project

Projeto criado para realizar estudos do Framework Quarkus Framework https://quarkus.io/.

### Tecnologias utilizadas:


Banco de dados: MongoDB https://cloud.mongodb.com/.

Implementação JPA: Hibernate ORM Panache mongodb https://quarkus.io/guides/mongodb-panache, na qual foi utilizado principalmente o recurso Active Record com o objetivo de explorar os recursos disponíveis.

Autorização/Permissionamento: JWT com uso da biblioteca `smallrye-jwt` https://quarkus.io/guides/security-jwt

Especificação: Utilizando biblioteca `quarkus-smallrye-openapi` para gerar interface de teste e mapeamento das assinaturas das apis https://quarkus.io/guides/openapi-swaggerui    
    
Testes: `quarkus-panache-mock` necessário para mockar metodos estaticos do PanacheEntityBase, `quarkus-junit5-mockito` e `org.testcontainers` para criação de banco mongodb em container para execução exclusiva dos testes em tempo de execução.

Docker : in progress

## Rodando aplicação em modo desenvolvedor


```shell script
./mvnw compile quarkus:dev
```


##Criando e executando imagem nativa


```shell script
./mvnw package -Pnative
```
