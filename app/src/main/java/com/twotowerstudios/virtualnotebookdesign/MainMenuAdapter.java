package com.twotowerstudios.virtualnotebookdesign;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Panther II on 10/10/2016.
 */

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.ViewHolder>{

	private List<CommonBooksCard> cardList;
	private Context context;
	public static class ViewHolder extends RecyclerView.ViewHolder{
		public ViewHolder(View v){
			super(v);
		}
	}
	public MainMenuAdapter(Context context, List<CommonBooksCard> cardList){
		this.cardList = cardList;
		this.context = context;
	}
	@Override
	public MainMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.commonbookscard, parent, false);
		return new ViewHolder(v);
	}


	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {

	}

	@Override
	public int getItemCount() {
		return cardList.size() ;
	}
}

