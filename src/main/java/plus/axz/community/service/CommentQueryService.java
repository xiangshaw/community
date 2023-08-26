package plus.axz.community.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.community.model.pojo.Comment;
import plus.axz.community.model.common.dtos.Result;
import plus.axz.community.model.vo.CommentQueryVo;

import java.text.ParseException;
import java.util.List;

/**
 * @author xiaoxiang
 * @date 2023年02月18日
 * @Description: 评论
 */
public interface CommentQueryService extends IService<Comment> {
    // 分页条件查询
    IPage<Comment> selectPage(Page<Comment> pageParam, CommentQueryVo commentQueryVo) throws ParseException;
    // 根据用户ID查询该用户所有评论id数据
    List<Long> getCommentById(String id);
    // 删除评论
    public Result deleteById(String id);
    // 批量删除评论
    public Result batchRemove(List<Long> ids);
}
