package plus.axz.community.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plus.axz.community.mapper.UserMapper;
import plus.axz.community.model.pojo.User;
import plus.axz.community.model.UserExample;
import plus.axz.community.service.AdUserService;
import plus.axz.community.utils.CodecUtils;
import plus.axz.community.utils.CookieUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Service
public class LoginServiceImpl {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdUserService adUserService;

    private static final int COOKIE_EXPIRY = 60 * 60 * 24 * 7;

    /**
     * 登录
     *
     * @param email
     * @param password
     */
    public Integer checkLogin(String email, String password, HttpServletResponse response) {
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(email);
        List<User> users = userMapper.selectByExample(example);
        if (users.size() == 1) {
            // 获取用户
            User user = users.get(0);
            // 根据用户ID查询用户状态
            boolean userStatus = adUserService.findUserStatusById(user.getId().toString());
            // 状态非0 false
            if (!userStatus) {
                // 密码加密
                password = CodecUtils.md5Hex(password, user.getSalt());
                // 和数据库密码比对
                if (StringUtils.equals(user.getPassword(), password)) {
                    // 设置token
                    CookieUtils.setCookie(response, "token", user.getToken(), COOKIE_EXPIRY);
                    // 更新用户登录时间
                    user.setLoginTime(new Date().getTime());
                    UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("id", user.getId());
                    updateWrapper.set("login_time", user.getLoginTime());
                    userMapper.update(null, updateWrapper);
                    return 0;
                }
            }
            return 1;
        }
        return 2;
    }
}
