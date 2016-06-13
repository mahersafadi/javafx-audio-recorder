/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hods.meeting.recorder.impl;

import com.hods.meeting.recorder.AudioFileWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author maher
 */
public class AudioFileWriterImpl extends AbstractAudioHandler implements AudioFileWriter {

    private final Logger logger = LoggerFactory.getLogger(AudioFileWriterImpl.class);

    @Override
    public void write(Long meetingId, RecordingElem recordingElem) {
        String dir = this.getClass().getClassLoader().getResource(".").getPath();
        if (dir.startsWith("/")) {
            dir = dir.substring(1);
        }
        if (meetingId != null) {
            dir += meetingId;
        } else {
            dir += System.currentTimeMillis();
        }
        logger.info("Saving directory is :" + dir);
        java.io.File f = new java.io.File(dir);
        f.mkdirs();
        logger.info("Direcotry is created if it is not exist");
        AudioFormat frmt = this.getFormat();
        AudioInputStream ais = new AudioInputStream(
                new ByteArrayInputStream(recordingElem.getData()), frmt,
                recordingElem.getData().length / frmt.getFrameSize()
        );
        try {
            AudioSystem.write(ais, AudioFileFormat.Type.WAVE,
                    new File(dir + "/" + recordingElem.getName() + ".wav")
            );
            logger.info("File " + recordingElem.getName() + ".wav is saved");
        } catch (Exception e) {
            logger.error("An error accured during save the file " + recordingElem.getName() + ".wav", e);
        }
    }
}
