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
 * @Description: 种类
 */
@Data
@ApiModel(description = "sort")
@TableName("sorts")
public class Sort implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    @ApiModelProperty(value = "类别名称")
    @TableField("sort_name")
    private String sortName;
    @ApiModelProperty(value = "创建时间")
    @TableField("gmt_create")
    private Long gmtCreate;
}
