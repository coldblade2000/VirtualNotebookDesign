package com.twotowerstudios.virtualnotebookdesign.NewNotebookDialog;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;
import com.twotowerstudios.virtualnotebookdesign.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewNotebookFragment extends DialogFragment {

	RecyclerView rvNewNotebook;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_new_notebook,container,false);
	}
	@Override
	public void onViewCreated(View v, Bundle savedInstanceState){
		Toolbar toolbar = (Toolbar) v.findViewById(R.id.newnotebooktoolbar);
		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				return false;
			}
		});
		toolbar.inflateMenu(R.menu.newnotebook);
		toolbar.setTitle("Create new Notebook");

		rvNewNotebook = (RecyclerView) v.findViewById(R.id.rvNewNotebook);
		final GridLayoutManager rvNotebookManager = new GridLayoutManager(getContext(),4);
		rvNewNotebook.setLayoutManager(rvNotebookManager);
		NewNotebookAdapter adapter= new NewNotebookAdapter(getContext(), Helpers.getPossibleColors(),10);
		rvNewNotebook.setAdapter(adapter);
		Log.d("onViewCreated", ""+rvNewNotebook.getWidth());


	}

	public static NewNotebookFragment newInstance() {
			NewNotebookFragment f = new NewNotebookFragment();
			return f;

		}
}
