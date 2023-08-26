package plus.axz.community.controller.admin.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import plus.axz.community.dto.AdLoginDto;
import plus.axz.community.model.pojo.User;
import plus.axz.community.model.common.dtos.Result;
import plus.axz.community.model.common.enums.ResultEnum;
import plus.axz.community.service.AdUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author xiaoxiang
 * @date 2023年02月16日
 * @Description: admin用户登录
 */
@RestController
@RequestMapping("/api/v1/aduser/login")
public class AdUserLoginController {
    @Autowired
    AdUserService adUserService;
    // 用户登录
    @PostMapping("/in")
    public Result login(@RequestBody AdLoginDto adLoginDto) {
        return adUserService.adLogin(adLoginDto);
    }
    // 用户信息
    @GetMapping("/info")
    public Result info(HttpServletRequest request) {
        // 根据用户id查找用户
        User user = (User) request.getSession().getAttribute("user");


        if (user == null){
            return Result.errorResult(ResultEnum.DATA_NOT_EXIST,"用户不存在");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",user.getId());
        map.put("name",user.getName());
        map.put("bio",user.getBio());
        map.put("avatarUrl",user.getAvatarUrl());
        return Result.okResult(map);
    }
    // 退出
    @PostMapping("/logout")
    public Result logout(){
        return Result.okResult(ResultEnum.SUCCESS);
    }
}
