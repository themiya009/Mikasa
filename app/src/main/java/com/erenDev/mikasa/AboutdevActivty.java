package com.erenDev.mikasa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class AboutdevActivty extends AppCompatActivity {

    ImageView gitbtn;
    ImageButton backbtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutdev_activty);

        gitbtn = findViewById(R.id.gitbtn);
        backbtn = findViewById(R.id.bck);

        gitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserGit = new Intent(Intent.ACTION_VIEW,Uri.parse("https://github.com/themiya009"));
                startActivity(browserGit);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutdevActivty.this, MainActivity.class));
            }
        });
    }
}