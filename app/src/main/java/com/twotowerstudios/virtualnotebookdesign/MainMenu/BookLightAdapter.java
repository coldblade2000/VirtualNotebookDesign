package com.twotowerstudios.virtualnotebookdesign.MainMenu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twotowerstudios.virtualnotebookdesign.Objects.Notebook;
import com.twotowerstudios.virtualnotebookdesign.R;

import java.util.ArrayList;

/**
 * Created by Panther II on 10/10/2016.
 */

public class BookLightAdapter  extends RecyclerView.Adapter<BookLightAdapter.ViewHolder>{

	BookLightAdapter.MainMenuToNotebook Interface;

	public interface MainMenuToNotebook{
		void openNotebook(Notebook notebook);
	}

	protected Context context;
	private ArrayList<Notebook> bookList;
	public static class ViewHolder extends RecyclerView.ViewHolder {
		// each data item is just a string in this case
		public ImageView ivBook;
		public RelativeLayout rvBookLight;
		public TextView tvName;
		public ViewHolder(View view) {
			super(view);
			rvBookLight = (RelativeLayout) view.findViewById(R.id.rvBookLight);
			ivBook = (ImageView) view.findViewById(R.id.ivBookLight);
			tvName = (TextView) view.findViewById(R.id.tvName);
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
	public void onBindViewHolder(ViewHolder holder, final int position){
		holder.tvName.setText(bookList.get(position).getName());
		holder.ivBook.setColorFilter(bookList.get(position).getColor());
		holder.rvBookLight.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Interface.openNotebook(bookList.get(position));
			}
		});
	}

	@Override
	public int getItemCount() {
		return bookList.size();
	}
}