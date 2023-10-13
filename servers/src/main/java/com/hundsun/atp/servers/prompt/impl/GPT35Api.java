package com.hundsun.atp.servers.prompt.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hundsun.atp.common.prompt.LLMResponse;
import com.hundsun.atp.common.prompt.PromptModel;
import com.hundsun.atp.common.prompt.impl.GPTResponse;
import com.hundsun.atp.servers.prompt.AbstractLLMApi;
import com.hundsun.atp.servers.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GPT35Api extends AbstractLLMApi {

    private Logger logger = LoggerFactory.getLogger(GPT35Api.class);

    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public GPT35Api(String apiKey, String apiUrl) {
        super(apiKey, apiUrl);
    }

    @Override
    protected LLMResponse request(List<PromptModel> promptList, double temperature) throws IOException {
        String prompt = OBJECT_MAPPER.writeValueAsString(promptList);

        Map<String,String> requestProperties = new HashMap<>();
        requestProperties.put("Content-Type", "application/json");
        requestProperties.put("Authorization", apiKey);

        String requestData = String.format("{\"frequency_penalty\": 0.0," +
                "\"messages\": %s ," +
                "\"model\": \"gpt-35-turbo\"," +
                "\"n\": 1," +
                "\"presence_penalty\": 0.0," +
                "\"stream\": false," +
                "\"temperature\": %s,"+
                "\"top_p\": 1.0," +
                "\"max_tokens\": 3500," +
                "\"user\": \"hs_36141\"}", prompt, temperature);

        String response = HttpUtils.sendPostRequest(apiUrl, requestData, requestProperties);
        GPTResponse gptBody = OBJECT_MAPPER.readValue(response, GPTResponse.class);
        return gptBody;
    }


}
