package com.anakki.data.entity.common;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * ClassName: DateTimeEntity
 * Description:
 *
 * @author Anakki
 * @date 2023/3/17 23:20
 */
@Data
public class DateTimeEntity {
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
