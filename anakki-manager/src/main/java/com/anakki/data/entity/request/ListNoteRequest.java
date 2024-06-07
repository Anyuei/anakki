package com.anakki.data.entity.request;

import com.anakki.data.entity.common.Pagination;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * ClassName: CreateNoteRequest
 * Description:
 *
 * @author Anakki
 * @date 2024/6/6 3:39
 */
@Data
public class ListNoteRequest extends Pagination {
    @ApiModelProperty("笔记类型")
    private String type;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("创建时间-查询开始")
    private LocalDateTime createTimeStart;

    @ApiModelProperty("创建时间-查询结束")
    private LocalDateTime createTimeEnd;
}