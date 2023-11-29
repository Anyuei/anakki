package com.anakki.data.utils.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anakki.data.bean.common.ChatCompletionResponse;
import com.anakki.data.bean.common.WXYYMessage;
import com.anakki.data.bean.common.WXYYToken;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: WXYYUtil
 * Description:
 *
 * @author Anakki
 * @date 2023/11/29 17:19
 */
@Component
public class WXYYUtil {

    private static final String grant_type = "client_credentials";
    private static final String client_id = "LKhwuLAVFfZ1E7toXgGp9kC3";
    private static final String client_secret = "G7IOYGLkOcpVmM9TU7XmT8pI5zqUWyNC";

    public static ConcurrentHashMap<String, List<WXYYMessage>> userWXYYMessageMap = new ConcurrentHashMap<>(128);

    public static ConcurrentHashMap<String, WXYYToken> userWxyyTokenMap = new ConcurrentHashMap<>(128);

    public static void main(String[] args) throws IOException {
        WXYYMessage wxyyMessage = new WXYYMessage();
        wxyyMessage.setRole("user");
        wxyyMessage.setContent("你好");
        ArrayList<WXYYMessage> objects = new ArrayList<>();
        objects.add(wxyyMessage);
        List<WXYYMessage> test = sendMessage("test", objects);
        System.out.println(JSONObject.toJSONString(test));
    }

    public static List<WXYYMessage> sendMessage(String userId, List<WXYYMessage> messages) throws IOException {
        String tokenKey = "WXYY_TOKEN_" + userId;
        WXYYToken wxxyToken;
        String result;
        try {
            result = RedisUtil.StringOps.get(tokenKey);
            if (null == result) {
                wxxyToken = getToken(grant_type, client_id, client_secret);
                RedisUtil.StringOps.setEx(tokenKey, JSONObject.toJSONString(wxxyToken), 25, TimeUnit.DAYS);
            } else {
                wxxyToken = JSONObject.parseObject(result, WXYYToken.class);
            }
        } catch (Exception e) {
            if (userWxyyTokenMap.containsKey(tokenKey)) {
                wxxyToken = userWxyyTokenMap.get(tokenKey);
            } else {
                wxxyToken = getToken(grant_type, client_id, client_secret);
                userWxyyTokenMap.put(tokenKey, wxxyToken);
            }

        }

        String json = JSONObject.toJSONString(messages);
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(60000, TimeUnit.MILLISECONDS)
                .readTimeout(60000, TimeUnit.MILLISECONDS)
                .build();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"messages\": " + json + "\r\n}");
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions_pro?access_token=" + wxxyToken.getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = null;
            response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
                String bodyString = null;
                bodyString = responseBody.string();
                // 处理JSON响应数据
                ChatCompletionResponse chatCompletionResponse = JSONObject.parseObject(bodyString, ChatCompletionResponse.class);
                String content = chatCompletionResponse.getResult();
                WXYYMessage wxyyMessage = new WXYYMessage();
                wxyyMessage.setRole("assistant");
                wxyyMessage.setContent(content);
                messages.add(wxyyMessage);
                return messages;
    }
    public static WXYYToken getToken(String grant_type, String client_id, String client_secret) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token?grant_type=" + grant_type + "&client_id=" + client_id + "&client_secret=" + client_secret)
                .method("POST", body)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException("获取文心一言token失败" + e.getMessage());
        }
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            String json = null;
            try {
                json = responseBody.string();
            } catch (IOException e) {
                throw new RuntimeException("获取文心一言token失败" + e.getMessage());
            }
            // 处理JSON响应数据
            return JSONObject.parseObject(json, WXYYToken.class);
        } else {
            throw new RuntimeException("获取文心一言token失败");
        }
    }
}
