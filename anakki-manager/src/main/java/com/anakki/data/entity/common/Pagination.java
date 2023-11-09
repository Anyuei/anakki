package com.anakki.data.entity.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName: Pagination
 * Description:
 *
 * @author Anakki
 * @date 2023/4/26 4:21
 */
@Data
@ApiModel("分页参数")
public class Pagination {
    @ApiModelProperty("分页大小")
    private long size;
    @ApiModelProperty("当前页")
    private long current;

    public Pagination() {
        this.size = 10;
        this.current = 1;
    }
}
