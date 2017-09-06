package com.twotowerstudios.virtualnotebookdesign.NotebookSelection;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
	final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 3;
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
		if(!SharedPrefs.getBoolean(getApplicationContext(), "StorageLocDiagShown")){
			SharedPrefs.setBoolean(getApplicationContext(), "StorageLocDiagShown", true);
			new AlertDialog.Builder(this)
					.setTitle("Do you want to use your SD card to store photos?")
					.setMessage("No matter the location, these photos will be stored independently from the gallery. This choice might not be possible to reverse.")
					.setPositiveButton("SD Card", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (ContextCompat.checkSelfPermission(getApplicationContext(),
									Manifest.permission.WRITE_EXTERNAL_STORAGE)
									!= PackageManager.PERMISSION_GRANTED) {
								if (ActivityCompat.shouldShowRequestPermissionRationale(NotebookSelection.this,
										Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
								} else {
									ActivityCompat.requestPermissions(NotebookSelection.this,
											new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
											MY_PERMISSIONS_REQUEST_READ_CONTACTS);
								}
							}
						}
					})
					.setNegativeButton("Internal Storage", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					}).show();
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
		rvNotebookSelection.addOnScrollListener(new RecyclerView.OnScrollListener() {
			/**
			 * Callback method to be invoked when the RecyclerView has been scrolled. This will be
			 * called after the scroll has completed.
			 * <p>
			 * This callback will also be called if visible item range changes after a layout
			 * calculation. In that case, dx and dy will be 0.
			 *
			 * @param recyclerView The RecyclerView which scrolled.
			 * @param dx           The amount of horizontal scroll.
			 * @param dy           The amount of vertical scroll.
			 */
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				if(dy>10){
					fabSelection.hide();
					fabAddBook.hide();
				}else if(dy<10){
					fabSelection.show();
					if(isMainfabOpen()){
						fabAddBook.show();
					}
				}
			}
		});
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
        if (id == R.id.action_debug) {
			SharedPrefs.setBoolean(getApplicationContext(), "StorageLocDiagShown", false);
			return true;
        }else if(id== R.id.action_delete){
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Notebooks.json");
            file.delete();
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
