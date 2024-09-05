package com.anakki.data.entity.response;

import com.anakki.data.entity.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Anakki
 * @since 2024-06-06
 */
@Getter
@Setter
public class ListUserForNoteResponse {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("是否协作者")
    private Boolean IsCollaborator;

    @ApiModelProperty("文章权限")
    private List<NoteUserPermissionResponse> permissions;
}
