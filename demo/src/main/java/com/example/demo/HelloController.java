package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;

import static com.example.demo.encrypt.*;

/**
 * Контроллер для управления шифрованием файлов и отправкой на сервер.
 */
public class HelloController{

    @FXML
    private Button kikiSendKey;
    @FXML
    private TextField keyWrite;
    @FXML
    private Button kikiGet;
    @FXML
    private Button kikiGetKey;
    @FXML
    private Button keyUpload;
    @FXML
    private ComboBox<String> chooseType;
    private final ObservableList<String> encryptionTypes = FXCollections.observableArrayList(
            "Симметричный", "Ассиметричный"
    );
    /**
     * Инициализирует список выбора типа шифрования.
     */
    @FXML
    public void initialize() {
        chooseType.setItems(encryptionTypes);
    }
    @FXML
    private Label chosenFile;

    @FXML
    private Label keyWarning;


    @FXML
    private Button kikiSend;

    private int k = 0;

    @FXML
    private Button kikiUpload;
    @FXML
    private Label noCypherType;
    /**
     * Вызывается при нажатии кнопки "Отправить" для шифрования и отправки файла на сервер.
     *
     * @param event Событие нажатия кнопки.
     * @throws Exception Возникает при ошибке в процессе шифрования или отправки на сервер.
     */
    @FXML
    void kikiSend(ActionEvent event) throws Exception {
        String selectedFilePath = chosenFile.getText();
        if (selectedFilePath.equals("Выбранный файл")) {
            displayError("Вы не выбрали файл!!");
        } else {
            String selectedEncryptionType = chooseType.getValue();
            if ("Симметричный".equals(selectedEncryptionType)) {
                k++;
                encryptFileSymmetric(selectedFilePath, "symm1.txt", "keySymmetric.txt");
                client.sendFileToServer("localhost", 12345, "symm1.txt");
                client.sendFileToServer("localhost", 12345, "KeySymmetric.txt");
                System.out.println("Симм");
            } else if ("Ассиметричный".equals(selectedEncryptionType)) {
                k++;
                encryptFileAsymmetric(selectedFilePath, "assym1.txt","keyAsymmetric.txt", "KeyAssymetricclose.txt");
                client.sendFileToServer("localhost", 12345, "assym1.txt");
                client.sendFileToServer("localhost", 12345, "KeyAssymetricclose.txt");
                System.out.println("Асим");
            } else {
                displayError("Вы не выбрали тип шифрования!");
            }
        }
    }
    /**
     * Вызывается при нажатии кнопки "Выбрать файл" для загрузки файла для шифрования.
     *
     * @param event Событие нажатия кнопки.
     */
    @FXML
    void kikiUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл");
        File selectedFile = fileChooser.showOpenDialog(null); // открываем диалог выбора файла и сохраняем выбранный файл в переменную
        if (selectedFile != null) {
            chosenFile.setText(selectedFile.getPath()); // отображаем путь к выбранному файлу в Label
        }
    }
    /**
     * Вызывается при нажатии кнопки "Отправить ключ" для отправки выбранного типа шифрования на сервер.
     *
     * @param event Событие нажатия кнопки.
     */
    @FXML
    void kikiSendKey(ActionEvent event) {
        if(k == 0){
            displayError("Первым отправляется файл!");
            k = 0;
        }else {
            String selectedEncryptionType = chooseType.getValue();
            if ("Симметричный".equals(selectedEncryptionType)) {
                client.sendStringToServer("localhost", 12345, "Symm");
                System.out.println("Симм");
            } else if ("Ассиметричный".equals(selectedEncryptionType)) {
                client.sendStringToServer("localhost", 12345, "Assym");
                System.out.println("Асим");
            } else {
                displayError("Вы не закончили шифрование вашего файла!");
            }
        }
    }
    /**
     * Отображает сообщение об ошибке в случае некорректных действий пользователя.
     *
     * @param errorMessage Сообщение об ошибке.
     */
    private void displayError(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage, ButtonType.OK);
        alert.showAndWait();
    }
    public static void main(String[] args) {

    }

}
