package com.example.e_learning.e_learningapp;

import android.content.Intent;
import android.content.SharedPreferences;
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

import static android.content.Context.MODE_PRIVATE;


public class SuppList extends AppCompatActivity  {

    ListView lstSuppList;

    private String result;
    private int id;
    private ArrayList<Supplements> arrlist=new ArrayList<>();
    private int arraylength;
    private int topic_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_supp_list);

        lstSuppList = (ListView) findViewById(R.id.lstSuppList);

        Intent next = getIntent();
        id = next.getExtras().getInt("topic_id") ;
        Log.i("id",id+"");

        new BackgroundTask().execute();
        getTopiclist();
        //lstSuppList.setOnItemClickListener(SuppList.this);
    }

    private void getTopiclist() {
        SharedPreferences getData = getSharedPreferences("SuppList",MODE_PRIVATE);
        result = getData.getString("result","");

        try {
            JSONObject jsonobj = new JSONObject(result);
            JSONArray jsonarr = jsonobj.getJSONArray("result") ;
            for(int i = 0 ; i < jsonarr.length() ; i++) {
                JSONObject jsonObject = jsonarr.getJSONObject(i);
                int topic_id = Integer.parseInt(jsonObject.getString("topic_id"));
                int supplement_id = Integer.parseInt(jsonObject.getString("supplement_id"));
                int subject_id = Integer.parseInt(jsonObject.getString("subject_id"));
                //int sub_admin_id = Integer.parseInt(jsonObject.getString("sub_admin_id"));
                String topic_content = jsonObject.getString("topic_content");
                Log.i("subject_id",String.valueOf(subject_id));
                Log.i("topic_content",topic_content);
                arrlist.add(new Supplements(supplement_id,subject_id,topic_id,topic_content)) ;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SuppListCustomAdapter adapter = new SuppListCustomAdapter(SuppList.this);
        adapter.setNotice(arrlist);
        adapter.notifyDataSetChanged();
        lstSuppList.setAdapter(adapter);
    }


    class BackgroundTask extends AsyncTask<Void, Void, Void> {
        String jsonurl ;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            jsonurl = "http://192.168.43.145:8181/E-Learning/w_getSupplements.php?topic_id=".concat(String.valueOf(id)) ;
            //jsonurl = "http://192.168.1.110:8181/E-Learning/w_getSupplements.php?topic_id=".concat(String.valueOf(id)) ;

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
            SharedPreferences preferences = getSharedPreferences("SuppList",MODE_PRIVATE);
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
