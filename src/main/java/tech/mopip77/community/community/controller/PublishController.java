package tech.mopip77.community.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.mopip77.community.community.dto.QuestionDTO;
import tech.mopip77.community.community.model.Question;
import tech.mopip77.community.community.model.User;
import tech.mopip77.community.community.service.QuestionService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(name = "title", required = false) String title,
                            @RequestParam(name = "description", required = false) String description,
                            @RequestParam(name = "tag", required = false) String tag,
                            @RequestParam(name = "id", required = false) Long id,
                            HttpServletRequest request,
                            Model model) {

        model.addAttribute("tag", tag);
        model.addAttribute("title", title);
        model.addAttribute("description", description);

        // check format
        if (title == null || title.equals("")) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || description.equals("")) {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if (tag == null || tag.equals("")) {
            model.addAttribute("error", "分类不能为空");
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);

        return "redirect:/";
    }

    @GetMapping("/publish/{questionId}")
    public String edit(@PathVariable("questionId") Long id, Model model) {
        QuestionDTO question = questionService.getById(id);
        if (question != null) {
            model.addAttribute("tag", question.getTag());
            model.addAttribute("title", question.getTitle());
            model.addAttribute("description", question.getDescription());
            model.addAttribute("id", question.getId());
            return "publish";
        } else {
            return "redirect:/";
        }
    }
}
