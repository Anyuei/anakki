package com.anakki.data.entity.request;

import com.anakki.data.entity.common.Pagination;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ClassName: ListCommentsRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/14 12:01
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ListResourceManageRequest extends Pagination {

    private String createBy;

    private String type;

    private Boolean isPublic;

    private String description;

    private String title;
}
