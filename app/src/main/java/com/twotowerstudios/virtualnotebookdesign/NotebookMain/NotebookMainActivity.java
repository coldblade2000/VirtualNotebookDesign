package com.twotowerstudios.virtualnotebookdesign.NotebookMain;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;
import com.twotowerstudios.virtualnotebookdesign.NotebookMain.Fragments.NewPage.NewPageFragment;
import com.twotowerstudios.virtualnotebookdesign.Objects.Notebook;
import com.twotowerstudios.virtualnotebookdesign.Objects.Page;
import com.twotowerstudios.virtualnotebookdesign.PageActivityMain.PageActivityMain;
import com.twotowerstudios.virtualnotebookdesign.R;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Calendar;

public class NotebookMainActivity extends AppCompatActivity implements NewPageFragment.OnFragmentInteractionListener, NotebookAdapterToAct{

	Toolbar toolbar;
	Notebook notebook;
	FloatingActionButton fabnotebookmain;
	CollapsingToolbarLayout collapsingToolbarLayout;
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
	public void clickListener(int position) {
		Intent intent = new Intent(this, PageActivityMain.class);
		intent.putExtra("page", Parcels.wrap(pageList.get(position)));
		intent.putExtra("notebookUID16", notebookUID16);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
	}


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook_main);
		isFirstTime=true;
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
			viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),pageList,this));

			tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
			tabLayout.setupWithViewPager(viewPager);
		}
		parent = getIntent().getExtras().getString("parent");
		Toast.makeText(getApplicationContext(), "Parent is: "+ parent, Toast.LENGTH_SHORT).show();
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
			case android.R.id.home:
				// Launch the correct Activity here
				finish();
				/*if (parent.equals("MainActivity")) {
					Intent intent = new Intent(this, NotebookSelection.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
					startActivity(intent);
					finish();
					return true;
				}else if(parent.equals("NotebookSelection")){
					Intent intent = new Intent(this, MainActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
					startActivity(intent);
					finish();
					return true;
				}*/

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(isFirstTime){
			isFirstTime=false;
		}else{
			pageList.clear();
			pageList.addAll(Helpers.getNotebookFromUID(notebookUID16, getApplicationContext()).getPages());
			viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), pageList, this));
			tabLayout.setupWithViewPager(viewPager);
		}
	}

	@Override
	public void onFragmentInteraction(String name, int pageNum, Calendar cal) {
		pageList.add(new Page(name, pageNum, cal.getTimeInMillis()+43200000, notebook.getUID16()));
		notebook.setLastModified(Helpers.getCurrentTimeInMillis());
		notebook.setPages(pageList);
		Helpers.addToNotebookList(notebook, getApplicationContext());
		tvSub.setText("Last Modified: "+ DateUtils.getRelativeTimeSpanString(notebook.getLastModified(), Helpers.getCurrentTimeInMillis(), DateUtils.SECOND_IN_MILLIS));
		getSupportActionBar().setSubtitle("Last modified: " + DateUtils.getRelativeTimeSpanString(notebook.getLastModified(), Helpers.getCurrentTimeInMillis(), DateUtils.SECOND_IN_MILLIS));

		if (notebook == null) {
			Log.d("OnFragmentInteraction", "notebook == null");
		}
		emptyNotebook.setVisibility(View.GONE);
		notEmptyNotebook.setVisibility(View.VISIBLE);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),pageList, this);
		viewPager.setAdapter(viewPagerAdapter);

		tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
		tabLayout.setupWithViewPager(viewPager);
		fabnotebookmain.setVisibility(View.VISIBLE);
	}
}
