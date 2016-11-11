package com.twotowerstudios.virtualnotebookdesign.NewNotebookDialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
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
	int widthOfParent;
	public NewNotebookAdapter(){

	}
	public static class ViewHolder extends RecyclerView.ViewHolder {
		public ImageView ivSwatch;
		public ViewHolder(View itemView) {
			super(itemView);
			ivSwatch = (ImageView) itemView.findViewById(R.id.ivSwatch);

		}
	}
	public NewNotebookAdapter(Context context, ArrayList<String> colors, int widthofParent){
		this.context=context;
		this.colors=colors;
		this.widthOfParent=widthofParent;
	}
	/**
	 * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
	 * an item.
	 * <p>
	 * This new ViewHolder should be constructed with a new View that can represent the items
	 * of the given type. You can either create a new View manually or inflate it from an XML
	 * layout file.
	 * <p>
	 * The new ViewHolder will be used to display items of the adapter using
	 * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
	 * different items in the data set, it is a good idea to cache references to sub views of
	 * the View to avoid unnecessary {@link View#findViewById(int)} calls.
	 *
	 * @param parent   The ViewGroup into which the new View will be added after it is bound to
	 *                 an adapter position.
	 * @param viewType The view type of the new View.
	 * @return A new ViewHolder that holds a View of the given view type.
	 * @see #getItemViewType(int)
	 * @see #onBindViewHolder(ViewHolder, int)
	 */
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.colorcircle,parent,false);
		widthOfParent = v.getLayoutParams().width;
		return new ViewHolder(v);
	}
	@Override
	public void onBindViewHolder(ViewHolder holder, int x) {
		holder.ivSwatch.setImageResource(R.drawable.colorcircle);
		Drawable drawable = ContextCompat.getDrawable(context,R.drawable.colorcircle).mutate();
		drawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor(colors.get(x)), PorterDuff.Mode.DST_IN));
		holder.ivSwatch.setBackground(drawable);

		holder.ivSwatch.getLayoutParams().width=widthOfParent/4;
		holder.ivSwatch.getLayoutParams().height=widthOfParent/4;
		Log.d("onBindViewHolder", ""+holder.ivSwatch.getLayoutParams().width);
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
