package com.example.praticeboard.model;

public class Board {

    public static long sequence = 1;
    private Long id;
    private String title;
    private String content;
    private String username;
    private int viewCount;

    public Board(){}

    public Board(String title, String content, String username, int viewCount) {
        this.id = sequence++;
        this.title = title;
        this.content = content;
        this.username = username;
        this.viewCount = 0;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}
