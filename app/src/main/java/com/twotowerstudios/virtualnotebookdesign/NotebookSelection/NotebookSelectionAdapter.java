package com.twotowerstudios.virtualnotebookdesign.NotebookSelection;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;
import com.twotowerstudios.virtualnotebookdesign.Objects.Notebook;
import com.twotowerstudios.virtualnotebookdesign.R;

import java.util.List;

/**
 * Created by Panther II on 22/10/2016.
 */

public class NotebookSelectionAdapter extends RecyclerView.Adapter<NotebookSelectionAdapter.ViewHolder> /*implements NewNotebookFragment.ToAdapterInterface*/{
	Context context;
	List<Notebook> notebookList;

	SelectionToNotebookInterface Interface;

	public interface SelectionToNotebookInterface{
		void openNotebookActivity(int position);
	}

	public static class ViewHolder extends RecyclerView.ViewHolder{

		public TextView tvCardNameSel, tvCardSub, tvPageCount;
		public ImageView ivCardImage;
		public CardView card;

		public ViewHolder(View view){
			super(view);
			ivCardImage = (ImageView) view.findViewById(R.id.tvCardImage);
			tvCardNameSel = (TextView) view.findViewById(R.id.tvCardNameSel);
			tvCardSub = (TextView) view.findViewById(R.id.tvCardSub);
			tvPageCount = (TextView) view.findViewById(R.id.tvPageCount);
			card = (CardView) view.findViewById(R.id.notebookSelectionCard);
		}
	}
	public NotebookSelectionAdapter(Context context, List<Notebook> list, SelectionToNotebookInterface Interface){
		this.context = context;
		this.notebookList = list;
		this.Interface=Interface;
	}
	@Override
	public NotebookSelectionAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
	View v = LayoutInflater.from(parent.getContext())
	.inflate(R.layout.notebookselectioncard, parent, false);
	return new ViewHolder(v);
	}


	 @Override
	 public void onBindViewHolder(ViewHolder holder,  int position){
	 	 Notebook notebookSelection = notebookList.get(position);
		 final int position2 = position;
	 	 holder.tvCardNameSel.setText(""+notebookSelection.getName());
	 	 holder.tvCardSub.setText("Last modified: "+
				 DateUtils.getRelativeTimeSpanString(notebookSelection.getLastModified(), Helpers.getCurrentTimeInMillis(), DateUtils.SECOND_IN_MILLIS));
	 	 holder.tvPageCount.setText("" +notebookSelection.debugNumOfPages + " Pages");
	 	 holder.ivCardImage.setColorFilter(notebookSelection.getColor());
		 holder.card.setOnClickListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View view) {
				 Interface.openNotebookActivity(position2);
			 }
		 });
	 }
	 @Override
	 public int getItemCount() {
	 	return notebookList.size();
	 }
	/**@Override
	public void refreshData() {
		notifyDataSetChanged();
	}*/
}