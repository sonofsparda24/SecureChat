package chatapp;

import util.RSAUtil;
import database.DatabaseHandler;
import java.io.*;
import java.net.*;
import java.security.KeyPair;
import java.security.PublicKey;

public class ChatPeer {
    private Socket socket;
    private ServerSocket serverSocket;
    private BufferedReader input;
    private PrintWriter output;
    private KeyPair rsaKeyPair;
    private PublicKey publicKey;
    private PublicKey remotePublicKey;
    private DatabaseHandler dbHandler;

    public ChatPeer() {
        dbHandler = new DatabaseHandler();
        dbHandler.connectToDB("jdbc:mysql://localhost:3306/SecureChat", "root", "youssef");
        rsaKeyPair = RSAUtil.generateKeyPair();
        publicKey = rsaKeyPair.getPublic();
    }

    public void startServer(int port) {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    new Thread(() -> handleClient(clientSocket)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void handleClient(Socket clientSocket) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            // Exchange public keys
            String clientPublicKeyStr = input.readLine();
            remotePublicKey = RSAUtil.stringToPublicKey(clientPublicKeyStr);
            output.println(RSAUtil.keyToString(publicKey));

            while (true) {
                String encryptedMessage = input.readLine();
                String decryptedMessage = RSAUtil.decrypt(encryptedMessage, rsaKeyPair.getPrivate());
                dbHandler.saveMessage("Peer", decryptedMessage, false);
                System.out.println("Received: " + decryptedMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connectToPeer(String host, int port) {
        try {
            socket = new Socket(host, port);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            // Exchange public keys
            output.println(RSAUtil.keyToString(publicKey));
            remotePublicKey = RSAUtil.stringToPublicKey(input.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        String encryptedMessage = RSAUtil.encrypt(message, remotePublicKey);
        output.println(encryptedMessage);
        dbHandler.saveMessage("You", message, true);
    }

    public String receiveMessage() {
        try {
            String encryptedMessage = input.readLine();
            return RSAUtil.decrypt(encryptedMessage, rsaKeyPair.getPrivate());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}