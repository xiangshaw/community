package plus.axz.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import plus.axz.community.config.GiteeParams;
import plus.axz.community.config.GithubParams;
import plus.axz.community.config.QQParams;
import plus.axz.community.dto.AccessTokenDto;
import plus.axz.community.dto.GiteeUser;
import plus.axz.community.dto.GithubUser;
import plus.axz.community.dto.QQUser;
import plus.axz.community.exception.CustomizeErrorCode;
import plus.axz.community.exception.CustomizeException;
import plus.axz.community.mapper.UserMapper;
import plus.axz.community.model.pojo.User;
import plus.axz.community.provider.GiteeProvider;
import plus.axz.community.provider.GithubProvider;
import plus.axz.community.provider.QQProvider;
import plus.axz.community.service.Impl.UserServiceImpl;
import plus.axz.community.utils.CookieUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
public class AuthoriseController {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private GithubParams githubParams;
    @Autowired
    private GiteeParams giteeParams;
    @Autowired
    private QQParams qqParams;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private QQProvider qqProvider;
    @Autowired
    private GiteeProvider giteeProvider;

    private AccessTokenDto accessTokenDto = new AccessTokenDto();

    private static final int COOKIE_EXPIRY = 60 * 60 * 24 * 7;

    @Value("${xiao.defaultAvatar}")
    private String defaultAvatar;

    @GetMapping("/githubCallback")
    public String githubCallback(@RequestParam("code") String code,
                                 @RequestParam("state") String state,
                                 HttpServletResponse response) {
        /*setAccessTokenDto(code, state, githubParams.getClient_id(), githubParams.getClient_secret(), githubParams.getRedirect_uri());
        //获取access_token
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        //根据accessToken获取用户信息
        GithubUser githubUser = githubProvider.getGithubUser(accessToken);
        if (githubUser != null && githubUser.getId() != null) {
            String token = UUID.randomUUID().toString();
            //设置user信息
            setUserInfo(token,githubUser.getName(), githubUser.getAvatarUrl(), "Github-" + githubUser.getId(), githubUser.getBio());
            CookieUtils.setCookie(response, "token", token, COOKIE_EXPIRY);
            return "redirect:/";
        } else {
            log.error("githubUser获取失败");
            throw new CustomizeException(CustomizeErrorCode.LOGIN_CONNECT_ERROR);
        }*/
        setAccessTokenDto(code, state, githubParams.getClient_id(), githubParams.getClient_secret(), githubParams.getRedirect_uri());
        accessTokenDto.setClient_id(githubParams.getClient_id());
        accessTokenDto.setClient_secret(githubParams.getClient_secret());
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(githubParams.getRedirect_uri());
        accessTokenDto.setState(state);
        //获取access_token
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        //根据accessToken获取用户信息
        GithubUser githubUser = githubProvider.getGithubUser(accessToken);
        System.out.println(githubUser.getName());
        if (githubUser != null && githubUser.getId() != null) {
            String token = UUID.randomUUID().toString();
            //设置user信息
            setUserInfo(token,githubUser.getName(), githubUser.getAvatarUrl(), "Github-" + githubUser.getId(), githubUser.getBio());
            CookieUtils.setCookie(response, "token", token, COOKIE_EXPIRY);
            return "redirect:/";
        } else {
            log.error("githubUser获取失败");
            throw new CustomizeException(CustomizeErrorCode.LOGIN_CONNECT_ERROR);
        }
    }

    @GetMapping("/giteeCallback")
    public String giteeCallback(@RequestParam("code") String code,
                                @RequestParam("state") String state,
                                HttpServletResponse response) {
        // 生成AccessToken
        setAccessTokenDto(code, state, giteeParams.getClient_id(), giteeParams.getClient_secret(), giteeParams.getRedirect_uri());
        // 获取token
        String accessToken = giteeProvider.getAccessToken(accessTokenDto);
        // 获取gitee用户
        GiteeUser giteeUser = giteeProvider.getGiteeUser(accessToken);
        if (giteeUser != null && giteeUser.getId() != null) {
            // 用户id
            Long id = giteeUser.getId();
            String ids = String.valueOf(id);
            // 查询条件
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.select("account_id").like("account_id","Gitee-" + ids).eq("status",0);
            // 查询用户
            List<User> users = userMapper.selectList(wrapper);
            // 分情况(主要区分注册和登录，避免自定义资料被替换)
            // 1. 数据为0 注册用户并登录
            if (users.size() == 0) {
                // 生成登录token
                String token = UUID.randomUUID().toString();
                //设置user信息
                setUserInfo(token, giteeUser.getName(), giteeUser.getAvatarUrl(), "Gitee-" + giteeUser.getId(), giteeUser.getBio());
                CookieUtils.setCookie(response, "token", token, COOKIE_EXPIRY);
                return "redirect:/";
            }
            // 2. 数据为1 只登录
            if (users.size() == 1) {
                // 生成登录token
                String token = UUID.randomUUID().toString();
                // 获取用户
                User user = users.get(0);
                //从数据库获取数据来设置user信息
                setUserInfo(token, user.getName(), user.getAvatarUrl(), user.getAccountId(), user.getBio());
                CookieUtils.setCookie(response, "token", token, COOKIE_EXPIRY);
                return "redirect:/";
            }
            // 都不是，返回登录出错
            throw new CustomizeException(CustomizeErrorCode.LOGIN_CONNECT_ERROR);
        } else {
            log.error("giteeUser获取失败");
            throw new CustomizeException(CustomizeErrorCode.LOGIN_CONNECT_ERROR);
        }
    }

    @GetMapping("/qqCallback")
    public String qqCallback(HttpServletResponse response,
                             @RequestParam("code") String code,
                             @RequestParam("state") String state) {
        String accessToken = qqProvider.getAccessToken(code);
        String openId = qqProvider.getOpenId(accessToken);
        QQUser qqUser = qqProvider.getQQUser(accessToken, openId);
        if (qqUser != null && qqUser.getRet() == 0) {
            String token = UUID.randomUUID().toString();
            setUserInfo(token, qqUser.getNickname(), qqUser.getFigureurl_qq_2(), "QQ-" + openId, null);
            CookieUtils.setCookie(response, "token", token, COOKIE_EXPIRY);
            //返回首页
            return "redirect:/";
        } else {
            log.error("qqUser获取失败");
            throw new CustomizeException(CustomizeErrorCode.LOGIN_CONNECT_ERROR);
        }
    }


    private void setAccessTokenDto(String code, String state, String clientId, String clientSecret, String redirectUri) {
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setState(state);
    }

    private void setUserInfo(String token, String name, String avatarUrl, String accountId, String bio) {
        User user = new User();
        user.setToken(token);
        user.setName(name);
        user.setAvatarUrl(avatarUrl);
        user.setAccountId(accountId);
        user.setBio(bio);
        user.setCertification(false);
        userService.createOrUpdateUser(user);
    }
}
