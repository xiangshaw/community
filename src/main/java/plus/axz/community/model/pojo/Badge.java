package plus.axz.community.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaoxiang
 * @date 2022年03月29日
 * @particulars 徽标
 */
@Data
@TableName("tb_badge")
public class Badge implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    // 备注
    @TableField("title")
    private String title;
    // 地址
    @TableField("url")
    private String url;
    // 主体
    @TableField("badge_name")
    private String badgeName;
    // 值
    @TableField("value")
    private String value;
    // 颜色
    @TableField("color")
    private String color;
    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

}