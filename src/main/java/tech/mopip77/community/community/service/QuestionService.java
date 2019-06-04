package tech.mopip77.community.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.mopip77.community.community.dto.QuestionDTO;
import tech.mopip77.community.community.mapper.QuestionMapper;
import tech.mopip77.community.community.mapper.User2Mapper;
import tech.mopip77.community.community.model.Question;
import tech.mopip77.community.community.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private User2Mapper user2Mapper;

    @Autowired
    private QuestionMapper questionMapper;


    public List<QuestionDTO> list() {
        List<QuestionDTO> results = new ArrayList<>();

        List<Question> questions = questionMapper.list();
        for (Question question : questions) {
            User user = user2Mapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            results.add(questionDTO);
        }

        return results;
    }
}
