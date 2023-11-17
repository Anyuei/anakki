package com.anakki.data.entity.request;

import com.anakki.data.entity.common.Pagination;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * ClassName: GetContentRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/8 22:55
 */
@Data
public class SendMailRequest{

    @ApiModelProperty("收件箱")
    @NotBlank(message = "收件箱不能为空")
    private String to;
    @ApiModelProperty("主题")
    @NotBlank(message = "主题不能为空")
    private String subject;
    @ApiModelProperty("内容")
    @NotBlank(message = "内容不能为空")
    private String content;
}
