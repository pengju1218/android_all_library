package com.loveplusplus.update;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MyDialog {

    private AlertDialog alertDialog = null;
    private Button Ok, Cancel;
    private TextView title, content;
    private Context context;
    private String url;

    public MyDialog(Context context) {
        this.context = context;
    }

    public MyDialog(Context context, int theme) {
        this.context = context;
        alertDialog = new AlertDialog.Builder(context,theme).create();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog);
        content = (TextView) window.findViewById(R.id.content);
        title = (TextView) window.findViewById(R.id.title);
        title.setText("提示");

        Ok = (Button) window.findViewById(R.id.ok);
        Cancel = (Button) window.findViewById(R.id.cancel);
        Ok.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                goToDownload();
                alertDialog.dismiss();
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    public void setContent(String contents) {

        if (contents != null && content != null) {
            content.setText(contents);
        }
    }


    public void setUrl(String url) {
        this.url = url;
    }

    private void goToDownload() {
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra(Constants.APK_DOWNLOAD_URL, url);
        context.startService(intent);
    }

    public void show() {
        alertDialog.show();
    }

}