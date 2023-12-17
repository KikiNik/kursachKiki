
package com.example.demo;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * ������� ����� ����������, �������������� �� ������ Application.
 */
public class HelloApplication extends Application {

    /**
     * ����� ������� ����������, ������� ��������� ������������� �� ����� FXML � ���������� ��� �� �����.
     *
     * @param stage ������ ���� Stage ��� ����������� �����
     * @throws IOException ���� ��� �������� ������������� �������� �������� � ������/�������
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 682, 400);
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * ����� main, ����������� ����������.
     *
     * @param args ��������� ��������� ������
     */
    public static void main(String[] args) {
        launch();
    }
}