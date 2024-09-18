package com.anakki.data.entity.request;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * ClassName: ListCommentsRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/14 12:01
 */
@Data
public class ReplyCommentRequest {
    @ApiModelProperty("评论id")
    private Long commentId;

    @ApiModelProperty("评论")
    @NotBlank(message = "请输入评论")
    private String comment;
}
