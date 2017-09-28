package com.twotowerstudios.virtualnotebookdesign.PageActivityMain;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.commonsware.cwac.cam2.CameraActivity;
import com.commonsware.cwac.cam2.ZoomStyle;
import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;
import com.twotowerstudios.virtualnotebookdesign.Objects.ChildBase;
import com.twotowerstudios.virtualnotebookdesign.Objects.Page;
import com.twotowerstudios.virtualnotebookdesign.R;

import org.parceler.Parcels;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PageActivityMain extends AppCompatActivity implements PageActivityAdapter.PageAdapterToAct, NewPageChildFragment.OnFragmentInteractionListener, ModalBottomSheet.OnModalBottomSheetListener {

	Toolbar tbpagemain;
	RecyclerView rvpagemain;
	PageActivityAdapter pageAdapter;
	Page page;
	ArrayList<ChildBase> contents = new ArrayList<>();
	String notebookUID16;
	boolean isMainfabOpen;
	/*LinearLayout bottom_drawer;
	BottomSheetBehavior bottomSheetBehavior;*/
	FloatingActionButton fabPageMain1, fabTextChild, fabImageChild;
	int CAMERAPIC =3, GALLERYPIC=2;
	private boolean allowCamera;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		page = Parcels.unwrap(getIntent().getParcelableExtra("page"));
		contents = page.getContent();
		//DEBUG, REMOVE
		/**if (true) {
			ArrayList<ChildBase> newcontent = new ArrayList<>();
			for (ChildBase a : contents) {
				if (a.getPageUID() == null) {
					a.setPageUID(page.getUID());
					a.setNotebookUID(page.getParentUID());

				}
				newcontent.add(a);
			}
			page.setContent(newcontent);
			Helpers.addPageFromUID16(page.getParentUID(), page, getApplicationContext());
		}*/

		notebookUID16 = getIntent().getStringExtra("notebookUID16");
		setContentView(R.layout.activity_page_main);
		allowCamera = ContextCompat.checkSelfPermission(this,
				Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
		tbpagemain = (Toolbar) findViewById(R.id.tbpagemain);
		rvpagemain = (RecyclerView) findViewById(R.id.rvpagemain);
		setSupportActionBar(tbpagemain);
		tbpagemain.inflateMenu(R.menu.pagemainmenu);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("" + page.getName());
		if (page.getDateMillis() != 0) {
			getSupportActionBar().setSubtitle("" + Helpers.millisDateToString(page.getDateMillis(), 2));
		}
		//===============================================================================================================
		StaggeredGridLayoutManager lmpagemain = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
		rvpagemain.setLayoutManager(lmpagemain);
		pageAdapter = new PageActivityAdapter(getApplicationContext(), contents, this, this);
		rvpagemain.setAdapter(pageAdapter);
		fabPageMain1 = (FloatingActionButton) findViewById(R.id.fabPageMain1);
		isMainfabOpen = false;
		fabTextChild = ((FloatingActionButton) findViewById(R.id.fabTextChild));
		fabImageChild = ((FloatingActionButton) findViewById(R.id.fabImageChild));
		//fabDriveChild = ((FloatingActionButton) findViewById(R.id.fabDriveChild)); UNUSED FOR NOW
		/* bottom_drawer = (LinearLayout) findViewById(R.id.bottom_drawer);
		 bottomSheetBehavior = BottomSheetBehavior.from(bottom_drawer);
		 bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);*/
		//===============================================================================================================
		fabPageMain1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (!isMainfabOpen) {
					fabTextChild.show();
					fabImageChild.show();
					//fabDriveChild.show();
					isMainfabOpen = true;
					ObjectAnimator openFirstSubfab = ObjectAnimator.ofFloat(fabTextChild, View.TRANSLATION_Y, 200, 0);
					openFirstSubfab.start();
					ObjectAnimator openSecondSubfab = ObjectAnimator.ofFloat(fabImageChild, View.TRANSLATION_Y, 400, 0);
					openSecondSubfab.start();
					//ObjectAnimator openThirdSubfab = ObjectAnimator.ofFloat(fabDriveChild, View.TRANSLATION_Y, 600, 0);
					//openThirdSubfab.start();
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
					//ObjectAnimator closeThirdSubfab = ObjectAnimator.ofFloat(fabDriveChild, View.TRANSLATION_Y, 0, 600);
					//closeThirdSubfab.start();
					fabTextChild.hide();
					fabImageChild.hide();
					//fabDriveChild.hide();
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
		//===============================================================================================================
	}

	@Override
	protected void onResume() {
		super.onResume();
		pageAdapter.notifyItemChanged(contents.size()-1);
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
				/*Intent intent = new Intent(this, NotebookMainActivity.class);
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
	public void returnTextChildInfo(String title, String text) {
		Log.d("PageActivityMain", "returnTextChildInfo called.");
		ChildBase newChild = new ChildBase(title, text, page.getParentUID(), page.getUID());
		page.addToPage(newChild);
		Helpers.addPageFromUID16(page.getParentUID(), page, getApplicationContext());
		//((PageActivityAdapter) rvpagemain.getAdapter()).refreshList(newChild);
		rvpagemain.getAdapter().notifyDataSetChanged();
	}

	@Override
	public void returnDecision(String tag, final String title) {
		final String newImageName = "i" + Helpers.generateUniqueId(16);
		if (tag.equals("camera")) {
			locationpermission();
				File photo = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath()+"/"+ notebookUID16+ "/"+ newImageName + ".png");
				Intent takePicture = new CameraActivity.IntentBuilder(PageActivityMain.this)
						.debug()
						.requestPermissions()
						.zoomStyle(ZoomStyle.PINCH)
						.to(photo)
						.confirmationQuality(0.8f)
						.build()
						.putExtra("path", getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + notebookUID16+"/"+ newImageName + ".png");
				startActivityForResult(takePicture, CAMERAPIC);
				ChildBase newImage = new ChildBase("" + title, newImageName, page.getParentUID(), page.getUID(),
						Uri.fromFile(photo), getApplicationContext());
				page.addToPage(newImage);
				Helpers.addPageFromUID16(page.getParentUID(), page, getApplicationContext());
				//contents.add(newImage);
				pageAdapter.notifyItemInserted(contents.size() - 1);
		} else if (tag.equals("gallery")) {
			File nomedia = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath()+"/"+ notebookUID16+ "/" + ".nomedia");
			if (!nomedia.exists()) {
				try {
					nomedia.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			intent.putExtra("title", title);
			startActivityForResult(Intent.createChooser(intent,
					"Select Picture"), GALLERYPIC);

		}
	}
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		final String filename = "i"+Helpers.generateUniqueId(16);
		if (requestCode == GALLERYPIC && resultCode == RESULT_OK && data != null){
			File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath()+"/"+ notebookUID16+ "/"+ filename+".png");
			Uri selectedImageUri = data.getData();
			Bitmap bitmap = null;
			try {
				bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
			} catch (IOException e) {
				e.printStackTrace();
			}

			FileOutputStream outStream = null;
			try {
				outStream = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.PNG, 97, outStream);
				outStream.flush();
				outStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			ChildBase newImage = new ChildBase("", filename, page.getParentUID(), page.getUID(), Uri.fromFile(file), getApplicationContext());
			contents.add(newImage);
			page.setContent(contents);
			Helpers.addPageFromUID16(page.getParentUID(), page, getApplicationContext());
			pageAdapter.notifyItemInserted(contents.size()-1);
		}
	}

	private static final int MY_PERMISSIONS_REQUEST_CAMERA = 0;

	private void locationpermission() {
		// Here, thisActivity is the current activity
		if (ContextCompat.checkSelfPermission(this
				,
				Manifest.permission.CAMERA)
				!= PackageManager.PERMISSION_GRANTED) {

			// Should we show an explanation?
			if (ActivityCompat.shouldShowRequestPermissionRationale(this,
					Manifest.permission.CAMERA)) {

				// Show an expanation to the user *asynchronously* -- don't block
				// this thread waiting for the user's response! After the user
				// sees the explanation, try again to request the permission.

			} else {

				// No explanation needed, we can request the permission.

				ActivityCompat.requestPermissions(this,
						new String[]{Manifest.permission.CAMERA},
						MY_PERMISSIONS_REQUEST_CAMERA);

				// MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
				// app-defined int constant. The callback method gets the
				// result of the request.
			}
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {
			case MY_PERMISSIONS_REQUEST_CAMERA:
				// permission was granted, yay! Do the
// contacts-related task you need to do.
// permission denied, boo! Disable the
// functionality that depends on this permission.
				allowCamera = grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED;
				return;
		}
	}

	@Override
	public void clickListener(String uid) {
		ChildBase child = null;
		for (ChildBase a : contents) {
			if (a.getUID16().equals(uid)) {
				child = a;
				break;
			}
		}
		if (child != null) {
			Intent intent = new Intent(this, ImageZoomActivity.class);
			intent.putExtra("imagechild", Parcels.wrap(child));
			startActivity(intent);
		}
	}


}
