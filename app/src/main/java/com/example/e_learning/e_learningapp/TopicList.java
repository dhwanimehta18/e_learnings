package com.example.e_learning.e_learningapp;

import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.e_learning.e_learningapp.R;
import com.example.e_learning.e_learningapp.TopicListCustomAdapter;
import com.example.e_learning.e_learningapp.Topics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TopicList extends AppCompatActivity implements AdapterView.OnItemClickListener {

    GridView lstTopicList;
    private int id;
    private String result;
    private int arraylength;
    private int topic_id;

    private ArrayList<Topics> arrlist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_list);

        /*StrictMode.ThreadPolicy s=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(s);*/

        lstTopicList = (GridView) findViewById(R.id.lstTopicList);

        Intent next = getIntent();
        id = next.getExtras().getInt("subject_id") ;
        Log.i("id",id+"");

        new BackgroundTask().execute();
        //TopicListCustomAdapter topicListCustomAdapter = new TopicListCustomAdapter(TopicList.this,arrlist);
        //topicListCustomAdapter.setNotice(arrlist);
        ///lstTopicList.setAdapter(topicListCustomAdapter);
        getTopicList();
        lstTopicList.setOnItemClickListener(TopicList.this);
    }

    private void getTopicList() {
        SharedPreferences getData = getSharedPreferences("TopicList",MODE_PRIVATE);
        result = getData.getString("result","");
        try {
            JSONObject jsonObject = new JSONObject(result);
            Log.i("result get",result);
            JSONArray jsonArray = jsonObject.getJSONArray("result");

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                int topic_id = Integer.parseInt(jsonObject1.getString("topic_id"));
                int subject_id = Integer.parseInt(jsonObject1.getString("subject_id"));
                String topic_name = jsonObject1.optString("topic_name");
                arrlist.add(new Topics(topic_id,subject_id,topic_name));

                Log.i("topic_id get",topic_id+"");
                Log.i("subject_id get",subject_id+"");
                Log.i("topic_name get",topic_name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final TopicListCustomAdapter topicListCustomAdapter = new TopicListCustomAdapter(TopicList.this);
        topicListCustomAdapter.setNotice(arrlist);
        topicListCustomAdapter.notifyDataSetChanged();
        lstTopicList.setAdapter(topicListCustomAdapter);
        //arrlist.clear();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(getApplicationContext(),""+arrlist.get(position).getSubject_id(),Toast.LENGTH_SHORT).show();
        topic_id = arrlist.get(position).getSubject_id();
        Intent next=new Intent(TopicList.this,Buttons.class);
        next.putExtra("topic_id",topic_id);
        startActivity(next);
    }

    class BackgroundTask extends AsyncTask<Void, Void, Void>{

        String jsonurl ;
        String result;

        @Override
        protected void onPreExecute() {
            jsonurl = "http://192.168.43.145:8181/E-Learning/w_getTopics.php?subject_id=".concat(String.valueOf(id)) ;
            //jsonurl = "http://192.168.1.110:8181/E-Learning/w_getTopics.php?subject_id=".concat(String.valueOf(id)) ;
            Log.i("id",jsonurl);
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                URL url = new URL(jsonurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                result = ConvertStreamToString(inputStream);
                Log.i("result",result);

                /*SharedPreferences preferences = getSharedPreferences("TopicList",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("result", result);
                editor.putInt("arraylength", arraylength);
                editor.commit();*/

                JSONObject jobj = new JSONObject(result);
                JSONArray jarray = jobj.getJSONArray("result");
                arraylength = jarray.length();

                for (int i = 0; i < jarray.length(); i++){
                    JSONObject jsononj = jarray.getJSONObject(i);
                    int topic_id = Integer.parseInt(jsononj.getString("topic_id"));
                    int subject_id = Integer.parseInt(jsononj.getString("subject_id"));
                    String topic_name = jsononj.optString("topic_name");
                    arrlist.add(new Topics(topic_id,subject_id,topic_name));

                    Log.i("topic_id",topic_id+"");
                    Log.i("subject_id",subject_id+"");
                    Log.i("topic_name",topic_name);
                }
                httpURLConnection.connect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            SharedPreferences preferences = getSharedPreferences("TopicList",MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("result", result);
            editor.putInt("arraylength", arraylength);
            editor.commit();
        }
    }

    private String ConvertStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String buff = "" ;
        String line ;

        try {
            while ((line = bufferedReader.readLine()) != null){
                buff = line + buff ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buff ;
    }
}