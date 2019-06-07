package tech.mopip77.community.community.advice;

import com.alibaba.fastjson.JSON;
import com.sun.java.swing.plaf.windows.WindowsRadioButtonMenuItemUI;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import tech.mopip77.community.community.dto.ResultDTO;
import tech.mopip77.community.community.exception.CustomizeErrorCode;
import tech.mopip77.community.community.exception.CustomizeException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Throwable ex, Model model) {
        // api 和 普通页面 区分返回 json 和 html

        String contentType = request.getContentType();

        if ("application/json".equals(contentType)) {
            ResultDTO resultDTO;
            if (ex instanceof CustomizeException) {
                resultDTO = ResultDTO.errorOf((CustomizeException) ex);
            } else {
                resultDTO = ResultDTO.errorOf((CustomizeErrorCode.SYSTEM_ERROR));
            }

            try {
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.flush();
                writer.close();
            } catch (IOException e) {

            }
            return null;
        } else {
            if (ex instanceof CustomizeException) {
                model.addAttribute("message", ex.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorCode.SYSTEM_ERROR.getMessage());
            }

            return new ModelAndView("error");
        }
    }
}
