package plus.axz.community.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plus.axz.community.dto.BadgeDto;
import plus.axz.community.mapper.BadgeMapper;
import plus.axz.community.model.pojo.Badge;
import plus.axz.community.model.common.dtos.PageResponse;
import plus.axz.community.model.common.dtos.Result;
import plus.axz.community.model.common.enums.ResultEnum;
import plus.axz.community.service.BadgeService;

import java.util.Date;
import java.util.List;

/**
 * @author xiaoxiang
 * @date 2022年03月29日
 * @particulars
 */
@Service
public class BadgeServiceImpl extends ServiceImpl<BadgeMapper, Badge> implements BadgeService {
    @Autowired
    private BadgeMapper badgeMapper;

    @Override
    public Result findByNameAndPage(BadgeDto dto) {
        // 1.检查参数
        if (dto == null){
            return Result.errorResult(ResultEnum.PARAM_INVALID,"徽章不存在");
        }
        // 2. 分页检查
        dto.checkParam();
        // 3.模糊分页查询
        // 当前页-每页条数
        Page page = new Page(dto.getPage(), dto.getSize());
        // 泛型
        LambdaQueryWrapper<Badge> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(dto.getBadgeName())){
            lambdaQueryWrapper.like(Badge::getBadgeName,dto.getBadgeName());
        }
        Page result = page(page, lambdaQueryWrapper);
        PageResponse pageResult = new PageResponse(dto.getPage(), dto.getSize(), (int) result.getTotal());
        pageResult.setData(result.getRecords());
        return pageResult;
    }

    @Override
    public Result insert(Badge badge) {
        // 1.检查参数
        if (badge==null){
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 查询数据库信息
        List<Badge> list = list(Wrappers.<Badge>lambdaQuery().eq(Badge::getBadgeName, badge.getBadgeName()));
        if (list != null && list.size() ==1){
            return Result.errorResult(ResultEnum.DATA_EXIST);
        }
        badge.setCreateTime(new Date());
        save(badge);
        return Result.okResult(ResultEnum.SUCCESS);
    }

    @Override
    public Result update(Badge badge) {
        // 1. 检查参数
        if (badge == null && badge.getId() == null){
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        badge.setUpdateTime(new Date());
        updateById(badge);
        return Result.okResult(ResultEnum.SUCCESS);
    }

    @Override
    public Result deleteById(Integer id) {
        // 1.检查参数
        if (id == null){
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 判断
        Badge badge = getById(id);
        if (badge == null){
            return  Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        removeById(id);
        return Result.okResult(ResultEnum.SUCCESS);
    }

    @Override
    public Result findBadgeName(Integer page, Integer size, String search) {
        if (search == null){
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        LambdaQueryWrapper<Badge> badgeLambdaQueryWrapper = Wrappers.<Badge>lambdaQuery();
        if (StringUtils.isNotBlank(search)){
            badgeLambdaQueryWrapper.like(Badge::getBadgeName,search);
            return Result.okResult(badgeMapper.selectPage(new Page(page,size),badgeLambdaQueryWrapper));
        }
        return Result.errorResult(ResultEnum.DATA_NOT_EXIST,"未搜索到该数据~");
    }
}
