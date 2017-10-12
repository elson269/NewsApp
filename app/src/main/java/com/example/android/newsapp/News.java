package com.example.android.newsapp;

public class News {
    private String title;
    private String author;
    private String url;
    private String time;

    public News(String title, String author, String url, String time){
        this.title = title;
        this.author = author;
        this.url= url;
        this.time = time;
    }

    public String getTitle(){
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }

    public String getTime() {
        return time;
    }
}
