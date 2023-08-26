package plus.axz.community.controller.admin.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.axz.community.mapper.WebsiteStatisticsMapper;
import plus.axz.community.model.common.dtos.Result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author xiaoxiang
 * @date 2023年02月20日
 * @Description: 后台图表数据
 */
@RestController
@RequestMapping("/api/v1/aduser/")
public class ChartController {
    @Autowired
    private WebsiteStatisticsMapper websiteStatisticsMapper;

    @GetMapping("/charts")
    public Result getChartsTotal() {
        /**
         * 总数
         * @author xiaoxiang
         * @date 2023/2/21/0021 15:21:34
         * @return plus.axz.community.model.common.dtos.Result
         */
        // 用户
        String countUser = websiteStatisticsMapper.countUser();
        // 问题
        String countQuestion = websiteStatisticsMapper.countQuestion();
        // 评论
        String countComment = websiteStatisticsMapper.countComment();
        // 类别
        String countSorts = websiteStatisticsMapper.countSorts();
        // 标签
        String countTags = websiteStatisticsMapper.countTags();
        return fiveBitArray(countUser, countQuestion, countComment, countSorts, countTags);
    }

    // 今日数据
    @GetMapping("/todays/charts")
    public Result getTodayChartsTotal() throws ParseException {
        /**
         * 今日新增
         * @author xiaoxiang
         * @date 2023/2/21/0021 15:21:46
         * @return plus.axz.community.model.common.dtos.Result
         */
        // 指定格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 获取昨日时间戳
        Date yesterday = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        String s = simpleDateFormat.format(yesterday);
        long yesterDayTime = simpleDateFormat.parse(s).getTime();
        String countTodayUser = websiteStatisticsMapper.countTodayUser(String.valueOf(yesterDayTime));
        String countTodayQuestion = websiteStatisticsMapper.countTodayQuestion(String.valueOf(yesterDayTime));
        String countTodayComment = websiteStatisticsMapper.countTodayComment(String.valueOf(yesterDayTime));
        String countTodaySorts = websiteStatisticsMapper.countTodaySorts(String.valueOf(yesterDayTime));
        String countTodayTags = websiteStatisticsMapper.countTodayTags(String.valueOf(yesterDayTime));
        return fiveBitArray(countTodayUser, countTodayQuestion, countTodayComment, countTodaySorts, countTodayTags);
    }

