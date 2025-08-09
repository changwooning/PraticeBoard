package com.example.praticeboard.controller;

import com.example.praticeboard.dto.CommentCreateRequest;
import com.example.praticeboard.dto.CommentUpdateRequest;
import com.example.praticeboard.model.CommentEntity;
import com.example.praticeboard.service.CommentServiceJpa;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jpa/comments")
public class CommentJpaController {

    private final CommentServiceJpa service;

    public CommentJpaController(CommentServiceJpa service) {
        this.service = service;
    }

    @GetMapping("/board/{boardId}")
    public List<CommentEntity> list(@PathVariable Long boardId) {
        return service.list(boardId);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody CommentCreateRequest req) {
        return ResponseEntity.ok(service.create(req));
    }

    @PatchMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody CommentUpdateRequest req) {
        service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
