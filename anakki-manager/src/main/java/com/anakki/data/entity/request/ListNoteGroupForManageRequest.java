package com.anakki.data.entity.request;

import com.anakki.data.entity.common.BaseEntity;
import com.anakki.data.entity.common.Pagination;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Anakki
 * @since 2024-09-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ListNoteGroupForManageRequest extends Pagination {

    @ApiModelProperty("分组名称")
    private String groupName;

    @ApiModelProperty("介绍")
    private String description;

    @ApiModelProperty("分组状态")
    private String status;

    @ApiModelProperty("公开范围（0：全站可见，1：仅某人可见，2：自己可见）")
    private Integer scopeAccess;

    @ApiModelProperty("排序字段")
    private String orderBy;

}
