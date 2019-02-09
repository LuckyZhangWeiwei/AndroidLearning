package com.example.wwez.Imooc_Listview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wwez.myapplication.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {

    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        mListView = findViewById(R.id.listview);

        ArrayList<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage(1, 2, "刘晓明", "8:20", "你好吗", true));
        chatMessages.add(new ChatMessage(2, 1, "小军", "8:21", "我很好", false));
        chatMessages.add(new ChatMessage(1, 2, "刘晓明", "8:22", "今天天气怎么样", true));
        chatMessages.add(new ChatMessage(2, 1, "小军", "8:23", "热成狗啦", false));

        mListView.setAdapter(new ChatMessageAdapter(this, chatMessages));
    }

    public class ChatMessage {
        private int MID;
        private int mFriendID;
        private String mName;
        private String mDate;
        private String mContent;
        private boolean mIsComeMessage;

        public int getMID() {
            return MID;
        }

        public void setMID(int MID) {
            this.MID = MID;
        }

        public int getmFriendID() {
            return mFriendID;
        }

        public void setmFriendID(int mFriendID) {
            this.mFriendID = mFriendID;
        }

        public String getmName() {
            return mName;
        }

        public void setmName(String mName) {
            this.mName = mName;
        }

        public String getmDate() {
            return mDate;
        }

        public void setmDate(String mDate) {
            this.mDate = mDate;
        }

        public String getmContent() {
            return mContent;
        }

        public void setmContent(String mContent) {
            this.mContent = mContent;
        }

        public boolean ismIsComeMessage() {
            return mIsComeMessage;
        }

        public void setmIsComeMessage(boolean mIsComeMessage) {
            this.mIsComeMessage = mIsComeMessage;
        }

        public ChatMessage(int MID, int mFriendID, String mName, String mDate, String mContent, boolean mIsComeMessage) {
            this.MID = MID;
            this.mFriendID = mFriendID;
            this.mName = mName;
            this.mDate = mDate;
            this.mContent = mContent;
            this.mIsComeMessage = mIsComeMessage;
        }
    }

    public static class ChatMessageAdapter extends BaseAdapter {
        ArrayList<ChatMessage> chatMessages = new ArrayList<>();
        Context context;

        public ChatMessageAdapter( Context context, ArrayList<ChatMessage> chatMessages) {
            this.chatMessages = chatMessages;
            this.context = context;
        }

        interface IMessageViewType {
            int COM_MESSAGE = 0;
            int TO_MESSAGE = 1;
        }

        @Override
        public int getCount() {
            return chatMessages.size();
        }

        @Override
        public Object getItem(int position) {
            return chatMessages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ChatMessage chatMessage = chatMessages.get(position);
            boolean isComMsg = getItemViewType(position) == 0 ? true : false;
            ViewHolder viewHolder;
            if(convertView == null) {
                if(chatMessage.ismIsComeMessage()) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.chatting_item_msg_text_left, null);
                } else {
                    convertView = LayoutInflater.from(context).inflate(R.layout.chatting_item_msg_text_right, null);
                }
                viewHolder = new ViewHolder();
                viewHolder.mSendTime = convertView.findViewById(R.id.tv_send_time);
                viewHolder.mUserName = convertView.findViewById(R.id.tv_username);
                viewHolder.mContent = convertView.findViewById(R.id.tv_chat_content);
                viewHolder.mUserAvatar = convertView.findViewById(R.id.iv_user_head);
                viewHolder.mIsComMessage = isComMsg;
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.mSendTime.setText(chatMessage.getmDate());
            viewHolder.mContent.setText(chatMessage.getmContent());
            viewHolder.mContent.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
            viewHolder.mUserName.setText(chatMessage.getmName());

            if(isComMsg) {
                viewHolder.mUserAvatar.setImageResource(R.drawable.ic_launcher);
            } else {
                viewHolder.mUserAvatar.setImageResource(R.drawable.ic_launcher);
            }

            return convertView;
        }

        @Override
        public int getItemViewType(int position) {
            ChatMessage chatMessage = chatMessages.get(position);
            return chatMessage.ismIsComeMessage() ? IMessageViewType.COM_MESSAGE : IMessageViewType.TO_MESSAGE;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        class ViewHolder {
            TextView mSendTime;
            TextView mUserName;
            TextView mContent;
            ImageView mUserAvatar;
            boolean mIsComMessage;
        }
    }
}
