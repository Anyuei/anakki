package com.anakki.data.entity.request;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Blob;
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
public class CreateNoteRequest {

    @ApiModelProperty("共同编辑者id")
    private List<Long> authorIds=new ArrayList<>();

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("封面图片")
    private String coverImage;

    @ApiModelProperty("笔记状态（正常：COMMON，失效 INVALID，草稿：DRAFT")
    private String status;

    @ApiModelProperty("笔记分组ids")
    private List<Long> noteGroupIds;
}
