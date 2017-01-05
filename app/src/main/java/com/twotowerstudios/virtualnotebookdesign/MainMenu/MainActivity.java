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
import com.twotowerstudios.virtualnotebookdesign.Misc.FirstBookLightOffsetDecoration;
import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;
import com.twotowerstudios.virtualnotebookdesign.Misc.SharedPrefs;
import com.twotowerstudios.virtualnotebookdesign.NotebookMain.NotebookMainActivity;
import com.twotowerstudios.virtualnotebookdesign.NotebookSelection.NotebookSelection;
import com.twotowerstudios.virtualnotebookdesign.Objects.Notebook;
import com.twotowerstudios.virtualnotebookdesign.R;

import org.parceler.Parcels;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BookLightAdapter.MainMenuToNotebook{

    //https://github.com/mikepenz/MaterialDrawer

    private AccountHeader accountHeader;
    RecyclerView bookLightRecyclerView;
    ArrayList<Notebook> notebookList;
    private FloatingActionButton fab1, fabShoot, fabImage, fabPage;
	//private RecyclerView.LayoutManager CommonBooksCardLayoutManager;


    boolean isMainfabOpen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		SharedPrefs.setBoolean(getApplicationContext(), "debug", true);

		if(Helpers.getStringFromFile("Notebooks.json", getApplicationContext()).equals("")){
			new File(getFilesDir(), "Notebooks.json");
		}
		/*if(InitNotebooks.isDebug(getApplicationContext())){
			Log.d("isDebugNoteSelect", "DEBUG MODE = true;");
			InitNotebooks.populateDebugBooks(getApplicationContext(), InitNotebooks.isDebug(getApplicationContext()));
		}*/
           //    ============================

		bookLightRecyclerView = (RecyclerView) findViewById(R.id.rvCommonBooks);

        final LinearLayoutManager BookLightLayoutManager = new LinearLayoutManager(this);
        BookLightLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        bookLightRecyclerView.setLayoutManager(BookLightLayoutManager);
        notebookList = Helpers.getNotebookList(getApplicationContext());
		RecyclerView.Adapter bookLightAdapter = new BookLightAdapter(this, notebookList, this);
        bookLightRecyclerView.setAdapter(bookLightAdapter);
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
        //PrimaryDrawerItem diNotebooks= new PrimaryDrawerItem().withName("Notebooks").withDescription("Full list of notebooks");
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
                            if (position == 2){
                                intent = new Intent(MainActivity.this, NotebookSelection.class);
                            }
                            if (intent != null){
                                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(intent);
								finish();
                            }
                        }
                        return false;
                    }
                })

                .build();
        Glide.with(this).load(R.drawable.header2).into(accountHeader.getHeaderBackgroundView());
        /**Color.parseColor("#00FFFF")*/
		FirstBookLightOffsetDecoration firstBookLightOffsetDecoration = new FirstBookLightOffsetDecoration((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics()));
   		bookLightRecyclerView.addItemDecoration(firstBookLightOffsetDecoration);
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        accountHeader.getHeaderBackgroundView().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void openNotebook(Notebook notebook) {
        Intent intent = new Intent(this, NotebookMainActivity.class);
        intent.putExtra("notebook", Parcels.wrap(notebook));
        intent.putExtra("parent", "MainActivity");
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        notebookList.clear();
        notebookList.addAll(Helpers.getNotebookList(getApplicationContext()));
        bookLightRecyclerView.getAdapter().notifyDataSetChanged();
    }
}
