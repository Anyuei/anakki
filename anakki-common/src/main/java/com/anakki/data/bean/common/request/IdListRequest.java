package com.anakki.data.bean.common.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * ClassName: Request
 * Description:
 *
 * @author Anakki
 * @date 2023/4/26 3:49
 */
@Data
@ApiModel(description = "常用于只传id的post请求")
public class IdListRequest {
    @ApiModelProperty("id数组")
    private List<Long> idList;
}
