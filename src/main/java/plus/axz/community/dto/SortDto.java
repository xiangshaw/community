package plus.axz.community.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import plus.axz.community.model.common.dtos.PageRequestDto;

/**
 * @author xiaoxiang
 * @date 2023年01月07日
 * @Description:
 */
@Data/*重写get、set方法*/
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SortDto extends PageRequestDto {
    @ApiModelProperty("类别名称")
    private String sortName;
}