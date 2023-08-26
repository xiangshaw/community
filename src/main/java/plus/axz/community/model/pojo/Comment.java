package plus.axz.community.model.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

public class Comment  implements Serializable {
    @TableField("id")
    private Long id;
    @TableField("parent_id")
    private Long parentId;

    @TableField(exist = false)  // 文章标题
    private String title;
    @TableField("type")
    private Integer type;
    @TableField("content")
    private String content;
    @TableField("commentator")
    private Long commentator;
    @TableField(exist = false)  // 作者
    private String author;
    @TableField("gmt_create")
    private Long gmtCreate;
    @TableField("gmt_modified")
    private Long gmtModified;
    @TableField("like_count")
    private Long likeCount;
    @TableField("comment_count")
    private Long commentCount;
    @TableLogic  //逻辑删除 默认效果 0 没有删除 1 已经删除
    @TableField("is_deleted")
    private Boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Long getCommentator() {
        return commentator;
    }

    public void setCommentator(Long commentator) {
        this.commentator = commentator;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}