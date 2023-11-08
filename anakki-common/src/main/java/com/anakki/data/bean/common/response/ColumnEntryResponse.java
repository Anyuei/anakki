package com.anakki.data.bean.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel("表中字段信息")
public class ColumnEntryResponse {
    @ApiModelProperty("字段名称")
    private String columnName;
    @ApiModelProperty("字段类型（数据类型）")
    private String typeName;
    @ApiModelProperty("字段大小（单位：字节）")
    private String columnSize;
    @ApiModelProperty("字段备注")
    private String remarks;
    @ApiModelProperty("此字段是否是主键")
    private boolean isPrimaryKey;
}