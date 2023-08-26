package plus.axz.community.dto;

import lombok.Data;

/**
 * @author xiaoxiang
 * @date 2023年02月06日
 * @Description: 网站信息统计 WebsiteStatistics
 */
@Data
public class StatisticsDto {

    // 评论数量统计
    private String commentStatistics;
    // 问题数量统计
    private String questionStatistics;
    // 网站用户数量统计
    private String userStatistics;

}
