package com.twotowerstudios.virtualnotebookdesign.TransferNotebookDialog;



import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;
import com.twotowerstudios.virtualnotebookdesign.Objects.Collection;
import com.twotowerstudios.virtualnotebookdesign.Objects.Notebook;
import com.twotowerstudios.virtualnotebookdesign.R;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class TransferNotebookDialog extends DialogFragment implements AdapterView.OnItemSelectedListener
{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
     ArrayList<Collection> collections;
     Notebook notebook;

    private OnFragmentInteractionListener mListener;
    public interface OnFragmentInteractionListener {
        void onDialogClosed(Collection collection);
    }
    public TransferNotebookDialog() {
    }

    Spinner transferSpinner;
    Collection selectedCollection;
    Button button, button2;
    String collectionUID;
    Toolbar transfortoolbar;
    List<String> uids;

    public static TransferNotebookDialog newInstance(ArrayList<Collection> list, Notebook notebook, String collectionUID) {
        TransferNotebookDialog fragment = new TransferNotebookDialog();
        Bundle args = new Bundle();
        args.putParcelable("list", Parcels.wrap(list));
        args.putParcelable("book", Parcels.wrap(notebook));
        args.putString("collectionUID", collectionUID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            collections = Parcels.unwrap(getArguments().getParcelable("list"));
            notebook = Parcels.unwrap(getArguments().getParcelable("book"));
            collectionUID = getArguments().getString("collectionUID");
            uids = new ArrayList<>();
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
        selectedCollection = collections.get(collections.size()-1);
        transferSpinner = (Spinner) v.findViewById(R.id.transferSpinner);
        button = (Button) v.findViewById(R.id.button);
        button2 = (Button) v.findViewById(R.id.button2);
        transfortoolbar = (Toolbar) v.findViewById(R.id.transfertoolbar);

        transferSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<Collection> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, collections);
        transferSpinner.setAdapter(adapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collection collection = ((Collection) transferSpinner.getSelectedItem());
                if(collection.getUID8().equals(collectionUID)){
                    Toast.makeText(getContext(), "This notebook is already in this collection", Toast.LENGTH_SHORT).show();
                }else {
                    if (mListener != null) {
                        breakout:
                        for (Collection a : collections) { //TODO Remove the for loop and replace with UID lookup
                            for (String b : a.getContentUIDs()) {
                                if (notebook.getUID16().equals(b)) {
                                    ArrayList<String> newContentUIDsFromOldCollection = a.getContentUIDs();
                                    newContentUIDsFromOldCollection.remove(notebook.getUID16());
                                    a.setContentUIDs(newContentUIDsFromOldCollection);
                                    Helpers.writeOneCollectionToFile(a, getActivity());

                                    //Add notebook to new collection
                                    collection.addUID(notebook.getUID16());
                                    Helpers.writeOneCollectionToFile(collection, getActivity());
                                    mListener.onDialogClosed(collection);
                                    break breakout;
                                }
                            }
                        }
                    }
                    dismiss();
                }
            }
        });
        super.onViewCreated(v, savedInstanceState);
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Collection item = (Collection) adapterView.getItemAtPosition(i);
        selectedCollection = item;
        Toast.makeText(getActivity(), "Selected "+ item.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
