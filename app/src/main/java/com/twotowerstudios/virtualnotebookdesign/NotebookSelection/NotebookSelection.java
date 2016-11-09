package com.twotowerstudios.virtualnotebookdesign.NotebookSelection;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
import com.twotowerstudios.virtualnotebookdesign.DeleteNotebookFragment;
import com.twotowerstudios.virtualnotebookdesign.Initialization.InitNotebooks;
import com.twotowerstudios.virtualnotebookdesign.MainMenu.MainActivity;
import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;
import com.twotowerstudios.virtualnotebookdesign.NewNotebookFragment;
import com.twotowerstudios.virtualnotebookdesign.Notebook;
import com.twotowerstudios.virtualnotebookdesign.R;

import java.io.File;
import java.util.ArrayList;

public class NotebookSelection extends AppCompatActivity implements DeleteNotebookFragment.DeleteNotebookDialogListener {
    private AccountHeader accountHeader;
    private RecyclerView rvNotebookSelection;
    private RecyclerView.Adapter rvNotebookSelectionAdapter;
    private ArrayList<Notebook> notebookSelectionCardList;
	private FloatingActionButton fabSelection, fabAddBook;
	boolean isMainfabOpen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook_selection);
		if(new Helpers().getStringFromFile("Notebooks.json", getApplicationContext())==null
				|| new Helpers().getStringFromFile("Notebooks.json", getApplicationContext())==""){
			File file = new File(getFilesDir(),"Notebooks.json");
		}
		if(InitNotebooks.isDebug(getApplicationContext())){
			Log.d("isDebugNoteSelect", "DEBUG MODE = true;");
			InitNotebooks.populateDebugBooks(getApplicationContext(), InitNotebooks.isDebug(getApplicationContext()));
		}
        rvNotebookSelection = (RecyclerView) findViewById(R.id.rvnotebookselection);

		//================================================
		isMainfabOpen = false;
		fabSelection = (FloatingActionButton) findViewById(R.id.fabSelection);
		fabAddBook = (FloatingActionButton) findViewById(R.id.fabAddBlock);
		fabSelection.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view) {
				if (!isMainfabOpen) {

					fabAddBook.show();

					isMainfabOpen = true;
					ObjectAnimator openAddBookfab = ObjectAnimator.ofFloat(fabAddBook, View.TRANSLATION_Y, 200,0); openAddBookfab.start();
					ObjectAnimator rotateMainfab = ObjectAnimator.ofFloat(fabSelection, View.ROTATION, 0, 135); rotateMainfab.start();

				} else if(isMainfabOpen){
					isMainfabOpen = false;
					ObjectAnimator rotateMainfab = ObjectAnimator.ofFloat(fabSelection, View.ROTATION, 135, 270); rotateMainfab.start();
					ObjectAnimator closeFirstSubfab = ObjectAnimator.ofFloat(fabAddBook, View.TRANSLATION_Y, 0,200); closeFirstSubfab.start();
					fabAddBook.hide();
				}
			}


		});
		fabAddBook.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
                showDialog();
			}
		});
		//============================================
        final LinearLayoutManager rvNotebookSelectionManager = new LinearLayoutManager(this);
        rvNotebookSelection.setLayoutManager(rvNotebookSelectionManager);
		notebookSelectionCardList = new Helpers().getNotebookList(getApplicationContext());
        //prepareNotebookSelectionCards();
        rvNotebookSelectionAdapter = new NotebookSelectionAdapter(this, notebookSelectionCardList);
        rvNotebookSelection.setAdapter(rvNotebookSelectionAdapter);

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
        Drawer drawer = new DrawerBuilder()
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
		drawer.setSelection(2);
        Glide.with(this).load(R.drawable.header2).into(accountHeader.getHeaderBackgroundView());

    }

    private void showDialog() {

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        NewNotebookFragment newFragment = NewNotebookFragment.newInstance();
        newFragment.show(ft, "dialog");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            //showEditDialog();
            getApplicationContext().deleteFile("Notebooks.json");
            notebookSelectionCardList.clear();
            rvNotebookSelectionAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onFinishEditDialog(String inputText) {
		Toast.makeText(getApplicationContext(), "Deleted book", Toast.LENGTH_SHORT);
        try{
            new Helpers().deleteNotebookByName(inputText, getApplicationContext());
			Log.d("DeleteBook", "Deleted book");

        }catch (Exception e){
            e.printStackTrace();
            Log.d("DeleteBook", "Failed to delete book");
			Toast.makeText(getApplicationContext(), "Failed to delete book", Toast.LENGTH_SHORT);
        }
    }
    private void showEditDialog(){
        FragmentManager fm = getSupportFragmentManager();

        DeleteNotebookFragment deleteNotebookFragment = DeleteNotebookFragment.newInstance("Delete Notebook");

        deleteNotebookFragment.show(fm, "fragment_delete_notebook");
    }



}
