package plus.axz.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.axz.community.dto.BadgeDto;
import plus.axz.community.model.pojo.Badge;
import plus.axz.community.model.common.dtos.Result;

/**
 * @author xiaoxiang
 * @date 2022年03月29日
 * @particulars
 */
public interface BadgeService extends IService<Badge> {
    /**
     * 根据名称分页查询徽章列表
     */
    public Result findByNameAndPage(BadgeDto dto);
    /**
     * 新增
     */
    public Result insert(Badge badge);
    /**
     * 修改
     */
    public Result update(Badge badge);
    /**
     * 删除
     */
    public Result deleteById(Integer id);
    // 关键字查询
    public Result findBadgeName(Integer page, Integer size, String search);
}
