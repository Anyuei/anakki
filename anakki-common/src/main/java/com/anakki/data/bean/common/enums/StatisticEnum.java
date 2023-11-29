package com.anakki.data.bean.common.enums;

/**
 * ClassName: CommentStateEnum
 * Description:
 *
 * @author Anakki
 * @date 2023/11/23 1:26
 */
public enum StatisticEnum {
    MAINPAGE_FLOW("主页"),
    COMMENT("评论区"),
    SPACEX("SPACEX主页"),
    ABOUT_ME("关于我");

    public String getDescription() {
        return description;
    }

    private final String description;

    StatisticEnum(String description) {
        this.description = description;
    }
    public static boolean isValidEnumName(String name) {
        try {
            CommentStateEnum.valueOf(name);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
