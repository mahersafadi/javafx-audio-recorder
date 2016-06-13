/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hods.meeting.recorder.events;

import com.hods.meeting.recorder.Recorder;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author maher
 */
public class RecorderPauseImgEventHandler implements EventHandler<Event>{
    private final Logger logger = LoggerFactory.getLogger(RecorderPauseImgEventHandler.class);
    private Recorder recorder;
    public RecorderPauseImgEventHandler(Recorder recorder){
        this.recorder = recorder;
    }
    @Override
    public void handle(Event event) {
        if(event instanceof MouseEvent){
            MouseEvent e = (MouseEvent)event;
            if(e.isPrimaryButtonDown()){
                logger.info("toggle the pause of the recording");
                recorder.togglePauseRecording();
            }
        }
    }
    
}
