/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hods.meeting.recorder.impl;

import com.hods.Application;
import com.hods.meeting.recorder.AudioCapture;
import com.hods.meeting.recorder.AudioFileWriter;
import com.hods.meeting.recorder.AudioPlayer;
import com.hods.meeting.recorder.Recorder;
import com.hods.meeting.recorder.events.PlaySelectedAudioEventHandler;
import com.hods.meeting.recorder.events.RecorderPauseImgEventHandler;
import com.hods.meeting.recorder.events.RecorderStartStopImgEventHandler;
import com.hods.meeting.recorder.events.SelectedAudioOptionsEventHandler;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author maher
 */
class RecorderImpl implements Recorder {

    protected final Logger logger = LoggerFactory.getLogger(RecorderImpl.class);
    protected ImageView recorderTimerImg;
    protected Label recoringTimerLabel;
    protected RecordingState state;
    protected Pane recordingsMainPane;
    protected Timeline timeline;
    protected Calendar timer;
    protected SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    protected List<RecordingElem> recordnigs;
    protected AudioCapture audioCapture;
    protected Image recordingImage;
    protected Image nonRecordingImage;
    protected Image rowPlayImage;
    protected Image rowOptionImage;
    protected final PlaySelectedAudioEventHandler playSelectedAudioEvent;
    protected final SelectedAudioOptionsEventHandler selectedAudioOptionsEventHandler;
    AudioFileWriter audioFileWriter;

    public RecorderImpl(AudioCapture audioCapture, AudioPlayer audioPlayer) {
        logger.info("Begin Initializing the recorder");
        state = RecordingState.INITIAL;
        recordingImage = new Image(this.getClass().getClassLoader().getResourceAsStream("fxml/recorder/1.png"));
        nonRecordingImage = new Image(this.getClass().getClassLoader().getResourceAsStream("fxml/recorder/2.png"));
        rowPlayImage = new Image(this.getClass().getClassLoader().getResourceAsStream("fxml/recorder/play.png"));
        rowOptionImage = new Image(this.getClass().getClassLoader().getResourceAsStream("fxml/recorder/option.png"));
        recordnigs = new CopyOnWriteArrayList<>();
        this.audioCapture = audioCapture;
        playSelectedAudioEvent = new PlaySelectedAudioEventHandler(this, audioPlayer);
        selectedAudioOptionsEventHandler = new SelectedAudioOptionsEventHandler(this);
        audioFileWriter = new AudioFileWriterImpl();
        logger.info("Recorder is initialized successfully");
    }

    @Override
    public void initRecorderTimerImgEvent(ImageView recorderTimerImg) {
        this.recorderTimerImg = recorderTimerImg;
    }

    @Override
    public void initRecorderStartStopImg(ImageView recorderStartStopImg) {
        logger.info("appyly start stop recorder event over the start/stop imagview");
        recorderStartStopImg.addEventHandler(EventType.ROOT, new RecorderStartStopImgEventHandler(this));
    }

    @Override
    public void initRecorderPauseImg(ImageView recorderPauseImg) {
        logger.info("apply pause/play event against the pause/play imageview");
        recorderPauseImg.addEventHandler(EventType.ROOT, new RecorderPauseImgEventHandler(this));
    }

    @Override
    public void initRecordingsMainPane(Pane pane) {
        this.recordingsMainPane = pane;
    }

    @Override
    public void setRecoringTimerLabel(Label recoringTimerLabel) {
        this.recoringTimerLabel = recoringTimerLabel;
    }

    @Override
    public void startNewRecording() {
        logger.info("start new recording command received");
        this.state = RecordingState.RECORDING;
        this.recorderTimerImg.setImage(recordingImage);
        timer = Calendar.getInstance();
        timer.set(0, 0, 0, 0, 0, 0);
        recoringTimerLabel.setText(dateFormat.format(timer.getTime()));
        timeline = new Timeline(new KeyFrame(
                Duration.seconds(1), ev -> {
            if (state == RecordingState.RECORDING) {
                timer.set(Calendar.SECOND, timer.get(Calendar.SECOND) + 1);
                recoringTimerLabel.setText(dateFormat.format(timer.getTime()));
            }
        }
        ));
        timeline.setCycleCount(Animation.INDEFINITE);
        logger.info("Start audio capturing process");
        audioCapture.startAudioCapturing();
        logger.info("start screen recording timer");
        timeline.play();
    }

    @Override
    public void stopRecording() {
        logger.info("Stop recording command received");
        this.state = RecordingState.INITIAL;
        this.recorderTimerImg.setImage(nonRecordingImage);
        this.recoringTimerLabel.setText("");
        //stop recording thread and save the recording session in DB
        logger.info("Stop screen Timer");
        timeline.stop();
        logger.info("stop audio capturing process");
        byte[] res = audioCapture.stopAndGet();
        if (res != null) {
            this.recordnigs.add(new RecordingElem("Track" + (this.recordnigs.size() + 1), res, timer.getTime()));
            refereshRecordings();
        }
    }

    @Override
    public void togglePauseRecording() {
        logger.info("toggle pause command received");
        if (state == RecordingState.PAUSED) {
            state = RecordingState.RECORDING;
            logger.info("continue capturing");
            audioCapture.pause(false);
        } else if (state == RecordingState.RECORDING) {
            state = RecordingState.PAUSED;
            logger.info("Pause capturing");
            audioCapture.pause(true);
        }
    }

    @Override
    public synchronized RecordingState getRecordingState() {
        return this.state;
    }

    @Override
    public void addRecordings(RecordingElem... recordings) {
        if (recordings != null) {
            this.recordnigs.addAll(Arrays.asList(recordings));
        }
        refereshRecordings();
    }

    @Override
    public synchronized List<RecordingElem> getRecordings() {
        return this.recordnigs;
    }

    @Override
    public void refereshRecordings() {
        int index = 0;
        this.recordingsMainPane.getChildren().clear();
        double x = recordingsMainPane.getWidth();
        for (RecordingElem r : this.recordnigs) {
            AnchorPane curr = new AnchorPane();
            curr.setStyle("-fx-background-color:#2c7a9f");
            curr.setLayoutX(0.0);
            curr.setLayoutY(index * 18.0);
            ImageView playImage = new ImageView(rowPlayImage);
            playImage.setLayoutX(1.0);
            playImage.setId(String.valueOf(index));
            playImage.addEventHandler(EventType.ROOT, playSelectedAudioEvent);
            Label l = new Label(r.getName() + ", " + dateFormat.format(r.getPeriod()));
            l.setLayoutX(20.0);
            l.setTextFill(Paint.valueOf("white"));
            l.setFont(Font.font(9));
            ImageView optionImage = new ImageView(rowOptionImage);
            optionImage.setLayoutX(x - 18);
            optionImage.setId(String.valueOf(index));
            optionImage.addEventHandler(EventType.ROOT, selectedAudioOptionsEventHandler);
            curr.getChildren().add(playImage);
            curr.getChildren().add(l);
            curr.getChildren().add(optionImage);
            this.recordingsMainPane.getChildren().add(curr);
            index++;
        }
    }

    @Override
    public synchronized void save(Long meetingId) {
        Session sessoin = Application.getDatabase().getCurrentSession();
        for (RecordingElem recordingElem : this.recordnigs) {
            recordingElem = (RecordingElem) sessoin.save(recordingElem);
            audioFileWriter.write(meetingId, recordingElem);
        }
    }
}
