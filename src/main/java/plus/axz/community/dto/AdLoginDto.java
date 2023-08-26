package plus.axz.community.dto;

import lombok.Data;

/**
 * @author xiaoxiang
 * @date 2023年01月09日
 * @Description: admin用户登录
 */
@Data
public class AdLoginDto {
    // 用户名
    private String name;

    // 密码
    private String password;
}