package tech.mopip77.community.community.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tech.mopip77.community.community.dto.CommentCreateDTO;
import tech.mopip77.community.community.dto.CommentDTO;
import tech.mopip77.community.community.dto.ResultDTO;
import tech.mopip77.community.community.enums.CommentTypeEnum;
import tech.mopip77.community.community.exception.CustomizeErrorCode;
import tech.mopip77.community.community.model.Comment;
import tech.mopip77.community.community.model.User;
import tech.mopip77.community.community.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {

        User commentUser = (User) request.getSession().getAttribute("user");
        if (commentUser == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NOT_LOGIN_IN);
        }

        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setType(commentCreateDTO.getType());
        comment.setCommentator(commentUser.getId());
        commentService.insertSelective(comment);
        return ResultDTO.okOf();
    }

    @ResponseBody
    @GetMapping("/comment/{id}")
    public ResultDTO comments(@PathVariable(name = "id") Long id) {
        List<CommentDTO> commentDTOS = commentService.listByQuestionId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }
}
