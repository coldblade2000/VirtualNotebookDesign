package com.twotowerstudios.virtualnotebookdesign;

import android.animation.ObjectAnimator;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.bumptech.glide.Glide;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //https://github.com/mikepenz/MaterialDrawer

    private AccountHeader accountHeader;
    private FloatingActionButton fab1, fabShoot, fabImage, fabPage;
    private RecyclerView BookLightRecyclerView, rvMainMenu;
    private RecyclerView.Adapter BookLightAdapter, rvMainMenuAdapter;
    //private RecyclerView.LayoutManager CommonBooksCardLayoutManager;

    private List<BookLight> bookLightList;
    //TODO: This is broken, it will only display CommonBooksCard, it can't display any other card. Fix this
    private List<CommonBooksCard> cardlist;
    boolean isMainfabOpen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //==============


        /**rvMainMenu = (RecyclerView) findViewById(R.id.rvMainMenu);

        final LinearLayoutManager rvMainMenuLayoutManager = new LinearLayoutManager(this);
        rvMainMenu.setLayoutManager(rvMainMenuLayoutManager);

        rvMainMenuAdapter = new MainMenuAdapter(this, cardlist);
        rvMainMenu.setAdapter(rvMainMenuAdapter);

        cardlist = new ArrayList<>();
        CommonBooksCard a = new CommonBooksCard(); cardlist.add(a);*/

           //    ============================

        BookLightRecyclerView = (RecyclerView) findViewById(R.id.rvCommonBooks);

        final LinearLayoutManager BookLightLayoutManager = new LinearLayoutManager(this);
        BookLightLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        BookLightRecyclerView.setLayoutManager(BookLightLayoutManager);

        bookLightList = new ArrayList<>();
        prepareBookLightList();

        BookLightAdapter = new BookLightAdapter(this, bookLightList);
        BookLightRecyclerView.setAdapter(BookLightAdapter);


        //==================
        isMainfabOpen = false;
        fab1 = (FloatingActionButton) findViewById(R.id.fabMain);
        fabShoot = (FloatingActionButton) findViewById(R.id.fabShoot);
        fabImage = (FloatingActionButton) findViewById(R.id.fabImage);
        fabPage = (FloatingActionButton) findViewById(R.id.fabPage);
        fab1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (!isMainfabOpen) {

                    fabShoot.show();
                    fabImage.show();
                    fabPage.show();
                    isMainfabOpen = true;
                    ObjectAnimator openFirstSubfab = ObjectAnimator.ofFloat(fabShoot, fabShoot.TRANSLATION_Y, 200,0); openFirstSubfab.start();
                    ObjectAnimator openSecondSubfab = ObjectAnimator.ofFloat(fabImage, fabImage.TRANSLATION_Y, 400,0); openSecondSubfab.start();
                    ObjectAnimator openThirdSubfab = ObjectAnimator.ofFloat(fabPage, fabPage.TRANSLATION_Y, 600, 0); openThirdSubfab.start();
                    ObjectAnimator rotateMainfab = ObjectAnimator.ofFloat(fab1, fab1.ROTATION, 0, 135); rotateMainfab.start();

                } else if(isMainfabOpen){
                    isMainfabOpen = false;
                    ObjectAnimator rotateMainfab = ObjectAnimator.ofFloat(fab1, fab1.ROTATION, 135, 270); rotateMainfab.start();
                    ObjectAnimator closeFirstSubfab = ObjectAnimator.ofFloat(fabShoot, fabShoot.TRANSLATION_Y, 0,200); closeFirstSubfab.start();
                    ObjectAnimator closeSecondSubfab = ObjectAnimator.ofFloat(fabImage, fabImage.TRANSLATION_Y, 0,400); closeSecondSubfab.start();
                    ObjectAnimator closeThirdSubfab = ObjectAnimator.ofFloat(fabPage, fabPage.TRANSLATION_Y, 0,600); closeThirdSubfab.start();
                    fabShoot.hide();
                    fabImage.hide();
                    fabPage.hide();
                }
            }


        });
        /**PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Primary one");
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("Secondary One");*/
        final IProfile h1 = new ProfileDrawerItem().withName("Header 1");
        final IProfile h2 = new ProfileDrawerItem().withName("Header 2");
        final IProfile h3 = new ProfileDrawerItem().withName("Header 3");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        h1,h2,h3
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        if (profile == h1){
                            Glide.with(getApplicationContext()).load(R.drawable.header).into(accountHeader.getHeaderBackgroundView());
                        } else if (profile == h2){
                            Glide.with(getApplicationContext()).load(R.drawable.header2).into(accountHeader.getHeaderBackgroundView());
                        } else if (profile == h3){
                            Glide.with(getApplicationContext()).load(R.drawable.header3).into(accountHeader.getHeaderBackgroundView());
                        }
                        return false;
                    }
                })
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
        Glide.with(this).load(R.drawable.header2).into(accountHeader.getHeaderBackgroundView());
        /**Color.parseColor("#00FFFF")*/
		DisplayMetrics displayMetrics = new DisplayMetrics();
		FirstBookLightOffsetDecoration firstBookLightOffsetDecoration = new FirstBookLightOffsetDecoration((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics()));
   		BookLightRecyclerView.addItemDecoration(firstBookLightOffsetDecoration);
    }
    private void prepareBookLightList(){
        BookLight a = new BookLight("Spanish", "BLUE"); bookLightList.add(a);
        a = new BookLight("English", "RED"); bookLightList.add(a);
        a = new BookLight("Chemistry", "GREEN"); bookLightList.add(a);
        a = new BookLight("Art", "MAGENTA"); bookLightList.add(a);
        a = new BookLight("ECL", "YELLOW"); bookLightList.add(a);
        a = new BookLight("Design and Technology", "CYAN"); bookLightList.add(a);
        a = new BookLight("Physics", "#555555"); bookLightList.add(a);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        accountHeader.getHeaderBackgroundView().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        super.onPostCreate(savedInstanceState);
    }
}
