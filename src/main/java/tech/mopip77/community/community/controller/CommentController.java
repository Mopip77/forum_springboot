package tech.mopip77.community.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tech.mopip77.community.community.dto.CommentDTO;
import tech.mopip77.community.community.dto.ResultDTO;
import tech.mopip77.community.community.exception.CustomizeErrorCode;
import tech.mopip77.community.community.model.Comment;
import tech.mopip77.community.community.model.User;
import tech.mopip77.community.community.service.CommentService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request) {

        User commentUser = (User) request.getSession().getAttribute("user");
        if (commentUser == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NOT_LOGIN_IN);
        }

        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setType(commentDTO.getType());
        comment.setCommentator(commentUser.getId());
        commentService.insertSelective(comment);
        return ResultDTO.okOf();
    }
}
