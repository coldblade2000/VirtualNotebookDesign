package com.twotowerstudios.virtualnotebookdesign.NotebookSelection;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.twotowerstudios.virtualnotebookdesign.Initialization.InitNotebooks;
import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;
import com.twotowerstudios.virtualnotebookdesign.Misc.SharedPrefs;
import com.twotowerstudios.virtualnotebookdesign.NewNotebookDialog.NewNotebookFragment;
import com.twotowerstudios.virtualnotebookdesign.NotebookMain.NotebookMainActivity;
import com.twotowerstudios.virtualnotebookdesign.Objects.Notebook;
import com.twotowerstudios.virtualnotebookdesign.R;

import org.parceler.Parcels;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class NotebookSelection extends AppCompatActivity implements NotebookSelectionAdapter.SelectionToNotebookInterface {
    private AccountHeader accountHeader;
    private RecyclerView rvNotebookSelection;
    private RecyclerView.Adapter rvNotebookSelectionAdapter;
    private ArrayList<Notebook> notebookSelectionCardList;
	private FloatingActionButton fabSelection, fabAddBook;
	static boolean isMainfabOpen;
	private boolean isFirstTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook_selection);
		isFirstTime=true;
		SharedPrefs.setBoolean(getApplicationContext(), "debug", false);
		File nomedia = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), ".nomedia");
		if(!nomedia.exists()){
			try {
				nomedia.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(Helpers.getStringFromFile("Notebooks.json", getApplicationContext()).equals("")){
			File file = new File(getFilesDir(),"Notebooks.json");
			Log.d("NotebookSelection", file.getPath());
		}
		if(InitNotebooks.isDebug(getApplicationContext())){
			Log.d("isDebugNoteSelect", "DEBUG MODE = true;");
			InitNotebooks.populateDebugBooks(getApplicationContext(), InitNotebooks.isDebug(getApplicationContext()));
		}


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
		rvNotebookSelection = (RecyclerView) findViewById(R.id.rvnotebookselection);
        final LinearLayoutManager rvNotebookSelectionManager = new LinearLayoutManager(this);
        rvNotebookSelection.setLayoutManager(rvNotebookSelectionManager);
		notebookSelectionCardList = Helpers.getNotebookList(getApplicationContext());
        //prepareNotebookSelectionCards();
        rvNotebookSelectionAdapter = new NotebookSelectionAdapter(this, notebookSelectionCardList, this, this);
        rvNotebookSelection.setAdapter(rvNotebookSelectionAdapter);
		//===================================================================
        final IProfile h1 = new ProfileDrawerItem().withName("Header 1");
        final IProfile h2 = new ProfileDrawerItem().withName("Header 2");
        final IProfile h3 = new ProfileDrawerItem().withName("Header 3");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("Notebooks");
        /**accountHeader = new AccountHeaderBuilder()
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
                        //if (drawerItem != null) {
                         //Intent intent = null;
                         //if (drawerItem.getIdentifier() == 1) {
                         //intent = new Intent(DrawerActivity.this, CompactHeaderDrawerActivity.class);
                         //}
                        if (drawerItem != null){
                            Intent intent = null;
                            if (position == 1){
                                intent = new Intent(NotebookSelection.this, MainActivity.class);
                            }else if (position == 2){

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
		drawer.setSelection(2);*/
		//=========================================================b
//        Glide.with(this).load(R.drawable.header2).into(accountHeader.getHeadcerBackgroundView());

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
            /**showEditDialog();
            getApplicationContext().deleteFile("Notebooks.json");
            notebookSelectionCardList.clear();
            rvNotebookSelectionAdapter.notifyDataSetChanged();*/
            //TODO make better the delete option
            Toast.makeText(this, "Sorry, this feature is disabled during the exhibition!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	public static boolean isMainfabOpen(){
		return isMainfabOpen;
	}

	public void refreshData(Notebook newNotebook) {
		Helpers.addToNotebookList(newNotebook, getApplicationContext());
		notebookSelectionCardList.clear();
		notebookSelectionCardList.addAll(Helpers.getNotebookList(getApplicationContext()));
		rvNotebookSelectionAdapter.notifyDataSetChanged();
		rvNotebookSelection.getLayoutManager().scrollToPosition(notebookSelectionCardList.size());
	}

	@Override
	public void openNotebookActivity(int position) {
		Intent intent = new Intent(this, NotebookMainActivity.class);
		intent.putExtra("notebook", Parcels.wrap(notebookSelectionCardList.get(position)));
        intent.putExtra("parent","NotebookSelection");
		startActivity(intent);
	}

    @Override
    protected void onResume() {
		if(isFirstTime){
			isFirstTime=false;
		}else{
			notebookSelectionCardList.clear();
			notebookSelectionCardList.addAll(Helpers.getNotebookList(getApplicationContext()));
			rvNotebookSelectionAdapter.notifyDataSetChanged();
		}
        super.onResume();

    }
}
