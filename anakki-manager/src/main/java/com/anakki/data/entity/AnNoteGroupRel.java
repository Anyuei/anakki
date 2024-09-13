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
 * @since 2024-09-14
 */
@Getter
@Setter
@TableName("an_note_group_rel")
@ApiModel(value = "AnNoteGroupRel对象", description = "")
public class AnNoteGroupRel extends BaseEntity implements Serializable {


    @ApiModelProperty("分组id")
    @TableField("note_group_id")
    private Long noteGroupId;

    @ApiModelProperty("笔记id")
    @TableField("note_id")
    private Long noteId;



}
