package com.twotowerstudios.virtualnotebookdesign;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

public class MainActivity extends AppCompatActivity {

    //Implement https://github.com/mikepenz/MaterialDrawer
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Primary one");
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("Secondary One");*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withSelectionListEnabledForSingleProfile(false)
                .build();
        new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(accountHeader)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("I'm primary #1").withDescription("I'm black and very pronounced"),
                        new PrimaryDrawerItem().withName("I'm primary #2").withDescription("I'm another black one"),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("I'm secondary #1").withDescription("I'm a bit more faded"),
                        new SecondaryDrawerItem().withName("I'm secondary #2").withDescription("I'm also just as faded as my brother, but I'm very long cause fuck it")

                )
                .build();
        Glide.with(this).load(R.drawable.header).into(accountHeader.getHeaderBackgroundView());
        /**Color.parseColor("#00FFFF")*/
        accountHeader.getHeaderBackgroundView().setColorFilter(Color.parseColor("#FF4081"), PorterDuff.Mode.OVERLAY);
    }
}
