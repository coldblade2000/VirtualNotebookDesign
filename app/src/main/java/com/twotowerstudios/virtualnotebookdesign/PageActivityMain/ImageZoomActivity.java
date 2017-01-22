package com.twotowerstudios.virtualnotebookdesign.PageActivityMain;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.twotowerstudios.virtualnotebookdesign.Objects.ChildBase;
import com.twotowerstudios.virtualnotebookdesign.R;

import org.parceler.Parcels;

public class ImageZoomActivity extends AppCompatActivity {
	ChildBase image;
	Toolbar tbfullimage;
	SubsamplingScaleImageView ivFullscreenPage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_image_zoom);
		image = Parcels.unwrap(getIntent().getParcelableExtra("imagechild"));


		tbfullimage = (Toolbar)findViewById(R.id.tbfullimage);
		setSupportActionBar(tbfullimage);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle(image.getTitle());
		tbfullimage.setTitleTextColor(Color.parseColor("#ffffff"));
		tbfullimage.setNavigationIcon(R.drawable.ic_close_white_24dp);
		tbfullimage.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		ivFullscreenPage = (SubsamplingScaleImageView) findViewById(R.id. ivFullscreenPage);
		ivFullscreenPage.setOrientation(SubsamplingScaleImageView.ORIENTATION_USE_EXIF);
		ivFullscreenPage.setImage(ImageSource.uri(image.getUri()));
		ivFullscreenPage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
}
