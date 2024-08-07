package com.anakki.data.utils.common;

import com.anakki.data.bean.constant.CosBucketNameConst;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.auth.COSStaticCredentialsProvider;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.Date;
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

    public static BasicSessionCredentials getSessionCredential() {
        Response credential = getCredential();
        // 实际应用中，这里通过云api请求得到临时秘钥后，构造BasicSessionCredential
        return new BasicSessionCredentials(
                credential.credentials.tmpSecretId,
                credential.credentials.tmpSecretKey,
                credential.credentials.sessionToken);
    }

    public static BasicSessionCredentials getGetObjCredential() {
        Response credential = getObjCredential();
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
            config.put("bucket", CosBucketNameConst.BUCKET_NAME_IMAGES);
            // 换成 bucket 所在地区
            config.put("region", region);
            // 可以通过 allowPrefixes 指定前缀数组, 例子： a.jpg 或者 a/* 或者 * (使用通配符*存在重大安全风险, 请谨慎评估使用)
            config.put("allowPrefixes", new String[]{
                    "front/*", "images/*", "avatar/*", "resource/*","note-resource/*"
            });
            // 密钥的权限列表。简单上传和分片需要以下的权限，其他权限列表请看 https://cloud.tencent.com/document/product/436/31923
            String[] allowActions = new String[]{
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

    public static Response getObjCredential() {
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
            config.put("bucket", CosBucketNameConst.BUCKET_NAME_IMAGES);
            // 换成 bucket 所在地区
            config.put("region", region);
            // 可以通过 allowPrefixes 指定前缀数组, 例子： a.jpg 或者 a/* 或者 * (使用通配符*存在重大安全风险, 请谨慎评估使用)
            config.put("allowPrefixes", new String[]{
                    "front/*", "images/*", "avatar/*"
            });
            // 密钥的权限列表。简单上传和分片需要以下的权限，其他权限列表请看 https://cloud.tencent.com/document/product/436/31923
            String[] allowActions = new String[]{
                    // 简单下载
                    "name/cos:GetObject"
            };
            config.put("allowActions", allowActions);
            return CosStsClient.getCredential(config);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("no valid secret !");
        }
    }

    public static String uploadImage(
            MultipartFile file,
            String region,
            String bucketName
            , String path,
            Boolean isRaw) throws IOException {
        return uploadImage(
                file,
                getSessionCredential(),
                region,
                bucketName,
                path,
                isRaw);
    }

    public static String uploadImage(
            MultipartFile file,
            BasicSessionCredentials basicSessionCredential,
            String region,
            String bucketName,
            String path,
            Boolean isRaw) throws IOException {
        try {
            // 获取上传的文件的输入流
            InputStream inputStream = file.getInputStream();
            // 初始化COS客户端
            ClientConfig clientConfig = new ClientConfig(new Region(region));
            COSClient cosClient = new COSClient(new COSStaticCredentialsProvider(basicSessionCredential), clientConfig);
            // 避免文件覆盖，生成唯一的文件名
            String originalFilename = file.getOriginalFilename();
            String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = UUID.randomUUID() + fileType;
            String key = path + fileName;
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setHeader("Content-Disposition","attachment");
            cosClient.putObject(bucketName, key, inputStream, objectMetadata);
            String url;
            if (isRaw&&(file.getContentType() != null && file.getContentType().startsWith("image/"))) {
                url = HOST + key + "?imageMogr2/thumbnail/400x";
            }else{
                url = HOST + key;
            }
            // 关闭COS客户端
            cosClient.shutdown();
            return url;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String uploadResource(
            MultipartFile file,
            BasicSessionCredentials basicSessionCredential,
            String region,
            String bucketName,
            String path) throws IOException {
        try {
            // 获取上传的文件的输入流
            InputStream inputStream = file.getInputStream();
            // 初始化COS客户端
            ClientConfig clientConfig = new ClientConfig(new Region(region));
            COSClient cosClient = new COSClient(new COSStaticCredentialsProvider(basicSessionCredential), clientConfig);
            // 避免文件覆盖，生成唯一的文件名
            String originalFilename = file.getOriginalFilename();
            String key = getFileName(path, originalFilename);
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setHeader("Content-Disposition","attachment");
            cosClient.putObject(bucketName, key, inputStream, objectMetadata);
            // 关闭COS客户端
            cosClient.shutdown();
            return key;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String getFileName(String path, String originalFilename) {
        if (!StringUtils.isEmpty(originalFilename)) {
            String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = UUID.randomUUID() + fileType;
            return path + fileName;
        } else {
            return path + UUID.randomUUID();
        }
    }

    public static void main(String[] args) {
        System.out.println(getObjectUrl(CosBucketNameConst.BUCKET_NAME_IMAGES, "resource/DiskGenius.zip"));
    }

    public static void deleteObject(String bucketName, String objectKey) {
        String replace = objectKey.replace(COSUtil.HOST, "");
        // 初始化COS客户端
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        COSClient cosClient = new COSClient(new BasicCOSCredentials(TencentCloudAKSK.SECRET_ID, TencentCloudAKSK.SECRET_KEY), clientConfig);
        // 删除对象
        cosClient.deleteObject(bucketName, replace);
        // 关闭COS客户端
        cosClient.shutdown();
    }

    public static String getObjectUrl(String bucketName, String objectKey) {
        String replace = objectKey.replace(COSUtil.HOST, "");
        // 初始化COS客户端
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        COSClient cosClient = new COSClient(new BasicCOSCredentials(TencentCloudAKSK.SECRET_ID, TencentCloudAKSK.SECRET_KEY), clientConfig);
        // 获取对象url
        Date expirationDate = new Date(System.currentTimeMillis() + 60 * 1000);
        URL objectUrl = cosClient.generatePresignedUrl(bucketName, replace, expirationDate, HttpMethodName.GET);
        // 关闭COS客户端
        cosClient.shutdown();
        return objectUrl.toString();
    }
}
