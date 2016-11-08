package com.twotowerstudios.virtualnotebookdesign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class DeleteNotebookFragment extends DialogFragment implements TextView.OnEditorActionListener {
    EditText idToDelete;
    Button buttonDelete;
    public interface DeleteNotebookDialogListener {

        void onFinishEditDialog(String inputText);

    }
    public DeleteNotebookFragment() {

        // Empty constructor is required for DialogFragment

        // Make sure not to add arguments to the constructor

        // Use `newInstance` instead as shown below

    }



    public static DeleteNotebookFragment newInstance(String title) {

        DeleteNotebookFragment frag = new DeleteNotebookFragment();

        Bundle args = new Bundle();

        args.putString("title", title);

        frag.setArguments(args);

        return frag;

    }



    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_delete_notebook, container);

    }



    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        // Get field from view

        idToDelete = (EditText) view.findViewById(R.id.idToDelete);
        buttonDelete = (Button) view.findViewById(R.id.buttonDelete);
        // Fetch arguments from bundle and set title

        String title = getArguments().getString("title", "name");

        getDialog().setTitle(title);

        // Show soft keyboard automatically and request focus to field

        idToDelete.requestFocus();

        getDialog().getWindow().setSoftInputMode(

                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        idToDelete.setOnEditorActionListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {

            // Return input text back to activity through the implemented listener

            DeleteNotebookDialogListener listener = (DeleteNotebookDialogListener) getActivity();

            listener.onFinishEditDialog(idToDelete.getText().toString());
            // Close the dialog and return back to the parent activity

            dismiss();

            return true;

        }

        return false;
    }
}
