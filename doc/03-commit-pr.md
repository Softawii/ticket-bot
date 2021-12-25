<h3 > 🎫 Commits e Pull Requests 🎫 </h3>

<div >
Foi definido um padrão de commits para tentar facilitar o rastreamento das mudanças e motivações da mesma. Todo commit tem uma ou mais Issues relacionadas, assim, é possível entender sobre as motivações do commit de maneira fácil.

<br>

<h3> 🧁 Estrutura dos Commits 🧁 </h3>

<font color="#E67497"> <b> tipo: </b> </font> 
<font color="#F2E26D"> (escopo [opcional]) </font> 
<font color="#FCB172"> Explicação sobre o Commit </font>
<font color="#7FCBF5"> <b> #Número da Issue </b> </font>

<h4> 🍔 Exemplo 🍔 </h4>

<font color="#E67497"> <b> feat: </b> </font> 
<font color="#FCB172"> Getting Started to JDA </font>
<font color="#7FCBF5"> <b> [#5](https://github.com/Softawii/ticket-bot/issues/5) </b> </font>

Foi possível criar um link entre a issue tratada e a implementação feita.

<br>

### 💥 Tipos possíveis 💥 

`fix` - Commits do tipo fix indicam que seu trecho de código commitado está solucionando um problema (bug fix), (se relaciona com o PATCH do versionamento semântico).

`feat` - Commits do tipo feat indicam que seu trecho de código está incluindo um novo recurso (se relaciona com o MINOR do versionamento semântico).

`docs` - Commits do tipo docs indicam que houveram mudanças na documentação, como por exemplo no Readme do seu repositório. (Não inclui alterações em código).

`style` - Commits do tipo style indicam que houveram alterações referentes a formatações de código, semicolons, trailing spaces, lint... (Não inclui alterações em código).

`refactor` - Commits do tipo refactor referem-se a mudanças devido a refatorações que não alterem sua funcionalidade, como por exemplo, uma alteração no formato como é processada determinada parte da tela, mas que manteve a mesma funcionalidade, ou melhorias de performance devido a um code review.

`build` - Commits do tipo build são utilizados quando são realizadas modificações em arquivos de build e dependências.

`test` - Commits do tipo test são utilizados quando são realizadas alterações em testes, seja criando, alterando ou excluindo testes unitários. (Não inclui alterações em código)

`chore` - Commits do tipo chore indicam atualizações de tarefas de build, configurações de administrador, pacotes... como por exemplo adicionar um pacote no gitignore. (Não inclui alterações em código)

Mais detalhes em [padrões de commit](https://github.com/iuricode/padroes-de-commits) e também em [conventional commits](https://www.conventionalcommits.org/en/v1.0.0/).

<br>

<h3> 🧁 Estrutura dos Pull Requests 🧁 </h3>

<div >
    Assim como foi definido um padrão para as Issues também definimos um padrão para as Pull Requests, sendo necessário definir bem sua solução para as Issues selecionadas. 
</div>