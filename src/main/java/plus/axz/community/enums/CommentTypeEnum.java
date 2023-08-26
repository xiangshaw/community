package plus.axz.community.enums;

public enum CommentTypeEnum {
    //问题的回复
    TYPE_QUESTION(1),
    //评论的回复
    TYPE_COMMENT(2);

    private Integer type;

    CommentTypeEnum(Integer type){
        this.type = type;
    }

    public static boolean isNotExist(int type) {
        for(CommentTypeEnum commentType : CommentTypeEnum.values()){
            if(type == commentType.getType()){
                return false;
            }
        }
        return true;
    }

    public Integer getType() {
        return type;
    }
}
