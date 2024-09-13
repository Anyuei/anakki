package com.anakki.data.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * ClassName: CreateNoteRequest
 * Description:
 *
 * @author Anakki
 * @date 2024/6/6 3:39
 */
@Data
public class UpdateNoteGroupRequest {

    @ApiModelProperty("分组名称")
    @NotBlank(message = "分组名称不能为空")
    private String groupName;

    @ApiModelProperty("介绍")
    private String description;

    @ApiModelProperty("封面图片")
    private String coverImage;

    @ApiModelProperty("分组状态")
    private String status;

    @ApiModelProperty("公开范围（0：全站可见，1：仅某人可见，2：自己可见）")
    private Integer scopeAccess;

}
