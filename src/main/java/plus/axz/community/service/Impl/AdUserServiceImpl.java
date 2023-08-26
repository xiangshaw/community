package plus.axz.community.service.Impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import plus.axz.community.dto.AdLoginDto;
import plus.axz.community.dto.AdUserDto;
import plus.axz.community.mapper.UserMapper;
import plus.axz.community.model.common.dtos.PageResponse;
import plus.axz.community.model.common.dtos.Result;
import plus.axz.community.model.common.enums.ResultEnum;
import plus.axz.community.model.pojo.User;
import plus.axz.community.service.AdUserService;
import plus.axz.community.service.CommentQueryService;
import plus.axz.community.service.QuestionQueryService;
import plus.axz.community.utils.CodecUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author xiaoxiang
 * @date 2023年02月16日
 * @Description: admin用户登录实现
 */
@Service
public class AdUserServiceImpl extends ServiceImpl<UserMapper, User> implements AdUserService {


    @Value("${xiao.defaultAvatar}")
    private String defaultAvatar;
    private final UserMapper userMapper;
    private final CommentQueryService commentQueryService;
    private final QuestionQueryService questionQueryService;

    public AdUserServiceImpl(UserMapper userMapper, CommentQueryService commentQueryService, QuestionQueryService questionQueryService) {
        this.userMapper = userMapper;
        this.commentQueryService = commentQueryService;
        this.questionQueryService = questionQueryService;
    }

