package com.anakki.data.entity.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * ClassName: BaseEntity
 * Description:
 *
 * @author Anakki
 * @date 2023/3/17 23:19
 */
@Data
public class BaseEntity extends DateTimeEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
}
