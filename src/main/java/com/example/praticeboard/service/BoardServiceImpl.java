package com.example.praticeboard.service;

import com.example.praticeboard.dto.BoardDto;
import com.example.praticeboard.model.Board;
import com.example.praticeboard.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }


    @Override
    public List<BoardDto> listBoards() {
        return boardRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BoardDto getBoard(Long id) {
        Board board = boardRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("해당 게시글이 없습니다."));
        board.setViewCount(board.getViewCount() + 1);
        boardRepository.save(board);
        return toDto(board);
    }

    @Override
    public void createBoard(BoardDto boardDto, String username) {
        Board board = new Board(boardDto.getTitle(), boardDto.getContent(), username, boardDto.getViewCount() + 1);
        boardRepository.save(board);
    }

    @Override
    public void updateBoard(Long id, BoardDto boardDto, String username) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 게시글이 없습니다."));
        if (!board.getUsername().equals(username)) {
            throw new RuntimeException("수정 권한 없음");
        }
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        boardRepository.save(board);
    }

    @Override
    public void deleteBoard(Long id, String username) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 게시글이 없습니다."));
        if (!board.getUsername().equals(username)) {
            throw new RuntimeException("삭제 권한 없음");
        }
        boardRepository.delete(board);
    }

    private BoardDto toDto(Board board) {
        BoardDto dto = new BoardDto();
        dto.setId(board.getId());
        dto.setTitle(board.getTitle());
        dto.setContent(board.getContent());
        dto.setUsername(board.getUsername());
        dto.setViewCount(board.getViewCount());
        return dto;
    }
}
