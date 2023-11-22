package com.anakki.data.entity.request;

import com.anakki.data.entity.common.Pagination;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName: ListCommentsRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/14 12:01
 */
@Data
public class UpdateCommentStateRequest {

    @ApiModelProperty("状态（IN_REVIEW，NORMAL，CLOSED）")
    @TableField("commentId")
    @NotNull
    private List<Long> commentIdList;

    @ApiModelProperty("状态（IN_REVIEW，NORMAL，CLOSED）")
    @TableField("status")
    @NotBlank
    private String status;

}
