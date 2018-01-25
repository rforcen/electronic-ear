package com.voicesyncelectronicear;

import java.util.ArrayList;
import android.util.FloatMath;

public class Bezier {
	private float sqr(float x) { return x*x; }
	private float pow(float x, int n) { float y; for (y=1; n!=0; n--) y*=x;	return y;	}
	private float distance(float x1,float y1, float x2,float y2) { return FloatMath.sqrt(sqr(x1-x2) + sqr(y1-y2)); 	}      
	private float fact(int k) {
		if(k==0 || k==1)	return 1;
		else				return k * fact(k-1);
	}
	private float Bernstain(int i, int n, float t) {	return fact(n) / (fact(i) * fact(n-i))* pow(t, i) * pow(1-t, n-i);	}                            
	//Computes a point's coordinates for a value of t
	private float[]P(float t, float[]points){
		float x=0,y=0;
		int n = points.length/2-1;
		for(int i=0; i <= n; i++){
			x += points[i*2+0] * Bernstain(i, n, t);
			y += points[i*2+1] * Bernstain(i, n, t);
		}                
		return new float[]{x,y};
	}
	
	public float []bezier(float[]points, int n){ //Computes the drawing/support points for the Bezier curve [x0 y0 x1 y1 ... ]
		ArrayList<Float>temp=new ArrayList<Float>();
		float step=1f/n;
		for(int i=0; i<n; i++){
			float[]p = P((float)i*step, points);
			temp.add(p[0]); temp.add(p[1]);
		}
		int sz=temp.size();
		float[]vtmp=new float[sz];
		for (int i=0; i<sz; i++) vtmp[i]=temp.get(i);
		return vtmp;
	}

}
