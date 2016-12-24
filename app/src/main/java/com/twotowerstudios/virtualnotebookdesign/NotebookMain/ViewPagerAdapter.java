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
	NotebookAdapterToAct interf;
	private String titles[] = {"All Pages", "Favorites"};
	public ViewPagerAdapter(FragmentManager fm, ArrayList<Page> list, NotebookAdapterToAct interf) {
		super(fm);
		pageList = list;
		this.interf=interf;
	}


	@Override
	public Fragment getItem(int position) {
		switch(position){
			case 0:
				return EveryPageFragment.newInstance(0, titles[0],pageList,interf);
			case 1:
				return FavoritesFragment.newInstance(1, titles[1],pageList,interf);
			default:
				return null;
		}
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titles[position];
	}

	@Override
	public int getCount() {
		return NUM_ITEMS;
	}
}
