package com.example.assignment3.view.adapter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.assignment3.R;
import com.example.assignment3.data.model.ChatMessage;
import com.google.firebase.auth.FirebaseAuth;
import java.util.List;

public class ChatListAdapter extends BaseAdapter {
    private List<ChatMessage> messages;
    private String currentUid = FirebaseAuth.getInstance().getUid();

    public ChatListAdapter(List<ChatMessage> messages) { this.messages = messages; }

    @Override
    public int getCount() { return messages.size(); }
    @Override
    public Object getItem(int i) { return messages.get(i); }
    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat, viewGroup, false);
        }

        ChatMessage msg = messages.get(i);
        TextView tv = view.findViewById(R.id.tv_message_text);
        LinearLayout container = view.findViewById(R.id.chat_container);

        tv.setText(msg.messageText);

        // REQUIREMENT: Clear distinction between sender and receiver
        if (msg.senderId.equals(currentUid)) {
            // My message: Right side, Blue bubble
            container.setGravity(Gravity.END);
            tv.setBackgroundResource(R.drawable.bg_sent_bubble); // Create a blue rounded drawable
            tv.setTextColor(0xFFFFFFFF);
        } else {
            // Their message: Left side, Gray bubble
            container.setGravity(Gravity.START);
            tv.setBackgroundResource(R.drawable.bg_received_bubble); // Create a gray rounded drawable
            tv.setTextColor(0xFF000000);
        }
        return view;
    }
}