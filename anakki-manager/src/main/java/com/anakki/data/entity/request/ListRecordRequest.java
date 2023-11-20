package com.anakki.data.entity.request;

import com.anakki.data.entity.common.Pagination;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * ClassName: GetContentRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/8 22:55
 */
@Data
public class ListRecordRequest extends Pagination {

    @ApiModelProperty("图片类型")
    private String type;

    @ApiModelProperty("图片标题")
    private String title;

    @ApiModelProperty("拍摄位置")
    private String location;

    @ApiModelProperty("图片介绍")
    private String description;

    @ApiModelProperty("图片地址")
    private String imgUrl;

    @ApiModelProperty("图片拍摄时间-开始")
    private LocalDateTime photoTimeStart;

    @ApiModelProperty("图片拍摄时间-结束")
    private LocalDateTime photoTimeEnd;

    @ApiModelProperty("图片上传时间-开始")
    private LocalDateTime createTimeStart;

    @ApiModelProperty("图片上传时间-结束")
    private LocalDateTime createTimeEnd;
}
