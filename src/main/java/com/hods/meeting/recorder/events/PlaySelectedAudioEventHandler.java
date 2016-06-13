/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hods.meeting.recorder.events;

import com.hods.meeting.recorder.AudioPlayer;
import com.hods.meeting.recorder.Recorder;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * this is the responsible event for playing the previously recorded track.
 *
 * @author maher
 */
public class PlaySelectedAudioEventHandler implements EventHandler<Event> {

    private Logger logger = LoggerFactory.getLogger(PlaySelectedAudioEventHandler.class);
    private AudioPlayer audioPlayer;
    private Recorder recorder;

    public PlaySelectedAudioEventHandler(Recorder recorder, AudioPlayer audioPlayer) {
        this.recorder = recorder;
        this.audioPlayer = audioPlayer;
    }

    @Override
    public void handle(Event event) {
        if (event instanceof MouseEvent) {
            MouseEvent me = (MouseEvent) event;
            if (me.isPrimaryButtonDown()) {
                ImageView imageView = (ImageView) event.getSource();
                logger.info(imageView.getId());
                //get the id from the imageID then get the related item from the recoding list, 
                //and then pass the recorded bytes to the audio recorder interface
                audioPlayer.play(recorder.getRecordings().get(Integer.valueOf(imageView.getId())).getData());
            }
        }
    }
}
