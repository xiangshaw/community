package plus.axz.community.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plus.axz.community.mapper.UserInfoMapper;
import plus.axz.community.mapper.UserMapper;
import plus.axz.community.model.pojo.User;
import plus.axz.community.model.UserExample;
import plus.axz.community.utils.CodecUtils;

import java.util.List;

@Service
public class UserServiceImpl {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RegisterServiceImpl registerService;

    /**
     * 根据user的accountId判断用户是否存在，存在则更新信息，不存在则创建新用户
     * @param user
     */
    public void createOrUpdateUser(User user) {
        //获取数据库里的user
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);

        if(users.size() == 0){
            //创建，创建时间修改时间均设置为当前时间
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setStatus(false);
            userMapper.insert(user);
        }else{
            User dbUser = users.get(0);
            // 修改时间
            dbUser.setGmtModified(System.currentTimeMillis());
            // 用户名称
            dbUser.setName(user.getName());
            // 描述
            dbUser.setBio(user.getBio());
            // token
            dbUser.setToken(user.getToken());
            // 头像
            dbUser.setAvatarUrl(user.getAvatarUrl());
            // 登录时间
            dbUser.setLoginTime(System.currentTimeMillis());
            UserExample userExample1 = new UserExample();
            userExample1.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(dbUser, userExample1);
        }
    }

    public User findById(Long id) {
        return this.userMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新密码
     * @param email
     * @param pwd
     */
    public void updatePwd(String email, String pwd) {

        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(email);
        List<User> users = userMapper.selectByExample(example);
        if(users != null && users.size() == 1){
            String salt = CodecUtils.generateSalt();
            pwd = CodecUtils.md5Hex(pwd, salt);
            User user = new User();
            user.setSalt(salt);
            user.setId(users.get(0).getId());
            user.setPassword(pwd);
            boolean b = userMapper.updateByPrimaryKeySelective(user) == 1;
            if(b) {
                //删除验证码
                registerService.deleteCode(email);
            }
        }
    }

    /*@Value("${file.upload.ip}")
    private String  ip;*/
    public void updateUserInfo(String id, String bio, String avatarUrl) {
        UpdateWrapper wrapper = new UpdateWrapper();
        wrapper.eq("id",id);
        wrapper.set("bio",bio);
        wrapper.set("avatar_url",avatarUrl);
        userInfoMapper.update(null,wrapper);
        /*User user = new User();
        if (id == null){
            return;
        }
        user.setAvatarUrl(avatarUrl);
        user.setBio(bio);*/
    }
    public void updateUserAvatar(String id, String avatarUrl) {
        UpdateWrapper wrapper = new UpdateWrapper();
        wrapper.eq("id",id);
        wrapper.set("avatar_url",avatarUrl);
        userInfoMapper.update(null,wrapper);
    }
}
