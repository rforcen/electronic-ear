package com.voicesyncelectronicear;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.voicesync.fileselector.FileSelectorActivity;

abstract class SessionFiler { // supports Session load/save/file picker  
	static final int REQUEST_PICK_FILE=2; // activities	
	Context context;
	String defaultFileName="default", name=defaultFileName, fName;
	String HD=Environment.getExternalStorageDirectory().getPath(), extension=""; // without '.'

	public SessionFiler(Context context) {this.context=context; this.extension=setExtension(); }
	
	abstract void reader(BufferedReader f);
	abstract void writer(FileWriter f);
	abstract String setExtension();
	
	public void setName(String name) {this.name=name; } // no '.' required
	public String getfName() {return fName;}
	public void getResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case REQUEST_PICK_FILE			: 	load(data.getStringExtra(FileSelectorActivity.EXTRA_FILE_PATH)); break;
			}
		}
	}
	public void loadDefault() { setName(defaultFileName); load(buildFileName()); 	}
	public boolean load(String fn) {
		boolean ok=false;
		try {
			fName=fn;
			name=getSessionName();
			BufferedReader f=new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(fn))));
			reader(f);
			f.close();
			ok=true;
		} 
		catch (FileNotFoundException e1) {}  
		catch (IOException e) {}
		return ok;
	}
	private String getSessionName() {
		final int lastPeriodPos = fName.lastIndexOf('.');
	    return (lastPeriodPos <= 0) ? fName : new File(fName.substring(0, lastPeriodPos)).getName();
	}
	public boolean save() {
		boolean ok=false;
		fName=buildFileName();
		try {
			FileWriter f=new FileWriter(fName);
			writer(f);
			f.close();
			ok=true;
		} 
		catch (FileNotFoundException e1) {}  catch (IOException e) {}
		return ok;
	}
	private String buildFileName() { return HD + "/" + name + "." + extension; }

	public boolean save(String name) {	setName(name); return save();	}
	public void doFilePicker() {
		final Intent intent = new Intent(context, FileSelectorActivity.class);
		intent.putExtra(FileSelectorActivity.EXTRA_ACCEPTED_FILE_EXTENSIONS, new String[]{extension});
		((Activity)context).startActivityForResult(intent, REQUEST_PICK_FILE);
	}
}


