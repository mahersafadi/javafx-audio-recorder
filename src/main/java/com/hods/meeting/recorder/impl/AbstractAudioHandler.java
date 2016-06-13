/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hods.meeting.recorder.impl;

import javax.sound.sampled.AudioFormat;

/**
 * Format of audio record is got from this class, it is very useful in
 * recording, playing and saving as wave file
 *
 * @author maher
 */
public class AbstractAudioHandler {

    protected AudioFormat getFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
                bigEndian);
    }
}
