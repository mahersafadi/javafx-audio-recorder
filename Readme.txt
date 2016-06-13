Here is a description about meeting recorder and how it is used:
Assumption: com.hods.meeting.MeetingController is attached to the fxml file
New Mode: when new meeting Mode
	use this method in MeetingController.initialize:

	private void initilizeRecorder() {
	        //create recorder instance
	        recorder = RecorderFactory.get();

	        //pass the imagesViews to it.
	        recorder.initRecorderTimerImgEvent(recorderTimerImg);
	        recorder.initRecorderStartStopImg(recorderStartStopImg);
	        recorder.initRecorderPauseImg(recorderPauseImg);
	        recorder.initRecordingsMainPane(recordingListMainPane);
	        //and also pass the label of timer to it
	        recorder.setRecoringTimerLabel(recoringTimerLabel);
	    }

	    on meeting 
	    	- save (on transaction):
	    		save the meeting and then call recorder.getRecordings to save the trackes
	    		after that, Optionally, call recorder.save(meetingID) to save tracks in .wav file. but make sure to remove hibernate.session from the method body to avoid double saving in database

	    	- save not one trasaction, call recorder.save(meetingID) then session must be got inside it

Edit Mode: when display a previous meeting Mode to edit it
	use this method in MeetingController.initialize:

	private void initilizeRecorder() {
	        //create recorder instance
	        recorder = RecorderFactory.get();

	        //pass the imagesViews to it.
	        recorder.initRecorderTimerImgEvent(recorderTimerImg);
	        recorder.initRecorderStartStopImg(recorderStartStopImg);
	        recorder.initRecorderPauseImg(recorderPauseImg);
	        recorder.initRecordingsMainPane(recordingListMainPane);
	        //and also pass the label of timer to it
	        recorder.setRecoringTimerLabel(recoringTimerLabel);

	        recorder.addRecordings(tracks from database that are already exist)
	        void refereshRecordings(); //to display them in the tracks section in the screen
	    }

Injoy...
Maher
