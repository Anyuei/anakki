package com.anakki.data.entity.request;

import com.anakki.data.entity.common.Pagination;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * ClassName: CreateNoteRequest
 * Description:
 *
 * @author Anakki
 * @date 2024/6/6 3:39
 */
@Data
public class CreateChatRoomManageRequest{


    @ApiModelProperty("房间类型")
    private String roomType="1";

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("房主id")
    @NotNull(message = "房主id不能为空")
    private Long roomMasterId;

    @ApiModelProperty("房间名称")
    @NotBlank(message = "房间名称不能为空")
    private String roomName;

    @ApiModelProperty("介绍")
    private String description="";

    @ApiModelProperty("是否临时消息")
    private Boolean isTemp=false;

    @ApiModelProperty("是否加密")
    private Boolean isEncrypt=false;

    @ApiModelProperty("是否公开可搜索")
    private Boolean isPublicForSearch=true;

    @ApiModelProperty("1:自由进入，2:密码进入，3:申请进入，4:邀请进入")
    private Integer enterType=1;

    @ApiModelProperty("加入密码")
    private String enterPassword;

    @ApiModelProperty("状态（NORMAL，CLOSED）")
    private String status="NORMAL";
}
