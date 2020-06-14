package com.example.esstelingapp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.esstelingapp.R;
import com.example.esstelingapp.Story;

public class StoryUnlockPopup extends AppCompatDialogFragment {
    private EditText editTextCode;
    private ExampleDialogListener listener;
    private Story story;
    private int position;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.story_unlock_popup, null);

        builder.setView(view).setTitle("Unlock").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String code = editTextCode.getText().toString();
                listener.applyCode(code, story, position);
            }
        });
        editTextCode = view.findViewById(R.id.popup_edit_text);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + " must implement ExampleDialogListener ");
        }
    }

    public interface ExampleDialogListener {
        void applyCode(String code, Story story, int position);
    }

    public void setStory(Story story, int position) {
        this.position = position;
        this.story = story;
    }
}
