package plus.axz.community.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plus.axz.community.dto.SortDto;
import plus.axz.community.mapper.SortMapper;
import plus.axz.community.model.pojo.Sort;
import plus.axz.community.model.common.dtos.PageResponse;
import plus.axz.community.model.common.dtos.Result;
import plus.axz.community.model.common.enums.ResultEnum;
import plus.axz.community.model.vo.SortQueryVo;
import plus.axz.community.service.SortService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author xiaoxiang
 * @date 2023年02月15日
 * @Description:
 */
@Service
public class SortServiceImpl extends ServiceImpl<SortMapper, Sort> implements SortService {
    @Autowired
    private SortMapper sortMapper;
    @Override
    public Result addSort(Sort sort) throws ParseException {
        // 1.参数检验
        if (sort == null){
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.判断是否添加
        List<Sort> list = list(Wrappers.lambdaQuery(Sort.class).eq(Sort::getSortName, sort.getSortName()));
        if (list != null && list.size() ==1){
            return Result.errorResult(ResultEnum.DATA_EXIST);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String x = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        long time = simpleDateFormat.parse(x).getTime();
        sort.setGmtCreate(time);
        save(sort);
        return Result.okResult(ResultEnum.SUCCESS);
    }

    @Override
    public Result updateSort(Sort sort) {
        // 1.检查参数
        if (sort == null && sort.getId() == null ){
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        updateById(sort);
        return Result.okResult(ResultEnum.SUCCESS);
    }

    @Override
    public Result deleteById(String id) {
        // 1.检查参数
        if (id == null){
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.判断是否存在
        Sort sort = getById(id);
        if (sort == null){
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        removeById(id);
        return Result.okResult(ResultEnum.SUCCESS);
    }

    @Override
    public Result findSortName(Integer page, Integer size, String search) {
        if (search == null){
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        LambdaQueryWrapper<Sort> sortLambdaQueryWrapper = Wrappers.<Sort>lambdaQuery();
        if (StringUtils.isNotBlank(search)){
            sortLambdaQueryWrapper.like(Sort::getSortName,search);
            return Result.okResult(sortMapper.selectPage(new Page(page, size), sortLambdaQueryWrapper));
        }
        return Result.errorResult(ResultEnum.PARAM_INVALID,"未搜索到该数据~");
    }

    @Override
    public IPage<Sort> selectPage(Page<Sort> pageParam, SortQueryVo sortQueryVo) {
        // 调用mapper方法实现
        return sortMapper.selectPages(pageParam, sortQueryVo);
    }

    @Override
    public Result getSortId(String id) {
        Sort sort = getById(id);
        if (sort == null){
            return Result.errorResult(ResultEnum.DATA_NOT_EXIST);
        }
        return Result.okResult(sort);
    }

    @Override
    public List<Sort> FindAll() {
        QueryWrapper<Sort> wrapper = new QueryWrapper<>();
        wrapper.select("sort_name");
        return sortMapper.selectList(wrapper);
    }

    // 查询所有
    @Override
    public Result QueryAll(SortDto sortDto) {
        if (sortDto == null){
            return Result.errorResult(ResultEnum.PARAM_INVALID,"类别不存在");
        }
        sortDto.checkParam();
        Page page = new Page(sortDto.getPage(), sortDto.getSize());
        LambdaQueryWrapper<Sort> sortLambdaQueryWrapper = Wrappers.<Sort>lambdaQuery();
        if (StringUtils.isNotBlank(sortDto.getSortName())){
            sortLambdaQueryWrapper.like(Sort::getSortName,sortDto.getSortName());
        }
        Page result = page(page, sortLambdaQueryWrapper);
        PageResponse pageResponse = new PageResponse(sortDto.getPage(), sortDto.getSize(), result.getRecords().size());
        pageResponse.setData(result.getRecords());
        return pageResponse;
    }

    @Override
    public Result lists() {
        List<Sort> sorts = sortMapper.selectList(Wrappers.lambdaQuery(Sort.class));
        return Result.okResult(sorts);
    }
}
