package com.anakki.data.entity.request;

import com.anakki.data.entity.common.Pagination;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * ClassName: CreateNoteRequest
 * Description:
 *
 * @author Anakki
 * @date 2024/6/6 3:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ListNoteRequest extends Pagination {
    private String type;
    private String status;
    private String content;
    private LocalDateTime createTimeStart;
    private LocalDateTime createTimeEnd;
    private Long noteGroupId;
}
