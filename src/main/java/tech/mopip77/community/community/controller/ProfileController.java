package tech.mopip77.community.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import tech.mopip77.community.community.dto.PaginationDTO;
import tech.mopip77.community.community.model.User;
import tech.mopip77.community.community.service.QuestionService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "3") Integer size,
                          HttpServletRequest request,
                          Model model) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户为登陆");
            return "redirect:/";
        }

        if (action.equals("questions")) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
        } else if (action.equals("replies")) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }

        PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
        model.addAttribute("questions", paginationDTO);


        return "profile";
    }

    @GetMapping("/profile")
    public String profileMainPage() {
        return "redirect:/profile/questions";
    }
}
