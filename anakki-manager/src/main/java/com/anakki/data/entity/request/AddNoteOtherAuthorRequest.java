package com.anakki.data.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AddNoteOtherAuthorRequest {
    @ApiModelProperty("共同编辑者id")
    @NotEmpty(message = "未添加用户")
    private List<Long> authorIds;

    @ApiModelProperty("操作笔记id")
    @NotNull(message = "无操作对象id")
    private Long targetNoteId;
}
