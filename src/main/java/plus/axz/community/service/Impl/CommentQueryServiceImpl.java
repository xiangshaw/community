package plus.axz.community.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plus.axz.community.mapper.CommentQueryMapper;
import plus.axz.community.model.pojo.Comment;
import plus.axz.community.model.common.dtos.Result;
import plus.axz.community.model.common.enums.ResultEnum;
import plus.axz.community.model.vo.CommentQueryVo;
import plus.axz.community.service.CommentQueryService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoxiang
 * @date 2023年02月18日
 * @Description: 评论模块
 */
@Service
public class CommentQueryServiceImpl extends ServiceImpl<CommentQueryMapper, Comment> implements CommentQueryService {

    @Autowired
    private CommentQueryMapper commentQueryMapper;

    @Override
    public IPage<Comment> selectPage(Page<Comment> pageParam, CommentQueryVo commentQueryVo) throws ParseException {
        if (commentQueryVo.getGmtCreate() == null && commentQueryVo.getGmtModified() == null){
            return commentQueryMapper.selectPage(pageParam, commentQueryVo);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 先把收到的 String格式日期 转换为 DATE类型 再format转换为指定格式
        long gmtCreate = simpleDateFormat.parse(commentQueryVo.getGmtCreate()).getTime();
        commentQueryVo.setGmtCreate(String.valueOf(gmtCreate));
        long gmtModified = simpleDateFormat.parse(commentQueryVo.getGmtModified()).getTime();
        commentQueryVo.setGmtModified(String.valueOf(gmtModified));
        return commentQueryMapper.selectPage(pageParam, commentQueryVo);
    }

    // 根据用户ID查询该用户所有评论id数据
    @Override
    public List<Long> getCommentById(String id) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.select("id");
        wrapper.eq("commentator",id);
        wrapper.eq("is_deleted",0);
        List<Comment> commentList = baseMapper.selectList(wrapper);
        // 将查询到的评论id放入数组中
        List<Long> ids = new ArrayList<>();
        for (Comment comment : commentList) {
             ids.add(comment.getCommentator());
        }
        return ids;
    }

    @Override
    public Result deleteById(String id) {
        // 1.检查参数
        if (id == null) {
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.判断是否存在 是否有效
        Comment comment = getById(id);
        if (comment == null) {
            return Result.errorResult(ResultEnum.DATA_NOT_EXIST);
        }
        // 3.删除
        removeById(id);
        return Result.okResult(ResultEnum.SUCCESS);
    }

    // 批量删除
    @Override
    public Result batchRemove(List<Long> ids) {
        // 1.检查参数
        if (ids == null){
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        List<Comment> comments = listByIds(ids);
        if (comments == null){
            return Result.errorResult(ResultEnum.DATA_NOT_EXIST);
        }
        // 3.删除并检查结果
        boolean b = removeByIds(ids);
        if (b){
            return Result.okResult(ResultEnum.SUCCESS);
        }
        return Result.errorResult(ResultEnum.FAIL);
    }
}
