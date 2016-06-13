/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hods.meeting.recorder;

import com.hods.meeting.recorder.impl.RecordingState;
import com.hods.meeting.recorder.impl.RecordingElem;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * The main interface for recorder, it is used to prepare the recorder
 * implementer.<br/>
 * functionalities:<br/>
 * Part1: initialize the display components using methods starts with init<br/>
 * Part2: control the recording using the methods start, stop and toggle
 * recoding<br/>
 * Part3: Control the recordings list by refresh the list, save it in DB and
 * wave files
 *
 * @author maher
 */
public interface Recorder {

    void initRecorderTimerImgEvent(ImageView recorderTimerImg);

    void initRecorderStartStopImg(ImageView recorderStartStopImg);

    void initRecorderPauseImg(ImageView recorderPauseImg);

    void initRecordingsMainPane(Pane pane);

    /**
     * Run recording thread for new recording, start the timer on the screen
     */
    void startNewRecording();

    /**
     * effected incase recording has been started, if it is recording then it
     * will pause and vise-versa. It stopes/continues the recording thread and
     * timer
     */
    void togglePauseRecording();

    /**
     * Effected incase recording has been started. It stop the recording thread
     * then add it to recordings items and then displays it in the recording
     * list
     */
    void stopRecording();

    void setRecoringTimerLabel(Label recoringTimerLabel);

    /**
     * Recording is an automate. initial -> recording <-> pause -> initial, so
     * this method returns the current status
     *
     * @return
     */
    RecordingState getRecordingState();

    /**
     * used when we want to update the meeting and set the recording items in
     * database, an also used when we want to add new already recorded item
     *
     * @param recordings
     */
    void addRecordings(RecordingElem... recordings);

    /**
     * Get the recording list (new ones and database items)
     *
     * @return
     */
    List<RecordingElem> getRecordings();

    /**
     * Clean the old recording list and redisplay it again
     */
    void refereshRecordings();

    /**
     * Save in database and write in wave files
     *
     * @param meetingId
     */
    void save(Long meetingId);
}
