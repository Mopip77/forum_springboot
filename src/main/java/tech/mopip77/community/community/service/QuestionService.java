package tech.mopip77.community.community.service;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.mopip77.community.community.dto.PaginationDTO;
import tech.mopip77.community.community.dto.QuestionDTO;
import tech.mopip77.community.community.exception.CustomizeErrorCode;
import tech.mopip77.community.community.exception.CustomizeException;
import tech.mopip77.community.community.mapper.QuestionExtMapper;
import tech.mopip77.community.community.mapper.QuestionMapper;
import tech.mopip77.community.community.mapper.UserMapper;
import tech.mopip77.community.community.model.Question;
import tech.mopip77.community.community.model.QuestionExample;
import tech.mopip77.community.community.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;


    public PaginationDTO list(Integer page, Integer size) {
        int totalCount = (int) questionMapper.countByExample(new QuestionExample());
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(totalCount, page, size);

        List<QuestionDTO> results = new ArrayList<>();
        // 用paginationDTO中的page, 防止传入的page越界
        Integer offset = size * (paginationDTO.getPage() - 1);
        QuestionExample example = new QuestionExample();
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));

        for (Question question : questions) {

            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            results.add(questionDTO);
        }

        paginationDTO.setQuestions(results);
        return paginationDTO;
    }


    public PaginationDTO list(Long userId, Integer page, Integer size) {
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        int totalCount = (int) questionMapper.countByExample(example);
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(totalCount, page, size);

        List<QuestionDTO> results = new ArrayList<>();
        // 用paginationDTO中的page, 防止传入的page越界
        Integer offset = size * (paginationDTO.getPage() - 1);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            results.add(questionDTO);
        }

        paginationDTO.setQuestions(results);
        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(userMapper.selectByPrimaryKey(question.getCreator()));
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            // create
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insertSelective(question);
        } else {
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setTitle(question.getTitle());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }
}
