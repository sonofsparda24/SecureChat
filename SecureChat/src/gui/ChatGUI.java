package gui;

import chatapp.ChatPeer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatGUI {
    private ChatPeer chatPeer;
    private JFrame frame;
    private JTextArea textArea;
    private JTextField textField;
    private JButton sendButton;

    public ChatGUI(ChatPeer chatPeer) {
        this.chatPeer = chatPeer;
    }

    public void initGUI() {
        frame = new JFrame("Chat Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        textArea = new JTextArea();
        textArea.setEditable(false);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        textField = new JTextField();
        panel.add(textField, BorderLayout.CENTER);

        sendButton = new JButton("Send");
        panel.add(sendButton, BorderLayout.EAST);

        frame.add(panel, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = textField.getText();
                chatPeer.sendMessage(message);
                textArea.append("You: " + message + "\n");
                textField.setText("");
            }
        });

        frame.setVisible(true);

        // Start a thread to receive messages
        new Thread(() -> {
            while (true) {
                String message = chatPeer.receiveMessage();
                if (message != null) {
                    textArea.append("Peer: " + message + "\n");
                }
            }
        }).start();
    }
}