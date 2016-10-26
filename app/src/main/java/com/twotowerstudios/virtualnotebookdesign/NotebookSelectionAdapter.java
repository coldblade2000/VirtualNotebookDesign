package com.twotowerstudios.virtualnotebookdesign;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Panther II on 22/10/2016.
 */

public class NotebookSelectionAdapter extends RecyclerView.Adapter<NotebookSelectionAdapter.ViewHolder>{
	Context context;
	List<NotebookSelectionCard> notebookSelectionList;
	public static class ViewHolder extends RecyclerView.ViewHolder{

		public TextView tvCardNameSel, tvCardSub;
		public ImageView tvCardImage;

		public ViewHolder(View view){
			super(view);
			tvCardImage = (ImageView) view.findViewById(R.id.tvCardImage);
			tvCardNameSel = (TextView) view.findViewById(R.id.tvCardNameSel);
			tvCardSub = (TextView) view.findViewById(R.id.tvCardSub);
		}
	}
	public NotebookSelectionAdapter(Context context, List<NotebookSelectionCard> list){
		this.context = context;
		this.notebookSelectionList = list;
	}
	@Override
	public NotebookSelectionAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
	View v = LayoutInflater.from(parent.getContext())
	.inflate(R.layout.notebookselectioncard, parent, false);
	return new ViewHolder(v);
	}


	 @Override
	 public void onBindViewHolder(ViewHolder holder, int position){
	 NotebookSelectionCard notebookSelection = notebookSelectionList.get(position);
	 holder.tvCardNameSel.setText(notebookSelection.getName());
	 holder.tvCardSub.setText(notebookSelection.getName());
	 //holder.ivBook.setColorFilter(Color.parseColor(bookLight.getBookColor()));
	 }
	 @Override
	 public int getItemCount() {
	 return notebookSelectionList.size();
	 }
}
