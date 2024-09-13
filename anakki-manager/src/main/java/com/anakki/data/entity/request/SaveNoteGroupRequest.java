package com.anakki.data.entity.request;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class SaveNoteGroupRequest {

    @ApiModelProperty("分组名称")
    @NotBlank(message = "分组名称不能为空")
    private String groupName;

    @ApiModelProperty("介绍")
    private String description;

    @ApiModelProperty("封面信息")
    private FileInfoRequest[] files;

    @ApiModelProperty("分组状态")
    private String status="COMMON";

    @ApiModelProperty("公开范围（0：全站可见，1：仅某人可见，2：自己可见）")
    private Integer scopeAccess=0;

}
