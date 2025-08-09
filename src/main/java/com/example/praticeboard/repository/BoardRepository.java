package com.example.praticeboard.repository;

import com.example.praticeboard.model.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    List<Board> findAll();

    Board save(Board board);

    Optional<Board> findById(Long id);

    void delete(Board board);
}
