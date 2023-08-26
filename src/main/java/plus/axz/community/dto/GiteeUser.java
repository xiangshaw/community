package plus.axz.community.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class GiteeUser implements Serializable {
    private Long id;
    private String name;
    private String bio;
    private String avatarUrl;
    private Boolean status;
}
