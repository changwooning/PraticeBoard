package com.example.praticeboard.controller;

import com.example.praticeboard.dto.CommentCreateRequest;
import com.example.praticeboard.dto.CommentUpdateRequest;
import com.example.praticeboard.model.Comment;
import com.example.praticeboard.service.CommentServiceJdbc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jdbc/comments")
public class CommentJdbcController {

    private final CommentServiceJdbc service;

    public CommentJdbcController(CommentServiceJdbc service) {
        this.service = service;
    }

    @GetMapping("/board/{boardId}")
    public List<Comment> list(@PathVariable Long boardId) {
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
