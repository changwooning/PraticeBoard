package com.example.praticeboard.controller;

import com.example.praticeboard.dto.CommentCreateRequest;
import com.example.praticeboard.dto.CommentUpdateRequest;
import com.example.praticeboard.service.CommentServiceJdbc;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
public class CommentWebController {

    private final CommentServiceJdbc commentServiceJdbc;

    public CommentWebController(CommentServiceJdbc commentServiceJdbc) {
        this.commentServiceJdbc = commentServiceJdbc;
    }

    // 새 댓글 등록 :
    @PostMapping("/board/{boardId}")
    public String create(@PathVariable Long boardId, @ModelAttribute CommentCreateRequest req,
                         HttpSession session) {
        String username = (String) session.getAttribute("username");
        req.setBoardId(boardId);
        req.setAuthor(username == null ? "anonymous" : username);
        commentServiceJdbc.create(req);
        return "redirect:/board/" + boardId;
    }
    // 댓글 수정
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @RequestParam Long boardId,
                         @RequestParam String content) {
        CommentUpdateRequest req = new CommentUpdateRequest();
        req.setContent(content);
        commentServiceJdbc.update(id, req);
        return "redirect:/board/" + boardId;
    }

    // 댓글 삭제
    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable Long id, @RequestBody Long boardId){
        commentServiceJdbc.delete(id);
        return "redirect:/board" + boardId;
    }


}
