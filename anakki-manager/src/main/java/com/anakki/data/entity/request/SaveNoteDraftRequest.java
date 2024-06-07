package com.anakki.data.entity.request;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName: CreateNoteRequest
 * Description:
 *
 * @author Anakki
 * @date 2024/6/6 3:39
 */
@Data
public class SaveNoteDraftRequest {

    @ApiModelProperty("笔记id")
    private Long noteId;

    @ApiModelProperty("内容")
    private String content="";

    @ApiModelProperty("介绍")
    private String description="";

    @ApiModelProperty("笔记类型")
    private String type;

}
