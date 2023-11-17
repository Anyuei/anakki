package com.anakki.data.entity.request;

import com.anakki.data.entity.common.Pagination;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName: GetContentRequest
 * Description:
 *
 * @author Anakki
 * @date 2023/11/8 22:55
 */
@Data
public class ListManagerRequest extends Pagination {

    @ApiModelProperty("管理员昵称")
    private String nickname;

    @ApiModelProperty("管理员邮箱")
    private String mail;
}
