package tech.mopip77.community.community.controller;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomizeErrorController extends AbstractErrorController {


    public CustomizeErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request,
                                  Model model) {
        HttpStatus status = getStatus(request);
        if (status.is4xxClientError()) {
            model.addAttribute("message", "4xx你有点问题");
        } else {
            model.addAttribute("message", "5xx服务器炸了");

        }
        return new ModelAndView("error");
    }

}
