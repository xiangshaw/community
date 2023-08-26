package plus.axz.community.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plus.axz.community.mapper.QuestionQueryMapper;
import plus.axz.community.model.pojo.Question;
import plus.axz.community.model.common.dtos.Result;
import plus.axz.community.model.common.enums.ResultEnum;
import plus.axz.community.model.vo.QuestionQueryVo;
import plus.axz.community.service.QuestionQueryService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoxiang
 * @date 2023年02月18日
 * @Description: 问题模块
 */
@Service
public class QuestionQueryServiceImpl extends ServiceImpl<QuestionQueryMapper, Question> implements QuestionQueryService {

    @Autowired
    private QuestionQueryMapper questionQueryMapper;

    @Override
    public IPage<Question> selectPage(Page<Question> pageParam, QuestionQueryVo questionQueryVo) throws ParseException {
        if (questionQueryVo.getGmtCreate() == null && questionQueryVo.getGmtModified() == null){
            return questionQueryMapper.selectPage(pageParam, questionQueryVo);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 先把收到的 String格式日期 转换为 DATE类型 再format转换为指定格式
        long gmtCreate = simpleDateFormat.parse(questionQueryVo.getGmtCreate()).getTime();
        questionQueryVo.setGmtCreate(String.valueOf(gmtCreate));
        long gmtModified = simpleDateFormat.parse(questionQueryVo.getGmtModified()).getTime();
        questionQueryVo.setGmtModified(String.valueOf(gmtModified));
        return questionQueryMapper.selectPage(pageParam, questionQueryVo);
    }

    @Override
    public Result updateStatus(String id, Boolean status) {
        // 1.检查参数
        if (id == null) {
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.根据id查询
        Question question = getById(id);
        // 3.设置状态值
        question.setStatus(status);
        // 4.修改
        updateById(question);
        return Result.okResult(ResultEnum.SUCCESS);
    }

    @Override
    public Result deleteById(String id) {
        // 1.检查参数
        if (id == null) {
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.判断类别是否存在 是否有效
        Question question = getById(id);
        if (question == null) {
            return Result.errorResult(ResultEnum.DATA_NOT_EXIST);
        }
        // 2.1再查是否有效
        if (question.getStatus()) {
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 3.删除问题
        removeById(id);
        return Result.okResult(ResultEnum.SUCCESS);
    }

    @Override
    public Result updateQuestion(Question question) {
        if (question == null) {
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        UpdateWrapper<Question> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", question.getId());
        wrapper.set("status", question.getStatus());
        return Result.okResult(questionQueryMapper.update(null, wrapper));
    }

    @Override
    public Result topQuestion(Question question) {
        if (question == null) {
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        UpdateWrapper<Question> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", question.getId());
        wrapper.set("top", question.getTop());
        return Result.okResult(questionQueryMapper.update(null, wrapper));
    }

    @Override
    public Result topQuestionPlus(String id, Integer top) {
        // 1.检查参数
        if (id == null && top > 0 && top < 3) {
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.根据id查询
        Question question = getById(id);
        // 3.设置状态值
        question.setTop(top);
        // 4.修改
        updateById(question);
        return Result.okResult(ResultEnum.SUCCESS);
    }

    // 根据用户id查询该用户所有的文章id数据
    @Override
    public List<Long> getQuestionById(String userId) {
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.select("id");
        wrapper.eq("creator",userId);
        wrapper.eq("status",0);
        wrapper.eq("is_deleted",0);
        List<Question> questions = baseMapper.selectList(wrapper);
        // 将查询到的id数据放入数组中
        List<Long> ids = new ArrayList<>();
        for (Question question : questions) {
            ids.add(question.getId());
        }
        return ids;
    }

    // 批量删除评论
    @Override
    public Result batchRemove(List<Long> ids) {
        if (ids == null){
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        List<Question> questions = listByIds(ids);
        if (questions == null){
            return Result.errorResult(ResultEnum.DATA_NOT_EXIST);
        }
        // 删除并检查结果
        boolean b = removeByIds(ids);
        if (b){
            return Result.okResult(ResultEnum.SUCCESS);
        }
        return Result.errorResult(ResultEnum.FAIL);
    }
}
