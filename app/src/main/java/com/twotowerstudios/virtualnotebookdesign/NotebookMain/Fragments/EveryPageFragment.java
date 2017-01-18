package com.twotowerstudios.virtualnotebookdesign.NotebookMain.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twotowerstudios.virtualnotebookdesign.NotebookMain.NotebookAdapterToAct;
import com.twotowerstudios.virtualnotebookdesign.Objects.Page;
import com.twotowerstudios.virtualnotebookdesign.R;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class EveryPageFragment extends Fragment{
	ArrayList<Page> pageList;
	public RecyclerView rvEveryPage;
	NotebookAdapterToAct interf;
	String parentUID16;

	public EveryPageFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pageList = Parcels.unwrap(getArguments().getParcelable("list"));
		parentUID16 = getArguments().getString("UID16");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_favorites, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		rvEveryPage = (RecyclerView) view.findViewById(R.id.rvFavorites);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
		rvEveryPage.setLayoutManager(linearLayoutManager);
		Collections.sort(pageList);
		TextView tvFavoritesEmpty = (TextView) view.findViewById(R.id.tvFavoritesEmpty);
		tvFavoritesEmpty.setVisibility(View.GONE);
		rvEveryPage.setAdapter(new NotebookPageAdapter(getContext(), pageList, interf,false));
	}

	public static EveryPageFragment newInstance(int page, String title, ArrayList<Page> pageList, NotebookAdapterToAct interf) {
		EveryPageFragment fragment = new EveryPageFragment();
		fragment.interf=interf;
		Bundle args = new Bundle();
		args.putInt("page", page);
		args.putString("title", title);
		args.putParcelable("list", Parcels.wrap(pageList));
		fragment.setArguments(args);
		return fragment;
	}
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		interf = (NotebookAdapterToAct) getActivity();
	}

	@Override
	public void onDetach() {
		super.onDetach();
		interf=null;
	}

}
