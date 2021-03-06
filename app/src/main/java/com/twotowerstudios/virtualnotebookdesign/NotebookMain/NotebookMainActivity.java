package com.twotowerstudios.virtualnotebookdesign.NotebookMain;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;
import com.twotowerstudios.virtualnotebookdesign.NotebookMain.Fragments.NewPage.NewPageFragment;
import com.twotowerstudios.virtualnotebookdesign.Objects.ChildBase;
import com.twotowerstudios.virtualnotebookdesign.Objects.Collection;
import com.twotowerstudios.virtualnotebookdesign.Objects.Notebook;
import com.twotowerstudios.virtualnotebookdesign.Objects.Page;
import com.twotowerstudios.virtualnotebookdesign.PageActivityMain.PageActivityMain;
import com.twotowerstudios.virtualnotebookdesign.R;
import com.twotowerstudios.virtualnotebookdesign.TransferNotebookDialog.TransferNotebookDialog;

import org.parceler.Parcels;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class NotebookMainActivity extends AppCompatActivity implements NewPageFragment.OnFragmentInteractionListener,TransferNotebookDialog.OnFragmentInteractionListener, NotebookAdapterToAct{

	Toolbar toolbar;
	Notebook notebook;
	FloatingActionButton fabnotebookmain;
	CollapsingToolbarLayout collapsingToolbarLayout;
	String collectionUID;
	String parent;
	String notebookUID16;
	TextView tvSub;
	ViewPager viewPager;
	boolean isFirstTime;
	TabLayout tabLayout;
	ArrayList<Page> pageList;
	ViewPagerAdapter viewPagerAdapter;
	RelativeLayout emptyNotebook;
	LinearLayout notEmptyNotebook;

	@Override
	public void clickListener(int position, boolean onlyFavorites) {
		Intent intent = new Intent(this, PageActivityMain.class);


		if(onlyFavorites){
			ArrayList<Page> favPageList = new ArrayList<>();
			for(Page p: pageList) {
				if(p.isFavorite()){
					favPageList.add(p);
				}
			}
			Collections.sort(favPageList);
			intent.putExtra("page", Parcels.wrap(favPageList.get(position)));

		}else{
			intent.putExtra("page", Parcels.wrap(pageList.get(position)));
		}
		intent.putExtra("notebookUID16", notebookUID16);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook_main);
		isFirstTime=true;
		if( collectionUID==null){
			collectionUID = getIntent().getExtras().getString("collectionUID");
		}
		if (notebook==null) {
			Log.d("NotebookMainActivity", "notebook == null");
			if (getIntent().getStringExtra("parent").equals("NotebookSelection")
					||(getIntent().getStringExtra("parent").equals("MainActivity"))) {
				notebook = Parcels.unwrap(getIntent().getParcelableExtra("notebook"));
			}else if(getIntent().getStringExtra("parent").equals("PageActivityMain")){
				notebook = Helpers.getNotebookFromUID(getIntent().getStringExtra("notebookUID16"), getApplicationContext());
			}

		}
		pageList = notebook.getPages();
		notebookUID16=notebook.getUID16();
		Log.d("NotebookMainActivity" , new Gson().toJson(notebook));
		emptyNotebook = (RelativeLayout) findViewById(R.id.emptyNotebook);
		notEmptyNotebook = (LinearLayout) findViewById(R.id.notEmptyNotebook);
		fabnotebookmain = (FloatingActionButton) findViewById(R.id.fabnotebookmain);
		fabnotebookmain.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
				if (prev != null) {
					ft.remove(prev);
				}
				ft.addToBackStack(null);

				// Create and show the dialog.
				NewPageFragment newFragment = NewPageFragment.newInstance(pageList, notebook.getAccentColor());
				newFragment.show(ft, "dialog");
			}
		});
		if (pageList.size()==0||pageList==null){ //if notebook is empty
			//pageList = InitNotebooks.populateDebugNotebookPages(pageList, 15);
			emptyNotebook.setVisibility(View.VISIBLE);
			fabnotebookmain.setVisibility(View.GONE);
			notEmptyNotebook.setVisibility(View.GONE);
			emptyNotebook.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
					Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
					if (prev != null) {
						ft.remove(prev);
					}
					ft.addToBackStack(null);
					NewPageFragment newFragment = NewPageFragment.newInstance(pageList, notebook.getAccentColor());
					newFragment.show(ft, "dialog");

				}
			});
		}else{ //if notebook is NOT empty
			emptyNotebook.setVisibility(View.GONE);
			notEmptyNotebook.setVisibility(View.VISIBLE);
			viewPager = (ViewPager) findViewById(R.id.viewpager);

			viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),pageList,Helpers.getSingleColorAccent(this,notebook.getColor()),this, notebookUID16, collectionUID));

			tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
			tabLayout.setupWithViewPager(viewPager);
		}
		parent = getIntent().getExtras().getString("parent");
		tvSub = (TextView) findViewById(R.id.tvSub);
		tvSub.setText("Last Modified: "+ DateUtils.getRelativeTimeSpanString(notebook.getLastModified(), Helpers.getCurrentTimeInMillis(), DateUtils.SECOND_IN_MILLIS));

		AppBarLayout appbarlayoutNotebook = (AppBarLayout) findViewById(R.id.appbarlayoutNotebook);
		appbarlayoutNotebook.setBackgroundColor(notebook.getColor());
		collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapseToolbarNotebook);
		toolbar = (Toolbar) findViewById(R.id.toolbarNotebook);
		setSupportActionBar(toolbar);
		toolbar.setBackgroundColor(notebook.getColor());
		getSupportActionBar().setTitle(notebook.getName());
		getSupportActionBar().setSubtitle("Last modified: " + DateUtils.getRelativeTimeSpanString(notebook.getLastModified(), Helpers.getCurrentTimeInMillis(), DateUtils.SECOND_IN_MILLIS));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		if(Helpers.isColorDark(notebook.getColor())){
			collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getApplicationContext(), R.color.md_white_1000));
			collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.md_white_1000));
			tvSub.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.md_white_1000));
		}else{
			collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getApplicationContext(), R.color.md_black_1000));
			collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.md_black_1000));
			tvSub.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.md_black_1000));
		}
		toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
		fabnotebookmain.setBackgroundTintList(ColorStateList.valueOf(Helpers.getSingleColorAccent(getApplicationContext(),notebook.getColor())));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			// Respond to the action bar's Up/Home button
			case R.id.exportnotebook:
				Toast.makeText(this, "Clicked share", Toast.LENGTH_SHORT).show();
				AlertDialog.Builder builder = new AlertDialog.Builder(NotebookMainActivity.this)
						.setMessage("Are you sure you want to share/export this notebook and all its content? This might take a while")
						.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								new AsyncExporting().execute(notebook);

							}

						})

						.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								dialogInterface.dismiss();
							}
						});
				builder.show();
				break;
			case R.id.transfernotebook:
				FragmentManager fm = getSupportFragmentManager();

				TransferNotebookDialog newFragment = TransferNotebookDialog.newInstance(Helpers.getCollections(getApplicationContext()), notebook, collectionUID);

				newFragment.show(fm, "dialog");
				break;
			case android.R.id.home:
				// Launch the correct Activity here
				finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDialogClosed(Collection collection) {

	}

	//=========



    class AsyncExporting extends AsyncTask<Notebook, Void, File> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(NotebookMainActivity.this);
            pd.setMessage("Creating files, please wait...");
            pd.show();
        }


        @Override
        protected File doInBackground(Notebook... notebook) {
            ArrayList<File> fileList = new ArrayList<>();
            for (Page b:notebook[0].getPages()) {
                for (ChildBase a: b.getContent()) {
                    if (a.getChildType()==1){
                        fileList.add(a.getFile());
                    }
                }
            }
            Gson gson = new Gson();
            Bundle bundle = new Bundle();
			String jsonString = gson.toJson(notebook[0]);
            bundle.putString("notebookJson", jsonString);
			bundle.putString("UID16", notebook[0].getUID16());
			Log.v("NotebookMainActivity", jsonString);

            return Helpers.zipFileArray(fileList, "z"+Helpers.generateUniqueId(16),bundle, getApplicationContext());

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(File file) {
            if (pd != null)
            {
                pd.dismiss();
            }
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("application/zip");
			intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(getApplicationContext(), "com.twotowerstudios.virtualnotebookdesign.fileprovider", file));
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			startActivity(Intent.createChooser(intent, "Send file using: "));
            super.onPostExecute(file);

			//http://androidxref.com/4.4.4_r1/xref/frameworks/base/media/java/android/media/MediaFile.java#174 MIME reference file


		}
    }

    //=========

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.notebookmenu, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(isFirstTime){
			isFirstTime=false;
		}else{
			pageList.clear();
			pageList.addAll(Helpers.getNotebookFromUID(notebookUID16, getApplicationContext()).getPages());
			viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), pageList, Helpers.getSingleColorAccent(this,notebook.getColor()), this, notebookUID16, collectionUID));
			tabLayout.setupWithViewPager(viewPager);
		}
	}

	@SuppressWarnings("WrongConstant")
	@Override
	public void onFragmentInteraction(String name, int pageNum, Calendar cal) {
		if (cal.get(Calendar.YEAR) != 1970) {
			pageList.add(new Page(name, pageNum, cal.getTimeInMillis()+43200000, notebook.getUID16()));
		} else {
			pageList.add(new Page(name, pageNum, 0, notebook.getUID16()));
		}
		notebook.setLastModified(Helpers.getCurrentTimeInMillis());
		notebook.setPages(pageList);
		Helpers.writeNotebookToFile(notebook, getApplicationContext());
		tvSub.setText("Last Modified: "+ DateUtils.getRelativeTimeSpanString(notebook.getLastModified(), Helpers.getCurrentTimeInMillis(), DateUtils.SECOND_IN_MILLIS));
		getSupportActionBar().setSubtitle("Last modified: " + DateUtils.getRelativeTimeSpanString(notebook.getLastModified(), Helpers.getCurrentTimeInMillis(), DateUtils.SECOND_IN_MILLIS));

		if (notebook == null) {
			Log.d("OnFragmentInteraction", "notebook == null");
		}
		emptyNotebook.setVisibility(View.GONE);
		notEmptyNotebook.setVisibility(View.VISIBLE);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),pageList, notebook.getColor(), this, notebookUID16, collectionUID);
		viewPager.setAdapter(viewPagerAdapter);

		tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
		tabLayout.setupWithViewPager(viewPager);
		fabnotebookmain.setVisibility(View.VISIBLE);
	}
}