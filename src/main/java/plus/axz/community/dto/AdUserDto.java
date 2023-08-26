package plus.axz.community.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import plus.axz.community.model.common.dtos.PageRequestDto;

/**
 * @author xiaoxiang
 * @date 2022年12月30日
 * @Description:
 */
@Data/*重写get、set方法*/
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdUserDto extends PageRequestDto {
    /**
     * 用户名
     */
    @ApiModelProperty("用户名称")
    private String name;
}