package com.anakki.data.entity.request;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Blob;

/**
 * ClassName: CreateNoteRequest
 * Description:
 *
 * @author Anakki
 * @date 2024/6/6 3:39
 */
@Data
public class CreateNoteRequest {

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
}
