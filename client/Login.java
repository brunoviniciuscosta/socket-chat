package chatclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Login extends Win {

  private JLabel labelHost, labelPorta;
  private JTextField textFieldHost, textFieldPorta;
  private JButton buttonConectar;


  public Login(int x, int y) {
    super("APS Chat - Login", x, y, 300, 150, JFrame.EXIT_ON_CLOSE);
    this.getRootPane().setDefaultButton(buttonConectar);
  }

  public Login() {
    this(-1, -1);
  }
  
  @Override
  protected void setupComponents() {
    setLayout(null);
    labelHost = new JLabel("Host");
    labelPorta = new JLabel("Porta");
    textFieldHost = new JTextField(20);
    textFieldPorta = new JTextField(5);
    buttonConectar = new JButton("Conectar");
    
    addRow(labelHost, textFieldHost);
    addRow(labelPorta, textFieldPorta);
    addRow(buttonConectar);
    
    ConectarHandler conectarHandler = new ConectarHandler();
    buttonConectar.addActionListener(conectarHandler);
    textFieldHost.addActionListener(conectarHandler);
    textFieldPorta.addActionListener(conectarHandler);
    
  }
  
  private class ConectarHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        ChatClient client = new ChatClient(textFieldHost.getText(), Integer.parseInt(textFieldPorta.getText()));
        client.startClient();
        dispose();
      } catch (NumberFormatException er) {
        JOptionPane.showMessageDialog(null, "O campo porta deve conter números!", "Erro!", JOptionPane.WARNING_MESSAGE);
      }
    }
  }
  
}
