package com.twotowerstudios.virtualnotebookdesign.PageActivityMain;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.twotowerstudios.virtualnotebookdesign.Objects.Page;
import com.twotowerstudios.virtualnotebookdesign.R;

import org.parceler.Parcels;


public class NewPageChildFragment extends DialogFragment {

    private OnFragmentInteractionListener mListener;

    char type;
    Page page;

    public NewPageChildFragment() {
        // Required empty public constructor
    }

    public static NewPageChildFragment newInstance(char type, Page page) {
        NewPageChildFragment fragment = new NewPageChildFragment();
        Bundle args = new Bundle();
        args.putChar("type", type);
        args.putParcelable("page", Parcels.wrap(page));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getChar("type");
            page = Parcels.unwrap(getArguments().getParcelable("page"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(type == 't'){
            return inflater.inflate(R.layout.fragment_new_page_child_text, container, false);
        }/*else if(type == 'i'){
            return inflater.inflate(R.layout.fragment_new_page_child_image, container, false);
        }else if(type == 'd'){
            return inflater.inflate(R.layout.fragment_new_page_child_drive, container, false);
        }*/else{return inflater.inflate(R.layout.fragment_new_page_child_text, container, false);}
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        if (type=='t') {
            Toolbar tbNewpagechild = (Toolbar) v.findViewById(R.id.tbNewpagechild);
            tbNewpagechild.inflateMenu(R.menu.newpage);
            tbNewpagechild.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_newpage:

                            break;
                    }return false;
                }
            });
        }else{

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
