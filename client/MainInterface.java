package chatclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainInterface extends Win {

  JTextArea textArea, textAreaUserList;
  JTextField textFieldMessage;
  JButton sendButton;

  public MainInterface(int x, int y) {
    super("Chat", x, y, 598, 500, JFrame.EXIT_ON_CLOSE);
    this.getRootPane().setDefaultButton(sendButton);
  }

  public MainInterface() {
    this(-1, -1);
  }

  @Override
  protected void setupComponents() {
    setLayout(null);
    
    textAreaUserList = new JTextArea(25, 10);
    textAreaUserList.setSize(textAreaUserList.getPreferredSize());
    textAreaUserList.setEditable(false);
    
    textArea = new JTextArea(25, 40);
    textArea.setSize(textArea.getPreferredSize());
    textArea.setEditable(false);
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    JScrollPane scrollPane = new JScrollPane(textArea);
    sendButton = new JButton("Enviar");
    sendButton.setSize(sendButton.getPreferredSize());
    textFieldMessage = new JTextField(40);
    textFieldMessage.setSize(textFieldMessage.getPreferredSize());

    add(scrollPane);
    addRow(textArea, textAreaUserList);
    addRow(textFieldMessage, sendButton);
    
    SendHandler sendHandler = new SendHandler();
    textFieldMessage.addActionListener(sendHandler);
    sendButton.addActionListener(sendHandler);


  }

  private class SendHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String text = textFieldMessage.getText();
      textArea.setCaretPosition(textArea.getDocument().getLength());
      textFieldMessage.setText("");
    }
  }
    
}