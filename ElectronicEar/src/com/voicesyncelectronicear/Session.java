package com.voicesyncelectronicear;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.AsyncTask;

public class Session {

	public static float secs=3, SampleRate=22050; // sound support
	static int bufferSize=1024;
	static short [][]Sbuff; // stereo buffer loop
	static boolean isPlaying=false;
	static playSoundTask ps;

	
	static void startPlaying() {
			isPlaying=true;
			ps=new playSoundTask(); 
			ps.execute();
	}
	static void stopPlaying() {
			isPlaying=false;
			if (ps!=null) ps.cancel(true);
	}
	static void switchPlaying() {
		if (isPlaying)  stopPlaying();
		else		    startPlaying();
	}
	/////////////////////////
	// play sound Async
	/////////////////////////
	static class playSoundTask extends AsyncTask<Void, Void, Void> {
		AudioTrack track;
		public void initAudio() {	// init the audio system
			int minSize = AudioTrack.getMinBufferSize( (int)SampleRate, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT );        
			track = new AudioTrack( AudioManager.STREAM_MUSIC, (int)SampleRate, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT,	 minSize, AudioTrack.MODE_STREAM);
			track.play();
		}
		@Override protected void onPreExecute() { 	initAudio();}
		@Override protected Void doInBackground(Void... params) { // gen & write
			int ibuff=0;
			while(isPlaying) {
				track.write( Sbuff[ibuff], 0, bufferSize );
			}
			return null;
		}
	}
}
