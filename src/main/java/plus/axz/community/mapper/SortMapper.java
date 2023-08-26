package plus.axz.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import plus.axz.community.model.pojo.Sort;
import plus.axz.community.model.vo.SortQueryVo;

/**
 * @author xiaoxiang
 * @date 2023年02月15日
 * @Description:
 */
@Mapper
public interface SortMapper extends BaseMapper<Sort> {
    IPage<Sort> selectPages(Page<Sort> pageParam, @Param("sortQueryVo") SortQueryVo sortQueryVo);
}
