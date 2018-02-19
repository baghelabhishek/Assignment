package com.server;// Unpublished Work (c) 2018 Deere & Company

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketClientExample {


    public static final int PORT = 6655;
    private static ObjectOutputStream objectOutputStream;
    private static ObjectInputStream objectInputStream;
    private static Socket socket;

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
        //get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();
        while(true){
            Scanner sc=new Scanner(System.in);
            System.out.println("Enter your command");
            String inputCommand = sc.nextLine();
            socket = new Socket(host.getHostName(), PORT);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(inputCommand);

            objectInputStream = new ObjectInputStream(socket.getInputStream());
            String message = (String) objectInputStream.readObject();
            System.out.println("Message: " + message);
            objectInputStream.close();
            objectOutputStream.close();

        }
    }

    public void startClient(){

    }
}
