package com.anakki.data.bean.common.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * ClassName: Request
 * Description:
 *
 * @author Anakki
 * @date 2023/4/26 3:49
 */
@Data
@ApiModel("常用于修改对象状态请求")
public class ChangeStatusRequest extends IdNotNullRequest{
    @ApiModelProperty("1：启用，0：禁用")
    @NotNull
    private Integer status;
}
