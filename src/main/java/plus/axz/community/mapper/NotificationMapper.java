package plus.axz.community.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import plus.axz.community.model.pojo.Notification;
import plus.axz.community.model.NotificationExample;

import java.util.List;

public interface NotificationMapper {
    long countByExample(NotificationExample example);
    int deleteByExample(NotificationExample example);
    int deleteByPrimaryKey(Long id);
    int insert(Notification record);
    int insertSelective(Notification record);
    List<Notification> selectByExampleWithRowbounds(NotificationExample example, RowBounds rowBounds);
    List<Notification> selectByExample(NotificationExample example);
    Notification selectByPrimaryKey(Long id);
    int updateByExampleSelective(@Param("record") Notification record, @Param("example") NotificationExample example);
    int updateByExample(@Param("record") Notification record, @Param("example") NotificationExample example);
    int updateByPrimaryKeySelective(Notification record);
    int updateByPrimaryKey(Notification record);
}