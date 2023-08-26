package plus.axz.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import plus.axz.community.dto.BadgeDto;
import plus.axz.community.model.pojo.Badge;
import plus.axz.community.model.common.dtos.Result;
import plus.axz.community.service.BadgeService;

import java.util.List;

/**
 * @author xiaoxiang
 * @date 2022年03月29日
 * @particulars
 */
@RestController/*两个注解的意思：1(Controller)加到spingboot容器管理,2(ResponseBody)将方法的返回值转成json并响应回去*/
@RequestMapping("/api/v1/badge")/*请求映射*/
public class BadgeController {
    @Autowired
    private BadgeService badgeService;

    @PostMapping("/list")
    public Result findByNameAndPage(@RequestBody BadgeDto dto) {
        return badgeService.findByNameAndPage(dto);
    }

    @PostMapping("/save")
    public Result save(@RequestBody Badge badge) {
        return badgeService.insert(badge);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Badge badge) {
        return badgeService.update(badge);
    }

    @DeleteMapping("/del/{id}")
    public Result deleteById(@PathVariable("id") Integer id) {
        return badgeService.deleteById(id);
    }

    @GetMapping("/badges")
    public Result findAll() {
        List<Badge> list = badgeService.list();
        return Result.okResult(list);
    }

    @GetMapping("/findBadgeName/{search}")
    public Result findBadgeName(
            @PathVariable("page") @RequestParam(defaultValue = "1") Integer page,
            @PathVariable("size") @RequestParam(defaultValue = "10") Integer size,
            @PathVariable("search") @RequestParam(defaultValue = "") String search) {
        return badgeService.findBadgeName(page, size, search);
    }
}
