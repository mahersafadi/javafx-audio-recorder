/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hods.meeting;

import com.hods.meeting.recorder.Recorder;
import com.hods.meeting.recorder.impl.RecorderFactory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 *
 * @author maher
 */
public class MeetingController implements Initializable{
    @FXML
    protected ImageView recorderTimerImg;
    
    @FXML
    protected ImageView recorderStartStopImg;
    
    @FXML
    protected ImageView recorderPauseImg;
    
    @FXML
    protected Pane recordingListMainPane;
    
    @FXML
    protected Label recoringTimerLabel;
    
    @FXML
    protected Button saveMeeting;
    
    private Recorder recorder;
    
    public MeetingController() {
        
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initilizeRecorder();
        initlizeMeeting();
    }

    private void initilizeRecorder() {
        recorder = RecorderFactory.get();
        recorder.initRecorderTimerImgEvent(recorderTimerImg);
        recorder.initRecorderStartStopImg(recorderStartStopImg);
        recorder.initRecorderPauseImg(recorderPauseImg);
        recorder.initRecordingsMainPane(recordingListMainPane);
        recorder.setRecoringTimerLabel(recoringTimerLabel);
    }

    private void initlizeMeeting() {
        saveMeeting.addEventHandler(EventType.ROOT, new EventHandler<Event>(){
            @Override
            public void handle(Event event) {
                if(event instanceof MouseEvent){
                    MouseEvent me = (MouseEvent)event;
                    if(me.isPrimaryButtonDown()){
                        recorder.save(1L);
                    }
                }
            }
        });
    }
}
