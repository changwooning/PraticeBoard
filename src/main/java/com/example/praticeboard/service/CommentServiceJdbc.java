package com.example.praticeboard.service;

import com.example.praticeboard.dto.CommentCreateRequest;
import com.example.praticeboard.dto.CommentUpdateRequest;
import com.example.praticeboard.model.Comment;
import com.example.praticeboard.repository.CommentJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceJdbc {

    private final CommentJdbcRepository repo;

    public CommentServiceJdbc(CommentJdbcRepository repo) {
        this.repo = repo;
    }

    public List<Comment> list(Long boardId) {
        return repo.findByBoardId(boardId);
    }

    public Long create(CommentCreateRequest req) {
        return repo.insert(req.getBoardId(), req.getAuthor(), req.getContent());
    }

    public void update(Long id, CommentUpdateRequest req) {
        repo.updateContent(id, req.getContent());
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
