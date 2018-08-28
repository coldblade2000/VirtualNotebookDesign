package com.twotowerstudios.virtualnotebookdesign.PageActivityMain;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;
import com.twotowerstudios.virtualnotebookdesign.Objects.ChildBase;
import com.twotowerstudios.virtualnotebookdesign.R;

import org.parceler.Parcels;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_zoom, menu);
		return true;
	}
	private static int exifToDegrees(int exifOrientation) {
		if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; }
		else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; }
		else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }
		return 0;
	}
	private static int degreesToExif(int degrees){
		switch (degrees){
			case 0:
				return ExifInterface.ORIENTATION_NORMAL;

			case 90:
				return ExifInterface.ORIENTATION_ROTATE_90;

			case 180:
				return ExifInterface.ORIENTATION_ROTATE_180;

			case 270:
				return ExifInterface.ORIENTATION_ROTATE_270;

			default:
				return ExifInterface.ORIENTATION_NORMAL;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Bitmap myImage = null;
		int rotationInDegrees = 0;
		ExifInterface oldInterface, newInterface;
		try {
			oldInterface = new ExifInterface(image.getPath());
			newInterface = new ExifInterface(image.getPath());
			int rotation = oldInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			rotationInDegrees = exifToDegrees(rotation)+90;
			myImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image.getUri());
			newInterface.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(degreesToExif(rotationInDegrees)));
			newInterface.saveAttributes();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Matrix mat = new Matrix();
		mat.preRotate(rotationInDegrees);
		myImage = Bitmap.createBitmap(myImage, 0, 0, myImage.getWidth(), myImage.getHeight(), mat, true);
		ivFullscreenPage.setImage(ImageSource.bitmap(myImage));

		/*try {
			FileOutputStream outputStream = new FileOutputStream(image.getFile());
			myImage.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
}
