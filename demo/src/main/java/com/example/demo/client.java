package com.example.demo;
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

/**
 * Класс, который отправляет файлы на сервер.
 */
public class client {

    /**
     * Отправляет файл на сервер.
     *
     * @param host      the host address of the server
     * @param port      the port number of the server
     * @param inputFile the path to the file to be sent
     */
    public static void sendFileToServer(String host, int port, String inputFile) {
        try {
            Socket socket = new Socket(host, port);

            File file = new File(inputFile);
            byte[] fileBytes = Files.readAllBytes(file.toPath());

            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(fileBytes, 0, fileBytes.length);
            outputStream.flush();

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Отправляет строку на сервер.
     *
     * @param host    the host address of the server
     * @param port    the port number of the server
     * @param message the string message to be sent
     */
    public static void sendStringToServer(String host, int port, String message) {
        try {
            Socket socket = new Socket(host, port);

            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);
            writer.println(message);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}