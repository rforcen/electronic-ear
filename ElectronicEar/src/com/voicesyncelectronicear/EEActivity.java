package com.voicesyncelectronicear;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.voicesync.fileselector.FileSelectorActivity;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class EEActivity extends Activity {
	TouchPanel 		tpPitch,tpBalance,tpNoise;
	Mixer			mixer;
	EESessionFiler 	sessionFiler;
	EditText		edSecs, edName; ImageButton ibPlay; // controls

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ee);

		init();
	}
	private void init() {
		tpPitch		=setPanel(R.id.tpPitch, 	0	,20000,	"Ruby");
		tpBalance	=setPanel(R.id.tpBalance, 	-1	,1,		"Orange");
		tpNoise		=setPanel(R.id.tpNoise, 	0	,1, 	"Bronze");
		
		edSecs		=(EditText)findViewById(R.id.edDuration);
		edName		=(EditText)findViewById(R.id.edName);
		ibPlay		=(ImageButton)findViewById(R.id.ibPlay);
		
		sessionFiler=new EESessionFiler(this);
		sessionFiler.loadDefault();
	}
	private TouchPanel setPanel(int id, float min, float max, String colName) {
		TouchPanel tp=(TouchPanel)findViewById(id); 		tp.setRangeColor(min	,max,	colName);
		return tp;
	}
	// task bar event click 
	public void onPlayClick(View v) {
		if (mixer==null) {
			mixer=new Mixer(tpPitch.getCuts(), tpBalance.getCuts(), tpNoise.getCuts(), Float.valueOf(edSecs.getText().toString()) );
			mixer.startPlaying();
			ibPlay.setImageResource(android.R.drawable.ic_media_pause);
		} else {
			mixer.stopPlaying();
			mixer=null;
			ibPlay.setImageResource(android.R.drawable.ic_media_play);
		}
	}
	public void onLoadClick(View v) { sessionFiler.doFilePicker(); }
	public void onSaveClick(View v) { if (sessionFiler.save(edName.getText().toString())) mess("session saved on file\n\n"+sessionFiler.getfName()); }

	private void stopPlaying() {	if (mixer!=null) mixer.stopPlaying();	mixer=null; }
	private void mess(String m) { Toast.makeText(this, m, Toast.LENGTH_SHORT).show();  }
	
	@Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		sessionFiler.getResult(requestCode, resultCode, data);
		edName.setText(sessionFiler.name);
	}

	@Override public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_ee, menu);
		return true;
	}
	@Override  public boolean onOptionsItemSelected(MenuItem item)  {
        switch (item.getItemId()) {
        case R.id.menu_new: tpPitch.initData(); tpBalance.initData(); tpNoise.initData();  return true;
        case R.id.menu_about: this.startActivity(new Intent(this, AboutActivity.class)); return true;
        }
        return false;
    }
	@Override protected void onPause()	  {super.onPause(); stopPlaying(); } 
	@Override protected void onResume() {super.onResume();} // app. resume
	@Override protected void onStop()   {super.onStop();  } // app stop

	class EESessionFiler extends SessionFiler { // SessionFiler extender
		EESessionFiler(Context context) {super(context);}

		@Override public void writer(FileWriter f) {
			float[][]params={	tpPitch.getCuts(), tpBalance.getCuts(), tpNoise.getCuts() };
			try {	
				f.write(edSecs.getText().toString()+"\n");	
				for (int i=0; i<params.length; i++)
					for (int j=0; j<params[i].length; j++) f.write(String.valueOf(params[i][j])+"\n");
			} catch (IOException e) {}
		}
		@Override public void reader(BufferedReader f) {
			float[][]params={	tpPitch.getCuts(), tpBalance.getCuts(), tpNoise.getCuts() };
			try {	
				edSecs.setText(f.readLine());
				for (int i=0; i<params.length; i++)
					for (int j=0; j<params[i].length; j++) params[i][j]=Float.valueOf(f.readLine());
			} catch (IOException e) {}
		}
		@Override String setExtension() {	return "eef";	}
	}
}

