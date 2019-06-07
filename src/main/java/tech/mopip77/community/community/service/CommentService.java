package tech.mopip77.community.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.mopip77.community.community.enums.CommentTypeEnum;
import tech.mopip77.community.community.exception.CustomizeErrorCode;
import tech.mopip77.community.community.exception.CustomizeException;
import tech.mopip77.community.community.mapper.CommentMapper;
import tech.mopip77.community.community.mapper.QuestionExtMapper;
import tech.mopip77.community.community.mapper.QuestionMapper;
import tech.mopip77.community.community.model.Comment;
import tech.mopip77.community.community.model.Question;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    public void insertSelective(Comment comment) {

        // check parent id
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if (comment.getType() == null || ! CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            // 回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
        } else {
            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            question.setCommentCount(1);
            questionExtMapper.incComment(question);
        }

    }

}
