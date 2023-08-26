package plus.axz.community.model.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xiaoxiang
 * @date 2022年11月13日
 * @Description: 类别查询
 */
public class TagQueryVo {
    @ApiModelProperty(name = "tagName", value = "标签名称", required = false)
    private String tagName;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}