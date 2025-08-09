package com.example.praticeboard.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Comment {

    private Long id;
    private Long boardId;
    private String author;
    private String content;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public Comment(){}

    public Comment(Long id, Long boardId, String author, String content, LocalDate createdAt, LocalDate updatedAt) {
        this.id = id;
        this.boardId = boardId;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getBoardId() {
        return boardId;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
}
