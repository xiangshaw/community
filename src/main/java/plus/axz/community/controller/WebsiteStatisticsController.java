package plus.axz.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.community.mapper.WebsiteStatisticsMapper;

/**
 * @author xiaoxiang
 * @date 2023年02月06日
 * @Description:
 */
@RestController
@RequestMapping("/api/v1")
public class WebsiteStatisticsController {
    @Autowired
    private WebsiteStatisticsMapper websiteStatisticsMapper;

    // 评论
    @GetMapping("/getCountComment")
    public String getCountComment(){
        return websiteStatisticsMapper.countComment();
    }
    // 问题
    @GetMapping("/getCountQuestion")
    public String getCountQuestion(){
        return websiteStatisticsMapper.countQuestion();
    }
    // 用户
    @GetMapping("/getCountUser")
    public String getCountUser(){
        return websiteStatisticsMapper.countUser();
    }
    // 列表
    @GetMapping("/getCountSorts")
    public String getCountSorts(){
        return websiteStatisticsMapper.countSorts();
    }
    // 标签
    @GetMapping("/getCountTags")
    public String getCountTags(){
        return websiteStatisticsMapper.countTags();
    }
}
