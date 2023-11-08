package com.anakki.data.utils.common;

import okhttp3.*;

import java.io.IOException;

/**
 * ClassName: RegularUtil
 * Description:
 *
 * @author Anakki
 * @date 2023/5/28 22:38
 */
public class GetTelephoneNumAddressUtil {
    public static void main(String[] args) throws IOException {
        getAddress("13533654268");
    }
    public static OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
    public static String getAddress(String telephone) throws IOException {

        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://shouji.bmcx.com/"+telephone+"__shouji/")
                .method("POST", body)
                .addHeader("Cookie", "c_y_g_j=18")
                .build();
        Response response = client.newCall(request).execute();
        String html = new String(response.body().bytes());
        System.out.println(html);
        System.out.println(html.split("<title>"+telephone+" ")[1].split(" - 手机号码查询归属地</title>")[0]);
        return "";
    }

}
