package tech.mopip77.community.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.mopip77.community.community.dto.QuestionDTO;
import tech.mopip77.community.community.mapper.QuestionMapper;
import tech.mopip77.community.community.mapper.User2Mapper;
import tech.mopip77.community.community.model.Question;
import tech.mopip77.community.community.model.User;
import tech.mopip77.community.community.service.QuestionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private User2Mapper user2Mapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String hello(HttpServletRequest request, Model model) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = user2Mapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        List<QuestionDTO> questionList = questionService.list();
        model.addAttribute("questions", questionList);
        return "index";
    }
}
