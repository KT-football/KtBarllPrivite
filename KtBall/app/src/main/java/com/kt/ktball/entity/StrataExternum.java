package com.kt.ktball.entity;

import java.util.ArrayList;

/**
 * Created by E7240A on 2016-04-12.
 */
public class StrataExternum {
    private String response;
    private int new_message;
    private ArrayList<Videos> videos=new ArrayList<>();

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getNew_message() {
        return new_message;
    }

    public void setNew_message(int new_message) {
        this.new_message = new_message;
    }

    public ArrayList<Videos> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<Videos> videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return "StrataExternum{" +
                "response='" + response + '\'' +
                ", new_message='" + new_message + '\'' +
                ", videos=" + videos +
                '}';
    }
}
