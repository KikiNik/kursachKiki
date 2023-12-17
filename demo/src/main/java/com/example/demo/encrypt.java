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
 * Класс для шифрования и дешифрования файлов с использованием симметричных и асимметричных ключей.
 */
public class encrypt {
    /**
     * Шифрует файл с использованием симметричного ключа.
     *
     * @param inputFile  путь к исходному файлу
     * @param outputFile  путь к файлу, в который будет сохранено зашифрованное содержимое
     * @param keyFile  путь к файлу, в который будет сохранен симметричный ключ
     * @throws Exception если возникает ошибка в процессе шифрования
     */
    public static void encryptFileSymmetric(String inputFile, String outputFile, String keyFile) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // Установка длины ключа (в битах)

        // Генерация ключа
        SecretKey symmetricKey = keyGen.generateKey();
        // Сохранение симметричного ключа в файл
        ObjectOutputStream keyOutputStream = new ObjectOutputStream(new FileOutputStream(keyFile));
        keyOutputStream.writeObject(symmetricKey);
        keyOutputStream.close();
        // Шифрование файла с использованием симметричного ключа
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
     * Шифрует файл с использованием асимметричного ключа.
     *
     * @param inputFile  путь к исходному файлу
     * @param outputFile  путь к файлу, в который будет сохранено зашифрованное содержимое
     * @param publicKeyFile  путь к файлу, в который будет сохранен открытый ключ
     * @param privateKeyFile путь к файлу, в который будет сохранен открытый ключ
     * @throws Exception если возникает ошибка в процессе шифрования
     */
    public static void encryptFileAsymmetric(String inputFile, String outputFile, String publicKeyFile, String privateKeyFile) throws Exception {
        // Генерация ключевой пары
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // Запись открытого ключа в файл
        ObjectOutputStream publicKeyOutputStream = new ObjectOutputStream(new FileOutputStream(publicKeyFile));
        publicKeyOutputStream.writeObject(publicKey);
        publicKeyOutputStream.close();

        // Запись закрытого ключа в файл
        ObjectOutputStream privateKeyOutputStream = new ObjectOutputStream(new FileOutputStream(privateKeyFile));
        privateKeyOutputStream.writeObject(privateKey);
        privateKeyOutputStream.close();

        // Шифрование файла с использованием открытого ключа
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        FileInputStream inputStream = new FileInputStream(inputFile);
        byte[] inputBytes = new byte[(int) new File(inputFile).length()];
        inputStream.read(inputBytes);
        byte[] outputBytes = cipher.doFinal(inputBytes);

        // Запись зашифрованных данных в файл
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(outputBytes);

        // Закрытие потоков
        inputStream.close();
        outputStream.close();
    }
    /**
     * Дешифрует файл с использованием симметричного ключа.
     *
     * @param inputFile  путь к зашифрованному файлу
     * @param outputFile  путь к файлу, в который будет сохранено расшифрованное содержимое
     * @param keyFile  путь к файлу, содержащему симметричный ключ
     * @throws Exception если возникает ошибка в процессе дешифрования
     */
    public static void decryptFileSymmetric(String inputFile, String outputFile, String keyFile) throws Exception {
        // Чтение симметричного ключа из файла
        ObjectInputStream keyInputStream = new ObjectInputStream(new FileInputStream(keyFile));
        SecretKey symmetricKey = (SecretKey) keyInputStream.readObject();
        keyInputStream.close();

        // Расшифрование файла с использованием симметричного ключа
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
     * Дешифрует файл с использованием асимметричного ключа.
     *
     * @param inputFile  путь к зашифрованному файлу
     * @param outputFile  путь к файлу, в который будет сохранено расшифрованное содержимое
     * @param privateKeyFile  путь к файлу, содержащему закрытый ключ
     * @throws Exception если возникает ошибка в процессе дешифрования
     */
    public static void decryptFileAsymmetric(String inputFile, String outputFile, String privateKeyFile) throws Exception {
        // Чтение закрытого ключа из файла
        ObjectInputStream keyInputStream = new ObjectInputStream(new FileInputStream(privateKeyFile));
        PrivateKey privateKey = (PrivateKey) keyInputStream.readObject();
        keyInputStream.close();

        // Расшифрование файла с использованием закрытого ключа
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
