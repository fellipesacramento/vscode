# Ambiente Java do OFX

Validado em 08/10/2026 no VS Code 1.140.0.

Abra esta pasta como projeto: `workspace_vscode/ofx/utilitarios/ofx`.

## Configuracao

- JDK: `D:\googledrive\git\desenvolvimento\jdk\jdk-21_windows-x64_bin\jdk-21.0.12.1`.
- Maven: `D:\googledrive\git\desenvolvimento\maven\apache-maven-3.9.16`.
- O `pom.xml` fixa Java 21 por `maven.compiler.release` e o compilador Maven 3.15.0.
- `.vscode/settings.json` configura Java, Maven e o terminal desta pasta.
- `.vscode/extensions.json` recomenda o pacote Java e o suporte Maven.
- `bin` e `target` ficam fora da importacao. Ha uma copia antiga do POM dentro de `bin`; ela nao deve ser usada como projeto.
- O modo Java e `Standard`. A comunicacao `java.transport: stdio` evita a falha da conexao por pipe que iniciava duas instancias sobre o mesmo cache.
- O Maven Central usa seu endereco oficial HTTPS `https://repo1.maven.org/maven2`, pois o endereco padrao apresentou erro de certificado neste ambiente.

## Uso

No terminal integrado desta pasta:

```text
java -version
javac -version
mvn -version
mvn clean verify
```

## Verificacao realizada

- Extensoes Java 1.56.0 e Maven 0.46.0 ativas.
- Servico Java pronto em modo completo, com apenas o projeto OFX importado.
- Nivel de linguagem e compilacao 21, com o JDK indicado acima.
- 45 entradas de classes/dependencias carregadas.
- Navegacao ate `java.lang.String` resolvida pelo JDK 21.
- Nenhum erro Java reportado no teste do editor.
- Terminal integrado confirmou Java e javac 21.0.12.1 e Maven 3.9.16.
- Compilacao do zero aprovada: o teste existente passou e `target/ofx-0.0.1-SNAPSHOT.jar` foi gerado. O teste existente e basico; nao valida o comportamento da conversao financeira.

O indice anterior foi preservado em:

`VSCode-win32-x64-1.140.0/data/user-data/User/workspaceStorage/0690065e1d221c96ab3e45e7c3ea3b4f/redhat.java/jdt_ws/.metadata/.plugins/org.eclipse.jdt.core.backup-20261008-2305`

Esse caminho e relativo ao repositorio `D:\googledrive\git\desenvolvimento\vscode`.

## Referencias

- [Configuracao de projetos Java no VS Code](https://code.visualstudio.com/docs/java/java-project).
- [Solucao do mantenedor para a conexao Java travada](https://github.com/redhat-developer/vscode-java/discussions/3666).
- [Java release no compilador Maven](https://maven.apache.org/plugins/maven-compiler-plugin/examples/set-compiler-release.html).
- [Enderecos HTTPS oficiais do Maven Central](https://central.sonatype.org/faq/ssl/).
