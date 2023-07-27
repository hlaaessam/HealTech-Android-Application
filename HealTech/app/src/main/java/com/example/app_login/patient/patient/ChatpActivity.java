package com.example.app_login.patient.patient;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_login.R;
import com.example.app_login.doctor.doctor.ChatGpt.Message;
import com.example.app_login.doctor.doctor.ChatGpt.MessageAdapter;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatpActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView welcomeTextView;
    EditText messageEditText;
    ImageView sendButton;
    List<Message> messageList;
    MessageAdapter messageAdapter;
    ArrayList<String> arrayList = new ArrayList<>();
    String FullText = "";


    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatp);


        getSupportActionBar().setTitle("Chat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Teal5)));

        messageList = new ArrayList<>();
        recyclerView = findViewById(R.id.chat_recycle_view);
        welcomeTextView = findViewById(R.id.welcome_text);
        messageEditText = findViewById(R.id.message_edit);
        sendButton = findViewById(R.id.send_btn);


        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        sendButton.setOnClickListener((v)->
        {
            String question = messageEditText.getText().toString();
            addToChat(question,Message.SENT_BY_ME);
            messageEditText.setText("");
            try {

                callAPI(question);
            } catch (ProtocolException e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
            welcomeTextView.setVisibility(View.GONE);
        });

    }
    void addToChat(String message,String sentBy)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                messageList.add(new Message(message,sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    void addResponse(String response)
    {
        messageList.remove(messageList.size()-1);
        addToChat(response,Message.SENT_BY_BOT);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
//api

    void callAPI(String question) throws ProtocolException {
        //okhttp
        messageList.add(new Message("Typing... ",Message.SENT_BY_BOT));
        JSONObject jsonBody = new JSONObject();
        try
        {
            jsonBody.put("model","text-davinci-003");
            jsonBody.put("prompt",question);
            jsonBody.put("max_tokens",4000);
            jsonBody.put("temperature",0);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
     /*   URL url = null;
        try {
            url = new URL("https://api-inference.huggingface.co/models/Amira2045/BioGPT-Finetuned");
        } catch (MalformedURLException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","application/json");
        connection.setRequestProperty("Authorization", "Bearer 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");
        JSONObject payload = new JSONObject();
*/

        RequestBody body = RequestBody.create(jsonBody.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api-inference.huggingface.co/models/Amira2045/BioGPT-Finetuned")
                .header( "Authorization", "Bearer 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")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {
                addResponse("Failed to load response due to "+e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    for(int i=0; i<response.body().contentLength();i++){
                        try {
                            String responseBody = response.body().string(); // Convert response body to string
                            arrayList.set(i, responseBody);

                            for (int x = 0; x < arrayList.size(); x++) {

                                FullText = FullText + " " +arrayList.get(x);
                            }
                            JSONObject jsonObject = new JSONObject(responseBody);
                            JSONArray jsonArray = jsonObject.getJSONArray("choices");
                            String result = jsonArray.getJSONObject(0).getString("text");
                            addResponse(result.trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }}
                } else {
                    addResponse("Failed to load response due to " + response.body().string());
                }
            }
        });
    }
}