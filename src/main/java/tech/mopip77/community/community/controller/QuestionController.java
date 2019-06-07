package tech.mopip77.community.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sun.rmi.runtime.Log;
import tech.mopip77.community.community.dto.QuestionDTO;
import tech.mopip77.community.community.mapper.QuestionMapper;
import tech.mopip77.community.community.service.QuestionService;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id, Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        questionService.incView(id);
        model.addAttribute("question", questionDTO);
        return "question";
    }
}
