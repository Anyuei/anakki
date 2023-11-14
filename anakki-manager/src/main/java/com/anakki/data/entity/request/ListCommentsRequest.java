package com.anakki.data.entity.request;

import com.anakki.data.entity.common.PageEntity;
import com.anakki.data.entity.common.Pagination;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * ClassName: ListCommentsRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/14 12:01
 */
@Data
public class ListCommentsRequest extends Pagination {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("评论模块类型")
    private String type="COMMON";

    @ApiModelProperty("评论开始时间范围查询")
    private LocalDateTime createTimeStart;

    @ApiModelProperty("评论结束时间范围查询")
    private LocalDateTime createTimeEnd;
}
