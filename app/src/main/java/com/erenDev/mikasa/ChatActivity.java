package com.erenDev.mikasa;

import static com.erenDev.mikasa.R.id.recylview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText msgEditView;
    ImageButton sendbtn, backbtn, infobtn;
    List<Message> messageList;
    MsgAdapter msgAdapter;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();


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
            String qst =msgEditView.getText().toString().trim();
            addtxtToChat(qst, Message.Sent_By_Me);
            msgEditView.setText("");
            callOpenAPI(qst);
        }));

    }

    void addtxtToChat(String message, String sentBy) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message, sentBy));
                msgAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(msgAdapter.getItemCount());
            }
        });

    }

    void addResponse(String response){
        addtxtToChat(response,Message.Sent_By_Bot);
    }

    void callOpenAPI(String qst) {
        JSONObject jsonbdy = new JSONObject();
        try {
            jsonbdy.put("model", "text-davinci-003");
            jsonbdy.put("prompt", qst);
            jsonbdy.put("max_tokens", 4000);
            jsonbdy.put("temperature", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(jsonbdy.toString(),JSON);
        Request req = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .header("Authorization", "Bearer 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")
                .post(body)
                .build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Response Failed Due to : " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String finalres = jsonArray.getJSONObject(0).getString("text");
                        addResponse(finalres.trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    addResponse("Failed to Response Due to " + response.body().string());
                }
            }
        });

    }


}