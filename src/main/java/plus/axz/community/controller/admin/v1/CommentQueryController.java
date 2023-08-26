package plus.axz.community.controller.admin.v1;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import plus.axz.community.model.pojo.Comment;
import plus.axz.community.model.common.dtos.Result;
import plus.axz.community.model.vo.CommentQueryVo;
import plus.axz.community.service.CommentQueryService;

import java.text.ParseException;

/**
 * @author xiaoxiang
 * @date 2023年02月18日
 * @Description: 后台评论查询
 */
@RestController
@RequestMapping("/api/v1/comment/")
public class CommentQueryController {
    @Autowired
    private CommentQueryService commentQueryService;

    //获取分页列表
    @GetMapping("/{page}/{limit}")
    public Result selectPage(
            @PathVariable Long page,
            @PathVariable Long limit,
            CommentQueryVo commentQueryVo) throws ParseException {
        Page<Comment> pageParam = new Page<>(page, limit);
        return Result.okResult(commentQueryService.selectPage(pageParam, commentQueryVo));
    }
    // 删
    @CacheEvict(cacheNames = "commentByParentId", allEntries = true, key = "'comment-type1-' + #root.args[0]")
    @DeleteMapping("/remove/{id}")
    public Result deleteById(@PathVariable("id") String id) {
        return commentQueryService.deleteById(id);
    }
}
