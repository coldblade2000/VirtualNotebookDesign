package com.twotowerstudios.virtualnotebookdesign.NotebookMain.Fragments.Favorite;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twotowerstudios.virtualnotebookdesign.R;

/**
 * Created by coldblade2000 on 11/24/16.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder>{
    Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
    public FavoritesAdapter(Context context){
       this.context=context;
    }

    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.colorcircle,parent,false);
        return new FavoritesAdapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(FavoritesAdapter.ViewHolder holder, final int position) {

    }
    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return colors.size();
    }
}
