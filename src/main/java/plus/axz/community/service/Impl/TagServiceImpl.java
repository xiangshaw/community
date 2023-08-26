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
import plus.axz.community.mapper.TagMapper;
import plus.axz.community.model.pojo.Tag;
import plus.axz.community.model.common.dtos.Result;
import plus.axz.community.model.common.enums.ResultEnum;
import plus.axz.community.model.vo.SortTagVo;
import plus.axz.community.model.vo.TagQueryVo;
import plus.axz.community.service.TagService;

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
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public Result addTag(Tag tag) throws ParseException {
        // 1.参数检验
        if (tag == null) {
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.判断是否添加
        List<Tag> list = list(Wrappers.lambdaQuery(Tag.class).eq(Tag::getTagName, tag.getTagName()));
        if (list != null && list.size() == 1) {
            return Result.errorResult(ResultEnum.DATA_EXIST);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String x = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        long time = simpleDateFormat.parse(x).getTime();
        tag.setGmtCreate(time);
        save(tag);
        return Result.okResult(ResultEnum.SUCCESS);
    }

    @Override
    public Result updateTag(Tag tag) {
        // 1.检查参数
        if (tag == null && tag.getId() == null) {
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        updateById(tag);
        return Result.okResult(ResultEnum.SUCCESS);
    }

    @Override
    public Result deleteById(String id) {
        // 1.检查参数
        if (id == null) {
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.判断是否存在
        Tag tag = getById(id);
        if (tag == null) {
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        removeById(id);
        return Result.okResult(ResultEnum.SUCCESS);
    }

    @Override
    public Result batchRemove(List<String> ids) {
        // 1.检查参数
        if (ids ==null){
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        // 2.判断是否存在
        List<Tag> tagList = listByIds(ids);
        if (tagList == null){
            return Result.errorResult(ResultEnum.DATA_NOT_EXIST);
        }
        removeByIds(ids);
        return Result.okResult(ResultEnum.SUCCESS);
    }

    @Override
    public Result findTagName(Integer page, Integer size, String search) {
        if (search == null) {
            return Result.errorResult(ResultEnum.PARAM_INVALID);
        }
        LambdaQueryWrapper<Tag> tagLambdaQueryWrapper = Wrappers.<Tag>lambdaQuery();
        if (StringUtils.isNotBlank(search)) {
            tagLambdaQueryWrapper.like(Tag::getTagName, search);
            return Result.okResult(tagMapper.selectPage(new Page(page, size), tagLambdaQueryWrapper));
        }
        return Result.errorResult(ResultEnum.PARAM_INVALID, "未搜索到该数据~");
    }

    @Override
    public IPage<Tag> selectPage(Page<Tag> pageParam, TagQueryVo tagQueryVo) {
        // 调用mapper方法实现
        return tagMapper.selectPages(pageParam, tagQueryVo);
    }

    @Override
    public Result getTagId(String id) {
        Tag tag = getById(id);
        if (tag == null) {
            return Result.errorResult(ResultEnum.DATA_NOT_EXIST);
        }
        return Result.okResult(tag);
    }

    @Override
    public List<Tag> FindAll() {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.select("tag_name");
        return tagMapper.selectList(wrapper);
    }

    @Override
    public List<SortTagVo> getSortByTag(String sortName) {
        return tagMapper.findBySortName(sortName);
    }
}
