package com.twotowerstudios.virtualnotebookdesign.TransferNotebookDialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.twotowerstudios.virtualnotebookdesign.Objects.Collection;
import com.twotowerstudios.virtualnotebookdesign.R;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class TransferNotebookDialog extends Fragment implements AdapterView.OnItemClickListener{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
     ArrayList<Collection> collections;

    private OnFragmentInteractionListener mListener;
    public TransferNotebookDialog() {
    }

    Spinner transferSpinner;
    Button button, button2;
    Toolbar transfortoolbar;
    List<String> uids;

    // TODO: Rename and change types and number of parameters
    public static TransferNotebookDialog newInstance(ArrayList<Collection> list) {
        TransferNotebookDialog fragment = new TransferNotebookDialog();
        Bundle args = new Bundle();
        args.putParcelable("list", Parcels.wrap(list));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            collections = getArguments().getParcelable("list");
            uids = new ArrayList<String>();
            for (int i = 0; i < collections.size(); i++) {
                uids.add(collections.get(i).getUID8());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transfer_notebook, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        transferSpinner = (Spinner) v.findViewById(R.id.transferSpinner);
        button = (Button) v.findViewById(R.id.button);
        button2 = (Button) v.findViewById(R.id.button2);
        transfortoolbar = (Toolbar) v.findViewById(R.id.transfertoolbar);

        transferSpinner.setOnItemClickListener(this);
        ArrayAdapter<Collection> adapter = new ArrayAdapter<Collection>(getContext(), android.R.layout.simple_spinner_item, collections);
        transferSpinner.setAdapter(adapter);
        super.onViewCreated(v, savedInstanceState);
    }

    public void onSubmit(String UID16) {
        if (mListener != null) {
            mListener.onFragmentInteraction(UID16);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String uri);
    }
}
