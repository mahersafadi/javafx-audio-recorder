/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hods.meeting.recorder.impl;

import com.hods.meeting.recorder.Recorder;

/**
 *
 * @author maher
 */
public final class RecorderFactory {

    private RecorderFactory() {
    }
    
    private static class _C {
        static Recorder instance = new RecorderImpl(new AudioCaptureImpl(), new AudioPlayerImpl());
    }

    public static Recorder get() {
        return _C.instance;
    }
}
