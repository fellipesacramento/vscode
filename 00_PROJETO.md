# VSCode — Entrada do Projeto


## Finalidade


Este arquivo é o ponto de entrada rápido para o projeto **VSCode**. Ele não substitui os arquivos de configuração existentes: sua função é indicar onde está a fonte atual, como localizar a instalação e o workspace e qual regra seguir para evitar confundir arquivos do programa com arquivos de trabalho.


## Fonte oficial do projeto


- **Fonte operacional canônica atual:** Google Drive
- **Caminho lógico:** `Meu Drive / git / desenvolvimento / vscode`
- **Caminho exposto ao ChatGPT:** `/Google Drive/git/desenvolvimento/vscode`
- **Google Drive Folder ID:** `1zpMdvFA8O56iKZBBqq7Y_z0trx5Rjrk9`
- **URL:** https://drive.google.com/drive/folders/1zpMdvFA8O56iKZBBqq7Y_z0trx5Rjrk9


### Hierarquia obrigatória


```text
Meu Drive
└── git
    └── desenvolvimento
        └── vscode
            ├── VSCode-win32-x64-1.136.2/
            └── workspace_vscode/
```


A pasta `vscode` deve ser tratada como o **ponto de entrada do projeto**. Não pesquisar por pastas homônimas espalhadas pelo Drive quando o Folder ID oficial estiver disponível.


## Áreas principais


### Instalação portátil do VSCode


- **Pasta:** `VSCode-win32-x64-1.136.2/`
- **Folder ID:** `1YXTCDJBvmHeCNu6O0TwqbaL3L7zoy-F_`
- **Finalidade:** arquivos da instalação portátil do Visual Studio Code, executável, recursos internos, políticas e componentes do programa.
- **Exemplos atualmente visíveis:** `Code.exe`, `bin/`, `tools/`, `policies/`, `appx/`.


Use esta área quando a tarefa envolver a **instalação do VSCode**, atualização, componentes internos, plugins/extensões instalados no ambiente portátil ou configurações que pertençam à própria instalação.


### Workspace do VSCode


- **Pasta:** `workspace_vscode/`
- **Folder ID:** `1jV9MTTqFRvzGzf4tI2v1BV1PfoMgpFuc`
- **Finalidade:** áreas de trabalho e projetos abertos pelo VSCode.
- **Subpastas atualmente visíveis:** `portal/` e `Empresas/`.


Use esta área quando a tarefa envolver **código-fonte, projetos, workspaces ou configurações específicas de projeto**.


## Regra principal


Antes de alterar qualquer arquivo, identificar se a tarefa pertence à **instalação portátil** ou ao **workspace**.


- Alterações no programa, extensões ou configuração global do VSCode: começar em `VSCode-win32-x64-1.136.2/`.
- Alterações em projetos, código e configurações locais de projeto: começar em `workspace_vscode/`.
- Não misturar arquivos do executável com arquivos do workspace.
- Não criar uma segunda instalação ou um segundo workspace quando a tarefa puder ser executada usando os caminhos oficiais acima.


Quando houver conflito entre uma cópia anexada em conversa antiga, memória de contexto ou material exportado anteriormente e o conteúdo atual desta pasta, **prevalece o estado atual do Google Drive**, salvo orientação explícita em contrário.


O Git/GitHub continua sendo a referência de histórico e versionamento para os arquivos versionados. O Google Drive é o ponto de entrada operacional para localizar a instalação e os workspaces disponíveis.


## Fluxo de consulta


1. Começar por este arquivo.
2. Usar o Folder ID oficial da raiz `vscode`.
3. Classificar a tarefa como **instalação** ou **workspace**.
4. Entrar somente na área necessária.
5. Ler os arquivos atuais antes de alterar configurações.
6. Preservar a estrutura existente e evitar duplicações.
7. Usar Git/GitHub quando histórico, diff, commit ou reversão forem relevantes.


## Regras para uso pelo ChatGPT / Work / Codex


- Usar `Meu Drive / git / desenvolvimento / vscode` como caminho canônico.
- Priorizar o Folder ID `1zpMdvFA8O56iKZBBqq7Y_z0trx5Rjrk9` para evitar ambiguidade.
- Não solicitar anexos do projeto quando a informação necessária estiver acessível nesta pasta.
- Antes de instalar, remover ou reconfigurar uma extensão, localizar os arquivos efetivamente usados pela instalação portátil atual.
- Antes de editar um projeto, localizar primeiro a pasta correspondente dentro de `workspace_vscode/`.
- Não presumir que uma pasta chamada `.vscode` encontrada fora desta hierarquia pertence a este projeto.
- Preservar arquivos versionados e consultar o Git antes de operações que possam sobrescrever ou reverter configurações.
- Não alterar binários ou arquivos fixos da instalação sem que a tarefa exija isso explicitamente.


## Regra de manutenção deste arquivo


Este arquivo deve continuar sendo um **mapa de acesso**, não uma cópia completa da documentação do VSCode.


Quando a versão portátil, o nome da pasta da instalação, o workspace principal ou a hierarquia no Google Drive mudar, atualizar apenas os caminhos, IDs e regras afetados.