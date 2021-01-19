# App EasyInsurance Saúde+ - FIAP 2020

## Introdução

O presente trabalho tem o intuito de apresentar a solução adotada para a criação de um aplicativo que emula a startup EasyInsurance Saúde+. 

Este aplicativo tem fins didáticos, pois as funcionalidades do aplicativo da startup estão sendo pensados e modelados. O objetivo principal foi emular dois perfis (ou personas) que estão envolvidos no conceito da startup, que é prover a aproximação entre clientes que querem contratar planos de saúde e corretores de planos de saúde que tem interesse em encontrar clientes e oferecer a eles um plano mais aderente ao seu perfil social e econômico.

Nesta solução foi construído um aplicativo para Android desenvolvido em Kotlin e as estruturas de backend foram centralizadas no servidor Firebase da Google. Neste serviço em nuvem foram utilizados os módulos Authentication (para criação dos usuários e gerenciamento do Login) e o Cloud Firestore, para armazenamento dos demais dados de usuários e mensagens enviadas entre eles.

O objetivo macro foi fazer um aplicativo capaz de gerenciar um logon, criar usuários novos para realizar novos logins de acordo com o perfil, gravar os seus dados em nuvem e simular uma relação simples entre um cliente e um corretor de planos de saúde via uma conversa.



## Desenho Básico da Solução

![trabalho_final_ws](https://user-images.githubusercontent.com/67294168/102436654-d0f32600-3ff7-11eb-84c7-5bfdee50beba.png)

## Prototipação no Figma

Foram criados protótipos para esta aplicação disponiveis no Figma no endereço abaixo: 

https://www.figma.com/proto/1X2yhIfKdpLTL5tJCSzkL1/Untitled?node-id=1%3A2&scaling=scale-down


### Modelagem das estruturas de dados

![Diagrama em branco](https://user-images.githubusercontent.com/67294168/102129921-e23d0680-3e2e-11eb-9839-cf5975dbeba2.png)

Os dados dos usuários são persistidos em um banco de dados dentro do Firebase, a Cloud Firestore. Nesta base, as estruturas de dados são gravadas em coleções com chaves separando cada uma e estruturas de {chave}={valor} para gravar os atributos.

Assim, foram criadas duas coleções a saber, sendo elas: 

    Users --> Responsável por guardar os dados dos usuários cadastrados no App (exceto login e senha)
    Conversations --> Responsável por guardar os dados das mensagens enviadas e conversas  por usuários (clientes e corretores).



## Frontend da Aplicação

  Para este desenvolvimento, foram desenvolvidas várias telas para permitir a interação descrita na solução.
  
  Seguem alguns prints das mesmas abaixo: 
  
  

## Conclusão

O desenvolvimento de aplicativos já faz parte da nossa vida. Hoje dispomos de duas grandes empresas responsáveis pela grande maioria dos sistemas operacionais em aplicativos móveis como tablets e celulares: Google e Apple.

O Android Studio, ferramenta de desenvolvimento para aplicativos que rodam em Android, do Google, permite que desenvolvamos aplicativos de maneira estrutura usando Kotlin e invocando de forma relativamente acessível recursos online em Clouds e consumindo webservices e dados de fontes na internet. Na internet, há vasta documentação sobre esta tecnologia.

Assim, o desenvolvimento de aplicativos nesta linguagem se popularizou. Hoje, tanto as lojas da Google como da Apple estão repletas de jogos e aplicativos para os mais diversos usos. Assim, cada vez mais as pessoas criam comodidade e as tem na palma de sua mão.

Este trabalho teve o objetivo e demonstrar que para empresas, startups e até pessoas, hoje em dia o mundo Mobile é essencial. Com os recursos utilizados na solução didática acima, pode-se desenvolver uma gama de aplicativos úteis para os mais diversos usos e fins. No caso demonstrado acima, houve uma simulação da relação entre dois perfis, sendo um o corretor de planos de saúde e outro um cliente interessado nesta contratação. Mas esta relação pode ser facilmente entre um banco e um cliente, uma loja e um cliente ou mesmo entre pessoas diversas em aplicativos de redes sociais.

Por fim, também foi demonstrado que pode-se capacitar programadores em geral que tenham conhecimentos em Orientação a Objetos e Java e que estejam dispostos a aprender esta nova vertente do desenvolvimento de software cada vez mais presente no nosso dia a dia.
