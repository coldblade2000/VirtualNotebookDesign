package com.twotowerstudios.virtualnotebookdesign.PageActivityMain;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;
import com.twotowerstudios.virtualnotebookdesign.Objects.ChildBase;
import com.twotowerstudios.virtualnotebookdesign.Objects.Page;
import com.twotowerstudios.virtualnotebookdesign.R;

import org.parceler.Parcels;

import java.io.File;

public class PageActivityMain extends AppCompatActivity implements PageActivityAdapter.PageAdapterToAct, NewPageChildFragment.OnFragmentInteractionListener, ModalBottomSheet.OnModalBottomSheetListener {

	Toolbar tbpagemain;
	RecyclerView rvpagemain;
	Page page;
	String notebookUID16;
	boolean isMainfabOpen;
	/*LinearLayout bottom_drawer;
	BottomSheetBehavior bottomSheetBehavior;*/
	FloatingActionButton fabPageMain1, fabTextChild, fabImageChild, fabDriveChild;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		page = Parcels.unwrap(getIntent().getParcelableExtra("page"));
		notebookUID16 = getIntent().getStringExtra("notebookUID16");
		setContentView(R.layout.activity_page_main);

		tbpagemain = (Toolbar) findViewById(R.id.tbpagemain);
		rvpagemain = (RecyclerView) findViewById(R.id.rvpagemain);
		setSupportActionBar(tbpagemain);
		tbpagemain.setTitle("" + page.getName());
		tbpagemain.inflateMenu(R.menu.pagemainmenu);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		StaggeredGridLayoutManager lmpagemain = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
		rvpagemain.setLayoutManager(lmpagemain);
		rvpagemain.setAdapter(new PageActivityAdapter(getApplicationContext(), page.getContent(), this));

		fabPageMain1 = (FloatingActionButton) findViewById(R.id.fabPageMain1);
		isMainfabOpen = false;
		fabTextChild = ((FloatingActionButton) findViewById(R.id.fabTextChild));
		fabImageChild = ((FloatingActionButton) findViewById(R.id.fabImageChild));
		fabDriveChild = ((FloatingActionButton) findViewById(R.id.fabDriveChild));

		/**bottom_drawer = (LinearLayout) findViewById(R.id.bottom_drawer);
		 bottomSheetBehavior = BottomSheetBehavior.from(bottom_drawer);
		 bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);*/


		fabPageMain1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (!isMainfabOpen) {

					fabTextChild.show();
					fabImageChild.show();
					fabDriveChild.show();
					isMainfabOpen = true;
					ObjectAnimator openFirstSubfab = ObjectAnimator.ofFloat(fabTextChild, View.TRANSLATION_Y, 200, 0);
					openFirstSubfab.start();
					ObjectAnimator openSecondSubfab = ObjectAnimator.ofFloat(fabImageChild, View.TRANSLATION_Y, 400, 0);
					openSecondSubfab.start();
					ObjectAnimator openThirdSubfab = ObjectAnimator.ofFloat(fabDriveChild, View.TRANSLATION_Y, 600, 0);
					openThirdSubfab.start();
					ObjectAnimator rotateMainfab = ObjectAnimator.ofFloat(fabPageMain1, View.ROTATION, 0, 135);
					rotateMainfab.start();

				} else if (isMainfabOpen) {
					isMainfabOpen = false;
					ObjectAnimator rotateMainfab = ObjectAnimator.ofFloat(fabPageMain1, View.ROTATION, 135, 270);
					rotateMainfab.start();
					ObjectAnimator closeFirstSubfab = ObjectAnimator.ofFloat(fabTextChild, View.TRANSLATION_Y, 0, 200);
					closeFirstSubfab.start();
					ObjectAnimator closeSecondSubfab = ObjectAnimator.ofFloat(fabImageChild, View.TRANSLATION_Y, 0, 400);
					closeSecondSubfab.start();
					ObjectAnimator closeThirdSubfab = ObjectAnimator.ofFloat(fabDriveChild, View.TRANSLATION_Y, 0, 600);
					closeThirdSubfab.start();
					fabTextChild.hide();
					fabImageChild.hide();
					fabDriveChild.hide();
				}
			}


		});
		fabTextChild.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//On clicking fab
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
				if (prev != null) {
					ft.remove(prev);
				}
				ft.addToBackStack(null);
				// Create and show the dialog.
				NewPageChildFragment newFragment = NewPageChildFragment.newInstance('t', page);
				newFragment.show(ft, "dialog");
			}
		});
		fabImageChild.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
				ModalBottomSheet modalBottomSheet = new ModalBottomSheet();
				modalBottomSheet.show(getSupportFragmentManager(), "bottom sheet");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.pagemainmenu, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (page.isFavorite()) {
			menu.findItem(R.id.mPageFav).setIcon(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.ic_star_white_24dp));
		} else {
			menu.findItem(R.id.mPageFav).setIcon(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.ic_star_border_white_24dp));
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.mPageFav:
				if (page.isFavorite()) {
					page.setIsFavorite(false);
				} else {
					page.setIsFavorite(true);
				}
				invalidateOptionsMenu();
				Helpers.addPageFromUID16(page.getParentUID(), page, getApplicationContext());
				break;
			case android.R.id.home:
				/**Intent intent = new Intent(this, NotebookMainActivity.class);
				 intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				 intent.putExtra("notebookUID16", notebookUID16);
				 intent.putExtra("parent", "PageActivityMain");
				 startActivity(intent);*/
				finish();
				return true;

		}
		return true;
	}


	@Override
	public void clickListener(int position) {

	}

	@Override
	public void returnTextChildInfo(String title, String text) {
		Log.d("PageActivityMain", "returnTextChildInfo called.");
		ChildBase newChild = new ChildBase(title, text, page.getParentUID());
		page.addToPage(newChild);
		Helpers.addPageFromUID16(page.getParentUID(), page, getApplicationContext());
		//((PageActivityAdapter) rvpagemain.getAdapter()).refreshList(newChild);
		rvpagemain.getAdapter().notifyDataSetChanged();
	}

	@Override
	public void returnDecision(String tag) {
		String newImageName = "i" + Helpers.generateUniqueId(16);
		if (tag.equals("camera")) {
			Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			if (takePicture.resolveActivity(getPackageManager()) != null) {
				File photo = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), newImageName + ".png");
				//Uri photoURI = FileProvider.getUriForFile(this, "com.twotowerstudios.virtualnotebookdesign.fileprovider", photo);
				Uri photoURI = Uri.fromFile(photo);
				takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
				startActivityForResult(takePicture, 1);
				ChildBase newImage = new ChildBase("", newImageName, page.getParentUID(), photoURI, getApplicationContext());
				page.addToPage(newImage);
				Helpers.addPageFromUID16(page.getParentUID(), page, getApplicationContext());
				rvpagemain.getAdapter().notifyDataSetChanged();
			}
		} else if (tag.equals("gallery")) {

		}
	}
}
