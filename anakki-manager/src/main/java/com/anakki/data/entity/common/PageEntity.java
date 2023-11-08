package com.anakki.data.entity.common;

import lombok.Data;

/**
 * ClassName: DateTimeEntity
 * Description:
 *
 * @author Anakki
 * @date 2023/3/17 23:20
 */
@Data
public class PageEntity {
    private Integer pageIndex=0;
    private Integer pageSize=10;
}
