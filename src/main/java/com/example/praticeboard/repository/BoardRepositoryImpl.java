package com.example.praticeboard.repository;

import com.example.praticeboard.model.Board;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepositoryImpl implements BoardRepository {

    private final List<Board> boards = new ArrayList<>();


    @Override
    public List<Board> findAll() {
        return boards;
    }

    @Override
    public Board save(Board board) {
        if (board.getId() == null) {
            board.setId(Board.sequence++);
        } else {
            delete(board);
        }
        boards.add(board);
        return board;
    }

    @Override
    public Optional<Board> findById(Long id) {
        return boards.stream().filter(b -> b.getId().equals(id)).findFirst();

    }

    @Override
    public void delete(Board board) {
        boards.removeIf(b -> b.getId().equals(board.getId()));
    }
}
