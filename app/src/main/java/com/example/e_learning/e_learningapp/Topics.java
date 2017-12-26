package com.example.e_learning.e_learningapp;

/**
 * Created by admin0 on 29-Aug-17.
 */

public class Topics {
    int subject_id;
    int topic_id;
    String topic_name;

    public Topics(int topic_id, int subject_id, String topic_name) {
        this.topic_id = topic_id;
        this.subject_id = subject_id;
        this.topic_name = topic_name;
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

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }
}
