package plus.axz.community.model.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaoxiang
 * @date 2022年11月13日
 * @Description: 类别查询
 */
public class SortTagVo {
    /*@ApiModelProperty(name = "sortName", value = "类别名称", required = false)
    private String sortName;*/
    @ApiModelProperty(name = "tagName", value = "标签名称", required = false)
    private String tagName;

    /*public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }*/

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}