package plus.axz.community.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaoxiang
 * @date 2023年02月15日
 * @Description: 标签
 */
@Data
@ApiModel(description = "tags")
@TableName("tags")
public class Tag implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "类别ID")
    @TableField("sort_id")
    private String sortId;
    @ApiModelProperty(value = "标签名称")
    @TableField("tag_name")
    private String tagName;
    @ApiModelProperty(value = "创建时间")
    @TableField("gmt_create")
    private Long gmtCreate;

    @Override
    public String toString() {
        return "Tag{" +
                "id='" + id + '\'' +
                ", sortId='" + sortId + '\'' +
                ", tagName='" + tagName + '\'' +
                ", gmtCreate='" + gmtCreate + '\'' +
                '}';
    }
}
