package plus.axz.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import plus.axz.community.model.pojo.User;

/**
 * @author xiaoxiang
 * @date 2023年02月07日
 * @Description:
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<User> {
}
