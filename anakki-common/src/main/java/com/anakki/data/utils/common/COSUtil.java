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

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
    ,            String path,
            Integer maxWidth,
            Integer maxHeight,
            Float quality,
            Boolean isRaw) {
        return uploadObject(
                file,
                getSessionCredential(),
                region,
                bucketName,
                path,
                maxWidth,
                maxHeight,
                quality,
                isRaw);
    }

    public static String uploadObject(
            MultipartFile file,
            BasicSessionCredentials basicSessionCredential,
            String region,
            String bucketName,
            String path,
            Integer maxWidth,
            Integer maxHeight,
            Float ratio,
            Boolean isRaw) {
        try {
            // 获取上传的文件的输入流
            InputStream inputStream = file.getInputStream();
            if (!isRaw){
                // 读取原始图片
                BufferedImage originalImage = ImageIO.read(inputStream);
                // 计算压缩后的尺寸
                int width = originalImage.getWidth();
                int height = originalImage.getHeight();
                int newWidth = width;
                int newHeight = height;
                if (null!=ratio){
                    newWidth = (int) (width * ratio);
                    newHeight = (int) (height * ratio);
                }else{
                    if (width > maxWidth || height > maxHeight) {
                        float widthRatio = (float) maxWidth / width;
                        float heightRatio = (float) maxHeight / height;
                        float ratioR = Math.min(widthRatio, heightRatio);
                        newWidth = (int) (width * ratioR);
                        newHeight = (int) (height * ratioR);
                    }
                }

                // 创建压缩后的图片
                BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
                Graphics2D g = resizedImage.createGraphics();
                g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
                g.dispose();
                // 将压缩后的图片转换为输入流
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(resizedImage, "jpg", outputStream);
                inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            }
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
            objectMetadata.setContentType("image/jpeg");
            // 上传压缩后的图片
            PutObjectResult putResult = cosClient.putObject(bucketName, key, inputStream, objectMetadata);
            // 创建文件的访问路径
            String url = HOST + key;
            // 关闭COS客户端
            cosClient.shutdown();
            return url;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
