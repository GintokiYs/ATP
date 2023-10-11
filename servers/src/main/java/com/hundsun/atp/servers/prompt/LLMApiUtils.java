package com.hundsun.atp.servers.prompt;

import com.hundsun.atp.common.exception.llm.LLMEnvException;
import com.hundsun.atp.common.prompt.LLMEnum;
import com.hundsun.atp.common.prompt.LLMResponse;
import com.hundsun.atp.common.prompt.PromptModel;
import com.hundsun.atp.servers.prompt.impl.GPT35Api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LLMApiUtils {
    // todo 提供默认使用的模型

    public static Map<LLMEnum, AbstractLLMApi> map = new HashMap<>();

    private static final String API_KEY = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoc18zNjE0MSIsImlhdCI6MTY5NDA4NDk5MiwiZXhwIjoxNzAyNzI0OTkyfQ.E6AFtY2WD17BOK5kBK6UMPH2hxVSfbWBIx6K7eRcQoE";
    private static final String API_URL = "http://10.20.33.13:8090/uis/chat/completions";
    private static AbstractLLMApi llmApi;

    public static void init(String apiKey, String apiUrl, LLMEnum llmEnum){
        llmApi = new GPT35Api(apiKey, apiUrl);
    }

    public static LLMResponse request(List<PromptModel> promptList, double temperature) throws Exception {
        checkInit();
        return llmApi.request(promptList, temperature);
    }

    private static void checkInit() throws Exception {
        if(llmApi == null){
           throw new LLMEnvException("请先初始化LLM环境,例如。LLMApiUtils.init('Your Api Key', 'Your api url', LLMEnum.GPT35);" );
        }
    }




}
