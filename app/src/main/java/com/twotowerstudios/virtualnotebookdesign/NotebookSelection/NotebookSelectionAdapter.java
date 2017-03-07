package com.twotowerstudios.virtualnotebookdesign.NotebookSelection;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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

import java.util.ArrayList;

/**
 * Created by Panther II on 22/10/2016.
 */

public class NotebookSelectionAdapter extends RecyclerView.Adapter<NotebookSelectionAdapter.ViewHolder> /*implements NewNotebookFragment.ToAdapterInterface*/{
	Context context;
	ArrayList<Notebook> notebookList;
	private Activity mActivity = null;
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
	public NotebookSelectionAdapter(Context context, ArrayList<Notebook> list, SelectionToNotebookInterface Interface, Activity mActivity){
		this.context = context;
		this.notebookList = list;
		this.Interface=Interface;
		this.mActivity = mActivity;
	}
	@Override
	public NotebookSelectionAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
	View v = LayoutInflater.from(parent.getContext())
	.inflate(R.layout.notebookselectioncard, parent, false);
	return new ViewHolder(v);
	}


	 @Override
	 public void onBindViewHolder(final ViewHolder holder, int position){
	 	 final Notebook notebookSelection = notebookList.get(position);
		 final int position2 = position;
	 	 holder.tvCardNameSel.setText(""+notebookSelection.getName());
	 	 holder.tvCardSub.setText("Last modified: "+
				 DateUtils.getRelativeTimeSpanString(notebookSelection.getLastModified(), Helpers.getCurrentTimeInMillis(), DateUtils.SECOND_IN_MILLIS));
	 	 holder.tvPageCount.setText("" +notebookSelection.getNumberOfPages() + " Pages");
	 	 holder.ivCardImage.setColorFilter(notebookSelection.getColor());
		 holder.card.setOnClickListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View view) {
				 Interface.openNotebookActivity(position2);
			 }
		 });
		 holder.card.setOnLongClickListener(new View.OnLongClickListener() {
			 @Override
			 public boolean onLongClick(View v) {
				 new AlertDialog.Builder(mActivity)
						 .setTitle("Do you want to delete this image?")
						 .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
							 @Override
							 public void onClick(DialogInterface dialog, int which) {
								 if (notebookList.get(holder.getAdapterPosition()).getNumberOfPages()<=0) {
									 notebookList.remove(holder.getAdapterPosition());
									 Helpers.writeListToFile(context, notebookList);
									 notifyItemRemoved(holder.getAdapterPosition());
								 } else {

								 }

							 }
						 })

						 .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							 @Override
							 public void onClick(DialogInterface dialog, int which) {

							 }
						 }).show();

				 return false;
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