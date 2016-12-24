package com.twotowerstudios.virtualnotebookdesign.NotebookMain.Fragments.Favorite;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;
import com.twotowerstudios.virtualnotebookdesign.NotebookMain.NotebookAdapterToAct;
import com.twotowerstudios.virtualnotebookdesign.Objects.Page;
import com.twotowerstudios.virtualnotebookdesign.R;

import java.util.ArrayList;

/**
 * Created by coldblade2000 on 11/24/16.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder>{
	Context context;
	ArrayList<Page> favPageList = new ArrayList<>();
	NotebookAdapterToAct interf;

    public static class ViewHolder extends RecyclerView.ViewHolder{
		public TextView tvFavPage;
		public TextView tvFavName;
		public TextView tvFavSub;
		public TextView tvFavItemCount;
		public ImageView ivFavStar;
		public LinearLayout llpagelistitem;

		public ViewHolder(View itemView) {
			super(itemView);
			tvFavPage = (TextView) itemView.findViewById(R.id.tvFavPage);
			tvFavSub = (TextView) itemView.findViewById(R.id.tvFavSub);
			tvFavName = (TextView) itemView.findViewById(R.id.tvFavName);
			tvFavItemCount = (TextView) itemView.findViewById(R.id.tvFavItemCount);
			ivFavStar = (ImageView) itemView.findViewById(R.id.ivFavStar);
			llpagelistitem = (LinearLayout) itemView.findViewById(R.id.llpagelistitem);
		}
    }
    public FavoritesAdapter(Context context, ArrayList<Page> list, NotebookAdapterToAct interf){
       this.favPageList=list;
		this.context=context;
		this.interf=interf;
    }

    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pagelistitem, parent, false));
	}


    @Override
    public void onBindViewHolder(FavoritesAdapter.ViewHolder holder, int position) {
		Page page = favPageList.get(position);
		final int newpos=position;
		holder.tvFavPage.setText(""+page.getPageNumber());
		holder.llpagelistitem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				interf.clickListener(newpos);
			}
		});
		holder.tvFavName.setText(""+page.getName());
		holder.tvFavSub.setText("Last Modified "+
				DateUtils.getRelativeTimeSpanString(page.getLastModifiedMillis(), Helpers.getCurrentTimeInMillis(),DateUtils.SECOND_IN_MILLIS));
		holder.tvFavItemCount.setText(""+page.getNumberOfItems());
		holder.ivFavStar.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return favPageList.size();
    }
}
