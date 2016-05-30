package com.prempal.crypt;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by prempal on 30/5/16.
 */
public class BaseActivity extends AppCompatActivity {

    public static final String BASE_URL = "http://192.168.4.1/";

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
    }

    protected void showProgressDialog(String message) {
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    protected void dismissProgressDialog() {
        progressDialog.dismiss();
    }
}
