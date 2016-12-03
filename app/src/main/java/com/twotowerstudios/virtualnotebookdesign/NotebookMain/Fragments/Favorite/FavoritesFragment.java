package com.twotowerstudios.virtualnotebookdesign.NotebookMain.Fragments.Favorite;


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
 * Use the {@link FavoritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment {
	ArrayList<Page> pageList;
	RecyclerView rvFavorite;
    public FavoritesFragment() {
        // Required empty public constructor
    }

    public static FavoritesFragment newInstance(int page, String title, ArrayList<Page> pageList) {
        FavoritesFragment fragment = new FavoritesFragment();
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
		pageList = Parcels.unwrap(getArguments().getParcelable("list"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		rvFavorite = (RecyclerView) view.findViewById(R.id.rvFavorites);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
		rvFavorite.setLayoutManager(linearLayoutManager);
		rvFavorite.setAdapter(new FavoritesAdapter(getContext(), pageList));
	}
}
