package com.yzk.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum ActivityType {
    FULL_REDUCTION(1,"满减"),
    FULL_DISCOUNT(2,"满量打折" );

    @EnumValue
    private Integer code ;
    private String comment ;

    ActivityType(Integer code, String comment ){
        this.code=code;
        this.comment=comment;
    }
    // ========= 根据code获取枚举对象 =========
    public static ActivityType getByCode(Integer code){
        for (ActivityType type : ActivityType.values()) {
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null; // 或者抛异常
    }

    public static String getCommentByCode(Integer code){
        ActivityType type = getByCode(code);
        return type == null ? null : type.getComment();
    }


}