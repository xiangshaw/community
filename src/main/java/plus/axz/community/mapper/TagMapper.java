package plus.axz.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import plus.axz.community.model.pojo.Tag;
import plus.axz.community.model.vo.SortTagVo;
import plus.axz.community.model.vo.TagQueryVo;

import java.util.List;

/**
 * @author xiaoxiang
 * @date 2023年02月15日
 * @Description: 标签信息
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    IPage<Tag> selectPages(Page<Tag> pageParam, @Param("tagQueryVo") TagQueryVo tagQueryVo);

    List<SortTagVo> findBySortName(@Param("sortName") String sortName);

    /*@Override
    List<Tag> selectList(Wrapper<Tag> queryWrapper);*/
}
