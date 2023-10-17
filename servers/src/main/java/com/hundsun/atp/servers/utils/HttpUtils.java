package com.hundsun.atp.servers.utils;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.rmi.server.ExportException;
import java.util.Map;

public class HttpUtils {

    public static String sendPostRequest(String url, String postDataString, Map<String, String> requestProperties) throws IOException {
        // 创建URL对象
        URL obj = new URL(url);
        // 打开连接
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // 设置请求方法为POST
        con.setRequestMethod("POST");

        // 启用输入输出流
        con.setDoOutput(true);

        // 设置请求属性
        if (requestProperties != null && requestProperties.size() > 0){
            requestProperties.forEach(con::setRequestProperty);
        }
        int responseCode = -1;
        StringBuilder response = new StringBuilder();

        try {
            // 发送POST数据
            try (OutputStream wr = con.getOutputStream()) {
                byte[] input = postDataString.getBytes(StandardCharsets.UTF_8);
                wr.write(input);
                wr.flush();
            }
            // 获取响应
            responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                }
                return response.toString();
            } else {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()))) {
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                }
                throw new IOException(String.format("Http请求异常, 错误码: %d, 错误信息：%s", responseCode, response));
            }
        } finally {
            con.disconnect();
        }




    }

}
