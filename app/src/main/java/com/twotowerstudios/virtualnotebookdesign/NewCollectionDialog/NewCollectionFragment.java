package com.twotowerstudios.virtualnotebookdesign.NewCollectionDialog;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;
import com.twotowerstudios.virtualnotebookdesign.NewNotebookDialog.ColorPickAdapter;
import com.twotowerstudios.virtualnotebookdesign.NotebookSelection.NotebookSelection;
import com.twotowerstudios.virtualnotebookdesign.Objects.Collection;
import com.twotowerstudios.virtualnotebookdesign.R;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewCollectionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewCollectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewCollectionFragment extends DialogFragment implements ColorPickAdapter.FromAdapterInterface{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView rvNewCollection;
    int activeColorIndex, activeColor;
    ArrayList<Integer> colors;
    ArrayList<Collection> collections;
    Toolbar toolbar;
    Switch swColors;
    EditText etNewCollection;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NewCollectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment NewCollectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewCollectionFragment newInstance(ArrayList<Collection> list) {
        NewCollectionFragment fragment = new NewCollectionFragment();
        Bundle args = new Bundle();
        args.putParcelable("list", Parcels.wrap(list));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            collections = Parcels.unwrap(getArguments().getParcelable("list"));
        }
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_collection, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void successfulSubmit(Collection collection) {
        if (mListener != null) {
            mListener.onFragmentInteraction(collection);
        }
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        rvNewCollection = (RecyclerView) v.findViewById(R.id.rvNewCollection);
        final GridLayoutManager rvNotebookManager = new GridLayoutManager(getContext(),6);
        rvNewCollection.setLayoutManager(rvNotebookManager);
        ColorPickAdapter adapter= new ColorPickAdapter(getContext(), Helpers.getPossibleColors(getContext()), activeColorIndex, this);
        rvNewCollection.setVisibility(View.GONE);
        colors = Helpers.getPossibleColors(getContext());
        activeColorIndex =new Random().nextInt(colors.size());
        activeColor = colors.get(activeColorIndex);
        swColors = (Switch) v.findViewById(R.id.switch1);

        etNewCollection = (EditText) v.findViewById(R.id.etNewCollection);
        toolbar = (Toolbar) v.findViewById(R.id.newcollectiontoolbar);
        toolbar.inflateMenu(R.menu.newnotebook);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String nameLow=etNewCollection.getText().toString().toLowerCase();
                String nameReal=etNewCollection.getText().toString();
                if(nameLow.trim().equalsIgnoreCase("")){
                    Toast.makeText(getContext(),"Name can't be empty", Toast.LENGTH_SHORT).show();
                    return false;
                }else{
                    boolean collectionExists=false;
                    try {
                        for(int i=0;i<collections.size();i++){
                            if (nameLow.equalsIgnoreCase(collections.get(i).getName())){
                                Toast.makeText(getContext(),"Notebook already exists", Toast.LENGTH_SHORT).show();
                                collectionExists=true;
                                break;
                            }
                        }
                    } catch (NullPointerException e) {
                        Log.d("NewNotebookFrag","Notebooklist was empty, adding notebook");
                    }
                    if (!collectionExists){
                        Toast.makeText(getContext(),"Created notebook called: \""+nameReal+"\"", Toast.LENGTH_SHORT).show();
                        Log.d("NewNotebookFrag","Created notebook called:"+nameReal);
                        Collection collection = new Collection(nameReal,new ArrayList<String>(), colors.get(activeColorIndex));
                        //((NotebookSelection)getActivity()).refreshData(new Notebook(nameReal,colors.get(activeColorIndex), Helpers.getSingleColorAccent(getContext(), activeColor)));
                        collection.setColor(colors.get(activeColorIndex));
                        successfulSubmit(collection);
                        dismiss(); //TODO finish making the collection
                        //refresh.refreshData();
                    }

                }
                return false;
            }
        });
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
    public void clickListener(int color) {
        changeColor(color);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Collection collection);
    }
}
