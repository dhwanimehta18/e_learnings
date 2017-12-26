package com.example.e_learning.e_learningapp;

/**
 * Created by admin0 on 31-Aug-17.
 */

public class Supplements {
    int supplement_id;
    int subject_id;
    int topic_id;
    String topic_content;

    public Supplements(int supplement_id, int subject_id, int topic_id, String topic_content) {
        this.supplement_id = supplement_id;
        this.subject_id = subject_id;
        this.topic_id = topic_id;
        this.topic_content = topic_content;
    }

    public int getSupplement_id() {
        return supplement_id;
    }

    public void setSupplement_id(int supplement_id) {
        this.supplement_id = supplement_id;
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

    public String getTopic_content() {
        return topic_content;
    }

    public void setTopic_content(String topic_content) {
        this.topic_content = topic_content;
    }
}
