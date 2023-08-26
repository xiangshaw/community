package plus.axz.community.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import plus.axz.community.model.pojo.Tag;
import plus.axz.community.model.common.dtos.Result;
import plus.axz.community.model.vo.SortTagVo;
import plus.axz.community.model.vo.TagQueryVo;

import java.text.ParseException;
import java.util.List;

/**
 * @author xiaoxiang
 * @date 2023年02月15日
 * @Description: 标签服务类
 */
public interface TagService {
    // 新增
    public Result addTag(Tag tag) throws ParseException;
    // 删除
    public Result deleteById(String id);
    // 批量删除
    // json数组格式 ---对应---Java的list集合
    Result batchRemove(List<String> ids);
    // 修改
    public Result updateTag(Tag tag);
    // 关键字查询
    public Result findTagName(Integer page, Integer size, String search);
    // 分页查
    public IPage<Tag> selectPage(Page<Tag> pageParam, TagQueryVo TagQueryVo);
    // 根据id查询
    public Result getTagId(String id);
    // 查询所有
    public List<Tag> FindAll();
    // 根据 类别名称查询 标签
    public List<SortTagVo> getSortByTag(String sortName);
}
