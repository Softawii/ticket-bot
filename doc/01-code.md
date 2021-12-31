<h3> <b> ☕ Sobre o Kotlin ☕ </b> </h3>
<!--
<div style="display: inline_block">
  <img  alt="C" height="30" width="40" src="https://raw.githubusercontent.com/devicons/devicon/2ae2a900d2f041da66e950e4d48052658d850630/icons/kotlin/kotlin-original.svg">
</div>
-->

#### Tópicos Importantes: 
1. [Documentação e Comentários](https://kotlinlang.org/docs/kotlin-doc.html)
2. [Criação de Comandos](#Criação-de-Comandos)
3. [Criação de Features](#Criação-de-Features)

## Criação de Comandos

Os comandos são agrupados por classe, que representa um conjunto de comandos, e cada método estático representa um comando do bot. 
Para uma classe se considerada um agrupador de comandos, é necessário a notação `@CommandClass`.
Essa notação é necessária para realizar a busca dos comandos que se encontram nessa classe.
Para um método ser considerado válido, é necessário a notação `@Command`.

Por limitações da linguagem e da Reflection API, foi necessário que os comandos sejam criados utilizando classes Java.  

As classes se encontram no pacote `com.softawii.ticketbot.command` que fica localizado na pasta `application/src/main/java`.

## Criação de Features

Sempre que possível, as features deverão ser implementadas em Kotlin.
Isso se deve pela decisão da equipe de desenvolver o bot em Kotlin para aprender sobre a linguagem.