    @Override
    public Result adLogin(AdLoginDto adLoginDto) {
        // 1.检查参数
        if (StringUtils.isEmpty(adLoginDto.getName()) && StringUtils.isEmpty(adLoginDto.getPassword())) {
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.用户名和密码登录
        User user = userMapper.selectOne(Wrappers.lambdaQuery(User.class).eq(User::getName, adLoginDto.getName()));
        // 2.1用户不为空 且为首位用户
        if (user != null && user.getId() == 1) {
            // 2.2加密
            String password = CodecUtils.md5Hex(adLoginDto.getPassword(), user.getSalt());
            // 2.3密码验证
            if (user.getPassword().equals(password)) {
                // 2.4验证通过
                HashMap<String, Object> map = new HashMap<>();
                user.setPassword(""); // 避免密码泄露，返回数据置空
                user.setSalt(""); // 加密盐置空
                map.put("token", user.getToken());
                map.put("user", user);
                map.put("name", user.getName());
                map.put("avatarUrl", user.getAvatarUrl());
                return Result.okResult(map);

            } else {
                return Result.errorResult(ResultEnum.LOGIN_PASSWORD_ERROR);
            }
        } else {
            return Result.errorResult(ResultEnum.DATA_NOT_EXIST, "用户不存在");
        }
    }

    @Override
    public boolean findUserStatusById(String id) {
        return getById(id).getStatus();
    }

    /**
     * admin后端用户查询端口
     *
     * @param adUserDto
     * @return plus.axz.community.model.common.dtos.Result
     * @author xiaoxiang
     * @date 2023/2/16/0016 22:45:40
     */
    // 1 查询
    @Override
    public Result listUser(AdUserDto adUserDto) {
        // 1.检查参数
        if (adUserDto == null) {
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.检查分页
        adUserDto.checkParam();
        Page page = new Page(adUserDto.getPage(), adUserDto.getSize());
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery(User.class);
        if (StringUtils.isNotBlank(adUserDto.getName())) {
            userLambdaQueryWrapper.like(User::getName, adUserDto.getName());
        }
        Page result = page(page, userLambdaQueryWrapper);
        PageResponse pageResponse = new PageResponse(adUserDto.getPage(), adUserDto.getSize(), (int) result.getTotal());
        pageResponse.setData(result.getRecords());
        return pageResponse;
    }

    // 2 修改
    @Override
    public Result updateUser(User user) {
        if (user == null && user.getId() == null) {
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        boolean b = updateById(user);
        if (Boolean.TRUE.equals(b)) {
            return Result.okResult(ResultEnum.SUCCESS);
        }
        return Result.errorResult(ResultEnum.PARAM_INVALID, "修改失败~");
    }

    // 3 删除
    @Override
    @CacheEvict(value = "questionById",allEntries = true)
    public Result removeUser(String id) {
        // 1.检查参数
        if (id == null) {
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.判断是否存在 是否有效
        User user = getById(id);
        if (user == null) {
            return Result.errorResult(ResultEnum.DATA_NOT_EXIST);
        }
        // 0 ture正常 1 false锁定
        if (Boolean.FALSE.equals(user.getStatus())) {
            return Result.errorResult(ResultEnum.PARAM_INVALID, "用户有效，暂不能删除~");
        }
        // 删除用户的同时，将用户的评论，提问一并删除
        // 根据用户id查询该用户所有评论
        List<Long> ids = commentQueryService.getCommentById(id);
        System.out.println("用户：" + id + " 所有评论ID：" + ids);
        // 判空
        if (ids != null && ids.size()!=0){
            // 删除评论
            commentQueryService.batchRemove(ids);
        }
        // 根据用户id删除提问
        // 根据用户id查询该用户所有提问
        List<Long> questionByIds = questionQueryService.getQuestionById(id);
        System.out.println("用户：" + id + " 所有提问ID：" + questionByIds);
        if (questionByIds != null && questionByIds.size() !=0){
            // 删除提问
            questionQueryService.batchRemove(questionByIds);
        }
        // 删除用户
        removeById(id);
        return Result.okResult(ResultEnum.SUCCESS);
    }

    // 4 锁定用户
    @Override
    public Result updateStatus(String id, Boolean status) {
        // 1.检查参数
        if (id == null) {
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.根据id查询
        User user = getById(id);
        if (user == null) {
            return Result.errorResult(ResultEnum.DATA_NOT_EXIST);
        }
        // 3.设置状态值
        user.setStatus(status);
        // 4.修改
        updateById(user);
        return Result.okResult(ResultEnum.SUCCESS);
    }

    // 5 关键词搜索用户
    @Override
    public Result findUserName(Integer page, Integer size, String search) {
        if (search == null) {
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.<User>lambdaQuery();
        if (StringUtils.isNotBlank(search)) {
            userLambdaQueryWrapper.like(User::getName, search);
            return Result.okResult(userMapper.selectPage(new Page(page, size), userLambdaQueryWrapper));
        }
        return Result.errorResult(ResultEnum.PARAM_INVALID, "未搜索到该数据~");
    }

    // 6 根据id查询用户
    @Override
    public Result findUserById(String id) {
        if (id == null) {
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        User user = getById(id);
        if (user == null) {
            return Result.errorResult(ResultEnum.DATA_NOT_EXIST);
        }
        // 0 正常 1锁定
        if (Boolean.TRUE.equals(user.getStatus())) {
            return Result.errorResult(ResultEnum.PARAM_INVALID, "用户无效~");
        }
        return Result.okResult(user);
    }

    // 7 用户总条数
    @Override
    public Result countUser() {
        int count = count(Wrappers.<User>lambdaQuery());
        return Result.okResult(count);
    }

    // 8 新增用户
    @Override
    public Result addUser(User user) {
        // 1.检查参数(非空)
        if (StringUtils.isNotBlank(user.getName())) {
            // 2.查数据库
            List<User> list = list(Wrappers.<User>lambdaQuery().eq(User::getAccountId, user.getAccountId()));
            if (list != null && list.size() == 1) {
                return Result.errorResult(ResultEnum.DATA_EXIST, "用户名已注册");
            }
            // 3.保存数据
            user.setAccountId(user.getName());
            user.setName(user.getName());
            user.setBio(user.getBio());
            user.setPhone(user.getPhone());
            user.setToken(UUID.randomUUID().toString());
            user.setCertification(true);
            user.setGmtCreate(new Date().getTime());
            user.setGmtModified(new Date().getTime());
            String salt = CodecUtils.generateSalt();
            user.setSalt(salt);
            String password = "123456";
            user.setPassword(CodecUtils.md5Hex(password, salt));
            user.setAvatarUrl(user.getAvatarUrl());
            save(user);
            return Result.okResult(ResultEnum.SUCCESS);
        }
        return Result.errorResult(ResultEnum.PARAM_INVALID);
    }

    @Value("${file.upload.path}")
    private String filePath;
    @Value("${file.upload.ip}")
    private String ip;

    @Override
    public Result adminUpload(MultipartFile file) {
        // 获取上传文件名
        String filename = file.getOriginalFilename();
        // 定义唯一标识
        String uuid = IdUtil.fastSimpleUUID();
        // 定义上传文件保存路径
        String path = filePath + "userAvatar/" + uuid + "_" + filename;
        // 新建文件
        File filepath = new File(path);
        // 判断路径是否存在，如果不存在就创建一个
        if (!filepath.getParentFile().exists()) {
            filepath.getParentFile().mkdirs();
        }
        // 5.通过hutool工具，将把文件写入到上传的路径（获取文件字节流，路径）
        /*FileUtil.writeBytes(file.getBytes(), path);*/
        try {
            // 写入文件
            FileUtil.writeBytes(file.getBytes(), path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 6.返回结果 url
        String flag = ip + uuid;
        return Result.okResult(flag);
    }

    @Override
    public Result updateUserInfo(User user) {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",user.getId()).set("avatar_url",user.getAvatarUrl()).set("bio",user.getBio());
        userMapper.update(null,wrapper);
        return Result.okResult(ResultEnum.SUCCESS);
    }
}
