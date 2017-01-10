package com.twotowerstudios.virtualnotebookdesign.PageActivityMain;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.twotowerstudios.virtualnotebookdesign.R;

public class ModalBottomSheet extends BottomSheetDialogFragment {
	LinearLayout bottomGalleryButton, bottomCameraButton;
	OnModalBottomSheetListener mListener;

	public interface OnModalBottomSheetListener{
		void returnDecision(String tag);
	}
	public ModalBottomSheet() {
		// Required empty public constructor
	}

	BottomSheetDialogFragment newinstance(){return new BottomSheetDialogFragment();}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_modal_bottom_sheet, container, false);
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if(context instanceof OnModalBottomSheetListener){
			mListener = (OnModalBottomSheetListener) context;
		}else{
			throw new RuntimeException(context.toString()
					+ " must implement OnModalBottomSheetListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	@Override
	public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
		bottomCameraButton = (LinearLayout) v.findViewById(R.id.bottomCameraButton);
		bottomGalleryButton = (LinearLayout) v.findViewById(R.id.bottomGalleryButton);

		bottomCameraButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mListener.returnDecision("camera");
				dismiss();
			}
		});
		bottomGalleryButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mListener.returnDecision("gallery");
				dismiss();
			}
		});
	}
}
