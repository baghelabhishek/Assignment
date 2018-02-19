package com.server;// Unpublished Work (c) 2018 Deere & Company

import com.FileInfoService;
import com.WelcomeService;
import com.service.BadRequestService;
import com.service.ChangeDirctoryService;
import com.service.CurrentPathService;
import com.service.DirectoryInfoService;
import com.service.ListOfFileService;
import com.service.QuitService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SocketServerExample {

    //static ServerSocket variable
    private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 6655;
    private FileInfoService fileInfoService;






    public static void main(String args[])throws IOException, ClassNotFoundException {
        SocketServerExample socketServerExample = new SocketServerExample();
        socketServerExample.startServer();



    }

    public void startServer() throws IOException, ClassNotFoundException {

        Map<String, Optional<FileInfoService>> strategyMap = buildStrategyMap();

        //create the socket server object
        server = new ServerSocket(port);
        //keep listens indefinitely until receives 'exit' call or program terminates
        while(true){
            System.out.println("Waiting for client request");
            //creating socket and waiting for client connection
            Socket socket = server.accept();
            //read from socket to ObjectInputStream object
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            //convert ObjectInputStream object to String
            String inCommingMessage = (String) objectInputStream.readObject().toString().toLowerCase();
            String[] split = inCommingMessage.split(" ");
            boolean info = inCommingMessage.contains("info");
            boolean dir = inCommingMessage.contains("cd");
            Optional<FileInfoService> fileInfoService = strategyMap.get(split[0]);
            String outputMessage = fileInfoService
                    .map((e) -> fileInfoService.get().execute(inCommingMessage))
                    .orElse(new BadRequestService().execute(inCommingMessage));

            System.out.println("Message Received: " + outputMessage);
            //create ObjectOutputStream object
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            //write object to Socket
            oos.writeObject(outputMessage);
            //close resources
            closeResources(socket, objectInputStream, oos);
            //terminate the server if client sends exit request
            if(inCommingMessage.equalsIgnoreCase("exit")) break;
        }
        System.out.println("Shutting down Socket server!!");
        //close the ServerSocket object
        server.close();

    }

    private void closeResources(Socket socket, ObjectInputStream objectInputStream, ObjectOutputStream oos) throws IOException {
        objectInputStream.close();
        oos.close();
        socket.close();
    }

    private Map<String, Optional<FileInfoService>> buildStrategyMap() {
        Map<String, Optional<FileInfoService>> strategyMap = new HashMap<>();
        strategyMap.put("connect",Optional.of(new WelcomeService()) );
        strategyMap.put("dir",Optional.of(new ListOfFileService()));
        strategyMap.put("info",Optional.of(new DirectoryInfoService()));
        strategyMap.put("cd",Optional.of(new ChangeDirctoryService()));
        strategyMap.put("pwd",Optional.of(new CurrentPathService()));
        strategyMap.put("quit",Optional.of(new QuitService()));
        return strategyMap;
    }
}
