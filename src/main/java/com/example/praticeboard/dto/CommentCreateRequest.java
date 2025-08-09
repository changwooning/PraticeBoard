package com.example.praticeboard.dto;

public class CommentCreateRequest {

    private Long boardId;
    private String author;
    private String content;

    public Long getBoardId() {
        return boardId;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
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
}
