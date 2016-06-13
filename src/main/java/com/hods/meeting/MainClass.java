/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hods.meeting;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author maher
 */
public class MainClass extends Application{
    public static void main(String [] args){
        System.out.println("com.hods.meeting.MainClass.main()");
        Application.launch(MainClass.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("com.hods.meeting.MainClass.start()");
        Parent root = FXMLLoader.load(this.getClass().getClassLoader().getResource("fxml/recorder/audo_recorder.fxml"));
        primaryStage.setTitle("Audio Recorder");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
