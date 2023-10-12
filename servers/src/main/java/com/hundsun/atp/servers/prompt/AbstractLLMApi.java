package com.hundsun.atp.servers.prompt;

import com.hundsun.atp.common.prompt.LLMResponse;
import com.hundsun.atp.common.prompt.PromptModel;

import java.util.List;

public abstract class AbstractLLMApi {
    protected String apiKey;
    protected String apiUrl;

    public AbstractLLMApi(String apiKey, String apiUrl) {
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
    }
    protected abstract LLMResponse request(List<PromptModel> promptList, double temperature) throws Exception;

}
