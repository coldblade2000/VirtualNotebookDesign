package com.twotowerstudios.virtualnotebookdesign.NotebookMain;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.twotowerstudios.virtualnotebookdesign.NotebookMain.Fragments.EveryPageFragment;
import com.twotowerstudios.virtualnotebookdesign.NotebookMain.Fragments.FavoritesFragment;
import com.twotowerstudios.virtualnotebookdesign.Objects.Page;

import java.util.ArrayList;

/**
 * Created by Panther II on 02/12/2016.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
	private static int NUM_ITEMS = 2;
	private ArrayList<Page> pageList;
	private NotebookAdapterToAct interf;
	private String titles[] = {"All Pages", "Favorites"};

	private EveryPageFragment everyPageFragment;
	private FavoritesFragment favoritesFragment;

	public ViewPagerAdapter(FragmentManager fm, ArrayList<Page> list, NotebookAdapterToAct interf) {
		super(fm);
		pageList = list;
		this.interf = interf;
	}


	@Override
	public Fragment getItem(int position) {
		switch (position) {
			case 0:
				everyPageFragment=  EveryPageFragment.newInstance(0, titles[0], pageList, interf);
				return everyPageFragment;
			case 1:
				favoritesFragment = FavoritesFragment.newInstance(1, titles[1], pageList, interf);
				return favoritesFragment;
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