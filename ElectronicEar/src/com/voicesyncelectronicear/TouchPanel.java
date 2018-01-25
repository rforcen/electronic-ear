package com.voicesyncelectronicear;

import java.util.Arrays;
import java.util.Locale;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class TouchPanel extends ImageView {
	float min=0, max=1; int nCuts=20; float[]cuts=new float[nCuts]; // parameters
	
	float val;
	int h,w,color=0, iGrad, th, tw;
	String colorName="";
	float dx;
	Canvas canvas; Paint paint=new Paint();
	Path path=new Path();

	public TouchPanel(Context context, AttributeSet attrs) { 
		super(context, attrs); 
		th=textHeight("0"); tw=textWidth("0");
		paint.setStyle(Style.FILL);
	}
	public void initData() {Arrays.fill(cuts,0); refresh();}
	private void refresh() {invalidate();}
	@Override protected void onDraw(Canvas canvas) {
		this.canvas=canvas;	getRect();
		drawLines();
		drawScale();
//		drawText();
	}
	private void drawScale() {
		paint.setShader(null);
		String sMax=String.format(Locale.ENGLISH,"%.1f", max), 
				sMin=String.format(Locale.ENGLISH,"%.1f", min),
				s2=String.format(Locale.ENGLISH,"%.1f", (max+min)/2f);
		int slen=textWidth("0000000");
		
		canvas.drawText(sMax, 0, th, paint);
		canvas.drawText(sMin, 0, h, paint);
		canvas.drawText(s2, 0, h/2, paint);
		
		paint.setColor(Color.LTGRAY);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(1);
		paint.setPathEffect(new DashPathEffect(new float[]{1,4},0));
		for (int i=0; i<h; i+=th) canvas.drawLine(slen, i, w, i, paint);
		for (int i=0; i<w-slen; i+=w/nCuts) canvas.drawLine(slen+i, 0, slen+i, h, paint);
		paint=new Paint();
	}
	private void drawBezier() {
		float[]coords=new float[(nCuts+2)*2]; int ic=1; // calc coords x,y starting from 0,0
		float x=0,y=0;
		for (int i=0; i<nCuts; i++) {
			x=dx*i; y=h-(cuts[i]/max)*(float)h + textHeight("0")/2;
			
			coords[ic*2+0]=x; coords[ic*2+1]=y; ic++;
		}
		coords[ic*2+0]=w; coords[ic*2+1]=h;

		int nbz=100; float[]bzCoords=new Bezier().bezier(coords, nbz); // bezier coords
		paint.setShader(null);
		path.reset();
		path.moveTo(0, h);
		for (int i=0; i<nbz; i++) path.lineTo(bzCoords[i*2+0], bzCoords[i*2+1]);
		path.lineTo(w, y); path.lineTo(w, h);
		path.close();
		paint.setShader(Gradients.linGrad(colorName, w, h));
		canvas.drawPath(path, paint);
		drawText();
	}
	private void drawLines() {
		paint.setShader(null);
		path.reset();
		path.moveTo(0, h);
		float x=0,y=0;
		for (int i=0; i<nCuts; i++) {
			x=dx*i; y=h-(cuts[i]/max)*(float)h + textHeight("0")/2;
			path.lineTo(x, y);
		}
		path.lineTo(w, y); path.lineTo(w, h);
		path.close();
		paint.setShader(Gradients.linGrad(colorName, w, h));
		canvas.drawPath(path, paint);
	}
	public void setNsegs(int nCuts) {this.nCuts=nCuts;}
	private void drawText() {
		paint.setShader(null);
		for (int i=0; i<nCuts; i++) {
			float x=0,y=0;
			x=dx*i; y=h-(cuts[i]/max)*(float)h + textHeight("0")/2;
			if (y!=0) {
				String tx=String.format(Locale.ENGLISH, "%.1f", cuts[i]); 			// numeric value
				paint.setColor(Color.BLACK);
				canvas.drawText(tx, x-textWidth(tx), y+textHeight(tx), paint);
			}
		}
	}
	private int textWidth(String s) {
		Rect r=new Rect();
		paint.getTextBounds(s, 0, s.length(), r);
		return r.width();		
	}
	private int textHeight(String s) {
		Rect r=new Rect();
		paint.getTextBounds(s, 0, s.length(), r);
		return r.height();		
	}
	@Override public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);

		float px=event.getX(), py=event.getY(); // get & filter touch point 
		if(px>=w) px=w; if (px<=0) px=0;
		if(py>=h) py=h; if (py<=0) py=0;

		int npnt=(int)(px/dx);
		val = max*(h-py)/(float)h; // coord Y to val
		
		cuts[npnt]=val; 
		
		invalidate();
		return true;
	}
	private void getRect() { Rect rec=canvas.getClipBounds();	w=rec.width();	h=rec.height(); dx=(float)w/(float)nCuts;}
	public void setMax(float max) {this.max=max;}
	public void setVal(float val) {this.val=val; invalidate(); }
	public void setColor(int color) {	this.color=color; paint.setColor(color);	invalidate();	}
	public float getVal() { return val; }
	public void setRange(float min, float max) {	this.min=min; this.max=max;	}
	public void setRangeColor(float min, float max, String colorName) {	setRange(min,max); this.colorName=colorName;}
	public float[]getCuts() { return cuts; }
	public void setCuts(float[]cuts) {this.cuts=cuts; invalidate();}
}
