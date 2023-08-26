package plus.axz.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import plus.axz.community.model.pojo.Comment;
import plus.axz.community.model.vo.CommentQueryVo;

/**
 * @author xiaoxiang
 * @date 2023年02月19日
 * @Description: 后台评论
 */
@Mapper
public interface CommentQueryMapper extends BaseMapper<Comment> {
    IPage<Comment> selectPage(Page<Comment> pageParam, @Param("vo") CommentQueryVo commentQueryVo);
}
