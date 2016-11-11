package com.twotowerstudios.virtualnotebookdesign.MainMenu;

import android.animation.ObjectAnimator;
import android.content.Intent;
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
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.twotowerstudios.virtualnotebookdesign.BookLight.BookLight;
import com.twotowerstudios.virtualnotebookdesign.BookLight.BookLightAdapter;
import com.twotowerstudios.virtualnotebookdesign.Misc.FirstBookLightOffsetDecoration;
import com.twotowerstudios.virtualnotebookdesign.Misc.SharedPrefs;
import com.twotowerstudios.virtualnotebookdesign.NotebookSelection.NotebookSelection;
import com.twotowerstudios.virtualnotebookdesign.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //https://github.com/mikepenz/MaterialDrawer

    private AccountHeader accountHeader;
    private FloatingActionButton fab1, fabShoot, fabImage, fabPage;
    private RecyclerView BookLightRecyclerView;
    private RecyclerView.Adapter BookLightAdapter;
    //private RecyclerView.LayoutManager CommonBooksCardLayoutManager;

    private List<BookLight> bookLightList;

    boolean isMainfabOpen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		SharedPrefs.setBoolean(getApplicationContext(), "debug", true);

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
                    ObjectAnimator openFirstSubfab = ObjectAnimator.ofFloat(fabShoot, View.TRANSLATION_Y, 200,0); openFirstSubfab.start();
                    ObjectAnimator openSecondSubfab = ObjectAnimator.ofFloat(fabImage, View.TRANSLATION_Y, 400,0); openSecondSubfab.start();
                    ObjectAnimator openThirdSubfab = ObjectAnimator.ofFloat(fabPage, View.TRANSLATION_Y, 600, 0); openThirdSubfab.start();
                    ObjectAnimator rotateMainfab = ObjectAnimator.ofFloat(fab1, View.ROTATION, 0, 135); rotateMainfab.start();

                } else if(isMainfabOpen){
                    isMainfabOpen = false;
                    ObjectAnimator rotateMainfab = ObjectAnimator.ofFloat(fab1, View.ROTATION, 135, 270); rotateMainfab.start();
                    ObjectAnimator closeFirstSubfab = ObjectAnimator.ofFloat(fabShoot, View.TRANSLATION_Y, 0,200); closeFirstSubfab.start();
                    ObjectAnimator closeSecondSubfab = ObjectAnimator.ofFloat(fabImage, View.TRANSLATION_Y, 0,400); closeSecondSubfab.start();
                    ObjectAnimator closeThirdSubfab = ObjectAnimator.ofFloat(fabPage, View.TRANSLATION_Y, 0,600); closeThirdSubfab.start();
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
        PrimaryDrawerItem diNotebooks= new PrimaryDrawerItem().withName("Notebooks").withDescription("Full list of notebooks");
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
						new PrimaryDrawerItem().withName("Main Menu").withIdentifier(1),
						new PrimaryDrawerItem().withName("Notebooks").withDescription("Full list of notebooks").withIdentifier(2),
						new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("I'm secondary #1").withDescription("I'm a bit more faded"),
                        new SecondaryDrawerItem().withName("I'm secondary #2").withDescription("I'm also just as faded as my brother, but I'm very long cause fuck it")

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        /**if (drawerItem != null) {
                         Intent intent = null;
                         if (drawerItem.getIdentifier() == 1) {
                         intent = new Intent(DrawerActivity.this, CompactHeaderDrawerActivity.class);
                         }*/
                        if (drawerItem != null){
                            Intent intent = null;
                            if (position == 1){

                            }else if (position == 2){
                                intent = new Intent(MainActivity.this, NotebookSelection.class);
                            }
                            if (intent != null){
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(intent);
                            }
                        }
                        return false;
                    }
                })

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
