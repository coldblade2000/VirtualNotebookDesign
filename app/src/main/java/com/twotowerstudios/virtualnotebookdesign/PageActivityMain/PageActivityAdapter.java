package com.twotowerstudios.virtualnotebookdesign.PageActivityMain;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.twotowerstudios.virtualnotebookdesign.Objects.ChildBase;
import com.twotowerstudios.virtualnotebookdesign.R;

import java.io.File;
import java.util.ArrayList;


public class PageActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	Context context;
	ArrayList<ChildBase> list;
	PageAdapterToAct interf;

	private final int TEXT = 0, IMAGE = 1, DRIVE = 2;

	public interface PageAdapterToAct {
		void clickListener(String uid);
	}

	public class ViewHolderText extends RecyclerView.ViewHolder {
		private TextView tvChild;
		private TextView tvChildTextTitle;

		public ViewHolderText(View v) {
			super(v);
			tvChild = (TextView) v.findViewById(R.id.tvChild);
			tvChildTextTitle = (TextView) v.findViewById(R.id.tvChildTextTitle);
		}
	}

	public class ViewHolderImage extends RecyclerView.ViewHolder {
		private ImageView ivChildImage;
		private TextView tvChildImage;

		public ViewHolderImage(View v) {
			super(v);
			ivChildImage = (ImageView) v.findViewById(R.id.ivChildImage);
			tvChildImage = (TextView) v.findViewById(R.id.tvChildImage);
		}
	}

	public class ViewHolderDrive extends RecyclerView.ViewHolder {
		private ImageView ivChildDrive;
		private TextView tvChildDrive;

		public ViewHolderDrive(View v) {
			super(v);
			ivChildDrive = (ImageView) v.findViewById(R.id.ivChildDrive);
			tvChildDrive = (TextView) v.findViewById(R.id.tvChildDrive);
		}
	}

	/**
	 * @Override public int getItemViewType(int position) {
	 * if(((ChildBase)list.get(position)).getUID16().charAt(0)=='t'){
	 * return TEXT;
	 * }else if(((ChildBase)list.get(position)).getUID16().charAt(0)=='c'){
	 * return IMAGE;
	 * }else if(((ChildBase)list.get(position)).getUID16().charAt(0)=='d'){
	 * return DRIVE;
	 * }
	 * return -1;
	 * }
	 */
	@Override
	public int getItemViewType(int position) {
		if (list.get(position).getChildType() == 0) {
			return TEXT;
		} else if (list.get(position).getChildType() == 1) {
			return IMAGE;
		} else if (list.get(position).getChildType() == 2) {
			return DRIVE;
		}
		return -1;
	}

	public PageActivityAdapter(Context context, ArrayList<ChildBase> list, PageAdapterToAct interf) {
		this.context = context;
		this.list = list;
		this.interf = interf;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		RecyclerView.ViewHolder viewHolder;
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());

		switch (viewType) {
			case 0:
				View v1 = inflater.inflate(R.layout.pchild_text, parent, false);
				viewHolder = new ViewHolderText(v1);
				break;
			case 1:
				View v2 = inflater.inflate(R.layout.pchild_image, parent, false);
				viewHolder = new ViewHolderImage(v2);
				break;
			case 3:
				View v3 = inflater.inflate(R.layout.pchild_drive, parent, false);
				viewHolder = new ViewHolderImage(v3);
				break;
			default:
				View v4 = inflater.inflate(R.layout.pchild_text, parent, false);
				viewHolder = new ViewHolderImage(v4);
				break;
		}
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder Vholder, int position) {
		Log.d("PageActivityAdapter", "onBindViewHolder: start");
		switch (Vholder.getItemViewType()) {
			case TEXT:
				Log.d("PageActivityAdapter", "onBindViewHolder: TEXT Itemviewtype");
				ViewHolderText holder = (ViewHolderText) Vholder;
				ChildBase child = list.get(position);
				holder.tvChild.setText("" + child.getText());
				if (child.getTitle() == null||child.getTitle().equals("")) {        //if theres no title, make Title disappear
					holder.tvChildTextTitle.setVisibility(View.GONE);
				} else { //make title appear
					holder.tvChildTextTitle.setVisibility(View.VISIBLE);
					holder.tvChildTextTitle.setText(child.getTitle());
				}
				//configureViewHolderText(vhText, position);
				break;
			case IMAGE:
				ViewHolderImage holderImage = (ViewHolderImage) Vholder;
				ChildBase childImage = list.get(position);
				final String imageUID = childImage.getUID16();
				if (!childImage.getTitle().equals("")||childImage.getTitle()==null) {
					holderImage.tvChildImage.setText(""+childImage.getTitle());
				} else {
					holderImage.tvChildImage.setVisibility(View.GONE);
				}

				Glide.with(context)
						//.load(childImage.getUri())
						.load(childImage.getUri().toString())
						.centerCrop()
						.error(R.drawable.ic_broken_image_red_24dp)
						.placeholder(R.drawable.ic_image_black_24dp)
						.into(holderImage.ivChildImage);

				File newFile = new File(childImage.getUri().getPath());
				//File fileuri = new File(childImage.getUri().getPath());

				/**try {
				 holderImage.ivChildImage.setImageBitmap(MediaStore.Images.Media.getBitmap(context.getContentResolver(), childImage.getUri()));
				 } catch (IOException e) {
				 e.printStackTrace();
				 }*/
				Log.d("PageActivityAdapter", "onBindViewHolder: uri exists: " + childImage.getUri().toString());
				Log.d("PageActivityAdapter", "onBindViewHolder: uri path: " + childImage.getUri().getPath());
				holderImage.ivChildImage.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						interf.clickListener(imageUID);
					}
				});
				holderImage.ivChildImage.setOnLongClickListener(new View.OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						return false;
					}
				});
				break;
			case DRIVE:
				ViewHolderDrive vhDrive = (ViewHolderDrive) Vholder;
				configureViewHolderDrive(vhDrive, position);
				break;
		}
		Log.d("PageActivityAdapter", "Vholder.getitemviewtype = " + Vholder.getItemViewType());
	}

	/**
	 * private void configureViewHolderText(ViewHolderText holder, int position){
	 * ChildText child = (ChildText) list.get(position);
	 * holder.tvChild.setText(""+child.getText());
	 * if(child.getTitle()==null){		//if theres no title, make Title disappear
	 * holder.tvChildTextTitle.setVisibility(View.GONE);
	 * }else{ //make title appear
	 * holder.tvChildTextTitle.setVisibility(View.VISIBLE);
	 * holder.tvChildTextTitle.setText(child.getTitle());
	 * }
	 * }
	 */
	//private void configureViewHolderImage(ViewHolderImage holder, int position){
	private void configureViewHolderDrive(ViewHolderDrive holder, int position) {
		ChildBase child = list.get(position);
		holder.tvChildDrive.setText("" + child.getTitle());
		switch (child.getType()) {
			case 0:
				Glide.with(context)
						.load(R.drawable.drivedocs)
						.into(holder.ivChildDrive);
				break;
			case 1:
				Glide.with(context)
						.load(R.drawable.drivesheets)
						.into(holder.ivChildDrive);
				break;
			case 2:
				Glide.with(context)
						.load(R.drawable.driveslides)
						.into(holder.ivChildDrive);
				break;
			default:
				Glide.with(context)
						.load(R.drawable.drivedefault)
						.into(holder.ivChildDrive);
				break;
		}
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	public void refreshList(ChildBase newChild) {
		list.add(newChild);
		notifyDataSetChanged();
		Log.d("PageActivityAdapter", "refreshList: called. list.size() == " + list.size());

	}
}
