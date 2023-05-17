package com.example.a81c;


import java.util.ArrayList;

public class UserModel {
    private String user_name;


    private ArrayList<String> playList;

    public UserModel(String user_name) {
        this.user_name = user_name;
    }

    public ArrayList<String> getPlayList() {
        return playList;
    }

    public void setPlayList(ArrayList<String> playList) {
        this.playList = playList;
    }

    public void addPlayList(String vedioLink){
        playList.add(vedioLink);
    }

}
