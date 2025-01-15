package chatapp;

import gui.ChatGUI;

public class ChatAppLauncher {
    public static void main(String[] args) {
        ChatPeer chatPeer = new ChatPeer();
        chatPeer.startServer(1234); // Start server on port 1234

        ChatGUI chatGUI = new ChatGUI(chatPeer);
        ((ChatGUI) chatGUI).initGUI();
    }
}