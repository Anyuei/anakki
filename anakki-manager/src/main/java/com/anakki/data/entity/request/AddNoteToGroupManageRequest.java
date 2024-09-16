package com.anakki.data.entity.request;

import com.anakki.data.entity.common.Pagination;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
public class AddNoteToGroupManageRequest {
    @ApiModelProperty("笔记id")
    private Long noteId;

    @ApiModelProperty("笔记分组id")
    private Long noteGroupId;
}
