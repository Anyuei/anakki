package com.anakki.data.entity.request;

import com.anakki.data.entity.common.Pagination;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * ClassName: ListCommentsRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/14 12:01
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ListAuthorUsersForNoteRequest extends Pagination {


    @ApiModelProperty("笔记id")
    @NotNull(message = "noteId不能为空")
    private Long noteId;
}
