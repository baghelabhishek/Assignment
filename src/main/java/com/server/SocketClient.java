package com.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
    private static final int PORT = 6655;
    private static ObjectOutputStream objectOutputStream;
    private static ObjectInputStream objectInputStream;
    private static Socket socket;

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
        InetAddress host = InetAddress.getLocalHost();
        while(true){
            Scanner sc=new Scanner(System.in);
            System.out.println("Enter your command");
            String inputCommand = sc.nextLine();
            sendMessageToClient(host, inputCommand);
            System.out.println("Message: " + getMessage());
            closeAllStream();

        }
    }

    private static void closeAllStream() throws IOException {
        objectInputStream.close();
        objectOutputStream.close();
    }

    private static String getMessage() throws IOException, ClassNotFoundException {
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        return (String) objectInputStream.readObject();
    }

    private static void sendMessageToClient(InetAddress host, String inputCommand) throws IOException {
        socket = new Socket(host.getHostName(), PORT);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(inputCommand);
    }

}
