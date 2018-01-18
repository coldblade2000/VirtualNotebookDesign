package com.twotowerstudios.virtualnotebookdesign.TransferNotebookDialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twotowerstudios.virtualnotebookdesign.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TransferNotebookDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TransferNotebookDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransferNotebookDialog extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private OnFragmentInteractionListener mListener;
    public TransferNotebookDialog() {
    }


    // TODO: Rename and change types and number of parameters
    public static TransferNotebookDialog newInstance(String param1) {
        TransferNotebookDialog fragment = new TransferNotebookDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String uri);
    }
}
