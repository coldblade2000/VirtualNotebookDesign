package com.twotowerstudios.virtualnotebookdesign.NotebookMain.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;
import com.twotowerstudios.virtualnotebookdesign.NotebookMain.NotebookAdapterToAct;
import com.twotowerstudios.virtualnotebookdesign.Objects.ChildBase;
import com.twotowerstudios.virtualnotebookdesign.Objects.Notebook;
import com.twotowerstudios.virtualnotebookdesign.Objects.Page;
import com.twotowerstudios.virtualnotebookdesign.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Panther II on 02/12/2016.
 */

public class NotebookPageAdapter extends RecyclerView.Adapter<NotebookPageAdapter.ViewHolder>{


	private Context context;
	private ArrayList<Page> pageList = new ArrayList<>();
	private NotebookAdapterToAct interf;
	private int color;
	private Page page;
	private boolean onlyFavorites;
	private String notebookUID;
	private String collectionUID;

	class ViewHolder extends RecyclerView.ViewHolder {
		TextView tvFavPage;
		TextView tvFavName;
		TextView tvFavSub;
		TextView tvFavItemCount;
		ImageView ivFavStar;
		LinearLayout llpagelistitem;

		ViewHolder(View itemView) {
			super(itemView);
			llpagelistitem = (LinearLayout) itemView.findViewById(R.id.llpagelistitem);
			tvFavPage = (TextView) itemView.findViewById(R.id.tvFavPage);
			tvFavSub = (TextView) itemView.findViewById(R.id.tvFavSub);
			tvFavName = (TextView) itemView.findViewById(R.id.tvFavName);
			tvFavItemCount = (TextView) itemView.findViewById(R.id.tvFavItemCount);
			ivFavStar = (ImageView) itemView.findViewById(R.id.ivFavStar);
		}
	}


	public NotebookPageAdapter(){}
	public NotebookPageAdapter(Context context, ArrayList<Page> list, NotebookAdapterToAct interf, int color,boolean onlyFavorites, String notebookUID, String collectionUID){
		this.onlyFavorites = onlyFavorites;
		this.context = context;
		this.interf=interf;
		this.color=color;
		this.collectionUID = collectionUID;
		pageList = list;
		this.notebookUID = notebookUID;
	}
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pagelistitem, parent, false));
	}
	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {
		final int newpos = position;
		page = pageList.get(position);
		holder.llpagelistitem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				interf.clickListener(newpos, onlyFavorites);
			}
		});
		holder.llpagelistitem.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				if(page.getNumberOfItems()==0){
					new AlertDialog.Builder(context)
							.setTitle("Do you want to delete this page?")
							.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									page.removeFromPage(holder.getAdapterPosition());
									Helpers.addPageFromUID16(page.getParentUID(), page, context);
									notifyItemRemoved(holder.getAdapterPosition());
								}
							})
							.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {

								}
							}).show();
				}else{
					final EditText edittext = new EditText(context);
					edittext.setRawInputType(InputType.TYPE_CLASS_NUMBER);
					AlertDialog.Builder dialog = new AlertDialog.Builder(context);
					dialog.setTitle("Do you want to delete this page?");
					final int randint= new Random().nextInt(8999)+1000;
					if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
						dialog.setMessage(Html.fromHtml("You will have to type out the random number shown below to confirm." + "<br>" + "<b>" + randint + "</b>", Html.FROM_HTML_MODE_LEGACY));
					}else {
						dialog.setMessage(Html.fromHtml("You will have to type out the random number shown below to confirm." + "<br>" + "<b>" + randint + "</b>"));
					}dialog.setView(edittext);
					dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							try {
								if(Integer.parseInt(edittext.getText().toString())==randint){
									for (ChildBase b: page.getContent()) {
										if(b.getChildType()==1){
											File fdelete = new File(b.getPath());
											if (fdelete.exists()) {
												if (fdelete.delete()) {
													Log.d("NotebookSelectionAdptr","file Deleted :" + context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)+"/"+notebookUID+"/"+b.getUri().getPath());
												} else {
													Log.d("NotebookSelectionAdptr", "file not Deleted :" + context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)+"/"+notebookUID+"/"+b.getUri().getPath());
												}
											}
										}
									}
									pageList.remove(holder.getAdapterPosition());
									Notebook notebook = Helpers.getNotebookFromUID(notebookUID, context);
									notebook.setPages(pageList);
									Helpers.addToNotebookList(notebook, context, collectionUID);
									notifyItemRemoved(holder.getAdapterPosition());
                                }else{
                                    Toast.makeText(context, "Page not deleted, the number you input was wrong.", Toast.LENGTH_SHORT).show();
                                }
							} catch (NumberFormatException e) {
								e.printStackTrace();
								dialog.dismiss();
							}
						}
					});
					dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					}).show();
				}
				return true;
			}
		});
		holder.tvFavSub.setTextColor(color);
		holder.ivFavStar.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
		if (!(page.getPageNumber()==100404)) {
			holder.tvFavPage.setText(""+page.getPageNumber());
		} else {
			holder.tvFavPage.setText("");
		}
		holder.tvFavName.setText(""+page.getName());
		//holder.tvFavSub.setText("Last Modified "+DateUtils.getRelativeTimeSpanString(page.getLastModifiedMillis(),Helpers.getCurrentTimeInMillis(),DateUtils.SECOND_IN_MILLIS));
		if((page.getDateMillis()!=0)) {
			holder.tvFavSub.setText("" + Helpers.millisDateToString(page.getDateMillis(), 2));
		}else{
			holder.tvFavSub.setVisibility(View.GONE);
		}
		holder.tvFavItemCount.setText(""+page.getNumberOfItems()+ "\nitems");
		if(onlyFavorites||page.isFavorite()){
			holder.ivFavStar.setVisibility(View.VISIBLE);
		}else{
			holder.ivFavStar.setVisibility(View.INVISIBLE);
		}
	}
	@Override
	public int getItemCount() {
		return pageList.size();
	}

}
