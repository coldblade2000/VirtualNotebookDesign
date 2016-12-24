package com.twotowerstudios.virtualnotebookdesign.PageActivityMain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.twotowerstudios.virtualnotebookdesign.Objects.Page;
import com.twotowerstudios.virtualnotebookdesign.R;

import org.parceler.Parcels;

public class PageActivityMain extends AppCompatActivity implements PageActivityAdapter.PageAdapterToAct{

	Toolbar tbpagemain;
	RecyclerView rvpagemain;
	Page page;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		page = Parcels.unwrap(getIntent().getParcelableExtra("page"));
		setContentView(R.layout.activity_page_main);

		tbpagemain = (Toolbar) findViewById(R.id.tbpagemain);
		rvpagemain = (RecyclerView)  findViewById(R.id.rvpagemain);

		setSupportActionBar(tbpagemain);
		tbpagemain.setTitle(""+page.name);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		StaggeredGridLayoutManager lmpagemain = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
		rvpagemain.setLayoutManager(lmpagemain);
		rvpagemain.setAdapter(new PageActivityAdapter(getApplicationContext(),page.getContent(), this));
	}

	@Override
	public void clickListener(int position) {

	}
}
