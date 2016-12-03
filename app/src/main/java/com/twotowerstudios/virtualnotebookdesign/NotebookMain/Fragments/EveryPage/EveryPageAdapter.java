package com.twotowerstudios.virtualnotebookdesign.NotebookMain.Fragments.EveryPage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;
import com.twotowerstudios.virtualnotebookdesign.Objects.Page;
import com.twotowerstudios.virtualnotebookdesign.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Panther II on 02/12/2016.
 */

public class EveryPageAdapter extends RecyclerView.Adapter<EveryPageAdapter.ViewHolder>{

	Context context;
	ArrayList<Page> pageList = new ArrayList<>();
	public class ViewHolder extends RecyclerView.ViewHolder {
		public TextView tvFavPage;
		public TextView tvFavName;
		public TextView tvFavSub;
		public TextView tvFavItemCount;
		public ImageView ivFavStar;

		public ViewHolder(View itemView) {
			super(itemView);
			tvFavPage = (TextView) itemView.findViewById(R.id.tvFavPage);
			tvFavSub = (TextView) itemView.findViewById(R.id.tvFavSub);
			tvFavName = (TextView) itemView.findViewById(R.id.tvFavName);
			tvFavItemCount = (TextView) itemView.findViewById(R.id.tvFavItemCount);
			ivFavStar = (ImageView) itemView.findViewById(R.id.ivFavStar);
		}
	}
	public EveryPageAdapter(){}
	public EveryPageAdapter(Context context, ArrayList<Page> list){
		this.context = context;
		Collections.sort(list);
		pageList = list;
	}
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pagelistitem, parent, false));
	}


	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Page page = pageList.get(position);
		holder.tvFavPage.setText(page.getPageNumber());
		holder.tvFavName.setText(page.getName());
		holder.tvFavSub.setText("Last Modified "+
				DateUtils.getRelativeTimeSpanString(page.getLastModifiedMillis(),Helpers.getCurrentTimeInMillis(),DateUtils.SECOND_IN_MILLIS));
		holder.tvFavItemCount.setText(page.getNumberOfItems());
		if(page.isFavorite()){
			holder.ivFavStar.setVisibility(View.VISIBLE);
		}else{
			holder.ivFavStar.setVisibility(View.INVISIBLE);
		}

	}

	@Override
	public int getItemCount() {
		return pageList.size();
	}


}
