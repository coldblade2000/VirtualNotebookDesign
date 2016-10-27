package com.twotowerstudios.virtualnotebookdesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import java.util.List;

public class NotebookSelection extends AppCompatActivity {
    private AccountHeader accountHeader;
    private RecyclerView rvNotebookSelection;
    private RecyclerView.Adapter rvNotebookSelectionAdapter;
    private List<NotebookSelectionCard> notebookSelectionCardList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook_selection);

        rvNotebookSelection = (RecyclerView) findViewById(R.id.rvnotebookselection);

        final LinearLayoutManager rvNotebookSelectionManager = new LinearLayoutManager(this);
        rvNotebookSelection.setLayoutManager(rvNotebookSelectionManager);
        rvNotebookSelectionAdapter = new NotebookSelectionAdapter(this, notebookSelectionCardList);

/**rvMainMenu = (RecyclerView) findViewById(R.id.rvMainMenu);

 final LinearLayoutManager rvMainMenuLayoutManager = new LinearLayoutManager(this);
 rvMainMenu.setLayoutManager(rvMainMenuLayoutManager);

 rvMainMenuAdapter = new MainMenuAdapter(this, cardlist);
 rvMainMenu.setAdapter(rvMainMenuAdapter);

 cardlist = new ArrayList<>();
 CommonBooksCard a = new CommonBooksCard(); cardlist.add(a);*/

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
                                intent = new Intent(NotebookSelection.this, MainActivity.class);
                            }else if (position == 2){

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

    }

}
