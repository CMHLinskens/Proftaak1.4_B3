package com.example.esstelingapp.games;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class RiddleSubmitPopup extends DialogFragment {
    private AlertDialog.Builder builder;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        builder = new AlertDialog.Builder(getActivity());
        return builder.create();
    }


    public void popupType(Boolean correct, int timesTried) {
        if (correct) {
            builder.setMessage("Correct").setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // FIRE ZE MISSILES!
                }
            });
        }
        else if (timesTried < 3){
            builder.setMessage("Wrong \nTry Again").setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // NOT FIRE ZE MISSILES!
                }
            });
        }
        else {
            builder.setMessage("Wrong \nBetter luck next time").setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // NOT FIRE ZE MISSILES!
                }
            });
        }
    }
}
