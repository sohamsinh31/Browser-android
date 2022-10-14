package com.example.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Second extends AppCompatActivity {
public String useragent = "Mozilla/5.0 (Linux; Android 12) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.5195.77 Mobile Safari/537.36";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        WebView wv1 = (WebView) findViewById(R.id.webb);
        wv1.setWebViewClient(new MyBrowser());
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        Context context = getApplicationContext();
        wv1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    WebView webView = (WebView) v;

                    switch(keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if (webView.canGoBack()) {
                                webView.goBack();
                                return true;
                            }
                            break;
                    }
                }

                return false;
            }
        });
        int duration = Toast.LENGTH_SHORT;
        try {
            wv1.getSettings().setLoadsImagesAutomatically(true);
            wv1.getSettings().setJavaScriptEnabled(true);
            wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            wv1.loadUrl("https://www.google.com/search?q="+url);
            wv1.getSettings().setUserAgentString(useragent);
            Toast toast = Toast.makeText(context, "viewing:-"+url, duration);
            toast.show();
        }
        catch (Exception e){
            Toast toast = Toast.makeText(context, "Enter valid url", duration);
            toast.show();
        }
    }
    public void callMain(View view){
        Intent i = new Intent(Second.this,Second.class);
        startActivity(i);
    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}