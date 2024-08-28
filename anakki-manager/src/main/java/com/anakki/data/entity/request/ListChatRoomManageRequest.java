package com.anakki.data.entity.request;

import com.anakki.data.entity.common.Pagination;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * ClassName: CreateNoteRequest
 * Description:
 *
 * @author Anakki
 * @date 2024/6/6 3:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ListChatRoomManageRequest extends Pagination {

    @ApiModelProperty("房间类型")
    private String roomType;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("房主id")
    private Long roomMasterId;

    @ApiModelProperty("房间名称")
    private String roomName;

    @ApiModelProperty("介绍")
    private String description;

    @ApiModelProperty("是否临时消息")
    private Boolean isTemp;

    @ApiModelProperty("是否加密")
    private Boolean isEncrypt;

    @ApiModelProperty("是否公开可搜索")
    private Boolean isPublicForSearch;

    @ApiModelProperty("1:自由进入，2:密码进入，3:申请进入，4:邀请进入")
    private Integer enterType;

    @ApiModelProperty("状态（NORMAL，CLOSED）")
    private String status;
}
