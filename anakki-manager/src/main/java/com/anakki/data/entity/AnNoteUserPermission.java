package com.anakki.data.entity;

import com.anakki.data.entity.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Anakki
 * @since 2024-09-05
 */
@Getter
@Setter
@TableName("an_note_user_permission")
@ApiModel(value = "AnNoteUserPermission对象", description = "")
public class AnNoteUserPermission extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("权限类型（0：读：1：修改，2：删除）")
    @TableField("type")
    private String type;

    @ApiModelProperty("最后操作人id")
    @TableField("create_by")
    private Long createBy;

    @ApiModelProperty("用户id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("笔记id")
    @TableField("note_id")
    private Long noteId;
}
