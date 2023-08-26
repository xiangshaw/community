package plus.axz.community.schedule;

import plus.axz.community.cache.HotTagCache;
import plus.axz.community.mapper.QuestionMapper;
import plus.axz.community.model.pojo.Question;
import plus.axz.community.model.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class HotTagTask {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate = 1000 * 60 * 60 * 4) //四个小时
    public void hotTagSchedule(){
        log.info("开始时间：{}", new Date());
        int offset = 0;
        int limit = 5;
        List<Question> list = new ArrayList<>();
        Map<String, Integer> priorities = new HashMap<>();
        while(offset == 0 || list.size() == limit){
            list = questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(), new RowBounds(offset, limit));
            for (Question question : list) {
                log.info("question id : {}", question.getId());
                String[] splitTags = StringUtils.split(question.getTags(), ",");
                for (String tag : splitTags) {
                    if(priorities.get(tag) != null){
                        priorities.put(tag, priorities.get(tag) + 5 + question.getCommentCount());
                    }else{
                        priorities.put(tag, 5 + question.getCommentCount());
                    }
                }
            }
            offset += limit;
        }
        hotTagCache.updateHotTag(priorities);
        log.info("结束时间：{}", new Date());
    }
}
