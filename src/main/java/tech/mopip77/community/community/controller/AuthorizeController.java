package tech.mopip77.community.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.mopip77.community.community.dto.AccessTokenDTO;
import tech.mopip77.community.community.dto.GithubUser;
import tech.mopip77.community.community.mapper.User2Mapper;
import tech.mopip77.community.community.model.User;
import tech.mopip77.community.community.provider.GithubProvider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client_id}")
    private String github_client_id;
    @Value("${github.secret}")
    private String github_secret;
    @Value("${github.redirect_uri}")
    private String github_redirect_uri;

//    @Resource(type = User2Mapper.class)
    @Autowired
    private User2Mapper user2Mapper;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(github_client_id);
        accessTokenDTO.setClient_secret(github_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(github_redirect_uri);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);

        if (githubUser != null) {
            //login write cookie and session
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());

            response.addCookie(new Cookie("token", token));
            user2Mapper.insert(user);
            return "redirect:/";
        } else {
            // fail relogin
            return "redirect:/";
        }
    }
}
