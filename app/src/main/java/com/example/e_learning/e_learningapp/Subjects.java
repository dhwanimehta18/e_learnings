package com.example.e_learning.e_learningapp;

/**
 * Created by admin0 on 29-Aug-17.
 */

public class Subjects {
    int subject_id;
    String subject_name;

    public Subjects(int subject_id, String subject_name) {
        this.subject_id = subject_id;
        this.subject_name = subject_name;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }
}
