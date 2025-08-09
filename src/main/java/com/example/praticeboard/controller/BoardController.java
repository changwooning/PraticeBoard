package com.example.praticeboard.controller;

import com.example.praticeboard.dto.BoardDto;
import com.example.praticeboard.service.BoardService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<BoardDto> boards = boardService.listBoards();
        model.addAttribute("boards", boards);
        return "board/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model, HttpSession session) {
        BoardDto board = boardService.getBoard(id);
        model.addAttribute("board", board);
        String username = (String) session.getAttribute("username");
        boolean editable = username != null && username.equals(board.getUsername());
        model.addAttribute("editable", editable);
        return "board/detail";
    }

    @GetMapping("/write")
    public String writeForm(Model model) {
        model.addAttribute("boardRequest", new BoardDto());
        return "board/write";
    }


    @PostMapping("/write")
    public String write(@Valid @ModelAttribute("boardRequest") BoardDto boardRequest,
                        BindingResult bindingResult,
                        HttpSession session,
                        Model model) {
        if (bindingResult.hasErrors()) {
            return "board/write";
        }
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/user/login";
        }
        boardService.createBoard(boardRequest, username);
        return "redirect:/board/list";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        BoardDto board = boardService.getBoard(id);
        if (!board.getUsername().equals(username)) {
            return "redirect:/board/list";
        }
        model.addAttribute("boardRequest", board);
        return "board/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id,
                       @Valid @ModelAttribute("boardRequest") BoardDto boardRequest,
                       BindingResult bindingResult,
                       HttpSession session,
                       Model model) {
        if (bindingResult.hasErrors()) {
            return "board/edit";
        }
        String username = (String) session.getAttribute("username");
        try {
            boardService.updateBoard(id, boardRequest, username);
            return "redirect:/board/" + id;
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "board/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session) {
        String username = (String) session.getAttribute("username");
        boardService.deleteBoard(id, username);
        return "redirect:/board/list";
    }


}
