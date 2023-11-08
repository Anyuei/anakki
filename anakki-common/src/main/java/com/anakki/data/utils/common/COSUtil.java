package com.anakki.data.utils.common;

import com.alibaba.fastjson.JSONObject;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;

import java.util.TreeMap;

/**
 * ClassName: COSUtil
 * Description:
 *
 * @author Anakki
 * @date 2023/5/30 2:29
 */
public class COSUtil {

    //地域
    public static String region = "ap-beijing";
    public static void main(String[] args) {
        System.out.println(JSONObject.toJSON(getCredential()));
    }
    public static BasicSessionCredentials getSessionCredential() {
        Response credential = getCredential();
        // 实际应用中，这里通过云api请求得到临时秘钥后，构造BasicSessionCredential
        return new BasicSessionCredentials(
                credential.credentials.tmpSecretId,
                credential.credentials.tmpSecretKey,
                credential.credentials.sessionToken);
    }
    public static Response getCredential() {
        TreeMap<String, Object> config = new TreeMap<String, Object>();
        try {
            // 云 api 密钥 SecretId
            config.put("secretId", TencentCloudUtil.SECRET_ID);
            // 云 api 密钥 SecretKey
            config.put("secretKey", TencentCloudUtil.SECRET_KEY);
            // 设置域名,可通过此方式设置内网域名
            //config.put("host", "sts.internal.tencentcloudapi.com");
            // 临时密钥有效时长，单位是秒
            config.put("durationSeconds", 1800);
            // 换成你的 bucket
            config.put("bucket", "securex-1258150206");
            // 换成 bucket 所在地区
            config.put("region", region);
            // 可以通过 allowPrefixes 指定前缀数组, 例子： a.jpg 或者 a/* 或者 * (使用通配符*存在重大安全风险, 请谨慎评估使用)
            config.put("allowPrefixes", new String[] {
                    "front/*"
            });
            // 密钥的权限列表。简单上传和分片需要以下的权限，其他权限列表请看 https://cloud.tencent.com/document/product/436/31923
            String[] allowActions = new String[] {
                    // 简单上传
                    "name/cos:PutObject",
                    "name/cos:PostObject",
                    // 分片上传
                    "name/cos:InitiateMultipartUpload",
                    "name/cos:ListMultipartUploads",
                    "name/cos:ListParts",
                    "name/cos:UploadPart",
                    "name/cos:CompleteMultipartUpload"
            };
            config.put("allowActions", allowActions);
            return CosStsClient.getCredential(config);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("no valid secret !");
        }
    }
}
