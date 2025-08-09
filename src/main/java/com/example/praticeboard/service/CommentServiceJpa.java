package com.example.praticeboard.service;

import com.example.praticeboard.dto.CommentCreateRequest;
import com.example.praticeboard.dto.CommentUpdateRequest;
import com.example.praticeboard.model.CommentEntity;
import com.example.praticeboard.repository.CommentJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceJpa {

    private final CommentJpaRepository repo;

    public CommentServiceJpa(CommentJpaRepository repo) {
        this.repo = repo;
    }

    public List<CommentEntity> list(Long boardId) {
        return repo.findByBoardIdOrderByIdDesc(boardId);
    }

    public Long create(CommentCreateRequest req) {
        CommentEntity e = new CommentEntity();
        e.setBoardId(req.getBoardId());
        e.setAuthor(req.getAuthor());
        e.setContent(req.getContent());
        return repo.save(e).getId();
    }

    public void update(Long id, CommentUpdateRequest req) {
        CommentEntity e = repo.findById(id).orElseThrow();
        e.setContent(req.getContent());
        e.setUpdatedAt(LocalDateTime.now());
        repo.save(e);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
