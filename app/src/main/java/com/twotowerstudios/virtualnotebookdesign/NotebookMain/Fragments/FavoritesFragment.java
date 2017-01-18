package com.twotowerstudios.virtualnotebookdesign.NotebookMain.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 * Use the {@link FavoritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment{
	ArrayList<Page> favPageList = new ArrayList<>();
	public RecyclerView rvFavorite;
	NotebookAdapterToAct interf;
	TextView tvFavoritesEmpty;

    public FavoritesFragment() {
        // Required empty public constructor
    }
    public static FavoritesFragment newInstance(int page, String title, ArrayList<Page> pageList, NotebookAdapterToAct interf) {
        FavoritesFragment fragment = new FavoritesFragment();
		fragment.interf=interf;
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("title", title);
		args.putParcelable("list", Parcels.wrap(pageList));
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		ArrayList<Page> fullList = Parcels.unwrap(getArguments().getParcelable("list"));
		for(Page p: fullList) {
			if(p.isFavorite()){
				favPageList.add(p);
			}
		}
		Collections.sort(favPageList);

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

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		Log.d("FavoriteFragment", "onviewcreated called");

		rvFavorite = (RecyclerView) view.findViewById(R.id.rvFavorites);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
		rvFavorite.setLayoutManager(linearLayoutManager);
		rvFavorite.setAdapter(new NotebookPageAdapter(getContext(), favPageList, interf,true));
		tvFavoritesEmpty = (TextView) view.findViewById(R.id.tvFavoritesEmpty);
		if(!favPageList.isEmpty()){
			tvFavoritesEmpty.setVisibility(View.GONE);
		}
	}


}
