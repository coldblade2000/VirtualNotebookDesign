package com.twotowerstudios.virtualnotebookdesign.NotebookSelection;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;
import com.twotowerstudios.virtualnotebookdesign.Objects.Notebook;
import com.twotowerstudios.virtualnotebookdesign.R;

import java.util.ArrayList;


/**
 * Created by Panther II on 22/10/2016.
 */

public class NotebookSelectionAdapter extends RecyclerView.Adapter<NotebookSelectionAdapter.ViewHolder> /*implements NewNotebookFragment.ToAdapterInterface*/ {
    Context context;
    ArrayList<Notebook> notebookList;
    private Activity mActivity = null;
    SelectionToNotebookSelectionInterface Interface;

    public interface SelectionToNotebookSelectionInterface {
        void openNotebookActivity(int position);

        void transferNotebook(int position);

        void deleteNotebook(int position, String UID16);

        void renameNotebook(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCardNameSel, tvCardSub, tvPageCount;
        public ImageView ivCardImage;
        public CardView card;

        public ViewHolder(View view) {
            super(view);
            ivCardImage = (ImageView) view.findViewById(R.id.tvCardImage);
            tvCardNameSel = (TextView) view.findViewById(R.id.tvCardNameSel);
            tvCardSub = (TextView) view.findViewById(R.id.tvCardSub);
            tvPageCount = (TextView) view.findViewById(R.id.tvPageCount);
            card = (CardView) view.findViewById(R.id.notebookSelectionCard);
        }
    }

    public NotebookSelectionAdapter(Context context, ArrayList<Notebook> list, SelectionToNotebookSelectionInterface Interface, Activity mActivity) {
        this.context = context;
        this.notebookList = list;
        this.Interface = Interface;
        this.mActivity = mActivity;
    }

    @Override
    public NotebookSelectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notebookselectioncard, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Notebook notebookSelection = notebookList.get(position);
        final int position2 = position;
        holder.tvCardNameSel.setText("" + notebookSelection.getName());
        holder.tvCardSub.setText("Last modified: " +
                DateUtils.getRelativeTimeSpanString(notebookSelection.getLastModified(), Helpers.getCurrentTimeInMillis(), DateUtils.SECOND_IN_MILLIS));
        holder.tvPageCount.setText("" + notebookSelection.getNumberOfPages() + " Pages");
        holder.ivCardImage.setColorFilter(notebookSelection.getColor());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Interface.openNotebookActivity(position2);
            }
        });
        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CharSequence colors[] = new CharSequence[]{"Rename Notebook", "Transfer Notebook", "Delete Notebook"};
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(v.getRootView().getContext());
                alertBuilder.setTitle("Select an action...");
                alertBuilder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface alertDialog, int which) {
                        switch (which) {
                            case 0:
                                Interface.renameNotebook(holder.getAdapterPosition());
                                break;
                            case 1:
                                Interface.transferNotebook(holder.getAdapterPosition());
                                break;
                            case 2:
                                if (notebookList.get(holder.getAdapterPosition()).getNumberOfPages() == 0) {
                                    //make this delete the pictures
                                    new AlertDialog.Builder(mActivity)
                                            .setTitle("Do you want to delete this notebook?")
                                            .setMessage("Every photo and item will be deleted")
                                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Interface.deleteNotebook(which, notebookList.get(holder.getAdapterPosition()).getUID16());
                                                    notebookList.remove(holder.getAdapterPosition());
                                                    notifyItemRemoved(holder.getAdapterPosition());
                                                }
                                            })
                                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            }).show();
                                } else {
                                    final EditText edittext = new EditText(context);
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
                                    dialog.setTitle("Do you want to delete this notebook?");
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                        dialog.setMessage(Html.fromHtml("You will have to type out the exact name of the notebook to confirm." + "<br>" + "<b>" + notebookSelection.getName() + "</b>", Html.FROM_HTML_MODE_LEGACY));
                                    } else {
                                        dialog.setMessage(Html.fromHtml("You will have to type out the exact name of the notebook to confirm. The name is case and space sensitive." + "<br>" + "<b>" + notebookSelection.getName() + "</b>"));
                                    }
                                    dialog.setView(edittext);
                                    dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (edittext.getText().toString().trim().equals(notebookSelection.getName().trim())) {
                                                Interface.deleteNotebook(which, notebookList.get(holder.getAdapterPosition()).getUID16());
                                                notebookList.remove(holder.getAdapterPosition());
                                                notifyItemRemoved(holder.getAdapterPosition());
                                            } else {
                                                Toast.makeText(context, "Notebook not deleted, the name you input was wrong.", Toast.LENGTH_SHORT).show();
                                            }


                                        }
                                    });

                                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();

                                }
                                break;
                        }
                    }
                });
                alertBuilder.show();

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return notebookList.size();
    }
    /**@Override public void refreshData() {
    notifyDataSetChanged();
    }*/
}