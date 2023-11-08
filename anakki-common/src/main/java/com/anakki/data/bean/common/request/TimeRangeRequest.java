package com.anakki.data.bean.common.request;

import com.anakki.data.bean.common.OrderBy;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * ClassName: TimeRangeRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/4/26 4:18
 */
@Data
@ApiModel("时间范围查询")
public class TimeRangeRequest extends OrderBy {

    @ApiModelProperty("创建时间起始")
    private LocalDateTime createTimeStart;

    @ApiModelProperty("创建时间结束")
    private LocalDateTime createTimeEnd;

    @ApiModelProperty("修改时间结束")
    private LocalDateTime updateTimeStart;

    @ApiModelProperty("修改时间结束")
    private LocalDateTime updateTimeEnd;
}
