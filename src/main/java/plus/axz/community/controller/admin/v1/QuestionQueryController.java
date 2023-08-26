package plus.axz.community.controller.admin.v1;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import plus.axz.community.model.pojo.Question;
import plus.axz.community.model.common.dtos.Result;
import plus.axz.community.model.vo.QuestionQueryVo;
import plus.axz.community.service.QuestionQueryService;

import java.text.ParseException;

/**
 * @author xiaoxiang
 * @date 2023年02月18日
 * @Description: 后台问题查询
 */
@RestController
@RequestMapping("/api/v1/question/")
public class QuestionQueryController {
    @Autowired
    private QuestionQueryService questionQueryService;

    //获取分页列表
    @GetMapping("/{page}/{limit}")
    public Result selectPage(
            @PathVariable Long page,
            @PathVariable Long limit,
            QuestionQueryVo questionQueryVo) throws ParseException {
        Page<Question> pageParam = new Page<>(page, limit);
        return Result.okResult(questionQueryService.selectPage(pageParam, questionQueryVo));
    }
    // 修改文章状态
    @GetMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable("id") String id, @PathVariable("status") Boolean status) {
        return questionQueryService.updateStatus(id, status);
    }
    // 删
    @DeleteMapping("/remove/{id}")
    public Result deleteById(@PathVariable("id") String id) {
        return questionQueryService.deleteById(id);
    }
    // 修改状态
    @PostMapping("/update")
    public Result updateQuestion(@RequestBody Question question){
        return questionQueryService.updateQuestion(question);
    }
    // 置顶
    @PostMapping("/top")
    public Result topQuestion(@RequestBody Question question){
        return questionQueryService.topQuestion(question);
    }
    // 置顶Plus
    @GetMapping("/top/{id}/{top}")
    public Result topQuestionPlus(@PathVariable("id") String id, @PathVariable("top") Integer top) {
        return questionQueryService.topQuestionPlus(id, top);
    }
}
