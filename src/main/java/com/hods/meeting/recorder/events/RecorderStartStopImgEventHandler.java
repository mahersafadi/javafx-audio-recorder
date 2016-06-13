/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hods.meeting.recorder.events;

import com.hods.meeting.recorder.Recorder;
import com.hods.meeting.recorder.impl.RecordingState;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author maher
 */
public class RecorderStartStopImgEventHandler implements EventHandler<Event>{
    private final Logger logger = LoggerFactory.getLogger(RecorderStartStopImgEventHandler.class);
    private Recorder recorder;
    
    public RecorderStartStopImgEventHandler(Recorder recorder){
        this.recorder = recorder;
    }
    
    @Override
    public void handle(Event event) {
        if(event instanceof MouseEvent){
            MouseEvent e = (MouseEvent)event;
            if(e.isPrimaryButtonDown()){
                logger.info("com.hods.meeting.recorder.events.RecorderStartStopImgEventHandler.handle()");
                if(this.recorder.getRecordingState() == RecordingState.INITIAL){
                    this.recorder.startNewRecording();                    
                }
                else{
                    this.recorder.stopRecording();
                }
            }
        }
    }
}
