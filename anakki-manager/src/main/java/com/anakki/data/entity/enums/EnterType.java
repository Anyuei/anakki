package com.anakki.data.entity.enums;

import io.swagger.annotations.ApiModelProperty;

/**
 * 进入类型枚举类
 */
public enum EnterType {

    FREE(1, "自由进入"),
    PASSWORD(2, "密码进入"),
    APPLY(3, "申请进入"),
    INVITE(4, "邀请进入");

    @ApiModelProperty("进入类型编码")
    private final Integer code;

    @ApiModelProperty("进入类型描述")
    private final String description;

    EnterType(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据编码获取对应的进入类型枚举
     *
     * @param code 进入类型编码
     * @return 对应的进入类型枚举
     */
    public static EnterType fromCode(Integer code) {
        for (EnterType type : EnterType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效的进入类型编码: " + code);
    }

    /**
     * 获取进入类型编码
     *
     * @return 进入类型编码
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 获取进入类型描述
     *
     * @return 进入类型描述
     */
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format("EnterType{code=%d, description='%s'}", code, description);
    }
}