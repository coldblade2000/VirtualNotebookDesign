package com.twotowerstudios.virtualnotebookdesign.NotebookMain.Fragments.EveryPage;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twotowerstudios.virtualnotebookdesign.Objects.Page;
import com.twotowerstudios.virtualnotebookdesign.R;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EveryPageFragment extends Fragment {
	ArrayList<Page> pageList;
	RecyclerView rvEveryPage;
	public EveryPageFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pageList = Parcels.unwrap(getArguments().getParcelable("list"));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_every_page, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		rvEveryPage = (RecyclerView) view.findViewById(R.id.rvEveryPage);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
		rvEveryPage.setLayoutManager(linearLayoutManager);
		rvEveryPage.setAdapter(new EveryPageAdapter(getContext(), pageList));
	}

	public static EveryPageFragment newInstance(int page, String title, ArrayList<Page> pageList) {
		EveryPageFragment fragment = new EveryPageFragment();
		Bundle args = new Bundle();
		args.putInt("page", page);
		args.putString("title", title);
		args.putParcelable("list", Parcels.wrap(pageList));
		fragment.setArguments(args);
		return fragment;
	}

}
