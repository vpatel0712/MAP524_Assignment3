package com.example.quiz_assignment;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class Utilities_Dialog {

    public static void showAlertDialog(Context context, String title, String message,String positiveButton,String negativeButton, final Runnable positiveCallback, final Runnable negativeCallback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle OK button click
                        if (positiveCallback != null) {
                            positiveCallback.run();
                        }
                    }
                })
                .setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle Cancel button click
                        if (negativeCallback != null) {
                            negativeCallback.run();
                        }
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}