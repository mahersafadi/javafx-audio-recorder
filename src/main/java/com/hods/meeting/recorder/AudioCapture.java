/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hods.meeting.recorder;

/**
 * responsible about capturing the audio and stop then get it. <br/>
 * you can also toggle the pause using {@link AudioCapture#pause(boolean)
 * to controle the audio catpturing
 *
 * @author maher
 */
public interface AudioCapture {
    /**
     * Start new capturing
     */
    void startAudioCapturing();
    
    /**
     * pause/continue audio capturing 
     * @param b 
     */
    void pause(boolean b);
    
    /**
     * stop and return the audio captured bytes
     * @return 
     */
    byte[] stopAndGet();
}
