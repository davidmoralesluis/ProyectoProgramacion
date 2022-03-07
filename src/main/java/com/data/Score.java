package com.data;


public class Score {

    private String userCode;

    private int score;

    private String date;


    public Score(User u, int score) {
        this.userCode = u.getCode();
        this.score = score;
    }


    public Score(String userCode, int score, String date) {
        this.userCode = userCode;
        this.score = score;
        this.date = date;
    }


    public String getUserCode() {
        return userCode;
    }

    public int getScore() {
        return score;
    }

}
