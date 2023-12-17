package com.example.demo;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * ����� ��� ���������� � ������������ ������ � �������������� ������������ � ������������� ������.
 */
public class encrypt {
    /**
     * ������� ���� � �������������� ������������� �����.
     *
     * @param inputFile  ���� � ��������� �����
     * @param outputFile  ���� � �����, � ������� ����� ��������� ������������� ����������
     * @param keyFile  ���� � �����, � ������� ����� �������� ������������ ����
     * @throws Exception ���� ��������� ������ � �������� ����������
     */
    public static void encryptFileSymmetric(String inputFile, String outputFile, String keyFile) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // ��������� ����� ����� (� �����)

        // ��������� �����
        SecretKey symmetricKey = keyGen.generateKey();
        // ���������� ������������� ����� � ����
        ObjectOutputStream keyOutputStream = new ObjectOutputStream(new FileOutputStream(keyFile));
        keyOutputStream.writeObject(symmetricKey);
        keyOutputStream.close();
        // ���������� ����� � �������������� ������������� �����
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, symmetricKey);

        FileInputStream inputStream = new FileInputStream(inputFile);
        byte[] inputBytes = new byte[(int) new File(inputFile).length()];
        inputStream.read(inputBytes);

        byte[] outputBytes = cipher.doFinal(inputBytes);

        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(outputBytes);

        inputStream.close();
        outputStream.close();
    }
    /**
     * ������� ���� � �������������� �������������� �����.
     *
     * @param inputFile  ���� � ��������� �����
     * @param outputFile  ���� � �����, � ������� ����� ��������� ������������� ����������
     * @param publicKeyFile  ���� � �����, � ������� ����� �������� �������� ����
     * @param privateKeyFile ���� � �����, � ������� ����� �������� �������� ����
     * @throws Exception ���� ��������� ������ � �������� ����������
     */
    public static void encryptFileAsymmetric(String inputFile, String outputFile, String publicKeyFile, String privateKeyFile) throws Exception {
        // ��������� �������� ����
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // ������ ��������� ����� � ����
        ObjectOutputStream publicKeyOutputStream = new ObjectOutputStream(new FileOutputStream(publicKeyFile));
        publicKeyOutputStream.writeObject(publicKey);
        publicKeyOutputStream.close();

        // ������ ��������� ����� � ����
        ObjectOutputStream privateKeyOutputStream = new ObjectOutputStream(new FileOutputStream(privateKeyFile));
        privateKeyOutputStream.writeObject(privateKey);
        privateKeyOutputStream.close();

        // ���������� ����� � �������������� ��������� �����
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        FileInputStream inputStream = new FileInputStream(inputFile);
        byte[] inputBytes = new byte[(int) new File(inputFile).length()];
        inputStream.read(inputBytes);
        byte[] outputBytes = cipher.doFinal(inputBytes);

        // ������ ������������� ������ � ����
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(outputBytes);

        // �������� �������
        inputStream.close();
        outputStream.close();
    }
    /**
     * ��������� ���� � �������������� ������������� �����.
     *
     * @param inputFile  ���� � �������������� �����
     * @param outputFile  ���� � �����, � ������� ����� ��������� �������������� ����������
     * @param keyFile  ���� � �����, ����������� ������������ ����
     * @throws Exception ���� ��������� ������ � �������� ������������
     */
    public static void decryptFileSymmetric(String inputFile, String outputFile, String keyFile) throws Exception {
        // ������ ������������� ����� �� �����
        ObjectInputStream keyInputStream = new ObjectInputStream(new FileInputStream(keyFile));
        SecretKey symmetricKey = (SecretKey) keyInputStream.readObject();
        keyInputStream.close();

        // ������������� ����� � �������������� ������������� �����
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, symmetricKey);

        FileInputStream inputStream = new FileInputStream(inputFile);
        byte[] inputBytes = new byte[(int) new File(inputFile).length()];
        inputStream.read(inputBytes);

        byte[] outputBytes = cipher.doFinal(inputBytes);

        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(outputBytes);

        inputStream.close();
        outputStream.close();
    }
    /**
     * ��������� ���� � �������������� �������������� �����.
     *
     * @param inputFile  ���� � �������������� �����
     * @param outputFile  ���� � �����, � ������� ����� ��������� �������������� ����������
     * @param privateKeyFile  ���� � �����, ����������� �������� ����
     * @throws Exception ���� ��������� ������ � �������� ������������
     */
    public static void decryptFileAsymmetric(String inputFile, String outputFile, String privateKeyFile) throws Exception {
        // ������ ��������� ����� �� �����
        ObjectInputStream keyInputStream = new ObjectInputStream(new FileInputStream(privateKeyFile));
        PrivateKey privateKey = (PrivateKey) keyInputStream.readObject();
        keyInputStream.close();

        // ������������� ����� � �������������� ��������� �����
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        FileInputStream inputStream = new FileInputStream(inputFile);
        byte[] inputBytes = new byte[(int) new File(inputFile).length()];
        inputStream.read(inputBytes);

        byte[] outputBytes = cipher.doFinal(inputBytes);

        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(outputBytes);

        inputStream.close();
        outputStream.close();
    }
}
