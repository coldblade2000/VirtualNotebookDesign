package com.twotowerstudios.virtualnotebookdesign.NewNotebookDialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.twotowerstudios.virtualnotebookdesign.R;

import java.util.ArrayList;

/**
 * Created by Panther II on 10/11/2016.
 */

public class NewNotebookAdapter extends RecyclerView.Adapter<NewNotebookAdapter.ViewHolder>{
	Context context;
	ArrayList<String> colors = new ArrayList<>();
	int activeColor;
	AdapterInterface clickListener;
	public NewNotebookAdapter(){

	}

	public interface AdapterInterface{
		void clickListener(int color);
	}
	public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
		public ImageView ivSwatch;
		public Toolbar toolbar;
		public ViewHolder(View itemView) {
			super(itemView);
			ivSwatch = (ImageView) itemView.findViewById(R.id.ivSwatch);
			toolbar = (Toolbar) itemView.findViewById(R.id.newnotebooktoolbar);
		}


		@Override
		public void onClick(View view) {
			NewNotebookFragment newNotebookFragment = new NewNotebookFragment();
			final int adapterPosition = ViewHolder.this.getAdapterPosition();
			Log.d("OnClick. Adapter", "getAdapterPosition: "+adapterPosition);
			newNotebookFragment.changeColor(adapterPosition);

		}
	}
	public NewNotebookAdapter(Context context, ArrayList<String> colors, int activeColor, AdapterInterface clickListener){
		this.context=context;
		this.colors=colors;
		this.activeColor=activeColor;
		this.clickListener = clickListener;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.colorcircle,parent,false);
		return new ViewHolder(v);
	}


	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {

		Drawable drawablefull = ContextCompat.getDrawable(context,R.drawable.colorcircle);
		Drawable drawableHollow = ContextCompat.getDrawable(context,R.drawable.coloroutlinecircle);
		if (position==activeColor){
			holder.ivSwatch.setImageDrawable(drawablefull.mutate());
		}else if (position!=activeColor){
			holder.ivSwatch.setImageDrawable(drawableHollow.mutate());
		}
		holder.ivSwatch.setColorFilter(Color.parseColor(colors.get(position)));

		holder.ivSwatch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				clickListener.clickListener(position);
				notifyDataSetChanged();
				activeColor=position;
			}
		});
	}
	/**
	 * Returns the total number of items in the data set held by the adapter.
	 *
	 * @return The total number of items in this adapter.
	 */
	@Override
	public int getItemCount() {
		return colors.size();
	}
}
