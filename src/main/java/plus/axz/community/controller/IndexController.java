package plus.axz.community.controller;

import plus.axz.community.cache.HotTagCache;
import plus.axz.community.dto.PaginationDto;
import plus.axz.community.dto.QuestionDto;
import plus.axz.community.dto.QuestionQueryDto;
import plus.axz.community.mapper.WebsiteStatisticsMapper;
import plus.axz.community.model.pojo.Badge;
import plus.axz.community.service.BadgeService;
import plus.axz.community.service.Impl.NotificationServiceImpl;
import plus.axz.community.service.Impl.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private QuestionServiceImpl questionService;

    @Autowired
    private NotificationServiceImpl notificationService;

    @Autowired
    private HotTagCache hotTagCache;

    @Autowired
    private WebsiteStatisticsMapper websiteStatisticsMapper;
    @Autowired
    private BadgeService badgeService;

    @GetMapping("/")
    public String test(Model model,
                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                       @RequestParam(value = "search", required = false) String search,
                       @RequestParam(value = "tag", required = false) String tag,
                       @RequestParam(value = "sort", required = false) String sort) {
        QuestionQueryDto queryDto = QuestionQueryDto.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .search(search)
                .tag(tag)
                .sort(sort)
                .build();
        // 评论
        String countComment = websiteStatisticsMapper.countComment();
        // 问题
        String countQuestion = websiteStatisticsMapper.countQuestion();
        // 用户
        String countUser = websiteStatisticsMapper.countUser();
        // 类别
        String countSorts = websiteStatisticsMapper.countSorts();
        // 标签
        String countTags = websiteStatisticsMapper.countTags();

        // 获取徽章
        List<Badge> badges = badgeService.list();

        PaginationDto<QuestionDto> pageInfo = questionService.findByCondition(queryDto);
        List<String> topTags = hotTagCache.getTopTags();
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("search", search);
        model.addAttribute("tag", tag);
        model.addAttribute("sortType", queryDto.getSort());
        model.addAttribute("hotTags", topTags);
        model.addAttribute("countComment", countComment);
        model.addAttribute("countQuestion", countQuestion);
        model.addAttribute("countUser", countUser);
        model.addAttribute("countSorts", countSorts);
        model.addAttribute("countTags", countTags);
        model.addAttribute("badges", badges);
        return "index";
    }
}
