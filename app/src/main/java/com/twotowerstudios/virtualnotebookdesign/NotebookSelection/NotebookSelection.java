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
import android.widget.RelativeLayout;

import com.mikepenz.materialdrawer.AccountHeader;
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
	private RelativeLayout emptyList;
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
		//===============================================================================================================
		notebookSelectionCardList = Helpers.getNotebookList(getApplicationContext());
		fabSelection = (FloatingActionButton) findViewById(R.id.fabSelection);
		emptyList = (RelativeLayout) findViewById(R.id.emptyFile);
		rvNotebookSelection = (RecyclerView) findViewById(R.id.rvnotebookselection);
		final LinearLayoutManager rvNotebookSelectionManager = new LinearLayoutManager(this);
		rvNotebookSelection.setLayoutManager(rvNotebookSelectionManager);
		rvNotebookSelectionAdapter = new NotebookSelectionAdapter(this, notebookSelectionCardList, this, this);
		rvNotebookSelection.setAdapter(rvNotebookSelectionAdapter);
		//===============================================================================================================
		if (!notebookSelectionCardList.isEmpty()) {
			//
			emptyList.setVisibility(View.GONE);
			rvNotebookSelection.setVisibility(View.VISIBLE);
			//================================================
			isMainfabOpen = false;

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

		} else {
			fabSelection.setVisibility(View.GONE);
			emptyList.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					showDialog();
				}
			});
		}
		//===================================================================
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("Notebooks");


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
        //getMenuInflater().inflate(R.menu.menu_main, menu);
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
            //Toast.makeText(this, "Sorry, this feature is disabled during the exhibition!", Toast.LENGTH_SHORT).show();
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
		emptyList.setVisibility(View.GONE);
		rvNotebookSelection.setVisibility(View.VISIBLE);
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
