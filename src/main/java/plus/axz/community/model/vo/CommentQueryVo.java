package plus.axz.community.model.vo;

/**
 * @author xiaoxiang
 * @date 2023年02月18日
 * @Description: 评论查询实体
 */
public class CommentQueryVo {
    private String keyword;
    private String gmtCreate;
    private String gmtModified;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }
}
