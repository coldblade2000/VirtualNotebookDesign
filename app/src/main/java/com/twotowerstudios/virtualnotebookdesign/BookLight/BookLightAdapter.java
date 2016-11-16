package com.twotowerstudios.virtualnotebookdesign.BookLight;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twotowerstudios.virtualnotebookdesign.Notebook;
import com.twotowerstudios.virtualnotebookdesign.R;

import java.util.ArrayList;

/**
 * Created by Panther II on 10/10/2016.
 */

public class BookLightAdapter  extends RecyclerView.Adapter<BookLightAdapter.ViewHolder>{

	protected Context context;
	private ArrayList<Notebook> bookList;
	public static class ViewHolder extends RecyclerView.ViewHolder {
		// each data item is just a string in this case
		public ImageView ivBook;
		public TextView tvName;
		/**public TextView cardHeader, cardSubtitle;
		public ImageView imageView;
		public Vie w cardHeaderBarColored;*/
		public ViewHolder(View view) {
			super(view);
			ivBook = (ImageView) view.findViewById(R.id.ivBookLight);
			tvName = (TextView) view.findViewById(R.id.tvName);
			/**cardHeader = (TextView) view.findViewById(R.id.cardHeader);
			cardSubtitle = (TextView) view.findViewById(R.id.cardSubtitle);
			imageView = (ImageView) view.findViewById(R.id.imageView);
			cardHeaderBarColored = (View) view.findViewById(R.id.cardHeaderBarColored);*/

		}
	}
	public BookLightAdapter (Context context, ArrayList<Notebook> bookList){
		this.bookList = bookList;
		this.context = context;
	}
	@Override
	public BookLightAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
		View v = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.booklight, parent, false);
		return new ViewHolder(v);
	}
	@Override
	public void onBindViewHolder(ViewHolder holder, int position){
		holder.tvName.setText(bookList.get(position).getName());
		holder.ivBook.setColorFilter(Color.parseColor(bookList.get(position).getColor()));
	}

	@Override
	public int getItemCount() {
		return bookList.size();
	}
}
