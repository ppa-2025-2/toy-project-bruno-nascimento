## TODO

Eu tenho algo implementado em https://github.com/ppa-2025-2/toy-cobaia/tree/orm
Não foi testado 🤷‍♂️

Sua tarefa é de implementar essa lógica no seu toy project de três maneiras:

[X] - Criar um IslandService e mover a lógica do alocarWorkstationDisponivel para lá.
Esse IslandService fará toda a lógica (ou seja, não há distinção se é um Application ou Domain Service)

[X] - Manter o IslandService como um ApplicationService e a lógica especificamente é para ser realizada em um DomainService, ambos para Island.
Crie pacotes para separar essas camadas.

[ ] - Manter o IslandService como um ApplicationService, que permite buscar e no fim persistir,
mas a lógica de alocação do usuário ficará nas entidades island e workstation.
Isso é uma abordagem domain drive.

O CONTROLLER pode ser um só, que invoca o caso de uso direto no Application Service. Não esqueça do endpoint e das requisições de teste :)



