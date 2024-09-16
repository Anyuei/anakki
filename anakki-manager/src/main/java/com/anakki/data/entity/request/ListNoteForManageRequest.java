package com.anakki.data.entity.request;

import com.anakki.data.entity.common.Pagination;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * ClassName: GetContentRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/8 22:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ListNoteForManageRequest extends Pagination {


    @ApiModelProperty("笔记类型")
    private String type;

    @ApiModelProperty("笔记分组id")
    private Long noteGroupId;

    @ApiModelProperty("笔记分组名称")
    private String noteGroupName;

    @ApiModelProperty("发布位置")
    private String location;

    @ApiModelProperty("介绍")
    private String description;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("作者名称")
    private String author;

    @ApiModelProperty("协作者id")
    private String authorId;

    @ApiModelProperty("作者id")
    private Long createBy;

    @ApiModelProperty("笔记状态（正常：COMMON，失效 INVALID")
    private String status;
}
