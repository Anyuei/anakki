package com.anakki.data.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName: RandomNameRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/8 22:55
 */
@Data
public class RandomNameRequest {
    @ApiModelProperty("礼物不能为空")
    @NotBlank(message = "礼物不能为空")
    private String gift;

    @ApiModelProperty("名字不能为空")
    @NotEmpty(message = "名字不能为空")
    private List<String> names;

    @ApiModelProperty("开奖时间不能为空")
    @NotNull(message = "开奖时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime openTime;
}
