package com.example.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static WeakReference<MainActivity> weakActivity;
    public String useragent = "Mozilla/5.0 (Linux; Android 12) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.5195.77 Mobile Safari/537.36";
    private DBHandler dbHandler;
    public String[] data = new String[100];
    public int n = 0;
    public ArrayAdapter<String> aa;

    public void setdata(String j){
        this.aa.add(j);
    }

    public static MainActivity getmInstanceActivity() {
        return weakActivity.get();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weakActivity = new WeakReference<>(MainActivity.this);
        AutoCompleteTextView edt = (AutoCompleteTextView) findViewById(R.id.text1);
        WebView wv1 = (WebView) findViewById(R.id.web2);
        wv1.setWebViewClient(new MyBrowser());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHandler = new DBHandler(this);
        ArrayList<String> n = dbHandler.readData();

        String [] stockArr = n.toArray(new String[0]);


        aa = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item,stockArr);
        edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edt.showDropDown();
                }

            }
        });

        edt.setThreshold(1);
        edt.setAdapter(aa);
        try {
            wv1.getSettings().setLoadsImagesAutomatically(true);
            wv1.getSettings().setJavaScriptEnabled(true);
            wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            wv1.loadUrl("https://news.google.com");
            wv1.getSettings().setUserAgentString(useragent);
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
            edt.setOnEditorActionListener((TextView.OnEditorActionListener) (v, actionId, event) -> {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    callSecondActivity(v);
                    handled = true;
                }
                return handled;
            });
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error occured here",Toast.LENGTH_SHORT).show();
        }
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    @SuppressLint("ResourceType")
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.xml.menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.mail:
                Toast m = Toast.makeText(getApplicationContext(),"mail",Toast.LENGTH_SHORT);
                m.show();
                Intent i = new Intent(MainActivity.this,Tabs.class);
                startActivity(i);
        }
        return true;
    }

    public void callSecondActivity(View view){
        AutoCompleteTextView edt = (AutoCompleteTextView) findViewById(R.id.text1);
        Intent i = new Intent(MainActivity.this,Second.class);
        String str = edt.getEditableText().toString();
       try {
            if(str.isEmpty()){
                Toast.makeText(this, "Please Enter your search first", Toast.LENGTH_SHORT).show();
                return;
            }
        //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        dbHandler = new DBHandler(this);
            dbHandler.addNewCourse(str);
            i.putExtra("url",str);
            startActivity(i);
        }
        catch (Exception e){
            Context context = getApplicationContext();
            CharSequence text = "Error occured";
            int duration = Toast.LENGTH_SHORT;

            Toast.makeText(context, e.getMessage(), duration).show();
        }
    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
