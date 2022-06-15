package chatserver;

import java.io.*;
import java.net.*;
import java.util.Collection;

public class User implements Runnable {

  private final Socket socket;
  private final ChatServer server;
  private String userName;
  private PrintWriter writer;

  public User(Socket socket, ChatServer server) {
    this.socket = socket;
    this.server = server;
  }

  @Override
  public void run() {
    try {
      InputStream input = socket.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(input));

      OutputStream output = socket.getOutputStream();
      writer = new PrintWriter(output, true);
      
      send("*** Digite seu nome.");
      String tryUserName = reader.readLine();
      
      do {
        if (server.userExists(tryUserName)) {
          send("*** Este nome ja esta em uso. Digite outro nome.\n");
          tryUserName = reader.readLine();
        }
        else {
          server.addUser(this, tryUserName);
          this.userName = tryUserName;
        }
      } while (this.userName == null);
      
      String message = "*** " + userName + " entrou no chat";
      server.broadcast(message);
      
      updateUsers();
      
      String clientMessage;
      do {
        clientMessage = reader.readLine();
        message = "[" + userName + "]: " + clientMessage;
        server.broadcast(message);
      } while (!clientMessage.equals("bye"));
      
      server.removeUser(this);
      message = "*** " + userName + " saiu do chat";
      server.broadcast(message);
      updateUsers();
      writer.close();
      socket.close();
    } catch (IOException e) {
      System.out.println("Erro na classe User: " + e.getMessage());
    }
  }
  
  public void updateUsers() {
    Collection<String> activeUsers = server.usersList();
    server.broadcast("ServerUpdate");
    server.broadcast(Integer.toString(activeUsers.size()));
    for (String user : activeUsers) server.broadcast(user);
  }

  public void send(String message) {
    writer.println(message);
  }

  public String getUserName() {
    return this.userName;
  }

}
