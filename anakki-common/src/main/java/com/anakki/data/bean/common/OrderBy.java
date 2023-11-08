package com.anakki.data.bean.common;

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
@ApiModel("排序参数")
public class OrderBy extends Pagination{

    @ApiModelProperty("是否升序")
    private boolean isAcs=false;

    @ApiModelProperty("排序字段")
    private String sort;
}
