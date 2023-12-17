
package com.example.demo;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Главный класс приложения, унаследованный от класса Application.
 */
public class HelloApplication extends Application {

    /**
     * Метод запуска приложения, который загружает представление из файла FXML и отображает его на сцене.
     *
     * @param stage объект типа Stage для отображения сцены
     * @throws IOException если при загрузке представления возникли проблемы с вводом/выводом
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
     * Метод main, запускающий приложение.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        launch();
    }
}