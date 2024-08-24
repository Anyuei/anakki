package com.anakki.data.entity.request;

import com.anakki.data.bean.common.request.IdNotNullRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * ClassName: GetContentRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/8 22:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateSystemConfigRequest extends IdNotNullRequest {


    @ApiModelProperty("设置类型")
    @NotBlank
    private String type;

    @ApiModelProperty("设置标题")
    @NotBlank
    private String configKey;

    @ApiModelProperty("设置值")
    @NotBlank
    private String configValue;

    @ApiModelProperty("设置描述")
    @NotBlank
    private String description;
}
