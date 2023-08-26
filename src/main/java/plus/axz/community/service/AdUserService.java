package plus.axz.community.service;

import org.springframework.web.multipart.MultipartFile;
import plus.axz.community.dto.AdLoginDto;
import plus.axz.community.dto.AdUserDto;
import plus.axz.community.model.pojo.User;
import plus.axz.community.model.common.dtos.Result;

/**
 * @author xiaoxiang
 * @date 2023年02月16日
 * @Description: 管理员登录
 */
public interface AdUserService {
    public Result adLogin(AdLoginDto adLoginDto);

    // 单独查询用户状态
    public boolean findUserStatusById(String id);

    /**
     * admin后端用户查询端口
     * @author xiaoxiang
     * @date 2023/2/16/0016 22:45:16
     * @param adUserDto
     * @return plus.axz.community.model.common.dtos.Result
     */
    // 1 查询
    public Result listUser(AdUserDto adUserDto);
    // 2 修改
    public Result updateUser(User user);
    // 3 删除
    public Result removeUser(String id);
    // 4 锁定用户
    public Result updateStatus(String id, Boolean status);
    // 5 关键词搜索用户
    public Result findUserName(Integer page, Integer size, String search);
    // 6 根据ID查询用户信息
    public Result findUserById(String id);

    // 7 用户总条数
    public Result countUser();
    // 8 新增用户
    public Result addUser(User user);

    public Result adminUpload(MultipartFile file);

    // 管理修改信息
    public Result updateUserInfo(User user);
}
