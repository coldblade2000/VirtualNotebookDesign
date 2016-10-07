package com.twotowerstudios.virtualnotebookdesign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mikepenz.materialdrawer.DrawerBuilder;

public class MainActivity extends AppCompatActivity {

    //Implement https://github.com/mikepenz/MaterialDrawer
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new DrawerBuilder().withActivity(this).build();
    }
}
