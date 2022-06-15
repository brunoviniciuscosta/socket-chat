package chatclient;

import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

public class UserRead implements Runnable {
  private BufferedReader reader;
  private Socket socket;
  private ChatClient client;
  private MainInterface grafico;
  
  public UserRead(Socket socket, ChatClient client, MainInterface grafico) {
    this.socket = socket;
    this.client = client;
    this.grafico = grafico;
    
    try {
      InputStream input = socket.getInputStream();
      reader = new BufferedReader(new InputStreamReader(input));
    } catch (IOException e) {
      System.out.println("Erro obtendo input do server: " + e.getMessage());
    }
    
  }
  
  @Override
  public void run() {
    boolean gotUsers = false;
    while (true) {
      try {
        String mensagemServidor = reader.readLine();
        if ("ServerUpdate".equals(mensagemServidor)) {
          grafico.textAreaUserList.setText(null);
          int numberOfUsers = Integer.parseInt(reader.readLine());
          for (int i = numberOfUsers; i > 0; i--) {
            grafico.textAreaUserList.append(reader.readLine() + "\n");
          }
        } else {
          grafico.textArea.append(mensagemServidor + "\n");
        }

      } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Erro na leitura do server: " + e.getMessage(), "Erro!", JOptionPane.WARNING_MESSAGE);
        break;
      }
    }
  }

}
