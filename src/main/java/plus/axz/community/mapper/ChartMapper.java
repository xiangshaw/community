package plus.axz.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.community.dto.ChartDto;

/**
 * @author xiaoxiang
 * @date 2023年02月20日
 * @Description: 今日图表
 */
@Mapper
public interface ChartMapper extends BaseMapper<ChartDto> {
}
