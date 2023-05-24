package com.erenDev.mikasa;

import static com.erenDev.mikasa.R.id.recylview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText msgEditView;
    ImageButton sendbtn, backbtn, infobtn;
    List<Message> messageList;
    MsgAdapter msgAdapter;


    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        messageList = new ArrayList<>();

        recyclerView = findViewById(R.id.recylview);
        msgEditView = findViewById(R.id.typing_space);
        sendbtn = findViewById(R.id.sendButton);
        backbtn = findViewById(R.id.backButton);
        infobtn = findViewById(R.id.infoButton);

        msgAdapter = new MsgAdapter(messageList);
        recyclerView.setAdapter(msgAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        sendbtn.setOnClickListener((v -> {
           String test = msgEditView.getText().toString().trim();
            Toast.makeText(ChatActivity.this, test,Toast.LENGTH_LONG).show();
        }));

    }


}