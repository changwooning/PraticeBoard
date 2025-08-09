package com.example.praticeboard.dto;

import jakarta.validation.constraints.NotBlank;

public class BoardDto {

    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String username;

    private int viewCount;

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
