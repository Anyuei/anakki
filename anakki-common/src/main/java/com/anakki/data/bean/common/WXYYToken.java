package com.anakki.data.bean.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName: WXYYMessage
 * Description:
 *
 * @author Anakki
 * @date 2023/11/29 17:19
 */
@Data
public class WXYYToken {
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("expires_in")
    private long expiresIn;
    @JsonProperty("session_key")
    private String sessionKey;
    @JsonProperty("access_token")
    private String accessToken;
    private String scope;
    @JsonProperty("session_secret")
    private String sessionSecret;
}
