package com.twotowerstudios.virtualnotebookdesign.NotebookMain;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.twotowerstudios.virtualnotebookdesign.NotebookMain.Fragments.EveryPageFragment;
import com.twotowerstudios.virtualnotebookdesign.NotebookMain.Fragments.FavoritesFragment;
import com.twotowerstudios.virtualnotebookdesign.Objects.Page;

import java.util.ArrayList;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {
	private static final int NUM_ITEMS = 2;
	private ArrayList<Page> pageList;
	private int color;
	private NotebookAdapterToAct interf;
	private String UID16;
	private final String titles[] = {"All Pages", "Favorites"};


	public ViewPagerAdapter(FragmentManager fm, ArrayList<Page> list, int color, NotebookAdapterToAct interf, String UID16) {
		super(fm);
		pageList = list;
		this.color=color;
		this.interf = interf;
		this.UID16 = UID16;
	}
	@Override
	public Fragment getItem(int position) {
		switch (position) {
			case 0:
				return EveryPageFragment.newInstance(0, titles[0], pageList,color, interf, UID16);
			case 1:
				return FavoritesFragment.newInstance(1, titles[1], pageList,color, interf, UID16);
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

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}
	public void refreshList(ArrayList<Page> list){
		pageList.clear();
		pageList.addAll(list);
		notifyDataSetChanged();
	}
}