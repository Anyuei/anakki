package com.anakki.data.entity.response;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Anakki
 * @since 2024-06-06
 */
@Getter
@Setter
public class NoteUserPermissionResponse {
    @ApiModelProperty("权限id")
    private Long id;

    @ApiModelProperty("权限类型（0：读：1：修改，2：删除）")
    private String type;

    @ApiModelProperty("最后操作人id")
    private Long createBy;
}
