package com.voicesyncelectronicear;

import java.util.Locale;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class ColorSlide extends ImageView {
	float val, max=100;
	int h,w,color=0, iGrad;
	String colorName="";
	Canvas cnv; Paint paint=new Paint();
	private boolean setShader=false;

	public ColorSlide(Context context, AttributeSet attrs) { 
		super(context, attrs); 
		paint.setStyle(Style.FILL);
	}
	@Override protected void onDraw(Canvas cnv){
		this.cnv=cnv;	getRect();
		paint.setShader(Gradients.linGrad(colorName, w, h)); 
		cnv.drawRoundRect(new RectF(0, 0, (float)w*val/max, h), 8, 8, paint); 	// the bar
		if (val!=0) {
			String tx=String.format(Locale.ENGLISH, "%.1f", val); 			// numeric value
			paint.setShader(null);
			paint.setColor(Color.BLACK);
			cnv.drawText(tx,0, h-textHeight(tx), paint);
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
		float px=event.getX(); if(px>=w) px=w; if (px<=0) px=0;
		val = max*px/(float)w;
		invalidate();
		return true;
	}
	private void getRect() { Rect rec=cnv.getClipBounds();	w=rec.width();	h=rec.height(); }
	public void setMax(float max) {this.max=max;}
	public void setVal(float val) {this.val=val; invalidate(); }
	public void setColor(int color) {	this.color=color; paint.setColor(color);	invalidate();	}
	public float getVal() { return val; }
}

class GradientColor {
	private String Name;
	private int ColorA, ColorB, ColorC, ColorD, ColorE;
	private int TextColor;
	public GradientColor(String name, int colorA, int colorB, int colorC, int colorD, int colorE, int textColor){
		Name = name; ColorA = colorA;  ColorB = colorB; ColorC = colorC;ColorD = colorD;ColorE = colorE;
		setTextColor(textColor);
	}
	public String getName() {return Name;	}
	public void setName(String name) {	Name = name;}
	public int getColorA() {return ColorA;	}
	public void setColorA(int colorA) {ColorA = colorA;}
	public int getColorB() {return ColorB;}
	public void setColorB(int colorB) {ColorB = colorB;}
	public int getColorC() {return ColorC;	}
	public void setColorC(int colorC) {ColorC = colorC;}
	public int getColorD() {return ColorD;}
	public void setColorD(int colorD) {ColorD = colorD;}
	public int getColorE() {return ColorE;}
	public void setColorE(int colorE) {ColorE = colorE;}
	public int getTextColor() {return TextColor;}
	public void setTextColor(int textColor) {TextColor = textColor;}
}
class Gradients {
	public static GradientColor[]AllGradients() {
		GradientColor []list = {
				new GradientColor("Ruby", 0xFFf5d2db, 0xFFeba4b8, 0xFFcc1c4d, 0xFF9e032e, 0xFF7a0222, 0xFFffffff),
				new GradientColor("Fire engine", 0xFFfacdd0, 0xFFeda4a7, 0xFFce2029, 0xFF9e030b, 0xFF73040b, 0xFFffffff),
				new GradientColor("Burgundy", 0xFFead0d2, 0xFFd4a0a4, 0xFF94121c, 0xFF6e060d, 0xFF4a090e, 0xFFffffff),
				new GradientColor("Brick red", 0xFFf4ded6, 0xFFe3ad99, 0xFFa32a00, 0xFF891700, 0xFF611000, 0xFFffffff),
				new GradientColor("Vermillion", 0xFFe6d1cf, 0xFFcca29f, 0xFFe34234, 0xFFad2416, 0xFF80170e, 0xFFffffff),
				new GradientColor("Red", 0xFFffd4cc, 0xFFffa899, 0xFFff2600, 0xFFbf1d00, 0xFF801300, 0xFFffffff),
				new GradientColor("Carmine", 0xFFf2ccd4, 0xFFe599aa, 0xFFff0038, 0xFFbf002a, 0xFF80001c, 0xFFffffff),
				new GradientColor("Orange red", 0xFFfadaca, 0xFFf0b092, 0xFFff4e00, 0xFFc72b00, 0xFF802700, 0xFFffffff),
				new GradientColor("Dark orange", 0xFFfaded0, 0xFFf6bea0, 0xFFe85c12, 0xFFb53300, 0xFF802200, 0xFFffffff),
				new GradientColor("Pumkin", 0xFFffe3d1, 0xFFffc8a3, 0xFFff7518, 0xFFaa3e00, 0xFFb13e1e, 0xFFffffff),
				new GradientColor("Orange", 0xFFffecd6, 0xFFffcf99, 0xFFff8f00, 0xFFf26c0d, 0xFFaa3e00, 0xFFffffff),
				new GradientColor("Orange peel", 0xFFffeccc, 0xFFffd999, 0xFFff9f00, 0xFFf27500, 0xFFc75000, 0xFFffffff),
				new GradientColor("Coral", 0xFFffe6db, 0xFFffcdb6, 0xFFff8249, 0xFFf06937, 0xFF993b17, 0xFFffffff),
				new GradientColor("Terracota", 0xFFf7e7e0, 0xFFecc3b2, 0xFFd06a3e, 0xFFb55632, 0xFF823113, 0xFFffffff),
				new GradientColor("Brown", 0xFFeadbcc, 0xFFd5b799, 0xFF964b00, 0xFF713a00, 0xFF553000, 0xFFffffff),
				new GradientColor("Chocolate", 0xFFebdfd9, 0xFFccaba1, 0xFF703422, 0xFF521c13, 0xFF4a140b, 0xFFffffff),
				new GradientColor("Sienna", 0xFFe8d7cf, 0xFFd1afa0, 0xFF8c3611, 0xFF69270d, 0xFF461a09, 0xFFffffff),
				new GradientColor("Dark coffee", 0xFFe0d7d4, 0xFFc1afa8, 0xFF633826, 0xFF4f1a03, 0xFF361305, 0xFFffffff),
				new GradientColor("Sepia", 0xFFe3d9cf, 0xFFc7b39f, 0xFF73420e, 0xFF593000, 0xFF402200, 0xFFffffff),
				new GradientColor("Umber", 0xFFeae0d9, 0xFFd5c2b3, 0xFF956642, 0xFF704d32, 0xFF4b3321, 0xFFffffff),
				new GradientColor("Tans", 0xFFf3e8df, 0xFFe6d2bf, 0xFFc18e60, 0xFF996a3d, 0xFF73502e, 0xFFffffff),
				new GradientColor("Bronze", 0xFFf6e5d4, 0xFFedcca9, 0xFFd27f29, 0xFFa65c11, 0xFF693906, 0xFFffffff),
				new GradientColor("Amber", 0xFFfef3d7, 0xFFfce19b, 0xFFffb300, 0xFFed7710, 0xFF9b4200, 0xFF693906),
				new GradientColor("Gold", 0xFFfff9d6, 0xFFfff099, 0xFFffd900, 0xFFffa400, 0xFFe68e15, 0xFF693906),
				new GradientColor("Sunglow", 0xFFfffae9, 0xFFffeaa5, 0xFFffca1d, 0xFFf5a71c, 0xFFe68e15, 0xFF7a4608),
				new GradientColor("Lemon", 0xFFfffdcc, 0xFFfffa99, 0xFFfff300, 0xFFffd400, 0xFFe3ac00, 0xFFa75400),
				new GradientColor("Pear", 0xFFf6f8d1, 0xFFeef2a4, 0xFFd4de1b, 0xFFb5bf07, 0xFF8f9601, 0xFF828a16),
				new GradientColor("Lime", 0xFFeafdcf, 0xFFcbfa87, 0xFFc1f900, 0xFF9fde23, 0xFF7fb900, 0xFF156615),
				new GradientColor("Chlorophyle", 0xFFebf4d3, 0xFFd7e9a8, 0xFF9cc925, 0xFF7ba60d, 0xFF5d8005, 0xFF49811f),
				new GradientColor("Foliage", 0xFFe2e8d1, 0xFFc4d1a4, 0xFF6c8b1b, 0xFF57730e, 0xFF3e5404, 0xFFffffff),
				new GradientColor("Olive", 0xFFe4eacb, 0xFFd6dfb2, 0xFF697800, 0xFF4a5200, 0xFF2f3600, 0xFFffffff),
				new GradientColor("Army", 0xFFdbdcd2, 0xFFb7baa5, 0xFF515918, 0xFF343b04, 0xFF26290f, 0xFFffffff),
				new GradientColor("Grass", 0xFFebfcdf, 0xFFc2f79e, 0xFF5ba825, 0xFF377d00, 0xFF00570a, 0xFFffffff),
				new GradientColor("Kelly green", 0xFFdbf1cc, 0xFFb6e299, 0xFF49b700, 0xFF378900, 0xFF255c00, 0xFFffffff),
				new GradientColor("Forest", 0xFFd2e7d2, 0xFFa4cfa4, 0xFF1c881c, 0xFF156615, 0xFF0e440e, 0xFFffffff),
				new GradientColor("Green", 0xFFcce5d8, 0xFF99cbb2, 0xFF007e3e, 0xFF005b2a, 0xFF004420, 0xFFffffff),
				new GradientColor("Emerald", 0xFFcceae4, 0xFF99d6c8, 0xFF009876, 0xFF007259, 0xFF004c3b, 0xFFffffff),
				new GradientColor("Turquoise", 0xFFd6f3f0, 0xFF99e1da, 0xFF00b3a2, 0xFF009488, 0xFF005b5a, 0xFFffffff),
				new GradientColor("Teal", 0xFFdeebee, 0xFFadced4, 0xFF338594, 0xFF006779, 0xFF004d5b, 0xFFffffff),
				new GradientColor("Cold blue", 0xFFcceaf1, 0xFF99d5e2, 0xFF0095b7, 0xFF007089, 0xFF004b5c, 0xFFffffff),
				new GradientColor("Cyaan", 0xFFcceffc, 0xFF99dff9, 0xFF00aeef, 0xFF0090bf, 0xFF006d90, 0xFFffffff),
				new GradientColor("Aquamarine", 0xFFd3f6fd, 0xFFa6edfc, 0xFF21d1f7, 0xFF00a2d9, 0xFF0079a8, 0xFFffffff),
				new GradientColor("Ice", 0xFFf8fdff, 0xFFf1faff, 0xFFdbf3ff, 0xFFa8e3ff, 0xFF71c2eb, 0xFF25679d),
				new GradientColor("Peace", 0xFFd7eaf8, 0xFFafd4f1, 0xFF3794dd, 0xFF136db5, 0xFF0b5794, 0xFFffffff),
				new GradientColor("Denim", 0xFFcce0f2, 0xFF99c1e5, 0xFF0064bf, 0xFF004f96, 0xFF003b71, 0xFFffffff),
				new GradientColor("Steel blue", 0xFFd7e6f0, 0xFFb0cde2, 0xFF3983b6, 0xFF246594, 0xFF154b70, 0xFFffffff),
				new GradientColor("Azure", 0xFFcce7ff, 0xFF99ceff, 0xFF0085ff, 0xFF0064bf, 0xFF004b8f, 0xFFffffff),
				new GradientColor("Royal blue", 0xFFd4e2f9, 0xFFa9c6f4, 0xFF2870e3, 0xFF1e54aa, 0xFF143872, 0xFFffffff),
				new GradientColor("Navy blue", 0xFFccd5e5, 0xFF99aacb, 0xFF003494, 0xFF00205d, 0xFF00163e, 0xFFffffff),
				new GradientColor("Indigo", 0xFFdad1e6, 0xFFb6a3ce, 0xFF481884, 0xFF33036e, 0xFF270059, 0xFFffffff),
				new GradientColor("Violet", 0xFFe5d4eb, 0xFFcba9d7, 0xFF7c279b, 0xFF5d1d74, 0xFF3e144e, 0xFFffffff),
				new GradientColor("Fuschia", 0xFFf5d8e9, 0xFFebb1d3, 0xFFce3c92, 0xFFab156d, 0xFF8f0758, 0xFFffffff),
				new GradientColor("Carnation pink", 0xFFffeef4, 0xFFffdde9, 0xFFffaac9, 0xFFcc7695, 0xFF9c546f, 0xFFffffff),
				new GradientColor("Salmon", 0xFFffeaed, 0xFFffd5da, 0xFFff95a3, 0xFFd9737f, 0xFFa65059, 0xFFffffff),
				new GradientColor("French rose", 0xFFfedde7, 0xFFfdbbd0, 0xFFfb5589, 0xFFc43b67, 0xFFa11d47, 0xFFffffff),
				new GradientColor("Pink", 0xFFfee5ef, 0xFFffd3df, 0xFFf42376, 0xFFd02261, 0xFFa8025b, 0xFFffffff),
				new GradientColor("Magenta", 0xFFffd6e9, 0xFFffacd2, 0xFFff308f, 0xFFbf246b, 0xFF801848, 0xFFffffff),
				new GradientColor("Cerise", 0xFFf9d8df, 0xFFf4b2c0, 0xFFe33e61, 0xFFb3213e, 0xFF86192f, 0xFFffffff) };

		return list;
	}
	public static GradientDrawable NewGradient(GradientColor gc) {
		GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {gc.getColorE(), gc.getColorC() });
		gd.setCornerRadius(0f);
		gd.setStroke(1, gc.getColorE());
		gd.setCornerRadius(3f);
		return gd;
	}
	public static LinearGradient linGrad(int i, int w, int h) {
		GradientColor gc=AllGradients()[i];
		LinearGradient lgrad = new LinearGradient(0,0, w,h, new int[]{ gc.getColorA(),gc.getColorB(),gc.getColorC(),gc.getColorD(),gc.getColorE()},null,TileMode.CLAMP);
		return lgrad;
	}
	public static LinearGradient linGrad(String name, int w, int h) {
		int i=0, n=AllGradients().length; 
		for (i=0; i<n; i++) 
			if(name.compareToIgnoreCase(AllGradients()[i].getName())==0) break;
		if (i>=n) i=0; // not found 
		GradientColor gc=AllGradients()[i];
		LinearGradient lgrad = new LinearGradient(0,0, w,h, new int[]{ gc.getColorA(),gc.getColorB(),gc.getColorC(),gc.getColorD(),gc.getColorE()},null,TileMode.CLAMP);
		return lgrad;
	}
	public static LinearGradient linGrad(int i, int w, int h, RectF rect) {
		GradientColor gc=AllGradients()[i];
		LinearGradient lgrad = new LinearGradient(rect.left*w,rect.top*h, rect.right*w,rect.bottom*h, 
				new int[]{ gc.getColorA(),gc.getColorB(),gc.getColorC(),gc.getColorD(),gc.getColorE()},null,TileMode.CLAMP);
		return lgrad;
	}
	public static LinearGradient linGrad(int i, int w, int h, Rect rect) {
		GradientColor gc=AllGradients()[i];
		LinearGradient lgrad = new LinearGradient(rect.left*w,rect.top*h, rect.right*w,rect.bottom*h, 
				new int[]{ gc.getColorA(),gc.getColorB(),gc.getColorC(),gc.getColorD(),gc.getColorE()},null,TileMode.CLAMP);
		return lgrad;
	}
}