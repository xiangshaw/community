package plus.axz.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import plus.axz.community.dto.PaginationDto;
import plus.axz.community.dto.QuestionDto;
import plus.axz.community.dto.QuestionQueryDto;
import plus.axz.community.exception.CustomizeErrorCode;
import plus.axz.community.exception.CustomizeException;
import plus.axz.community.model.pojo.User;
import plus.axz.community.service.Impl.NotificationServiceImpl;
import plus.axz.community.service.Impl.QuestionServiceImpl;
import plus.axz.community.service.Impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private QuestionServiceImpl questionService;
    @Autowired
    private NotificationServiceImpl notificationService;

    @GetMapping("/user/{id}")
    public String getInformationById(@PathVariable Long id, Model model,
                                     @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        User user = userService.findById(id);
        QuestionQueryDto queryDto = QuestionQueryDto.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .creatorId(user.getId())
                .build();
        //查询当前用户的问题列表
        PaginationDto<QuestionDto> pageInfo = questionService.findByCondition(queryDto);
        model.addAttribute("userInfo", user);
        model.addAttribute("pageInfo", pageInfo);
        return "user";
    }

    @GetMapping("/userInfo")
    public String getUserInfo(Model model, HttpServletRequest request) {
        // 根据用户id查找用户
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomizeException(CustomizeErrorCode.IS_NOT_LEGAL);
        }
        model.addAttribute("id", user.getId());
        model.addAttribute("name", user.getName());
        model.addAttribute("bio", user.getBio());
        model.addAttribute("avatarUrl", user.getAvatarUrl());
/*        model.addAttribute("phone", user.getPhone());
        model.addAttribute("sex", user.getSex());*/
        return "userInfo";
    }

    /**
     * 用户信息更改
     */
    @PostMapping("/user/update")
    public String updateUserInfo(@RequestParam(value = "id") String id,
                                 @RequestParam(value = "bio") String bio,
                                 @RequestParam(value = "avatarUrl") String avatarUrl) {
        userService.updateUserInfo(id, bio, avatarUrl);
        /*return "redirect:/";*/
        return "redirect:/user/" + id;
    }

    // 用户头像修改
    @GetMapping("/userAvatar")
    public String getUserAvatar(Model model, HttpServletRequest request) {
        // 根据用户id查找用户
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomizeException(CustomizeErrorCode.IS_NOT_LEGAL);
        }
        model.addAttribute("id", user.getId());
        model.addAttribute("name", user.getName());
        model.addAttribute("bio", user.getBio());
        model.addAttribute("avatarUrl", user.getAvatarUrl());
        return "userAvatar";
    }
    /**
     * 用户信息更改
     */
    @PostMapping("/user/update/avatar")
    public String updateUserAvatar(@RequestParam(value = "id") String id,
                                 @RequestParam(value = "avatarUrl") String avatarUrl) {
        userService.updateUserAvatar(id,avatarUrl);
        return "redirect:/user/" + id;
    }
}
