package chatclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

public class UserWrite implements Runnable {
  
  private PrintWriter writer;
  private Socket socket;
  private ChatClient client;
  private MainInterface grafico;
  
  public UserWrite(Socket socket, ChatClient client, MainInterface grafico) {
    this.socket = socket;
    this.client = client;
    this.grafico = grafico;
    
    try {
      OutputStream output = socket.getOutputStream();
      writer = new PrintWriter(output, true);
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, "Erro obtendo output: " + e.getMessage(), "Erro!", JOptionPane.WARNING_MESSAGE);
    }
  }
  
  @Override
  public void run() {
    WriterHandler writerHandler = new WriterHandler();
    grafico.sendButton.addActionListener(writerHandler);
    grafico.textFieldMessage.addActionListener(writerHandler);
  }
  
  private class WriterHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String text = grafico.textFieldMessage.getText();
      writer.println(text);
      if (text.equals("bye")) {
        try {
          socket.close();
          System.exit(0);
        } catch (IOException ee) {
          JOptionPane.showMessageDialog(null, "Erro escrevendo para o servidor: " + ee.getMessage(), "Erro!", JOptionPane.WARNING_MESSAGE);
        }
      }
    }
  }
  
}