package com.anakki.data.entity.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * ClassName: Pagination
 * Description:
 *
 * @author Anakki
 * @date 2023/4/26 4:21
 */
@Data
@ApiModel("分页参数")
public class Pagination {
    public long getSize() {
        if (size>100){
            return 100;
        }
        return size;
    }

    @ApiModelProperty("分页大小")
    private long size;
    @ApiModelProperty("当前页")
    private long current;

    @ApiModelProperty("访问时间-开始")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeStart;

    @ApiModelProperty("访问时间-结束")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeEnd;

    public Pagination() {
        this.size = 10;
        this.current = 1;
    }
}
