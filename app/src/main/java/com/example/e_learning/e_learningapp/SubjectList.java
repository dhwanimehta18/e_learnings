package com.example.e_learning.e_learningapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
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

public class SubjectList extends AppCompatActivity implements AdapterView.OnItemClickListener {

    GridView lstSubjects;
    private String result;
    private int subject_id;
    private ArrayList<Subjects> arrlist=new ArrayList<>();
    private int arraylength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);

        lstSubjects = (GridView) findViewById(R.id.lstSubjects);
        new BackgroundTask().execute();
        getSubjectList() ;
        lstSubjects.setOnItemClickListener(SubjectList.this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(getApplicationContext(),""+arrlist.get(position).getSubject_id(),Toast.LENGTH_SHORT).show();
        subject_id = arrlist.get(position).getSubject_id();
        Intent next=new Intent(SubjectList.this,TopicList.class);
        next.putExtra("subject_id",subject_id);
        startActivity(next);

    }

    private void getSubjectList() {
        SharedPreferences getData = getSharedPreferences("SubjectList",MODE_PRIVATE);
        result = getData.getString("result","");

        try {
            JSONObject jsonobj = new JSONObject(result);
            JSONArray jsonarr = jsonobj.getJSONArray("result") ;
            for(int i = 0 ; i < jsonarr.length() ; i++) {
                JSONObject jsonObject = jsonarr.getJSONObject(i);
                int subject_id = Integer.parseInt(jsonObject.getString("subject_id")) ;
                String subject_name = jsonObject.getString("subject_name") ;
                arrlist.add(new Subjects(subject_id,subject_name)) ;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SubjectListCustomAdapter adapter = new SubjectListCustomAdapter(SubjectList.this);
        adapter.setNotice(arrlist);
        adapter.notifyDataSetChanged();
        lstSubjects.setAdapter(adapter);
    }

    class BackgroundTask extends AsyncTask<Void,Void,Void> {
        String jsonurl ;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            jsonurl = "http://192.168.43.145:8181/E-Learning/w_getSubjects.php" ;
            //jsonurl = "http://192.168.1.110:8181/E-Learning/w_getSubjects.php" ;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            SharedPreferences preferences = getSharedPreferences("SubjectList",MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            //Toast.makeText(SubjectList.this,"post"+result,Toast.LENGTH_SHORT).show();
            editor.putString("result", result);
            editor.putInt("arraylength", arraylength);
            editor.commit();
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
                    httpconnection.connect();
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
