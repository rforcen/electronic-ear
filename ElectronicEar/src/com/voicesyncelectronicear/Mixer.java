package com.voicesyncelectronicear;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.AsyncTask;
import android.util.FloatMath;

public class Mixer {
	static float[]pitch, balance, noise;
	static float secs;

	static  int nCuts;
	public static final  float sampleRate=22050*2; // sound support
	Sound sound;


	public Mixer(float[]pitch, float[]balance, float[]noise, float secs) {
		this.pitch=pitch; this.balance=balance; this.noise=noise; this.secs=secs;
		nCuts=pitch.length;
		sound=new Sound();
	}
	public  void stopPlaying() 	{ sound.stopPlaying(); }
	public  void startPlaying() 	{ sound.startPlaying();}

	static class Generator {
		static float t, x, dt, dx, nvals;
		static float vR, vL;
		static short shR, shL;

		public static void init() {
			dt= 2f * (float)Math.PI/sampleRate;
			nvals=sampleRate*secs;
			dx=secs/nvals;
			x=t=0;
		}
		private static  short[] generateSamples(short []buff) {
			int lb=buff.length;
			for (int i=0; i<lb; i+=2) {
				genSample(t);
				buff[i+0]=shL;	buff[i+1]=shR;

				t+=dt; x+=dx;	
				if (x>secs) t=x=0; // inc time 't' incL=freqL*2.*M_PI/sampleRate;
			}
			return buff;
		}
		private static  void genSample(float t) { // generate a 16bit sample pair in (shL, shR)
			int ix=ixCut(x);
			float vsound=FloatMath.sin(pitch[ix] * t); 	// sound -1..1
			float vnoise=noise[ix]*(2f*(float)Math.random()-1); 	// noise 0..1 -> -1..1
			float vbal=balance[ix], fL=1, fR=1;						// -1=L, +1=R
			if (vbal==0) fL=fR=1; 									// LR factor according to balance
			else if (vbal<0) 	{ fL=Math.abs(vbal); 	fR=0; }
			else				{ fL=0;					fR=Math.abs(vbal); }

			float vs=(vsound + vnoise)/2;							// mix sound + noise

			vL = fL * vs * Short.MAX_VALUE;	shL=(short)vL; 			// apply balance and convert to short
			vR = fR * vs * Short.MAX_VALUE;	shR=(short)vR;
		}
		private static  int ixCut(float t) {	return (int)(((t/secs)%1f) * nCuts);	}
	}

	class playSoundTask extends AsyncTask<Void, Void, Void> {
		AudioTrack track;
		int bufferSize=1024;
		short []buff=new short[bufferSize]; // stereo buffer loop

		public void initAudio() {	// init the audio system
			Generator.init();
			int minSize = AudioTrack.getMinBufferSize( (int)sampleRate, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT );        
			track = new AudioTrack( AudioManager.STREAM_MUSIC, (int)sampleRate, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT,	 minSize, AudioTrack.MODE_STREAM);
			track.play();
		}
		@Override protected void onPreExecute() { 	initAudio();}
		@Override protected Void doInBackground(Void... params) { // gen & write
			int ibuff=0;
			while(sound.isPlaying) {
				track.write( Generator.generateSamples(buff), 0, bufferSize );
			}
			return null;
		}
	}

	class Sound {
		boolean isPlaying=false;
		playSoundTask ps;

		void startPlaying() {
			isPlaying=true;
			ps=new playSoundTask(); 
			ps.execute();
		}
		void stopPlaying() {
			isPlaying=false;
			if (ps!=null) ps.cancel(true);
		}
		void switchPlaying() {
			if (isPlaying)  stopPlaying();
			else		    startPlaying();
		}
	}
	
}

