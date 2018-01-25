package com.voicesyncelectronicear;

import android.os.Bundle;
import android.app.Activity;
import android.webkit.WebView;

public class AboutActivity extends Activity {
    @Override   public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ((WebView) findViewById(R.id.webView1)).loadUrl("file:///android_asset/html/ee.html");
    }
}
