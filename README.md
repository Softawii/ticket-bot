<h1 > <b> 🎫 Ticket Bot </b> </h1>

<div>
O bot é feito para auxiliar na sessão de suporte dos servidores. A ideia é expor menos os seus usuários em chats públicos e ao mesmo tempo facilitar a equipe em chat de suporte compartilhados.
</div>

<h2> 🔰 Manual de Uso </h2>

### 🔴 Requisitos:
- JDK 11
- PostgreSQL

### 🟢 Configuração das propriedades

O arquivo application.properties é onde as configurações do bot se encontram.
Ele deve estar localizado na mesma pasta em que o `ticket-bot-VERSION.jar` ou o `gradlew/gradlew.bat` estiver.

```properties
discord.token=DISCORD_TOKEN
hibernate.dialect=org.hibernate.dialect.PostgreSQL95Dialect
hibernate.connection.driver_class=org.postgresql.Driver
hibernate.connection.url=jdbc:postgresql://localhost:5432/database
hibernate.connection.username=DATABASE_USERNAME
hibernate.connection.password=DATABASE_PASSWORD
hibernate.show_sql=false
hibernate.format_sql=true
hibernate.hbm2ddl.auto=create
```

### 🔵 Botando para funcionar

```shell
# Para executar o bot diretamente do código fonte
./gradlew run

# Para gerar o executável .jar
./gradlew jar

# Para executar o .jar
java -jar ticket-bot-VERSION.jar
```

OBS: Executar na pasta raiz do repositório

<h2> <b> ✅ Para Colaboradores </b> </h2>

<h3>  <b> 👩‍💻 Padrões de Desenvolvimento 👩‍💻 </b> <sup> pós alpha </sup> </h3>

1. [Boas Práticas de Código](./doc/01-code.md)
2. [Padrão das Issues](./doc/02-issues.md)
3. [Padrão dos Commits e PRs](./doc/03-commit-pr.md)
4. [Contato dos Desenvolvedores](./doc/04-maintainers.md)
5. [Contribuição de Terceiros](./doc/05-third-party-contribution)

<!-- COLABORADORES -->

<br>
