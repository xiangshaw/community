package plus.axz.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.community.dto.StatisticsDto;

/**
 * @author xiaoxiang
 * @date 2023年02月06日
 * @Description: 网站统计
 */
@Mapper
public interface WebsiteStatisticsMapper extends BaseMapper<StatisticsDto> {
    /**
     * 总数
     * @author xiaoxiang
     * @date 2023/2/21/21 17:20:14
     * @return java.lang.String
     */
    // 评论数
    public String countComment();

    // 问题数
    public String countQuestion();

    // 用户总数
    public String countUser();

    // 类别总数
    public String countSorts();

    // 标签总数
    public String countTags();

    /**
     * 今日新增数
     * @author xiaoxiang
     * @date 2023/2/21/21 17:20:22
     * @return java.lang.String
     */

    // 新增评论
    public String countTodayComment(String todayTime);
    // 新增问题数
    public String countTodayQuestion(String todayTime);
    // 新增用户数
    public String countTodayUser(String todayTime);
    // 新增类别数
    public String countTodaySorts(String todayTime);
    // 新增标签数
    public String countTodayTags(String todayTime);
}
