package plus.axz.community.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import plus.axz.community.dto.CommentCreateDto;
import plus.axz.community.dto.CommentDto;
import plus.axz.community.dto.ResultDto;
import plus.axz.community.enums.CommentTypeEnum;
import plus.axz.community.exception.CustomizeErrorCode;
import plus.axz.community.exception.CustomizeException;
import plus.axz.community.mapper.CommentMapper;
import plus.axz.community.model.pojo.Comment;
import plus.axz.community.model.pojo.User;
import plus.axz.community.service.CommentQueryService;
import plus.axz.community.service.Impl.CommentServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;

    @PostMapping("/comment")
    @ResponseBody
    public ResultDto<?> insertComment(@RequestBody CommentCreateDto commentDto, HttpServletRequest request) {
        //获取用户
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDto.errorOf(CustomizeErrorCode.USER_NOT_LOGIN);
        }
        if (commentDto == null || StringUtils.isBlank(commentDto.getContent())) {
            return ResultDto.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDto, comment);
        comment.setCommentator(user.getId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setLikeCount(0L);
        commentService.insertComment(comment, user);
        return ResultDto.okOf();
    }

    /**
     * 获取回复下的评论
     *
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/comment/{id}")
    public ResultDto<List<CommentDto>> getComments(@PathVariable("id") Long id) {
        List<CommentDto> commentDtos = this.commentService.findByParentId(id, CommentTypeEnum.TYPE_COMMENT);
        return ResultDto.okOf(commentDtos);
    }

    /**
     * 删除问题下的评论
     *
     * @param id
     * @return
     */
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentQueryService commentQueryService;

    @CacheEvict(cacheNames = "commentByParentId", allEntries = true, key = "'comment-type1-' + #root.args[0]")
    @GetMapping("comment/delete/{id}")
    public void deleteComment(@PathVariable("id") Long id, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Comment comment = commentMapper.selectByPrimaryKey(id);
        Long commentParentId = comment.getParentId();
        if (user != null) {
            if (comment.getCommentator().equals(user.getId())) {
                commentQueryService.deleteById(String.valueOf(id));
            } else {
                throw new CustomizeException(CustomizeErrorCode.IS_NOT_LEGAL);
            }
//            commentService.deleteComment(id, user.getId());
        }else {
            throw new CustomizeException(CustomizeErrorCode.PARAM_INVALID);
        }
        /*return ResultDto.okOf();*/
    }
}
