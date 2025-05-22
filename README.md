# SafePass

Este é o repositório do app SafePass, um gerador de senhas desenvolvido como projeto para as disciplinas de Desenvolvimento Mobile e Engenharia de Software (Centro Universitário Facens).

O código em Kotlin a ser analisado está no diretório ["SafePass-App/app/src/main/java/com/example/safepass"][link-pasta].  
Na branch *master* está presente a prova de conceito da geração de senhas desenvolvida em Python.

## Integrantes da equipe
- **César Augusto de Almeida** - RA: 222909  
- **Darlan Henrique de Souza Oliveira** - RA: 211926  
- **Gustavo Eiji Tamezava** - RA: 222226  

## Padrões de Projeto Utilizados

### Strategy

Foi utilizado o padrão Strategy na definição de múltiplas fontes de dados para geração de entropia da seed.  
A interface [`FonteDado`][link-fonteDado] define o contrato `obterDado()`, e diferentes estratégias são implementadas nas classes (ao final do projeto, serão 5 ao todo):  
- [`FonteLocal`][link-fonteLocal]  
- [`ApiClima`][link-apiClima]  
- [`ApiRandom`][link-apiRandom]  

As estratégias são selecionadas dinamicamente em [`LogicaSenha.kt`][link-logicaSenha] de acordo com o "nível de irrastreabilidade" configurado pelo usuário.

### SOLID

#### SRP – Single Responsibility Principle
Cada uma das seguintes classes possui responsabilidade única:  
- [`ConfigUsuario`][link-configUsuario]: gerencia as configurações escolhidas pelo usuário.  
- [`GeradorSeed`][link-geradorSeed]: gera uma seed criptográfica a partir das fontes.  
- [`LogicaSenha.kt`][link-logicaSenha]: controla o processo de geração da senha com base na configuração.  

#### OCP – Open/Closed Principle
A classe [`GeradorSeed`][link-geradorSeed] trabalha com a interface [`FonteDado`][link-fonteDado], permitindo a adição de novas fontes de dados (novas estratégias) sem modificar a lógica existente.

#### LSP – Liskov Substitution Principle
Todas as classes que implementam [`FonteDado`][link-fonteDado] ([`FonteLocal`][link-fonteLocal], [`ApiClima`][link-apiClima], [`ApiRandom`][link-apiRandom]) podem ser utilizadas de forma intercambiável sem quebrar a lógica.

## Implementações de acordo com o Jira (21/05)
- A construção da interface foi realizada de acordo com o planejado;
- A prova de conceito da geração de senhas foi desenvolvida em python, seguindo os requisitos estabelecidos nas RNs e suas respectivas histórias;
- Tradução do código em python da geração de seeds e senha para kotlin foi realizada (antes da refatoração do código, o que poderia ter ocorrido depois para ter sido um trabalho mais suave);
- Refatoração do código de funcionamento das *activites* do app para se adequar aos padrões SOLID e Strategy;
- Inclusão de 2 APIs para obtenção de dados para a geração de seeds, totalizando 3 níveis de irrastreabilidade (dados locais do dispositivo inclusos). Isso é um ponto de atraso em relação ao planejamento, que originalmente previa a inclusão de 4 APIs e 5 Níveis de Irrastreabilidade nesse ponto do desenvolvimento


  ---

[link-pasta]: https://github.com/DarlanHSO/SafePass/blob/main/SafePass-App/app/src/main/java/com/example/safepass  
[link-configUsuario]: https://github.com/DarlanHSO/SafePass/blob/main/SafePass-App/app/src/main/java/com/example/safepass/ConfigUsuario.kt  
[link-geradorSeed]: https://github.com/DarlanHSO/SafePass/blob/main/SafePass-App/app/src/main/java/com/example/safepass/GeradorSeed.kt  
[link-logicaSenha]: https://github.com/DarlanHSO/SafePass/blob/main/SafePass-App/app/src/main/java/com/example/safepass/LogicaSenha.kt  
[link-fonteDado]: https://github.com/DarlanHSO/SafePass/blob/main/SafePass-App/app/src/main/java/com/example/safepass/FonteDado.kt  
[link-fonteLocal]: https://github.com/DarlanHSO/SafePass/blob/main/SafePass-App/app/src/main/java/com/example/safepass/FonteLocal.kt  
[link-apiClima]: https://github.com/DarlanHSO/SafePass/blob/main/SafePass-App/app/src/main/java/com/example/safepass/ApiClima.kt  
[link-apiRandom]: https://github.com/DarlanHSO/SafePass/blob/main/SafePass-App/app/src/main/java/com/example/safepass/ApiRandom.kt  
