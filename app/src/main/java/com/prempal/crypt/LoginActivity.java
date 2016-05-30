package com.prempal.crypt;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    TextView appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        appName = (TextView) findViewById(R.id.tv_app_name);
        Typeface face = Typeface.createFromAsset(getAssets(), "lobster-two.bold-italic.ttf");
        appName.setTypeface(face);
    }
}
