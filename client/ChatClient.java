package chatclient;

import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

public class ChatClient {
  private final String hostname;
  private final int port;
  
  public ChatClient(String hostname, int port) {
    this.hostname = hostname;
    this.port = port;
  }
  
  void startClient() {
    try {
      Socket socket = new Socket(hostname, port);
      
      MainInterface chat = new MainInterface();
      chat.createWindow();
      chat.textArea.append("Conectado no chat. Host: " + hostname + " Porta: " + port + "\n");
      
      
      UserRead userRead = new UserRead(socket, this, chat);
      Thread readingThread = new Thread(userRead);
      readingThread.start();
      
      UserWrite userWrite = new UserWrite(socket, this, chat);
      Thread writingThread = new Thread(userWrite);
      writingThread.start();
      
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, "Erro em cliente: " + e.getMessage() + "\nTente novamente.", "Erro!", JOptionPane.WARNING_MESSAGE);
    }
  }

  public static void main(String[] args) {
    Login login = new Login();
    login.createWindow();
  }
  
}
