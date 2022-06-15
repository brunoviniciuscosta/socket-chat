# socket-chat
Projeto de Graduação - Aplicação de chat - arquitetura servidor/cliente - Java

Projeto desenvolvido para disciplina APS da gradução de Ciência da Computação - UNIP para o 4º semestre

Aplicação de comunicação em rede utilizando sockets e uso de threads desenvolvida em Java.
Possui interface gráfica bastante simples e não há preocupação com questões de segurança, mensagem criptografadas.

O servidor é responsável por criar threads para cada client e distribuir as mensagens entre todos. Possui identificação de nome de cliente duplicado.
