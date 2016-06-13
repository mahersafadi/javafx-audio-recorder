/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hods.meeting.recorder;

import com.hods.meeting.recorder.impl.RecordingElem;

/**
 *
 * @author maher
 */
public interface AudioFileWriter {

    public void write(Long meetingId, RecordingElem recordingElem);
    
}
