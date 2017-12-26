package com.example.e_learning.e_learningapp;

/**
 * Created by admin0 on 05-Nov-17.
 */

public class Videos {

    int video_id;
    int subject_id;
    int topic_id;
    String video_path;
    public Videos(int video_id, int subject_id, int topic_id, String video_path) {
        this.video_id = video_id;
        this.subject_id = subject_id;
        this.topic_id = topic_id;
        this.video_path = video_path;
    }

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public String getVideo_path() {
        return video_path;
    }

    public void setVideo_path(String video_path) {
        this.video_path = video_path;
    }
}
