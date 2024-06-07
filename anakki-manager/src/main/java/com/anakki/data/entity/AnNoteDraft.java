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
 * @since 2024-06-06
 */
@Getter
@Setter
@TableName("an_note_draft")
@ApiModel(value = "AnNoteDraft对象", description = "")
public class AnNoteDraft extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("笔记id")
    @TableField("note_id")
    private Long noteId;

    @ApiModelProperty("内容")
    @TableField("content")
    private String content;
}
