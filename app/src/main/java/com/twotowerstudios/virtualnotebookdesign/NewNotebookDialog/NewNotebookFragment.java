package com.twotowerstudios.virtualnotebookdesign.NewNotebookDialog;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;
import com.twotowerstudios.virtualnotebookdesign.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewNotebookFragment extends DialogFragment {

	RecyclerView rvNewNotebook;
	int activeColor;
	ArrayList<String> colors=Helpers.getPossibleColors();
	Toolbar toolbar;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_new_notebook,container,false);
	}
	@Override
	public void onViewCreated(View v, Bundle savedInstanceState){

		activeColor=new Random().nextInt(colors.size());

		toolbar = (Toolbar) v.findViewById(R.id.newnotebooktoolbar);
		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				return false;
			}
		});
		toolbar.inflateMenu(R.menu.newnotebook);
		toolbar.setTitle("Create new Notebook");

		if(Helpers.isColorDark(Color.parseColor(colors.get(activeColor)))){
			toolbar.setTitleTextColor(getResources().getColor(R.color.md_dark_primary_text));
		}else{
			toolbar.setTitleTextColor(getResources().getColor(R.color.md_light_primary_text));
		}
		rvNewNotebook = (RecyclerView) v.findViewById(R.id.rvNewNotebook);
		final GridLayoutManager rvNotebookManager = new GridLayoutManager(getContext(),6);
		rvNewNotebook.setLayoutManager(rvNotebookManager);
		NewNotebookAdapter adapter= new NewNotebookAdapter(getContext(), Helpers.getPossibleColors(), activeColor);

		toolbar.setBackgroundColor(Color.parseColor(Helpers.getPossibleColors().get(activeColor)));
		rvNewNotebook.setAdapter(adapter);
		Log.d("onViewCreated", ""+rvNewNotebook.getWidth());


	}
	@Override
	public void onResume(){
		super.onResume();
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;
		getDialog().getWindow().setLayout((int)(width*0.9),(int)(height*0.8));
	}
	public static NewNotebookFragment newInstance() {
			NewNotebookFragment f = new NewNotebookFragment();
			return f;
		}
	public void changeColor(int position){
		if(Helpers.isColorDark(Color.parseColor(colors.get(position)))){
			toolbar.setTitleTextColor(getResources().getColor(R.color.md_dark_primary_text));
		}else{
			toolbar.setTitleTextColor(getResources().getColor(R.color.md_light_primary_text));
		}
		toolbar.setBackgroundColor(Color.parseColor(Helpers.getPossibleColors().get(position)));
	}
}
