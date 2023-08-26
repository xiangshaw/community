package plus.axz.community.controller.admin.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import plus.axz.community.dto.SortDto;
import plus.axz.community.model.pojo.Sort;
import plus.axz.community.model.common.dtos.Result;
import plus.axz.community.model.vo.SortQueryVo;
import plus.axz.community.service.SortService;

import java.text.ParseException;
import java.util.List;

/**
 * @author xiaoxiang
 * @date 2023年02月15日
 * @Description: 类别控制器
 */
@RestController/*两个注解的意思：1(Controller)加到spingboot容器管理,2(ResponseBody)将方法的返回值转成json并响应回去*/
@RequestMapping("/api/v1/sort")/*请求映射*/
public class SortController {
    @Autowired
    private SortService sortService;

    // 增
    @PostMapping("/save")
    public Result addSort(@RequestBody Sort sort) throws ParseException {
        return sortService.addSort(sort);
    }

    // 删
    @DeleteMapping("/remove/{id}")
    public Result deleteById(@PathVariable("id") String id) {
        return sortService.deleteById(id);
    }

    // 改
    @PutMapping("/update")
    public Result updateSort(@RequestBody Sort sort) {
        return sortService.updateSort(sort);
    }

    // 分页查
    @GetMapping("/{page}/{limit}")
    public Result selectPages(@PathVariable Long page,
                              @PathVariable Long limit,
                              SortQueryVo sortQueryVo) {
        Page<Sort> pageParam = new Page<>(page, limit);
        IPage<Sort> sortIPage = sortService.selectPage(pageParam, sortQueryVo);
        return Result.okResult(sortIPage);
    }

    // 关键词搜索
    @GetMapping("/findSortName/{search}")
    public Result findSortName(@PathVariable("page") @RequestParam(defaultValue = "1") Integer page,
                               @PathVariable("size") @RequestParam(defaultValue = "10") Integer size,
                               @PathVariable("search") @RequestParam("") String search) {
        return sortService.findSortName(page, size, search);
    }

    @GetMapping("/{id}")
    public Result getSortId(@PathVariable("id") String id){
        return sortService.getSortId(id);
    }

    @GetMapping("/all")
    public List<Sort> getAll(){
        return sortService.FindAll();
    }
    // 查询所有
    @PostMapping("/list")
    public Result QueryAll(@RequestBody SortDto sortDto) {
        return sortService.QueryAll(sortDto);
    }
    @GetMapping("/lists")
    public Result lists(){
       return sortService.lists();
    }
}
