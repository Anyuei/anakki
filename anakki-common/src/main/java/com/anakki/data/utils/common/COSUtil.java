package com.anakki.data.utils.common;

import com.alibaba.fastjson.JSONObject;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.auth.COSStaticCredentialsProvider;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.TreeMap;
import java.util.UUID;

/**
 * ClassName: COSUtil
 * Description:
 *
 * @author Anakki
 * @date 2023/5/30 2:29
 */
public class COSUtil {
    public static String HOST = "https://anakki-1258150206.cos.ap-nanjing.myqcloud.com/";
    //地域
    public static String region = "ap-nanjing";
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
            config.put("secretId", TencentCloudAKSK.SECRET_ID);
            // 云 api 密钥 SecretKey
            config.put("secretKey", TencentCloudAKSK.SECRET_KEY);
            // 设置域名,可通过此方式设置内网域名
            //config.put("host", "sts.internal.tencentcloudapi.com");
            // 临时密钥有效时长，单位是秒
            config.put("durationSeconds", 1800);
            // 换成你的 bucket
            config.put("bucket", "anakki-1258150206");
            // 换成 bucket 所在地区
            config.put("region", region);
            // 可以通过 allowPrefixes 指定前缀数组, 例子： a.jpg 或者 a/* 或者 * (使用通配符*存在重大安全风险, 请谨慎评估使用)
            config.put("allowPrefixes", new String[] {
                    "front/*","images/*","avatar/*"
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

    public static String uploadObject(
            MultipartFile file,
            String region,
            String bucketName
    ,            String path) {
        return uploadObject(
                file,
                getSessionCredential(),
                region,
                bucketName,
                path);
    }

    public static String uploadObject(
            MultipartFile file,
            BasicSessionCredentials basicSessionCredential,
            String region,
            String bucketName,
            String path) {
        try {
            // 初始化COS客户端
            ClientConfig clientConfig = new ClientConfig(new Region(region));
            COSClient cosClient = new COSClient(new COSStaticCredentialsProvider(basicSessionCredential), clientConfig);
            // 构造PutObjectRequest对象
            // 获取上传的文件的输入流
            InputStream inputStream = file.getInputStream();

            // 避免文件覆盖，获取文件的原始名称，如123.jpg,然后通过截取获得文件的后缀，也就是文件的类型
            String originalFilename = file.getOriginalFilename();
            //获取文件的类型
            String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
            //使用UUID工具  创建唯一名称，放置文件重名被覆盖，在拼接上上命令获取的文件类型
            String fileName = UUID.randomUUID() + fileType;
            // 指定文件上传到 COS 上的路径，即对象键。最终文件会传到存储桶名字中的images文件夹下的fileName名字
            String key = path + fileName;
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            // - 使用输入流存储，需要设置请求长度
            objectMetadata.setContentLength(inputStream.available());
            // - 设置缓存
            objectMetadata.setCacheControl("no-cache");
            // - 设置Content-Type
            objectMetadata.setContentType(fileType);
            //上传文件
            PutObjectResult putResult = cosClient.putObject(bucketName, key, inputStream, objectMetadata);
            // 创建文件的网络访问路径
            String url = HOST + key;
            //关闭 cosClient，并释放 HTTP 连接的后台管理线程
            // 关闭COS客户端
            cosClient.shutdown();
            return url;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
