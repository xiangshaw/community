package plus.axz.community.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import plus.axz.community.dto.SortDto;
import plus.axz.community.model.pojo.Sort;
import plus.axz.community.model.common.dtos.Result;
import plus.axz.community.model.vo.SortQueryVo;

import java.text.ParseException;
import java.util.List;

/**
 * @author xiaoxiang
 * @date 2023年02月15日
 * @Description:
 */
public interface SortService {
     // 新增
     public Result addSort(Sort sort) throws ParseException;
     // 删除
     public Result deleteById(String id);
     // 修改
     public Result updateSort(Sort sort);
     // 关键字查询
     public Result findSortName(Integer page, Integer size, String search);
     // 分页查
     public IPage<Sort> selectPage(Page<Sort> pageParam, SortQueryVo sortQueryVo);
     // 根据id查询
     public Result getSortId(String id);
     // 查询所有
     public List<Sort> FindAll();
     // 查询所有
     public Result QueryAll(SortDto sortDto);
     // 下拉框调用
     public Result lists();
}
