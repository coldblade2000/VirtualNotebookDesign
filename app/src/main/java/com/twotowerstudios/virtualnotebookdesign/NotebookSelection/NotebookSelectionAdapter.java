package com.twotowerstudios.virtualnotebookdesign.NotebookSelection;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;
import com.twotowerstudios.virtualnotebookdesign.Notebook;
import com.twotowerstudios.virtualnotebookdesign.R;

import java.util.List;

/**
 * Created by Panther II on 22/10/2016.
 */

public class NotebookSelectionAdapter extends RecyclerView.Adapter<NotebookSelectionAdapter.ViewHolder>{
	Context context;
	List<Notebook> notebookList;
	public static class ViewHolder extends RecyclerView.ViewHolder{

		public TextView tvCardNameSel, tvCardSub, tvPageCount;
		public ImageView ivCardImage;

		public ViewHolder(View view){
			super(view);
			ivCardImage = (ImageView) view.findViewById(R.id.tvCardImage);
			tvCardNameSel = (TextView) view.findViewById(R.id.tvCardNameSel);
			tvCardSub = (TextView) view.findViewById(R.id.tvCardSub);
			tvPageCount = (TextView) view.findViewById(R.id.tvPageCount);
		}
	}
	public NotebookSelectionAdapter(Context context, List<Notebook> list){
		this.context = context;
		this.notebookList = list;
	}
	@Override
	public NotebookSelectionAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
	View v = LayoutInflater.from(parent.getContext())
	.inflate(R.layout.notebookselectioncard, parent, false);
	return new ViewHolder(v);
	}


	 @Override
	 public void onBindViewHolder(ViewHolder holder, int position){
		 Helpers help = new Helpers();
	 	 Notebook notebookSelection = notebookList.get(position);
	 	 holder.tvCardNameSel.setText(""+notebookSelection.getName());
		 Log.d("notebookselectadapter","notebookselection.getLastModified() is: "+notebookSelection.getLastModified());
		 Log.d("notebookselectadapter","help.millisDateToString(notebookSelection.getLastModified()) "+help.millisDateToString(notebookSelection.getLastModified()));
	 	 holder.tvCardSub.setText("Last modified on: "+
				 help.millisDateToString(notebookSelection.getLastModified()));
	 	 holder.tvPageCount.setText("" +notebookSelection.getNumOfPages()+ " pages");
	 	 holder.ivCardImage.setColorFilter(Color.parseColor(notebookSelection.getColor()));
	 }
	 @Override
	 public int getItemCount() {
	 	return notebookList.size();
	 }
}