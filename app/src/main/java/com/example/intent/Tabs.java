package com.example.intent;

import com.example.intent.tabs.Tab1Fragment;
import com.example.intent.tabs.Tab2Fragment;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.intent.SectionPageAdapter;

import java.util.Objects;

public class Tabs extends AppCompatActivity {

    Toolbar t1;
    int ii = 1;
    private ViewPager mViewPager;
    SectionPageAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabhost);

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        t1 = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(t1);

    }
    private void setSupportActionBar(android.widget.Toolbar toolbar) {
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
                Toast.makeText(getApplicationContext(),"mail",Toast.LENGTH_SHORT).show();
                adapter.addFragment(new Tab1Fragment(), "Tab2");
                adapter.notifyDataSetChanged();
        }
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "Tab1");
//        adapter.addFragment(new Tab2Fragment(), "Tab2");
        //Objects.requireNonNull(viewPager.getAdapter()).notifyDataSetChanged();
        viewPager.setAdapter(adapter);
    }

}