package com.example.assignment3.view;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.assignment3.R;
import com.example.assignment3.data.model.ChatMessage;
import com.example.assignment3.view.adapter.ChatListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private ListView listView;
    private ChatListAdapter adapter;
    private List<ChatMessage> messageList = new ArrayList<>();
    private String senderId, receiverId;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        senderId = FirebaseAuth.getInstance().getUid();
        receiverId = getIntent().getStringExtra("seller_id"); // Passed from Home
        dbRef = FirebaseDatabase.getInstance().getReference("Chats");

        listView = findViewById(R.id.list_view_chat);
        adapter = new ChatListAdapter(messageList);
        listView.setAdapter(adapter);

        // Send Message Logic
        findViewById(R.id.btn_send).setOnClickListener(v -> {
            EditText et = findViewById(R.id.et_message);
            String text = et.getText().toString().trim();
            if (!text.isEmpty()) {
                ChatMessage msg = new ChatMessage(senderId, receiverId, text, System.currentTimeMillis());
                dbRef.push().setValue(msg);
                et.setText("");
            }
        });

        // Real-time listener
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                messageList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ChatMessage msg = ds.getValue(ChatMessage.class);
                    // Only show messages shared between these two specific users
                    if ((msg.senderId.equals(senderId) && msg.receiverId.equals(receiverId)) ||
                            (msg.senderId.equals(receiverId) && msg.receiverId.equals(senderId))) {
                        messageList.add(msg);
                    }
                }
                adapter.notifyDataSetChanged();
                listView.setSelection(messageList.size() - 1); // Auto-scroll to bottom
            }
            @Override public void onCancelled(DatabaseError error) {}
        });
    }
}