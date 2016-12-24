package com.twotowerstudios.virtualnotebookdesign.NewNotebookDialog;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;
import com.twotowerstudios.virtualnotebookdesign.NotebookSelection.NotebookSelection;
import com.twotowerstudios.virtualnotebookdesign.Objects.Notebook;
import com.twotowerstudios.virtualnotebookdesign.R;

import java.util.ArrayList;
import java.util.Random;


public class NewNotebookFragment extends DialogFragment implements NewNotebookAdapter.FromAdapterInterface {

	RecyclerView rvNewNotebook;
	int activeColorIndex, activeColor;
	Helpers help = new Helpers();
	ArrayList<Integer> colors;
	ArrayList<Notebook> notebookList;
	Toolbar toolbar;
	Switch swColors;
	EditText etNewName;
	/**ToAdapterInterface refresh;

	public interface ToAdapterInterface {
		void refreshData();
	}*/

		@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_new_notebook,container,false);
	}

	@Override
	public void onViewCreated(View v, Bundle savedInstanceState){

		colors= Helpers.getPossibleColors(getContext());
		activeColorIndex =new Random().nextInt(colors.size());
		activeColor = colors.get(activeColorIndex);
		swColors = (Switch) v.findViewById(R.id.switch1);

		etNewName = (EditText) v.findViewById(R.id.etNewName);
		notebookList = help.getNotebookList(getContext());
		toolbar = (Toolbar) v.findViewById(R.id.newnotebooktoolbar);
		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Log.d("Dialog", "onMenuItemClick: ");
				String nameLow=etNewName.getText().toString().toLowerCase();
				String nameReal=etNewName.getText().toString();
				if(nameLow.equalsIgnoreCase("")){
					Toast.makeText(getContext(),"Name can't be empty", Toast.LENGTH_SHORT).show();
					return false;
				}else{
					boolean notebookExists=false;
					try {
						for(int i=0;i<notebookList.size();i++){
							if (nameLow.equalsIgnoreCase(notebookList.get(i).getName())){
								Toast.makeText(getContext(),"Notebook already exists", Toast.LENGTH_SHORT).show();
								notebookExists=true;
								break;
							}
						}
					} catch (NullPointerException e) {
						Log.d("NewNotebookFrag","Notebooklist was empty, adding notebook");
					}
					if (!notebookExists){
						Toast.makeText(getContext(),"Created notebook called: \""+nameReal+"\"", Toast.LENGTH_SHORT).show();
						Log.d("NewNotebookFrag","Created notebook called:"+nameReal);
						((NotebookSelection)getActivity()).refreshData(new Notebook(nameReal,colors.get(activeColorIndex), Helpers.getSingleColorAccent(getContext(), activeColor)));
						dismiss();
						//refresh.refreshData();
					}

				}
				return false;
			}
		});
		toolbar.inflateMenu(R.menu.newnotebook);
		toolbar.setTitle("Create new Notebook");

		if(Helpers.isColorDark(activeColor)){
			toolbar.setTitleTextColor(getResources().getColor(R.color.md_dark_primary_text));
		}else{
			toolbar.setTitleTextColor(getResources().getColor(R.color.md_light_primary_text));
		}
		rvNewNotebook = (RecyclerView) v.findViewById(R.id.rvNewNotebook);
		final GridLayoutManager rvNotebookManager = new GridLayoutManager(getContext(),6);
		rvNewNotebook.setLayoutManager(rvNotebookManager);
		NewNotebookAdapter adapter= new NewNotebookAdapter(getContext(), Helpers.getPossibleColors(getContext()), activeColorIndex, this);
		rvNewNotebook.setVisibility(View.GONE);
		toolbar.setBackgroundColor(activeColor);
		rvNewNotebook.setAdapter(adapter);
		Log.d("onViewCreated", ""+rvNewNotebook.getWidth());

		swColors.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				if (b){
					rvNewNotebook.setVisibility(View.VISIBLE);
				}else{
					rvNewNotebook.setVisibility(View.GONE);
				}
			}
		});
	}
	@Override
	public void onResume(){
		super.onResume();
		/*DisplayMetrics displaymetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;
		getDialog().getWindow().setLayout((int)(width*0.9),(int)(height*0.8));*/
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(getDialog().getWindow().getAttributes());
		lp.width = (int)(width*0.9);
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

		getDialog().getWindow().setAttributes(lp);
	}
	public static NewNotebookFragment newInstance() {
			NewNotebookFragment f = new NewNotebookFragment();
			return f;
		}
	public void changeColor(int position){
		if(Helpers.isColorDark(colors.get(position))){
			toolbar.setTitleTextColor(getResources().getColor(R.color.md_dark_primary_text));
		}else{
			toolbar.setTitleTextColor(getResources().getColor(R.color.md_light_primary_text));
		}
		toolbar.setBackgroundColor(colors.get(position));
		this.activeColorIndex =position;
		this.activeColor= colors.get(activeColorIndex);
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
	}

	@Override
	public void clickListener(int color) {
		changeColor(color);
	}
}
