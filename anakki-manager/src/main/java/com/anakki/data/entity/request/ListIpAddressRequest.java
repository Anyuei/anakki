package com.anakki.data.entity.request;

import com.anakki.data.entity.common.Pagination;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * ClassName: GetContentRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/8 22:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ListIpAddressRequest extends Pagination {

    @ApiModelProperty("ip")
    private String ip;

    @ApiModelProperty("国家")
    private String country;

    @ApiModelProperty("省")
    private String prov;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("区")
    private String district;

    @ApiModelProperty("详情")
    private String contentJson;

    @ApiModelProperty("状态")
    private String state;

}
