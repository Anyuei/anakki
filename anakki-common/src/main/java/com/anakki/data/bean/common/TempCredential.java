package com.anakki.data.bean.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/***
 * @author pei
 * @date 2024/8/9 4:19
 */
@Data
@AllArgsConstructor
public class TempCredential {
    private String secretId;
    private String secretKey;
    private String sessionToken;
    private String host;
}
