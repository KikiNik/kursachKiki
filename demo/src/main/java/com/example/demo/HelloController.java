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
 * ���������� ��� ���������� ����������� ������ � ��������� �� ������.
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
            "������������", "�������������"
    );
    /**
     * �������������� ������ ������ ���� ����������.
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
     * ���������� ��� ������� ������ "���������" ��� ���������� � �������� ����� �� ������.
     *
     * @param event ������� ������� ������.
     * @throws Exception ��������� ��� ������ � �������� ���������� ��� �������� �� ������.
     */
    @FXML
    void kikiSend(ActionEvent event) throws Exception {
        String selectedFilePath = chosenFile.getText();
        if (selectedFilePath.equals("��������� ����")) {
            displayError("�� �� ������� ����!!");
        } else {
            String selectedEncryptionType = chooseType.getValue();
            if ("������������".equals(selectedEncryptionType)) {
                k++;
                encryptFileSymmetric(selectedFilePath, "symm1.txt", "keySymmetric.txt");
                client.sendFileToServer("localhost", 12345, "symm1.txt");
                client.sendFileToServer("localhost", 12345, "KeySymmetric.txt");
                System.out.println("����");
            } else if ("�������������".equals(selectedEncryptionType)) {
                k++;
                encryptFileAsymmetric(selectedFilePath, "assym1.txt","keyAsymmetric.txt", "KeyAssymetricclose.txt");
                client.sendFileToServer("localhost", 12345, "assym1.txt");
                client.sendFileToServer("localhost", 12345, "KeyAssymetricclose.txt");
                System.out.println("����");
            } else {
                displayError("�� �� ������� ��� ����������!");
            }
        }
    }
    /**
     * ���������� ��� ������� ������ "������� ����" ��� �������� ����� ��� ����������.
     *
     * @param event ������� ������� ������.
     */
    @FXML
    void kikiUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("�������� ����");
        File selectedFile = fileChooser.showOpenDialog(null); // ��������� ������ ������ ����� � ��������� ��������� ���� � ����������
        if (selectedFile != null) {
            chosenFile.setText(selectedFile.getPath()); // ���������� ���� � ���������� ����� � Label
        }
    }
    /**
     * ���������� ��� ������� ������ "��������� ����" ��� �������� ���������� ���� ���������� �� ������.
     *
     * @param event ������� ������� ������.
     */
    @FXML
    void kikiSendKey(ActionEvent event) {
        if(k == 0){
            displayError("������ ������������ ����!");
            k = 0;
        }else {
            String selectedEncryptionType = chooseType.getValue();
            if ("������������".equals(selectedEncryptionType)) {
                client.sendStringToServer("localhost", 12345, "Symm");
                System.out.println("����");
            } else if ("�������������".equals(selectedEncryptionType)) {
                client.sendStringToServer("localhost", 12345, "Assym");
                System.out.println("����");
            } else {
                displayError("�� �� ��������� ���������� ������ �����!");
            }
        }
    }
    /**
     * ���������� ��������� �� ������ � ������ ������������ �������� ������������.
     *
     * @param errorMessage ��������� �� ������.
     */
    private void displayError(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage, ButtonType.OK);
        alert.showAndWait();
    }
    public static void main(String[] args) {

    }

}
