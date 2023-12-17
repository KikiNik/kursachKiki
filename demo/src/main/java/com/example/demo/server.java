package com.example.demo;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс, представляющий простой сервер, способный принимать зашифрованные файлы и ключи с использованием сокетов.
 */
public class server {

    // Определяет количество подключений клиентов
    private static int clientCount = 0;

    // Дополнительный счетчик для отслеживания пары зашифрованного файла и ключа
    private static int k = 0;
    private static int k1 = 0;
    private static int k2 = 0;

    /**
     * Метод main, запускающий сервер на указанном порту и принимающий подключения от клиентов.
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        // Выбранный порт для работы сервера
        int port = 12345;
        try {
            // Создание серверного сокета
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is running on port " + port);

            while (true) {
                // Принятие подключения от клиента
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket);
                clientCount++;
                k++;

                // Принятие зашифрованного файла или ключа в зависимости от счетчика клиентов
                if (clientCount % 3 == 1) {
                    receiveFile(socket, "received_encrypted_file_" + clientCount / 2 + ".txt");
                    k1 = clientCount / 2;
                } else if(clientCount % 3 == 2){
                    receiveFile(socket, "received_key_" + (clientCount / 2) + ".txt");
                    k2 = clientCount / 2;
                } else if(clientCount % 3 == 0){
                    receiveString(socket);
                    k--;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод для приема текстовой строки через переданный сокет и выполнения соответствующих действий в зависимости от содержимого строки.
     * @param socket сокет для обмена данными с клиентом
     * @throws Exception возникает при ошибке ввода/вывода или при работе с шифрованием
     */
    private static void receiveString(Socket socket) throws Exception {
        // Получение данных из сокета
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String message = reader.readLine();

        // Обработка полученной строки в зависимости от содержимого
        if (message.equals("Assym")) {
            // Действие для "Assym"
            encrypt.decryptFileAsymmetric("received_encrypted_file_" + k1 + ".txt","decryptedAsymm.txt", "received_key_" + k2 + ".txt");
            System.out.println("Received Assym");
        } else if (message.equals("Symm")) {
            // Действие для "Symm"
            encrypt.decryptFileSymmetric("received_encrypted_file_" + k1 + ".txt","decryptedSymm.txt", "received_key_" + k2 + ".txt");
            System.out.println("Received Symm");
        }

        socket.close();
    }

    /**
     * Метод для приема файла через сокет и сохранения его на сервере.
     * @param socket сокет для обмена данными с клиентом
     * @param fileName имя файла для сохранения принятых данных
     * @throws IOException возникает при ошибке ввода/вывода
     */
    private static void receiveFile(Socket socket, String fileName) throws IOException {
        byte[] fileBytes = new byte[1024];
        InputStream inputStream = socket.getInputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileName));

        int bytesRead;
        while ((bytesRead = inputStream.read(fileBytes)) != -1) {
            bufferedOutputStream.write(fileBytes, 0, bytesRead);
        }

        bufferedOutputStream.close();
        socket.close();
    }
}

