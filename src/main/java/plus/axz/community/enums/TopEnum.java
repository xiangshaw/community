package plus.axz.community.enums;

public enum TopEnum {
    //设为置顶
    SET_TOP(1),
    //取消顶置
    UNSET_TOP(0);

    private Integer type;

    TopEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public static boolean isNotExist(int type){
        for(TopEnum topEnum : TopEnum.values()){
            if(topEnum.getType() != type){
                return false;
            }
        }
        return true;
    }
}
