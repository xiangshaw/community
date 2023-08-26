package plus.axz.community.mapper;

import plus.axz.community.dto.QuestionQueryDto;
import plus.axz.community.model.pojo.Question;

import java.util.List;

public interface QuestionExtMapper {
    void addViewCount(Question question);

    void addCommentCount(Question question);

    List<Question> findByTagsREGEXP(Question question);

    int countByCondition(QuestionQueryDto questionQuery);

    List<Question> findByCondition(QuestionQueryDto questionQuery);
}