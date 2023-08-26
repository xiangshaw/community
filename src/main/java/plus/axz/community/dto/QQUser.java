package plus.axz.community.dto;

import lombok.Data;

@Data
public class QQUser {
    private Long ret;//返回码
    private String msg;//错误信息
    private String nickname;
    private String gender;
    private String figureurl_qq_2;//头像
}
