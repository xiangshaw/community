package plus.axz.community.enums;

import plus.axz.community.exception.CustomizeErrorCode;
import plus.axz.community.exception.CustomizeException;

public enum NotificationTypeEnum {
    REPLY_QUESTION(1, "回复了你的问题"),
    REPLY_COMMENT(2, "回复了你的评论");

    private Integer type;
    private String description;

    NotificationTypeEnum(int type, String description) {
        this.type = type;
        this.description = description;
    }

    public static String descOf(int type) {
        for (NotificationTypeEnum value : NotificationTypeEnum.values()){
            if(value.type == type){
                return value.description;
            }
        }
        throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_TYPE_NOT_EXIST);
    }

    public Integer getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
