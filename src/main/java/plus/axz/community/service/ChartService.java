package plus.axz.community.service;

import plus.axz.community.dto.ChartDto;
import plus.axz.community.model.common.dtos.Result;

/**
 * @author xiaoxiang
 * @date 2023年02月20日
 * @Description:
 */
public interface ChartService {
    // 查询今日注册 人数、提问、评论、类别、标签
    public Result sumTotal(ChartDto chartDto);
}
