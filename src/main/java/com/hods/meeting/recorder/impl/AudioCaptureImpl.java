/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hods.meeting.recorder.impl;

import com.hods.meeting.recorder.AudioCapture;
import java.io.ByteArrayOutputStream;
import java.util.logging.Level;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import org.slf4j.*;

/**
 *
 * @author maher
 */
public class AudioCaptureImpl extends AbstractAudioHandler implements AudioCapture {

    protected final Logger logger = LoggerFactory.getLogger(AudioCaptureImpl.class);

    protected Capture capture;
    protected Thread t;

    @Override
    public void startAudioCapturing() {
        logger.info("Start capturing");
        capture = new Capture();
        t = new Thread(capture);
        t.setDaemon(true);
        t.start();
    }

    @Override
    public void pause(boolean b) {
        logger.info("Toggle pause to " + b);
        capture.pause = b;
    }

    @Override
    public byte[] stopAndGet() {
        try {
            logger.info("Stop audio capturing");
            capture.alive = false;
            t.join();
            return capture.out.toByteArray();
        } catch (InterruptedException ex) {
            logger.info("Stop audio catpuring is interrupted: " + ex.getMessage());
        }
        return null;
    }

    private class Capture implements Runnable {

        boolean alive = true;
        boolean pause = false;
        ByteArrayOutputStream out;

        @Override
        public void run() {
            try {
                final AudioFormat format = getFormat();
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                final TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
                line.open(format);
                line.start();
                int bufferSize = (int) format.getSampleRate() * format.getFrameSize();
                byte buffer[] = new byte[bufferSize];
                out = new ByteArrayOutputStream();
                while (alive) {
                    int count = line.read(buffer, 0, buffer.length);
                    if (count > 0 && !pause) {
                        out.write(buffer, 0, count);
                    }
                }
                out.close();
                line.close();
            } catch (Exception ex) {
                logger.error("error during audio capturing ", ex);
            }
        }
    }
}
