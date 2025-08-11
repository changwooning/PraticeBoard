package com.example.praticeboard.controller;

import com.example.praticeboard.dto.BoardDto;
import com.example.praticeboard.model.Comment;
import com.example.praticeboard.service.BoardService;
import com.example.praticeboard.service.CommentServiceJdbc;
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
    private final CommentServiceJdbc commentServiceJdbc;

    public BoardController(BoardService boardService, CommentServiceJdbc commentServiceJdbc) {
        this.boardService = boardService;
        this.commentServiceJdbc = commentServiceJdbc;
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<BoardDto> boards = boardService.listBoards();
        model.addAttribute("boards", boards);
        return "board/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model, HttpSession session) {
        BoardDto post = boardService.getBoard(id);
        model.addAttribute("post", post);

        List<Comment> comments = commentServiceJdbc.list(id);
        model.addAttribute("comments", comments);

        String username = (String) session.getAttribute("username");
        boolean editable = username != null && username.equals(post.getUsername());
        model.addAttribute("editable", editable);

        model.addAttribute("commentRequest", new com.example.praticeboard.dto.CommentCreateRequest());
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

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session) {
        String username = (String) session.getAttribute("username");
        boardService.deleteBoard(id, username);
        return "redirect:/board/list";
    }


}
