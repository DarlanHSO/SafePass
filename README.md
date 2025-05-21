# SafePass

Este é o repositório do app SafePass, um gerador de senhas desenvolvido como projeto para as disciplinas de Desenvolvimento Mobile e Engenharia de Software (Centro Universitário Facens).

O código em kotlin a ser analisado está no diretório *"SafePass-App/app/src/main/java/com/example/safepass"*. 
Na branch *master* está presente a prova de conceito da geração de senhas desenvolvida em python.

## Integrantes da equipe
César Augusto de Almeida - RA: 222909
Darlan Henrique de Souza Oliveira - RA: 211926
Gustavo Eiji Tamezava - RA: 222226

## Padrões de Projeto Utilizados

### Strategy

Foi utilizado o padrão Strategy na definição de múltiplas fontes de dados para geração de entropia da seed.  
A interface `FonteDado` define o contrato `obterDado()`, e diferentes estratégias são implementadas nas classes (ao final do projeto, serão 5 ao todo):
- `FonteLocal`
- `ApiClima`
- `ApiRandom`

As estratégias são selecionadas dinamicamente em `LogicaSenha.kt` de acordo com a complexidade configurada pelo usuário.

### SOLID

#### SRP – Single Responsibility Principle
Cada uma das seguintes classes possui responsabilidade única:
- `ConfigUsuario`: gerencia as configurações escolhidas pelo usuário.
- `GeradorSeed`: gera uma seed criptográfica a partir das fontes.
- [LogicaSenha.kt](https://github.com/usuario/SafePass-App/blob/master/app/src/main/java/com/example/safepass/LogicaSenha.kt)
: controla o processo de geração da senha com base na configuração.

#### OCP – Open/Closed Principle
A classe `GeradorSeed` trabalha com a interface `FonteDado`, permitindo a adição de novas fontes de dados (novas estratégias) sem modificar a lógica existente.

#### LSP – Liskov Substitution Principle
Todas as classes que implementam `FonteDado` (`FonteLocal`, `ApiClima`, `ApiRandom`) podem ser utilizadas de forma intercambiável sem quebrar a lógica.

