package com.server;

import com.service.FileInfoService;
import com.service.WelcomeService;
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

public class SocketServer {


    private static ServerSocket server;
    private static final int PORT = 6655;


    public static void main(String args[])throws IOException, ClassNotFoundException {
        SocketServer socketServer = new SocketServer();
        socketServer.startServer();
    }

    private void startServer() throws IOException, ClassNotFoundException {
        Map<String, Optional<FileInfoService>> strategyMap = buildStrategyMap();
        server = new ServerSocket(PORT);
        while(true){
            if (communicate(strategyMap)) break;
        }
        System.out.println("Shutting down Socket server!!");
        server.close();
    }

    private boolean communicate(Map<String, Optional<FileInfoService>> strategyMap) throws IOException, ClassNotFoundException {
        System.out.println("Waiting for client request");
        Socket socket = server.accept();
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        String inComingMessage = objectInputStream.readObject().toString().toLowerCase();
        String[] split = inComingMessage.split(" ");
        String outputMessage = processMessage(strategyMap.get(split[0]), inComingMessage);

        System.out.println("Message Received: " + outputMessage);
        ObjectOutputStream oos = writeMessage(socket, outputMessage);
        closeResources(socket, objectInputStream, oos);
        return inComingMessage.equalsIgnoreCase("exit");
    }

    private ObjectOutputStream writeMessage(Socket socket, String outputMessage) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(outputMessage);
        return oos;
    }

    private String processMessage(Optional<FileInfoService> fileInfoService, String inComingMessage) {
        return fileInfoService
                .map((e) -> fileInfoService.get().execute(inComingMessage))
                .orElse(new BadRequestService().execute(inComingMessage));
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
