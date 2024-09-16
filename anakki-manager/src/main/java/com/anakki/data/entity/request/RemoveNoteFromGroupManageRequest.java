package com.anakki.data.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class RemoveNoteFromGroupManageRequest {
    @ApiModelProperty("笔记id")
    private Long noteId;

    @ApiModelProperty("笔记分组id")
    private Long noteGroupId;
}
