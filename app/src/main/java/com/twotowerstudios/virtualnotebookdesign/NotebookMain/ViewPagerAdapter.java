package com.twotowerstudios.virtualnotebookdesign.NotebookMain;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.twotowerstudios.virtualnotebookdesign.NotebookMain.Fragments.EveryPage.EveryPageFragment;
import com.twotowerstudios.virtualnotebookdesign.NotebookMain.Fragments.Favorite.FavoritesFragment;
import com.twotowerstudios.virtualnotebookdesign.Objects.Page;

import java.util.ArrayList;

/**
 * Created by Panther II on 02/12/2016.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
	private static int NUM_ITEMS = 2;
	ArrayList<Page> pageList;
	public ViewPagerAdapter(FragmentManager fm, ArrayList<Page> list) {
		super(fm);
		pageList = list;
	}


	@Override
	public Fragment getItem(int position) {
		switch(position){
			case 0: //FavoritesFragment
				return EveryPageFragment.newInstance(0, "All Pages",pageList);
			case 1:
				return FavoritesFragment.newInstance(1, "Favorites",pageList);
			default:
				return null;
		}
	}


	@Override
	public int getCount() {
		return NUM_ITEMS;
	}
}
