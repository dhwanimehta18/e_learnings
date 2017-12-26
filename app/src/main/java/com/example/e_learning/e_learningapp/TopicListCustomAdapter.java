package com.example.e_learning.e_learningapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by admin0 on 29-Aug-17.
 */

public class TopicListCustomAdapter extends BaseAdapter  {
    private final Context mcontext;
    private ArrayList<Topics> notice;

    public TopicListCustomAdapter(Context mcontext) {
        this.mcontext = mcontext;
        //this.notice = arrlist;
    }

    public void setNotice(ArrayList<Topics> notice) {
        this.notice = notice;
    }

    @Override
    public int getCount() {
        return notice.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.topic_list, null);
        Log.i("notice", String.valueOf(notice));

        TextView tvSubjectName = (TextView) linearLayout.findViewById(R.id.tvTopicName);
        tvSubjectName.setText(notice.get(position).getTopic_name());

        return linearLayout;
    }
}
