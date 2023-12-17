package com.example.demo;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * �����, �������������� ������� ������, ��������� ��������� ������������� ����� � ����� � �������������� �������.
 */
public class server {

    // ���������� ���������� ����������� ��������
    private static int clientCount = 0;

    // �������������� ������� ��� ������������ ���� �������������� ����� � �����
    private static int k = 0;
    private static int k1 = 0;
    private static int k2 = 0;

    /**
     * ����� main, ����������� ������ �� ��������� ����� � ����������� ����������� �� ��������.
     * @param args ��������� ��������� ������
     */
    public static void main(String[] args) {
        // ��������� ���� ��� ������ �������
        int port = 12345;
        try {
            // �������� ���������� ������
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is running on port " + port);

            while (true) {
                // �������� ����������� �� �������
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket);
                clientCount++;
                k++;

                // �������� �������������� ����� ��� ����� � ����������� �� �������� ��������
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
     * ����� ��� ������ ��������� ������ ����� ���������� ����� � ���������� ��������������� �������� � ����������� �� ����������� ������.
     * @param socket ����� ��� ������ ������� � ��������
     * @throws Exception ��������� ��� ������ �����/������ ��� ��� ������ � �����������
     */
    private static void receiveString(Socket socket) throws Exception {
        // ��������� ������ �� ������
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String message = reader.readLine();

        // ��������� ���������� ������ � ����������� �� �����������
        if (message.equals("Assym")) {
            // �������� ��� "Assym"
            encrypt.decryptFileAsymmetric("received_encrypted_file_" + k1 + ".txt","decryptedAsymm.txt", "received_key_" + k2 + ".txt");
            System.out.println("Received Assym");
        } else if (message.equals("Symm")) {
            // �������� ��� "Symm"
            encrypt.decryptFileSymmetric("received_encrypted_file_" + k1 + ".txt","decryptedSymm.txt", "received_key_" + k2 + ".txt");
            System.out.println("Received Symm");
        }

        socket.close();
    }

    /**
     * ����� ��� ������ ����� ����� ����� � ���������� ��� �� �������.
     * @param socket ����� ��� ������ ������� � ��������
     * @param fileName ��� ����� ��� ���������� �������� ������
     * @throws IOException ��������� ��� ������ �����/������
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

