package plus.axz.community.model.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaoxiang
 * @date 2022年11月13日
 * @Description: 类别查询
 */
public class SortQueryVo{
    @ApiModelProperty(name = "sortName", value = "类别名称", required = false)
    private String sortName;

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }
}