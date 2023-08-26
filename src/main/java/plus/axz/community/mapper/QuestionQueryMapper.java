package plus.axz.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import plus.axz.community.model.pojo.Question;
import plus.axz.community.model.vo.QuestionQueryVo;

/**
 * @author xiaoxiang
 * @date 2023年02月18日
 * @Description:
 */
@Mapper
public interface QuestionQueryMapper extends BaseMapper<Question> {
    IPage<Question> selectPage(Page<Question> pageParam, @Param("vo") QuestionQueryVo questionQueryVo);
}
