package com.anakki.data.bean.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName: WXYYMessage
 * Description:
 *
 * @author Anakki
 * @date 2023/11/29 17:19
 */
@Data
public class WXYYMessage {
    @ApiModelProperty("对话角色")
    private String role;
    @ApiModelProperty("对话内容")
    private String content;
}
