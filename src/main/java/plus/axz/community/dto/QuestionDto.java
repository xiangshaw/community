package plus.axz.community.dto;

import plus.axz.community.model.pojo.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionDto implements Serializable {
    private Long id;
    private String title;
    private String description;
    private String tags;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer commentCount;
    private Integer likeCount;
    private Integer viewCount;
    private Integer top;
    private User user;
}
