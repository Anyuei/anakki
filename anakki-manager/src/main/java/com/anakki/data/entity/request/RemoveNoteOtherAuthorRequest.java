package com.anakki.data.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class RemoveNoteOtherAuthorRequest {
    @ApiModelProperty("共同编辑者id")
    @NotEmpty(message = "未添加需要移除的用户")
    private List<Long> authorIds;

    @ApiModelProperty("操作笔记id")
    private Long targetNoteId;
}
