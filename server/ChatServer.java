package chatserver;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {

  private final int port;
  private final Set<User> users = new HashSet<>();
  private final HashMap<User, String> userNames = new HashMap<>();

  private ChatServer(int port) {
    this.port = port;
  }

  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("Erro de sintaxe. Use: java -jar ChatServerAPS <numero da porta>");
      System.exit(0);
    }
    int port = Integer.parseInt(args[0]);
    ChatServer server = new ChatServer(port);
    server.startServer();
  }

  public void startServer() {
    try {
      ServerSocket serverSocket = new ServerSocket(port);
      System.out.println("Server aberto na porta: " + port);
      while (true) {
        Socket clientSocket = serverSocket.accept();
        System.out.println("Novo cliente conectado: ");

        User newUser = new User(clientSocket, this);
        Thread newThread = new Thread(newUser);
        newThread.start();
        users.add(newUser);

      }

    } catch (IOException e) {
      System.out.println("Erro no server: " + e.getMessage());
    }
  }

  public void broadcast(String message) {
    System.out.println(message);
    for (User user : users) {
        user.send(message);
    }
  }

  public boolean userExists(String username) {
    for (User key : userNames.keySet()) {
      if (userNames.get(key).equals(username)) {
        return true;
      }
    }
    return false;
  }
  
  public void addUser(User user, String username) {
    if (!userExists(username)) userNames.put(user, username);
  }

  public void removeUser(User user) {
    users.remove(user);
    userNames.remove(user);
  }

  public Collection<String> usersList() {
    Collection<String> userList = userNames.values();
    return userList;
  }
  
}
  
