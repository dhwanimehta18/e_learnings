package com.example.e_learning.e_learningapp;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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


public class VideoList extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lstVideoList;

    private String result;
    private int id;
    private ArrayList<Videos> arrlist=new ArrayList<>();
    private int arraylength;
    private int topic_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_video_list);

        lstVideoList = (ListView) findViewById(R.id.lstVideoList);

        Intent next = getIntent();
        id = next.getExtras().getInt("topic_id") ;
        Log.i("id",id+"");

        new BackgroundTask().execute();
        getVideoList();
        lstVideoList.setOnItemClickListener(VideoList.this);
        //lstSuppList.setOnItemClickListener(SuppList.this);
    }

    private void getVideoList() {
        SharedPreferences getData = getSharedPreferences("VideoList",MODE_PRIVATE);
        result = getData.getString("result","");

        try {
            JSONObject jsonobj = new JSONObject(result);
            JSONArray jsonarr = jsonobj.getJSONArray("result") ;
            for(int i = 0 ; i < jsonarr.length() ; i++) {
                JSONObject jsonObject = jsonarr.getJSONObject(i);
                int topic_id = Integer.parseInt(jsonObject.getString("topic_id"));
                int video_id = Integer.parseInt(jsonObject.getString("video_id"));
                int subject_id = Integer.parseInt(jsonObject.getString("subject_id"));
                String video_path = jsonObject.getString("video_path");
                Log.i("subject_id",String.valueOf(subject_id));
                Log.i("video_path",video_path);
                arrlist.add(new Videos(video_id,subject_id,topic_id,video_path)) ;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        VideoListCustomAdapter adapter = new VideoListCustomAdapter(VideoList.this);
        adapter.setNotice(arrlist);
        //adapter.notifyDataSetChanged();
        lstVideoList.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String videoFile = arrlist.get(position).getVideo_path();
        Toast.makeText(VideoList.this,videoFile,Toast.LENGTH_SHORT).show();

        try {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setDataAndType(Uri.parse("http://192.168.43.145:8181/E-Learning/" + videoFile), "video/*");
            //i.setDataAndType(Uri.parse("http://192.168.1.110:8181/E-Learning/" + videoFile), "video/*");


            startActivity(i);
        }catch (ActivityNotFoundException e){
            e.printStackTrace();
        }
    }


    class BackgroundTask extends AsyncTask<Void, Void, Void> {
        String jsonurl ;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //jsonurl = LOCAL_URL+"Test/PHP/Assignment/showAssignments.php?id=".concat(email);
            jsonurl = "http://192.168.43.145:8181/E-Learning/w_getVideos.php?topic_id=".concat(String.valueOf(id)) ;
            //jsonurl = "http://192.168.1.110:8181/E-Learning/w_getVideos.php?topic_id=".concat(String.valueOf(id)) ;
            Log.i("url",jsonurl);
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                URL url = new URL(jsonurl);
                HttpURLConnection httpconnection = (HttpURLConnection) url.openConnection();
                InputStream inputstream = httpconnection.getInputStream();
                result = ConvertStreamToString(inputstream);
                Log.i("result",result);
                JSONObject jobj = new JSONObject(result);
                JSONArray jsonarray = jobj.getJSONArray("result");
                arraylength = jsonarray.length();
                for(int i = 0; i < jsonarray.length(); i++){
                    JSONObject jobject = jsonarray.getJSONObject(i);
                }
                httpconnection.connect();
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
            SharedPreferences preferences = getSharedPreferences("VideoList",MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("result", result);
            editor.putInt("arraylength", arraylength);
            editor.commit();
        }
    }
    private String ConvertStreamToString(InputStream inputstream) {
        BufferedReader bufferreader = new BufferedReader(new InputStreamReader(inputstream));
        String buff = "";
        String line;
        try {
            while ((line = bufferreader.readLine()) != null) {
                buff = line + buff;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buff;
    }

}
