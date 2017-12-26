package com.example.e_learning.e_learningapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by admin0 on 31-Aug-17.
 */

public class SuppListCustomAdapter extends BaseAdapter {
    private final Context mcontext;
    private ArrayList<Supplements> notice;

    public SuppListCustomAdapter(Context mcontext) {
        this.mcontext = mcontext;
    }

    public void setNotice(ArrayList<Supplements> notice) {
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
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.topic_content_list, null);
        TextView tvTopicContent = (TextView) linearLayout.findViewById(R.id.tvTopicContent);
        tvTopicContent.setText(notice.get(position).getTopic_content());

        //tvTopicContent.setText(notice.get(position).getTopic_id());

        return linearLayout;
    }
}
