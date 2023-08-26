package plus.axz.community.service.Impl;

import plus.axz.community.dto.PaginationDto;
import plus.axz.community.dto.QuestionDto;
import plus.axz.community.dto.QuestionQueryDto;
import plus.axz.community.enums.SortEnum;
import plus.axz.community.enums.TopEnum;
import plus.axz.community.exception.CustomizeErrorCode;
import plus.axz.community.exception.CustomizeException;
import plus.axz.community.mapper.QuestionExtMapper;
import plus.axz.community.mapper.QuestionMapper;
import plus.axz.community.mapper.UserMapper;
import plus.axz.community.model.pojo.Question;
import plus.axz.community.model.QuestionExample;
import plus.axz.community.model.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    /**
     * 根据条件查询
     *
     * @param queryDto
     * @return
     */
    public PaginationDto<QuestionDto> findByCondition(QuestionQueryDto queryDto) {
        //将特殊字符去掉
        if (queryDto.getSearch() != null) {
            queryDto.setSearch(filterIllegal(queryDto.getSearch()));
        }
        if (queryDto.getTag() != null) {
            queryDto.setTag(filterIllegal(queryDto.getTag()));
        }
        Long time = null;
        String sort = queryDto.getSort();
        for (SortEnum sortEnum : SortEnum.values()) {
            if (sortEnum.name().toLowerCase().equals(sort)) {
                if ("hot7".equals(sort)) {
                    time = System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 7;
                } else if ("hot30".equals(sort)) {
                    time = System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 30;
                }
                queryDto.setTime(time);
                break;
            }
        }
        //根据条件获取记录数
        int totalCount = questionExtMapper.countByCondition(queryDto);
        return getPageInfo(totalCount, queryDto);
    }

    private String filterIllegal(String str) {
        return str.replace("+", "").replace("?", "").replace("*", "");
    }

    /**
     * 获取分页信息
     *
     * @param totalCount
     * @param queryDto
     * @return
     */
    public PaginationDto<QuestionDto> getPageInfo(int totalCount, QuestionQueryDto queryDto) {

        List<QuestionDto> questionDtos = new ArrayList<>();
        PaginationDto<QuestionDto> pageInfo = new PaginationDto<>();

        //如果总记录数为0，直接返回一个空数据
        if (totalCount == 0) {
            return pageInfo;
        }
        int pageNum = queryDto.getPageNum();
        int pageSize = queryDto.getPageSize();
        //设置分页信息
        pageInfo.setInfo(totalCount, pageNum, pageSize);
        //限定当前页范围
        if (pageNum < 1) {
            pageNum = 1;
        } else if (pageNum > pageInfo.getTotalPage()) {
            pageNum = pageInfo.getTotalPage();
        }

        //获取截取的位置
        int offerIndex = (pageNum - 1) * pageSize;
        queryDto.setOfferIndex(offerIndex);

        //根据条件查询分页信息
        List<Question> questions = questionExtMapper.findByCondition(queryDto);

        //先遍历获取所有的问题
        for (Question question : questions) {
            QuestionDto questionDto = new QuestionDto();
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            questionDtos.add(questionDto);
        }

        pageInfo.setList(questionDtos);

        return pageInfo;
    }

    /**
     * 根据问题id查询 问题详情
     *
     * @param id
     * @return
     */
    @Cacheable(cacheNames = "questionById", key = "'question-' + #root.args[0]")
    public QuestionDto findById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question, questionDto);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDto.setUser(user);
        return questionDto;
    }

    /**
     * 根据id是否存在，创建或修改问题
     *
     * @param question
     */
    @CacheEvict(cacheNames = "questionById", key = "'question-' + #root.args[0].getId()")
    public void createOrUpdateQuestion(Question question, User user) {
        // 判断问题ID是否为空
        if (question.getId() == null) {
            // 设置创建时间
            question.setGmtCreate(System.currentTimeMillis());
            // 设置修改时间
            question.setGmtModified(question.getGmtCreate());
            // 保存数据
            questionMapper.insertSelective(question);
        } else {
            // 根据ID查询
            Question q = questionMapper.selectByPrimaryKey(question.getId());
            // 判断创建用户 和 用户id不为空
            if (!q.getCreator().equals(user.getId())) {
                throw new CustomizeException(CustomizeErrorCode.IS_NOT_LEGAL);
            }
            // 设置修改时间
            question.setGmtModified(System.currentTimeMillis());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(question, questionExample);
            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    /**
     * 增加阅读数
     *
     * @param id
     */
    public void addViewCount(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.addViewCount(question);
    }

    /**
     * 将tags转为正则表达式后查询问题
     *
     * @param queryQuestionDto
     * @return
     */
    public List<QuestionDto> findByTags(QuestionDto queryQuestionDto) {
        if (StringUtils.isBlank(queryQuestionDto.getTags())) {
            return new ArrayList<>();
        }
        Question question = new Question();
        question.setId(queryQuestionDto.getId());
        //将，转为|
        String replaceTags = queryQuestionDto.getTags().replace("+", "").replace(",", "|");
        question.setTags(replaceTags);
        //查出所有符合表达式的question
        List<Question> questionList = questionExtMapper.findByTagsREGEXP(question);
        //赋值给questionDto
        return questionList.stream().map(q -> {
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(q, questionDto);
            return questionDto;
        }).collect(Collectors.toList());
    }

    /**
     * 设为顶置
     *
     * @param oper
     * @param id
     */
    public void setTopQuestion(int oper, Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        if (oper == TopEnum.SET_TOP.getType()) {
            question.setTop(1);
        } else {
            question.setTop(0);
        }
        questionMapper.updateByPrimaryKeySelective(question);
    }

    /**
     * 删除问题
     *
     * @param id
     */
    @CacheEvict(cacheNames = "questionById", key = "'question-' + #root.args[0]")
    public void deleteQuestion(Long id, Long userId) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question.getCreator().equals(userId)) {
            questionMapper.deleteByPrimaryKey(id);
        } else {
            throw new CustomizeException(CustomizeErrorCode.IS_NOT_LEGAL);
        }
    }
}
