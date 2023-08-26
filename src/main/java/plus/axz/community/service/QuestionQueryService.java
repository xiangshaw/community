package plus.axz.community.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.community.model.pojo.Question;
import plus.axz.community.model.common.dtos.Result;
import plus.axz.community.model.vo.QuestionQueryVo;

import java.text.ParseException;
import java.util.List;

/**
 * @author xiaoxiang
 * @date 2023年02月18日
 * @Description: 提问
 */
public interface QuestionQueryService extends IService<Question> {
    // 分页条件查询问题
    // 分页条件查询
    IPage<Question> selectPage(Page<Question> pageParam, QuestionQueryVo questionQueryVo) throws ParseException;
    // 修改状态
    public Result updateStatus(String id, Boolean status);
    // 删除
    public Result deleteById(String id);
    // 修改
    public Result updateQuestion(Question question);
    // 置顶文章
    public Result topQuestion(Question question);

    // 置顶文章 plus
    public Result topQuestionPlus(String id, Integer top);

    // 根据用户id查询该用户所有的文章id数据
    List<Long> getQuestionById(String userId);
    // 批量删除评论
    public Result batchRemove(List<Long> ids);
}
