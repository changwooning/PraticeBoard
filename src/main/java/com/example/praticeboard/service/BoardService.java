package com.example.praticeboard.service;

import com.example.praticeboard.dto.BoardDto;
import jakarta.validation.Valid;

import java.util.List;

public interface BoardService {
    List<BoardDto> listBoards();

    BoardDto getBoard(Long id);

    void createBoard(BoardDto boardRequest, String username);

    void updateBoard(Long id,BoardDto boardRequest, String username);

    void deleteBoard(Long id, String username);
}
