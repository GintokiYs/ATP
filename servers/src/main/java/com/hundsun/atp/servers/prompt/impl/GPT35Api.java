package com.hundsun.atp.servers.prompt.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hundsun.atp.common.prompt.LLMResponse;
import com.hundsun.atp.common.prompt.PromptModel;
import com.hundsun.atp.common.prompt.impl.GPTResponse;
import com.hundsun.atp.servers.prompt.AbstractLLMApi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GPT35Api extends AbstractLLMApi {

    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public GPT35Api(String apiKey, String apiUrl) {
        super(apiKey, apiUrl);
    }

    @Override
    protected LLMResponse request(List<PromptModel> promptList, double temperature) throws Exception{
        String prompt = OBJECT_MAPPER.writeValueAsString(promptList);
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", apiKey);
        connection.setDoOutput(true);
        String requestData = "{\"frequency_penalty\": 0.0," +
                "\"messages\": " + prompt + "," +
                "\"model\": \"gpt-35-turbo\"," +
                "\"n\": 1," +
                "\"presence_penalty\": 0.0," +
                "\"stream\": false," +
                "\"temperature\":" + temperature + ","+
                "\"top_p\": 1.0," +
                "\"max_tokens\": 3500," +
                "\"user\": \"hs_36141\"}";
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestData.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            StringBuilder responseBuilder = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                responseBuilder.append(inputLine);
            }
            in.close();
            connection.disconnect();
            String responseBody = responseBuilder.toString();
            GPTResponse gptBody = OBJECT_MAPPER.readValue(responseBody, GPTResponse.class);
            return gptBody;
        } else {
            StringBuilder responseBuilder = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                responseBuilder.append(inputLine);
            }
            in.close();
            connection.disconnect();
            String responseBody = responseBuilder.toString();
            throw new Exception(responseBody);
        }
    }
}
