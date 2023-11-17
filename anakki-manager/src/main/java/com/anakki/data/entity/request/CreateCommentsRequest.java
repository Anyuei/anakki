package com.anakki.data.entity.request;

import com.anakki.data.entity.common.Pagination;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class CreateCommentsRequest {

    @ApiModelProperty("评论模块类型")
    private String type="COMMON";

    @ApiModelProperty("评论")
    @TableField("comment")
    private String comment;
}
