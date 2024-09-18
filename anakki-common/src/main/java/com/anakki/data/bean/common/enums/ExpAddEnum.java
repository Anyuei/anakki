package com.anakki.data.bean.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/***
 * @author pei
 * @date 2024/9/19 0:47
 */
@Getter
public enum ExpAddEnum {

    UPLOAD_RESOURCE("上传资源",20),

    UPLOAD_MODEL("上传3D模型",25),

    LOGIN("登录",10),

    ADVICE_CONFIRM("建议被采纳",200),

    PUBLISH_NOTE("发布笔记",50),

    PUBLISH_NOTE_BE_LIKE("发布的笔记被点赞",5),

    COMMENT("留言",5);

    private final Integer exp;
    private final String behavior;
    ExpAddEnum(String behavior ,Integer exp) {
        this.behavior=behavior;
        this.exp = exp;
    }

    // 返回枚举项的信息
    public static List<String> getEnumInfo() {
        return Arrays.stream(ExpAddEnum.values())
                .map(enumItem -> String.format("用户行为: %s, 经验值: %d", enumItem.behavior, enumItem.exp))
                .collect(Collectors.toList());
    }
}
