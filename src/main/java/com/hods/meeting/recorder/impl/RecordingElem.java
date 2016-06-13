/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hods.meeting.recorder.impl;

import java.util.Date;

/**
 * Represents the model, 
 * @author maher
 */
public class RecordingElem implements java.io.Serializable{
    private Long dbId;
    private String name;
    private byte[] data;
    private Date period;
    
    public RecordingElem(String name, byte[] data, Date period) {
        this.name = name;
        this.data = data;
        this.period = period;
    }
    
    public RecordingElem(Long id, String name, byte[] data, Date period) {
        this(name, data, period);
        dbId = id;
    }

    public String getName() {
        return name;
    }

    public byte[] getData() {
        return data;
    }

    public Date getPeriod() {
        return period;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }
}
