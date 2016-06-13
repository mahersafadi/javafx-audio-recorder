/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hods.meeting.recorder.impl;

import com.hods.meeting.recorder.AudioPlayer;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import org.slf4j.LoggerFactory;

/**
 *
 * @author maher
 */
public class AudioPlayerImpl extends AbstractAudioHandler implements AudioPlayer {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(AudioPlayerImpl.class);
    private Handler handler;
    private Thread t;
    private Boolean b = new Boolean(true);

    public AudioPlayerImpl() {

    }

    @Override
    public void play(byte[] b) {
        synchronized (b) {
            if (handler == null) {
                logger.info("first time");
                handler = new Handler(b);
            } else {
                handler.audio = b;
            }
            logger.info("Player is initialized");
            if (handler.alive) {
                handler.alive = false;
            }
            logger.info("Try to stop previous track if there is");
            if (t != null) {
                try {
                    t.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(AudioPlayerImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            t = new Thread(handler);
            t.setDaemon(true);
            t.start();
            logger.info("Playing current record is done");
        }
    }

    private class Handler implements Runnable {

        private boolean alive = true;
        private byte[] audio;

        public Handler(byte[] audio) {
            this.audio = audio;
        }

        @Override
        public void run() {
            try {
                InputStream input = new ByteArrayInputStream(audio);
                final AudioFormat format = getFormat();
                final AudioInputStream ais
                        = new AudioInputStream(input, format,
                                audio.length / format.getFrameSize());
                DataLine.Info info = new DataLine.Info(
                        SourceDataLine.class, format);
                final SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
                line.open(format);
                line.start();
                int bufferSize = (int) format.getSampleRate()
                        * format.getFrameSize();
                byte buffer[] = new byte[bufferSize];
                int count;
                while ((count = ais.read(buffer, 0, buffer.length)) != -1) {
                    if (count > 0) {
                        line.write(buffer, 0, count);
                    }
                }
                line.drain();
                ais.close();
                line.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            t = null;
            System.out.println("finish playing");
        }
    }
}