    // 今日数据
    @GetMapping("/week/charts")
    public Result getWeekChartsTotal() throws ParseException {
        /**
         * 一周数据新增
         * @author xiaoxiang
         * @date 2023/2/21/0021 15:21:46
         * @return plus.axz.community.model.common.dtos.Result
         */
        // 指定格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 获取昨日时间戳
        Date yesterday = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        String s = simpleDateFormat.format(yesterday);
        long yesterDayTime = simpleDateFormat.parse(s).getTime();
        // 前天
        Date anteayer = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 2);
        String s1 = simpleDateFormat.format(anteayer);
        long anteayerTime = simpleDateFormat.parse(s1).getTime();
        // 大前天
        Date signaling = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 3);
        String s2 = simpleDateFormat.format(signaling);
        long signalingTime = simpleDateFormat.parse(s2).getTime();
        // 前三天
        Date firstThree = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 4);
        String s3 = simpleDateFormat.format(firstThree);
        long firstThreeTime = simpleDateFormat.parse(s3).getTime();
        // 前四天
        Date firstFour = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 5);
        String s4 = simpleDateFormat.format(firstFour);
        long firstFourTime = simpleDateFormat.parse(s4).getTime();
        // 前五天
        Date firstFive = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 6);
        String s5 = simpleDateFormat.format(firstFive);
        long firstFiveTime = simpleDateFormat.parse(s5).getTime();
        // 前六天
        Date firstSix = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 7);
        String s6 = simpleDateFormat.format(firstSix);
        long firstSixTime = simpleDateFormat.parse(s6).getTime();


        // 查询昨天 Today
        String countTodayUser = websiteStatisticsMapper.countTodayUser(String.valueOf(yesterDayTime));
        String countTodayQuestion = websiteStatisticsMapper.countTodayQuestion(String.valueOf(yesterDayTime));
        String countTodayComment = websiteStatisticsMapper.countTodayComment(String.valueOf(yesterDayTime));
        String countTodaySorts = websiteStatisticsMapper.countTodaySorts(String.valueOf(yesterDayTime));
        String countTodayTags = websiteStatisticsMapper.countTodayTags(String.valueOf(yesterDayTime));
        // 查询前天 AnteayerTime
        String countAnteayerUser = websiteStatisticsMapper.countTodayUser(String.valueOf(anteayerTime));
        String countAnteayerQuestion = websiteStatisticsMapper.countTodayQuestion(String.valueOf(anteayerTime));
        String countAnteayerComment = websiteStatisticsMapper.countTodayComment(String.valueOf(anteayerTime));
        String countAnteayerSorts = websiteStatisticsMapper.countTodaySorts(String.valueOf(anteayerTime));
        String countAnteayerTags = websiteStatisticsMapper.countTodayTags(String.valueOf(anteayerTime));
        // 查询大前天 Signaling
        String countSignalingUser = websiteStatisticsMapper.countTodayUser(String.valueOf(signalingTime));
        String countSignalingQuestion = websiteStatisticsMapper.countTodayQuestion(String.valueOf(signalingTime));
        String countSignalingComment = websiteStatisticsMapper.countTodayComment(String.valueOf(signalingTime));
        String countSignalingSorts = websiteStatisticsMapper.countTodaySorts(String.valueOf(signalingTime));
        String countSignalingTags = websiteStatisticsMapper.countTodayTags(String.valueOf(signalingTime));
        // 查询前三天 FirstThree
        String countFirstThreeUser = websiteStatisticsMapper.countTodayUser(String.valueOf(firstThreeTime));
        String countFirstThreeQuestion = websiteStatisticsMapper.countTodayQuestion(String.valueOf(firstThreeTime));
        String countFirstThreeComment = websiteStatisticsMapper.countTodayComment(String.valueOf(firstThreeTime));
        String countFirstThreeSorts = websiteStatisticsMapper.countTodaySorts(String.valueOf(firstThreeTime));
        String countFirstThreeTags = websiteStatisticsMapper.countTodayTags(String.valueOf(firstThreeTime));
        // 查询前四天 FirstFour
        String countFirstFourUser = websiteStatisticsMapper.countTodayUser(String.valueOf(firstFourTime));
        String countFirstFourQuestion = websiteStatisticsMapper.countTodayQuestion(String.valueOf(firstFourTime));
        String countFirstFourComment = websiteStatisticsMapper.countTodayComment(String.valueOf(firstFourTime));
        String countFirstFourSorts = websiteStatisticsMapper.countTodaySorts(String.valueOf(firstFourTime));
        String countFirstFourTags = websiteStatisticsMapper.countTodayTags(String.valueOf(firstFourTime));
        // 查询前五天 FirstFive
        String countFirstFiveUser = websiteStatisticsMapper.countTodayUser(String.valueOf(firstFiveTime));
        String countFirstFiveQuestion = websiteStatisticsMapper.countTodayQuestion(String.valueOf(firstFiveTime));
        String countFirstFiveComment = websiteStatisticsMapper.countTodayComment(String.valueOf(firstFiveTime));
        String countFirstFiveSorts = websiteStatisticsMapper.countTodaySorts(String.valueOf(firstFiveTime));
        String countFirstFiveTags = websiteStatisticsMapper.countTodayTags(String.valueOf(firstFiveTime));
        // 查询前六天 FirstSix
        String countFirstSixUser = websiteStatisticsMapper.countTodayUser(String.valueOf(firstSixTime));
        String countFirstSixQuestion = websiteStatisticsMapper.countTodayQuestion(String.valueOf(firstSixTime));
        String countFirstSixComment = websiteStatisticsMapper.countTodayComment(String.valueOf(firstSixTime));
        String countFirstSixSorts = websiteStatisticsMapper.countTodaySorts(String.valueOf(firstSixTime));
        String countFirstSixTags = websiteStatisticsMapper.countTodayTags(String.valueOf(firstSixTime));

        // 昨日
        Integer[] i = new Integer[5];
        i[0] = Integer.valueOf(countTodayUser);
        i[1] = Integer.valueOf(countTodayQuestion);
        i[2] = Integer.valueOf(countTodayComment);
        i[3] = Integer.valueOf(countTodaySorts);
        i[4] = Integer.valueOf(countTodayTags);
        // 查询前天 AnteayerTime
        Integer[] i1 = new Integer[5];
        i1[0] = Integer.valueOf(countAnteayerUser);
        i1[1] = Integer.valueOf(countAnteayerQuestion);
        i1[2] = Integer.valueOf(countAnteayerComment);
        i1[3] = Integer.valueOf(countAnteayerSorts);
        i1[4] = Integer.valueOf(countAnteayerTags);
        // 查询大前天 Signaling
        Integer[] i2 = new Integer[5];
        i2[0] = Integer.valueOf(countSignalingUser);
        i2[1] = Integer.valueOf(countSignalingQuestion);
        i2[2] = Integer.valueOf(countSignalingComment);
        i2[3] = Integer.valueOf(countSignalingSorts);
        i2[4] = Integer.valueOf(countSignalingTags);
        // 查询前三天 FirstThree
        Integer[] i3 = new Integer[5];
        i3[0] = Integer.valueOf(countFirstThreeUser);
        i3[1] = Integer.valueOf(countFirstThreeQuestion);
        i3[2] = Integer.valueOf(countFirstThreeComment);
        i3[3] = Integer.valueOf(countFirstThreeSorts);
        i3[4] = Integer.valueOf(countFirstThreeTags);
        // 查询前四天 FirstFour
        Integer[] i4 = new Integer[5];
        i4[0] = Integer.valueOf(countFirstFourUser);
        i4[1] = Integer.valueOf(countFirstFourQuestion);
        i4[2] = Integer.valueOf(countFirstFourComment);
        i4[3] = Integer.valueOf(countFirstFourSorts);
        i4[4] = Integer.valueOf(countFirstFourTags);
        // 查询前五天 FirstFive
        Integer[] i5 = new Integer[5];
        i5[0] = Integer.valueOf(countFirstFiveUser);
        i5[1] = Integer.valueOf(countFirstFiveQuestion);
        i5[2] = Integer.valueOf(countFirstFiveComment);
        i5[3] = Integer.valueOf(countFirstFiveSorts);
        i5[4] = Integer.valueOf(countFirstFiveTags);
        // 查询前六天 FirstSix
        Integer[] i6 = new Integer[5];
        i6[0] = Integer.valueOf(countFirstSixUser);
        i6[1] = Integer.valueOf(countFirstSixQuestion);
        i6[2] = Integer.valueOf(countFirstSixComment);
        i6[3] = Integer.valueOf(countFirstSixSorts);
        i6[4] = Integer.valueOf(countFirstSixTags);
        // i i1 i2 i3 i4 i5 i6
        ArrayList<Integer[]> list = new ArrayList<>();
        list.add(i);
        list.add(i1);
        list.add(i2);
        list.add(i3);
        list.add(i4);
        list.add(i5);
        list.add(i6);
        return Result.okResult(list);
    }

    // 抽出五位数组
    private Result fiveBitArray(String countTodayUser, String countTodayQuestion, String countTodayComment, String countTodaySorts, String countTodayTags) {
        Integer[] integers = new Integer[5];
        integers[0] = Integer.valueOf(countTodayUser);
        integers[1] = Integer.valueOf(countTodayQuestion);
        integers[2] = Integer.valueOf(countTodayComment);
        integers[3] = Integer.valueOf(countTodaySorts);
        integers[4] = Integer.valueOf(countTodayTags);
        return Result.okResult(integers);
    }

}
