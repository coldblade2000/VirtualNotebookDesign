package com.twotowerstudios.virtualnotebookdesign.MainMenu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twotowerstudios.virtualnotebookdesign.Objects.Notebook;
import com.twotowerstudios.virtualnotebookdesign.R;

import java.util.ArrayList;

class BookLightAdapter  extends RecyclerView.Adapter<BookLightAdapter.ViewHolder>{

	private BookLightAdapter.MainMenuToNotebook Interface;

	interface MainMenuToNotebook{
		void openNotebook(Notebook notebook);
	}

	protected Context context;
	private ArrayList<Notebook> bookList = new ArrayList<>();
	static class ViewHolder extends RecyclerView.ViewHolder {
		// each data item is just a string in this case
		ImageView ivBook;
		RelativeLayout rvBookLight;
		TextView tvName;
		ViewHolder(View view) {
			super(view);
			rvBookLight = (RelativeLayout) view.findViewById(R.id.rvBookLight);
			ivBook = (ImageView) view.findViewById(R.id.ivBookLight);
			tvName = (TextView) view.findViewById(R.id.tvName);
		}
	}
	BookLightAdapter(Context context, ArrayList<Notebook> bookList, MainMenuToNotebook openNotebook){
		this.bookList = bookList;
		this.context = context;
		Interface=openNotebook;
	}
	@Override
	public BookLightAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
		View v = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.booklight, parent, false);
		return new ViewHolder(v);
	}
	@Override
	public void onBindViewHolder(ViewHolder holder, int position){
		Log.d("", "onBindViewHolder: position = "+position);
		final int position2 = position;
		holder.tvName.setText(bookList.get(position).getName());
		holder.ivBook.setColorFilter(bookList.get(position).getColor());
		holder.rvBookLight.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(bookList==null){
					Log.d("" ,"onClick: booklist == null");
				}
				Interface.openNotebook(bookList.get(position2));
			}
		});
	}

	@Override
	public int getItemCount() {
		return bookList.size();
	}
}
