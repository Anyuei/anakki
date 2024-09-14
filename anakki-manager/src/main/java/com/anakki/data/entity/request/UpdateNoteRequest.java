package com.anakki.data.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: CreateNoteRequest
 * Description:
 *
 * @author Anakki
 * @date 2024/6/6 3:39
 */
@Data
public class UpdateNoteRequest {
    @ApiModelProperty("笔记id")
    private Long id;

    @ApiModelProperty("笔记类型")
    private String uploadFiles;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("封面图片")
    private String coverImage;

    @ApiModelProperty("笔记状态（正常：COMMON，失效 INVALID，草稿：DRAFT")
    private String status;

    @ApiModelProperty("笔记分组ids")
    private List<Long> noteGroupIds;
}
