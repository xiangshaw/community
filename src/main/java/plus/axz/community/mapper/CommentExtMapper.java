package plus.axz.community.mapper;

import plus.axz.community.model.pojo.Comment;

public interface CommentExtMapper {
    void addCommentCount(Comment comment);
}