/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hods.meeting.recorder.events;

import com.hods.meeting.recorder.Recorder;
import com.hods.meeting.recorder.impl.RecordingElem;
import java.text.SimpleDateFormat;
import java.util.Optional;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author maher
 */
public class SelectedAudioOptionsEventHandler implements EventHandler<Event> {

    private final Logger logger = LoggerFactory.getLogger(SelectedAudioOptionsEventHandler.class);
    private Dialog dialog;
    private Recorder recorder;
    private ButtonType ok;
    private ButtonType close;
    private ButtonType delete;
    private TextField tf;
    private Label period;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public SelectedAudioOptionsEventHandler(Recorder recorder) {
        this.recorder = recorder;
        initDialog();
    }

    @Override
    public void handle(Event event) {
        if (event instanceof MouseEvent) {
            MouseEvent me = (MouseEvent) event;
            if (me.isPrimaryButtonDown()) {
                ImageView optionsImage = (ImageView) event.getSource();
                RecordingElem elem = recorder.getRecordings().get(Integer.valueOf(optionsImage.getId()));
                tf.setText(elem.getName());
                period.setText(dateFormat.format(elem.getPeriod()));
                try {
                    logger.info("display the options");
                    Optional<ButtonType> res = dialog.showAndWait();
                    boolean mustRefresh = false;
                    if (res.get() == ok) {
                        logger.info("apply change track details");
                        elem.setName(tf.getText());
                        mustRefresh = true;
                    } else if (res.get() == delete) {
                        logger.info("apply delete the record");
                        recorder.getRecordings().remove(elem);
                        mustRefresh = true;
                    }
                    if (mustRefresh) {
                        logger.info("refresh the list after changes");
                        recorder.refereshRecordings();
                    }
                    dialog.close();
                } catch (Throwable t) {
                    logger.warn("Normal problem during close the dialg, "+t.getMessage());
                }
            }
        }
    }

    private void initDialog() {
        dialog = new Dialog();
        dialog.setTitle("Recoding item detail");
        ok = new ButtonType("OK");
        close = new ButtonType("close");
        delete = new ButtonType("delete");
        dialog.getDialogPane().getButtonTypes().addAll(ok, delete, close);

        GridPane grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(2);
        tf = new TextField();
        period = new Label();
        grid.add(new Label("Track name:"), 0, 0);
        grid.add(tf, 0, 1);
        grid.add(new Label("Period"), 1, 0);
        grid.add(period, 1, 1);
        dialog.getDialogPane().setContent(grid);
    }
}
