package com.anakki.data.bean.common.enums;

/**
 * ClassName: CommentStateEnum
 * Description:
 *
 * @author Anakki
 * @date 2023/11/23 1:26
 */
public enum CommentStateEnum {
    IN_REVIEW(),
    NORMAL(),
    CLOSED();

    public static boolean isValidEnumName(String name) {
        try {
            CommentStateEnum.valueOf(name);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
