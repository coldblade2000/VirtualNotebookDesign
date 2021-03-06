package com.twotowerstudios.virtualnotebookdesign.NotebookSelection;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;
import com.twotowerstudios.virtualnotebookdesign.Misc.SharedPrefs;
import com.twotowerstudios.virtualnotebookdesign.NewCollectionDialog.NewCollectionFragment;
import com.twotowerstudios.virtualnotebookdesign.NewNotebookDialog.NewNotebookFragment;
import com.twotowerstudios.virtualnotebookdesign.NotebookMain.NotebookMainActivity;
import com.twotowerstudios.virtualnotebookdesign.Objects.ChildBase;
import com.twotowerstudios.virtualnotebookdesign.Objects.Collection;
import com.twotowerstudios.virtualnotebookdesign.Objects.Notebook;
import com.twotowerstudios.virtualnotebookdesign.Objects.Page;
import com.twotowerstudios.virtualnotebookdesign.R;
import com.twotowerstudios.virtualnotebookdesign.TransferNotebookDialog.TransferNotebookDialog;

import org.parceler.Parcels;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class NotebookSelection extends AppCompatActivity implements NotebookSelectionAdapter.SelectionToNotebookSelectionInterface, NewCollectionFragment.OnFragmentInteractionListener, TransferNotebookDialog.OnFragmentInteractionListener {
	private RelativeLayout emptyList;
    private RecyclerView rvNotebookSelection;
    public RecyclerView.Adapter rvNotebookSelectionAdapter;
    private ArrayList<Notebook> notebookSelectionCardList;
	private FloatingActionButton fabSelection, fabAddBook, fabAddCollect;
    private ArrayList<Collection> collections;
	private int currentCollectionIndex;
	static boolean isMainfabOpen;
	private boolean isFirstTime;
	final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 3;
    final int REQUEST_CODE = 5;
    private Drawer drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook_selection);
		notebookSelectionCardList = new ArrayList<>();
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
		if(SharedPrefs.getInt(getApplicationContext(),"filestructure")!=1){ //Checks if the filestructure is the old one or the new one
			// Gets an arraylist of all notebooks using the deprecated getNotebookList method that loads from Notebooks.json
			ArrayList<Notebook> notebooklist = Helpers.getNotebookList(getApplicationContext());
			for (int i = 0; i < notebooklist.size(); i++) { // Goes through all the notebooks with an iterative for loop
				Notebook notebook = notebooklist.get(i); // Loads the notebook from the respective index
				File newFolderPIC = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath()
						+ "/" + notebook.getUID16() + "/"); // Makes a File reference to the new pictures folder for the notebook
				if (!newFolderPIC.isDirectory()) {
					newFolderPIC.mkdir(); // Creates the filder if it doesnt exist
				}
				ArrayList<Page> pages = notebook.getPages(); // Gets a list of the notebook's pages and iterates through them
				for (int j=0; j<pages.size();j++) {
					Page page = pages.get(j);
					ArrayList<ChildBase> content = pages.get(j).getContent(); // Gets a list of each page's content and iterates through it
					for (int k = 0; k< content.size();k++) {
						if (content.get(k).getChildType() == 1) { // checks if the child type is an image
							ChildBase childBase = content.get(k);
							File image = childBase.getFile(); // gets the file object for the child object's image
							// creates a File reference for the new place the image should go to
							File dest = new File(newFolderPIC.getAbsolutePath()+"/"+image.getName());
							if (image.renameTo(dest)) {
								childBase.setPath(dest.getAbsolutePath());
								childBase.setUri(Uri.fromFile(dest));      //If the image is successfully transferred,
																		   // update the path reference inside the child object
							}
							content.set(k, childBase); //Overwrites the child inside the content list with the new and updated one
						}
					}
					page.setContent(content);
					pages.set(j,page); //Updates the content and the pages
				}
				notebook.setPages(pages); //Sets the new set of pages for the notebook
				Helpers.writeNotebookToFile(notebook, getApplicationContext()); //Writes the notebook to file
			}
			//SharedPrefs.setInt(getApplicationContext(), "filestructure", 1);
		}
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
		/*if(!SharedPrefs.getBoolean(getApplicationContext(), "StorageLocDiagShown")){
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
		}*/
		if(Helpers.getCollections(getApplicationContext())==null){ //Creates an empty default collection if there aren't any yet
			ArrayList<String> UIDs = new ArrayList<>();
			for (Notebook a: Helpers.getNotebookList(getApplicationContext())){ //Uses the old, deprecated getNotebookList() method as it
				// has to find every possible notebook
				UIDs.add(a.getUID16());
			}
			final Collection defaultCollection = new Collection("Default Collection",UIDs,ContextCompat.getColor(getApplicationContext(), R.color.md_black_1000));
			ArrayList<Collection> defaultCollectionList = new ArrayList<>();
			defaultCollectionList.add(defaultCollection);
			Helpers.writeCollectionsToFile(defaultCollectionList, getApplicationContext()); //Writes collections to Collections.json
		}
		//===============================================================================================================

        SharedPrefs.setInt(this, "filestructure", 1);  //Declares that the app uses the new file structure
        collections = Helpers.getCollections(getApplicationContext()); //Gets all collections from Collections.json
        assert collections != null;
        for (Collection a: collections){ //Iterates through the collections
            if(SharedPrefs.getString(this, "lastUID8").equals(a.getUID8())){ //Checks the previously opened collection from the
												//last session and opens it
                notebookSelectionCardList = Helpers.getNotebooksFromCollection(a, getApplicationContext());
				currentCollectionIndex = collections.indexOf(a);
				break;
            }
        }
        if (notebookSelectionCardList == null || notebookSelectionCardList.size() == 0){
            notebookSelectionCardList = Helpers.getNotebooksFromCollection(collections.get(0), getApplicationContext());
			currentCollectionIndex = 0;
        }
		//notebookSelectionCardList = Helpers.getNotebookList(getApplicationContext());

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
				if(dy>5){
					fabSelection.hide();
					fabAddBook.hide();
				}else if(dy<5){
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
			listNotEmpty();
			//============================================
		} else {
			fabSelection.setVisibility(View.GONE);
			emptyList.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					showDialog("NewNotebookFragment");
				}
			});
		}
		//===================================================================
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("Notebooks");

		ArrayList<IDrawerItem> drawerItems = populateDrawer();
		drawer = new DrawerBuilder()
				.withActivity(this)
				.addDrawerItems(drawerItems.toArray(new IDrawerItem[drawerItems.size()]))
				.withSelectedItemByPosition(currentCollectionIndex)
				.withToolbar(toolbar)
				.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
					@Override
					public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
						currentCollectionIndex = position;
						changeSelectedCollection(collections.get(position));
						return true;
					}
				})
				.build();

    }

    private void changeSelectedCollection(Collection collection){
		ArrayList<Notebook> collectionContentList = new ArrayList<>();
		for(String a: collection.getContentUIDs()){
			collectionContentList.add(Helpers.getNotebookFromUID(a, getApplicationContext()));
		}
		notebookSelectionCardList.clear();
		notebookSelectionCardList.addAll(collectionContentList);
		rvNotebookSelectionAdapter.notifyDataSetChanged();
		/*rvNotebookSelectionAdapter = new NotebookSelectionAdapter(getApplicationContext(),notebookSelectionCardList , NotebookSelection.this, NotebookSelection.this);
		rvNotebookSelection.setAdapter(rvNotebookSelectionAdapter);*/
	}

	private void listNotEmpty() {
		emptyList.setVisibility(View.GONE);
		rvNotebookSelection.setVisibility(View.VISIBLE);
		//================================================
		isMainfabOpen = false;

		fabAddBook = (FloatingActionButton) findViewById(R.id.fabAddBlock);
		fabAddCollect = (FloatingActionButton) findViewById(R.id.fabAddCollect);
		fabSelection.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view) {
				if (!isMainfabOpen) {
					fabAddBook.show();
					fabAddCollect.show();
					isMainfabOpen = true;
					ObjectAnimator openAddBookfab = ObjectAnimator.ofFloat(fabAddBook, View.TRANSLATION_Y, 200,0); openAddBookfab.start();
					ObjectAnimator openAddCollectFab = ObjectAnimator.ofFloat(fabAddCollect, View.TRANSLATION_Y, 400,0); openAddCollectFab.start();
					ObjectAnimator rotateMainfab = ObjectAnimator.ofFloat(fabSelection, View.ROTATION, 0, 135); rotateMainfab.start();
				} else if(isMainfabOpen){
					isMainfabOpen = false;
					ObjectAnimator rotateMainfab = ObjectAnimator.ofFloat(fabSelection, View.ROTATION, 135, 270); rotateMainfab.start();
					ObjectAnimator closeFirstSubfab = ObjectAnimator.ofFloat(fabAddBook, View.TRANSLATION_Y, 0,200); closeFirstSubfab.start();
					ObjectAnimator closeAddCollectFab = ObjectAnimator.ofFloat(fabAddCollect, View.TRANSLATION_Y, 0,400); closeAddCollectFab.start();
					fabAddCollect.hide();
					fabAddBook.hide();
				}
			}


		});
		fabAddBook.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showDialog("NewNotebookFragment");
			}
		});
		fabAddCollect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showDialog("NewCollectionFragment");
			}
		});
	}

	private ArrayList<IDrawerItem> populateDrawer(){
		ArrayList<IDrawerItem> drawerArray = new ArrayList<>();
		for (Collection a:collections) {
			drawerArray.add(new PrimaryDrawerItem().withName(a.getName()).withIcon(R.drawable.ic_folder_white_24dp).withIconColor(a.getColor()).withIconTintingEnabled(true));
		}
		return drawerArray;
	}
    private void showDialog(String type) {

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
		if (type.equals("NewNotebookFragment")) {
			NewNotebookFragment newFragment = NewNotebookFragment.newInstance();
			newFragment.show(ft, "dialog");
		}else if(type.equals("NewCollectionFragment")) {
			NewCollectionFragment newFragment = NewCollectionFragment.newInstance(collections);
			newFragment.show(ft, "dialog");
		}
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
		/*if (SharedPrefs.getBoolean(getApplicationContext(), "debug")) {
		if (true) {
			menu.getItem(R.id.loghierarchy).setEnabled(true);
			menu.getItem(R.id.action_dumpjson).setEnabled(true);
		}*/
		getMenuInflater().inflate(R.menu.menu_main, menu);

		return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

     	if(id == R.id.action_import) {
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("*/*");
			startActivityForResult(intent, REQUEST_CODE);
			return true;
            /*
            else if(id== R.id.action_delete){
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath());
			for(File a: file.listFiles()){
				if(a.getName().substring(a.getName().length()-4).equals("json")){
					Gson gson = new Gson();
					Notebook notebook = gson.fromJson(Helpers.getStringFromFile(a), Notebook.class);
					Log.d("NotebookSelection", "Renamed from "+a.getAbsolutePath()+ "to "+ getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsoluteFile()+"/j"+notebook.getUID16().substring(1)+ ".json");
					a.renameTo(new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsoluteFile()+"/j"+notebook.getUID16().substring(1)+ ".json"));
				}
			}
             */
		}else if(id== R.id.action_delete){
				File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath());
				for(Notebook a: Helpers.getNotebookList(getApplicationContext())){
					for(Page b: a.getPages()){
						for(ChildBase c: b.getContent()){
							c.getFile().delete();
						}
					}
				}
				for(File ad: file.listFiles()){
					ad.delete();
				}
        }else if(id==R.id.action_dumpjson){
			String fileString = Helpers.getStringFromName("Collections.json", getApplicationContext());
			int reps = (fileString.length()/4000)+1;
			for (int i = 0; i < reps; i++) {
				if((i+1)*4000>fileString.length()) {
					Log.v("Helpers", "getNotebookList: \n" + fileString.substring(i * 4000, fileString.length()));
				}else{
					Log.v("Helpers", "getNotebookList: \n" + fileString.substring(i * 4000, (i + 1) * 4000));
				}
			}
		}else if(id==R.id.loghierarchy){
			String TAG = "HierarchyLog";
			for(Collection a: collections){
				Log.d(TAG, "Name: "+a.getName());
				Log.d(TAG, "UID 8: "+a.getUID8());
				Log.d(TAG, "Color: "+a.getColor());
				Log.d(TAG, "UIDs: ");
				for(String s: a.getContentUIDs()) {
					Log.d(TAG, s+", ");
				}
				for(Notebook ab : Helpers.getNotebooksFromCollection(a, getApplicationContext())){
					Log.d(TAG, ab.getName());
					Log.d(TAG, "*	UID16 = "+ab.getUID16());
					Log.d(TAG, "*	Path = "+ab.getPath());
					Log.d(TAG, "*	Number of Pages = "+ab.getNumberOfPages());
					for(Page b: ab.getPages()){
						Log.d(TAG, "	"+b.getName());
						Log.d(TAG, "	"+"*	UID16 = "+b.getUID());
						Log.d(TAG, "	"+"*	PageNumber "+b.getPageNumber());
						for(ChildBase c:b.getContent()){
							Log.d(TAG, "		"+"Title =" +c.getTitle());
							Log.d(TAG, "		"+"Child type = "+c.getChildType());
							Log.d(TAG, "		"+"Path = "+c.getPath());
							Log.d(TAG, "		"+"Image UID = "+c.getImageUID());
							Log.d(TAG, "		"+"UID16 = "+c.getUID16());

						}
					}
				}
			}
		}else if (id==R.id.debugaddcollection){
			for (Notebook a: Helpers.getNotebookList(getApplicationContext())){
				collections.get(0).addUID(a.getUID16());
				Helpers.writeCollectionsToFile(collections, getApplicationContext());

			}
		}
        return super.onOptionsItemSelected(item);
    }
	@Override
	public void onActivityResult(int requestCode, int resultCode,
								 Intent resultData) {
		// The ACTION_OPEN_DOCUMENT intent was sent with the request code
		// READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
		// response to some other intent, and the code below shouldn't run at all.
		if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			// The document selected by the user won't be returned in the intent.
			// Instead, a URI to that document will be contained in the return intent
			// provided to this method as a parameter.
			// Pull that URI using resultData.getData().
			Uri uri = null;
			if (resultData != null) {
				uri = resultData.getData();
				new AsyncImporting().execute(uri);

			}
		}
	}

	@Override
	public void onFragmentInteraction(Collection collection) { //Where a colllection is added to the notebookSelection activity
		collections.add(collection);
		//icon.setColorFilter(collection.getColor(), PorterDuff.Mode.MULTIPLY);
		drawer.addItem(new PrimaryDrawerItem().withName(collection.getName()).withIcon(R.drawable.ic_folder_white_24dp).withIconColor(collection.getColor()).withIconTintingEnabled(true));
		Helpers.writeCollectionsToFile(collections, getApplicationContext());
	}

	@Override
	public void onDialogClosed(Collection collection) { //When the TransferNotebookDialog is closed
		collections = Helpers.getCollections(getApplicationContext());
		for (int i = 0; i < collections.size(); i++) {
			if(collections.get(i).getUID8().equals(collection.getUID8())){
				currentCollectionIndex = i;
				break;
			}
		}
		drawer.setSelectionAtPosition(currentCollectionIndex);
		changeSelectedCollection(collection);
	}

	class AsyncImporting extends AsyncTask<Uri, Void, Notebook> {
		ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(NotebookSelection.this);
			pd.setMessage("Importing, please wait...");
			pd.show();
		}
		@Override
		protected Notebook doInBackground(Uri... uri) {
			String name = "u"+ Helpers.generateUniqueId(8);
			String TAG = "AsyncImporting";
			File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath()+"/"+name+"/");
			Helpers.unzip(uri[0], getApplicationContext(), file.getAbsolutePath());
			File[] fileList = file.listFiles();
			Log.d(TAG, "Unzipped in "+ file.getAbsolutePath());

			ArrayList<File> images = new ArrayList<>();
			Notebook notebook = null;
			Gson gson = new Gson();
			for(File a: fileList){
				Log.d("notebookSelection", a.getName().substring(a.getName().lastIndexOf('.')));
				if(a.getName().substring(a.getName().lastIndexOf('.')).equals(".json")){
					String json = Helpers.getStringFromFile(a);
					Log.d(TAG, "found JSON: "+ a.getAbsolutePath());
					notebook = gson.fromJson(json, Notebook.class);
					String notebookName = "j"+notebook.getUID16().substring(1);
					a.renameTo(new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsoluteFile()+"/"+notebookName+".json"));
					Log.d(TAG, "doInBackground: "+notebookName);
				}
			}
			if(notebook!= null){
				File newfolder = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath()+"/"+notebook.getUID16()+"/");
                if(file.renameTo(newfolder)){
					Log.d("AsyncImporting", "Renamed folder to "+file.getAbsolutePath());
				} else{
					Log.w("AsyncImporting", "Couldn't rename folder "+ file.getName());
				}
				for(File d : newfolder.listFiles()){
					if(!d.getName().substring(d.getName().length()-4).equalsIgnoreCase("json")){
						images.add(d);
					}
				}
				for(Page a: notebook.getPages()){
					for(ChildBase b: a.getContent()){
						if(b.getChildType()==1){
							for(File c: images) {
								if(c.getName().equals(b.getImageUID() + ".png")){
									b.setPath(c.getAbsolutePath());
									Log.d(TAG, "Set Path: "+ c.getAbsolutePath());
									b.setUri(Uri.fromFile(new File(c.getAbsolutePath())));
								}
							}
						}
					}
				}
				Helpers.writeNotebookToFile(notebook, getApplicationContext());
                collections.get(currentCollectionIndex).addUID(notebook.getUID16());
			}
			Log.d("NotebookSelection", gson.toJson(notebook));
			//file.delete();
			return notebook;
		}
		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}
		@Override
		protected void onPostExecute(Notebook notebook) {
			if (pd != null)
			{
				pd.dismiss();
			}
			Helpers.addToNotebookList(notebook, getApplicationContext(), collections.get(currentCollectionIndex).getUID8());
			Helpers.writeCollectionsToFile(collections, getApplicationContext());
			//Helpers.addToNotebookList(notebook, getApplicationContext());
			refreshData(notebook);
		}
	}

	public static boolean isMainfabOpen(){
		return isMainfabOpen;
	}

	public void refreshData(Notebook newNotebook) {
		Helpers.addToNotebookList(newNotebook, getApplicationContext());
		notebookSelectionCardList.clear();
		notebookSelectionCardList.addAll(Helpers.getNotebooksFromCollection(collections.get(currentCollectionIndex),getApplicationContext()));
		rvNotebookSelectionAdapter.notifyDataSetChanged();
		rvNotebookSelection.getLayoutManager().scrollToPosition(notebookSelectionCardList.size());
		emptyList.setVisibility(View.GONE);
		rvNotebookSelection.setVisibility(View.VISIBLE);
	}
	public void addNotebookToNBSelection(Notebook notebook){
		notebookSelectionCardList.add(notebook);
		rvNotebookSelectionAdapter.notifyItemInserted(notebookSelectionCardList.size()-1);
		collections.get(currentCollectionIndex).addUID(notebook.getUID16());
		Helpers.writeCollectionsToFile(collections, getApplicationContext());
		listNotEmpty();
	}
	@Override
	public void openNotebookActivity(int position) {
		Intent intent = new Intent(this, NotebookMainActivity.class);
		intent.putExtra("notebook", Parcels.wrap(notebookSelectionCardList.get(position)));
		intent.putExtra("parent","NotebookSelection");
		intent.putExtra("collectionUID",collections.get(currentCollectionIndex).getUID8());
		startActivity(intent);
	}

	@Override
	public void transferNotebook(int position) {
		FragmentManager fm = getSupportFragmentManager();
		TransferNotebookDialog newFragment = TransferNotebookDialog.newInstance(Helpers.getCollections(getApplicationContext()),
				notebookSelectionCardList.get(position), collections.get(currentCollectionIndex).getUID8());
		newFragment.show(fm, "dialog");
	}

	@Override
	public void renameNotebook(final int position) {
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setMessage("Enter the new title you want for the notebook \""+notebookSelectionCardList.get(position).getName()+'"');
		alert.setTitle("Rename Notebook");
		final EditText editText = new EditText(getApplicationContext());
		final int margin = (int) (24 * Resources.getSystem().getDisplayMetrics().density);
		//TODO Fix edittext margins
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(margin, 0 , margin, 0);
		editText.setLayoutParams(lp);
		alert.setView(editText);
		alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Notebook notebook1 = notebookSelectionCardList.get(position);
				notebook1.setName(editText.getText().toString().trim());
				Helpers.writeNotebookToFile(notebook1, getApplicationContext());
				if(notebookSelectionCardList.get(position).getUID16().equals(notebook1.getUID16())){
					notebookSelectionCardList.set(position,notebook1);
				}else{
					Log.e("renameNotebook", "onClick: Something went wrong when adding the notebook. UID in notebookselectioncardlist = "+ notebookSelectionCardList.get(position).getUID16());
					Log.e("renameNotebook", "onClick: UID in notebook1 = "+ notebook1.getUID16());
					Toast.makeText(NotebookSelection.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
				}
				rvNotebookSelectionAdapter.notifyItemChanged(position);
			}
		});
		alert.setNegativeButton("Cancel", null);
		alert.show();
	}

	@Override
	public void deleteNotebook(int position, String UID16) {
		Collection collection = collections.get(currentCollectionIndex);
		collection.removeUID(UID16);
		Helpers.writeOneCollectionToFile(collection, getApplicationContext());
		Helpers.deleteNotebookByUID(UID16, getApplicationContext());
	}

	@Override
    protected void onResume() {
		if (isFirstTime) {
			isFirstTime = false;
		} else {
			collections = Helpers.getCollections(getApplicationContext());
			notebookSelectionCardList.clear();
			notebookSelectionCardList.addAll(Helpers.getNotebooksFromCollection(collections.get(currentCollectionIndex), getApplicationContext()));
			rvNotebookSelectionAdapter.notifyDataSetChanged();
		}
		super.onResume();
	}

    public void addCollectionToAdapters(Collection collection){
    	collections.add(collection);

	}
}
