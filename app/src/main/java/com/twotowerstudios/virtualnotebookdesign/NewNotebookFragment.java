package com.twotowerstudios.virtualnotebookdesign;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewNotebookFragment extends Fragment {


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_new_notebook,container,false);
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState){
		Toolbar toolbar = (Toolbar) view.findViewById(R.id.newnotebooktoolbar);

	}

}
