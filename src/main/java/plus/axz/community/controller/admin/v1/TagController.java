package plus.axz.community.controller.admin.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import plus.axz.community.model.pojo.Tag;
import plus.axz.community.model.common.dtos.Result;
import plus.axz.community.model.vo.SortTagVo;
import plus.axz.community.model.vo.TagQueryVo;
import plus.axz.community.service.TagService;

import java.text.ParseException;
import java.util.List;

/**
 * @author xiaoxiang
 * @date 2023年02月15日
 * @Description: 标签控制器
 */
@RestController/*两个注解的意思：1(Controller)加到spingboot容器管理,2(ResponseBody)将方法的返回值转成json并响应回去*/
@RequestMapping("/api/v1/tag")/*请求映射*/
public class TagController {
    @Autowired
    private TagService tagService;

    // 增
    @PostMapping("/save")
    public Result addTag(@RequestBody Tag tag) throws ParseException {
        return tagService.addTag(tag);
    }

    // 删
    @DeleteMapping("/remove/{id}")
    public Result deleteById(@PathVariable("id") String id) {
        return tagService.deleteById(id);
    }

    // 批量删除
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<String> ids) {
        return tagService.batchRemove(ids);
    }

    // 改
    @PutMapping("/update")
    public Result updateTag(@RequestBody Tag tag) {
        return tagService.updateTag(tag);
    }

    // 分页查
    @GetMapping("/{page}/{limit}")
    public Result selectPages(@PathVariable Long page,
                              @PathVariable Long limit,
                              TagQueryVo tagQueryVo) {
        Page<Tag> pageParam = new Page<>(page, limit);
        IPage<Tag> tagIPage = tagService.selectPage(pageParam, tagQueryVo);
        return Result.okResult(tagIPage);
    }

    // 关键词搜索
    @GetMapping("/findTagName/{search}")
    public Result findTagName(@PathVariable("page") @RequestParam(defaultValue = "1") Integer page,
                               @PathVariable("size") @RequestParam(defaultValue = "10") Integer size,
                               @PathVariable("search") @RequestParam("") String search) {
        return tagService.findTagName(page, size, search);
    }

    @GetMapping("/{id}")
    public Result getTagId(@PathVariable("id") String id){
        return tagService.getTagId(id);
    }

    @GetMapping("/all")
    public List<Tag> getAll(){
        return tagService.FindAll();
    }

    @GetMapping("/st/{sortName}")
    public List<SortTagVo> getSortByTag(@PathVariable("sortName") String sortName){
        return tagService.getSortByTag(sortName);
    }
}
