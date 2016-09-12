package com.example.d10_alertdialog;

import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(android.R.id.content).setOnClickListener(this::popupAlertDialog);
    }

    private void popupAlertDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert Dialog Test")
                .setView(R.layout.adaptor)
                .setMessage("Cancel /OK를 눌러주세요")
                .setPositiveButton("OK",((dialog, which) -> showSnackbar(v, which)))
                .setNegativeButton("Cancel",((dialog, which) -> showSnackbar(v, which)))
                .show();
    }

    private void showSnackbar(View v, int which){
        String msg = (which == DialogInterface.BUTTON_POSITIVE ? "OK 를 선택 하셨습니다." : "Cancel 을 선택 하였습니다.");
        Snackbar.make(v, msg, Snackbar.LENGTH_LONG).show();
    }
}
