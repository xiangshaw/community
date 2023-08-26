package plus.axz.community.dto;

import plus.axz.community.model.pojo.User;
import lombok.Data;

/**
 * 数据库的评论数据
 */
@Data
public class CommentDto {
    private Long id;
    private Long parentId;
    private Integer type;
    private String content;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Long commentCount;
    private User user;
}
