package com.twotowerstudios.virtualnotebookdesign.NotebookMain;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.twotowerstudios.virtualnotebookdesign.MainMenu.MainActivity;
import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;
import com.twotowerstudios.virtualnotebookdesign.NotebookSelection.NotebookSelection;
import com.twotowerstudios.virtualnotebookdesign.Objects.Notebook;
import com.twotowerstudios.virtualnotebookdesign.Objects.Page;
import com.twotowerstudios.virtualnotebookdesign.R;

import org.parceler.Parcels;

import java.util.ArrayList;

public class NotebookMainActivity extends AppCompatActivity {

	Toolbar toolbar;
	Notebook notebook;
	FloatingActionButton fab;
	CollapsingToolbarLayout collapsingToolbarLayout;
	String parent;
	TextView tvSub;
	ViewPager viewPager;
	FragmentPagerAdapter viewPagerAdapter;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook_main);
		notebook = Parcels.unwrap(getIntent().getParcelableExtra("notebook"));
		parent = getIntent().getExtras().getString("parent");
		Toast.makeText(getApplicationContext(), "Parent is: "+ parent, Toast.LENGTH_SHORT).show();
		tvSub = (TextView) findViewById(R.id.tvSub);
		tvSub.setText("Last Modified: "+ DateUtils.getRelativeTimeSpanString(notebook.getLastModified(), Helpers.getCurrentTimeInMillis(), DateUtils.SECOND_IN_MILLIS));

		AppBarLayout appbarlayoutNotebook = (AppBarLayout) findViewById(R.id.appbarlayoutNotebook);
		appbarlayoutNotebook.setBackgroundColor(notebook.color);
		collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapseToolbarNotebook);
		toolbar = (Toolbar) findViewById(R.id.toolbarNotebook);
		setSupportActionBar(toolbar);
		toolbar.setBackgroundColor(notebook.color);
		getSupportActionBar().setTitle(notebook.name);
		getSupportActionBar().setSubtitle("Last modified: " + DateUtils.getRelativeTimeSpanString(notebook.getLastModified(), Helpers.getCurrentTimeInMillis(), DateUtils.SECOND_IN_MILLIS));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		if(Helpers.isColorDark(notebook.color)){
			collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getApplicationContext(), R.color.md_white_1000));
			collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.md_white_1000));
			tvSub.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.md_white_1000));
		}else{
			collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getApplicationContext(), R.color.md_black_1000));
			collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.md_black_1000));
			tvSub.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.md_black_1000));
		}
		toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
		fab = (FloatingActionButton) findViewById(R.id.fabnotebookmain);
		fab.setBackgroundTintList(ColorStateList.valueOf(Helpers.getSingleColorAccent(getApplicationContext(),notebook.color)));

		viewPager = (ViewPager) findViewById(R.id.viewpager);
		ArrayList<Page> pageList = notebook.pages;
		viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),pageList);
		viewPager.setAdapter(viewPagerAdapter);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			// Respond to the action bar's Up/Home button
			case android.R.id.home:
				// Launch the correct Activity here
				if (parent.equals("MainActivity")){
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
				}

		}
		return super.onOptionsItemSelected(item);
	}
}