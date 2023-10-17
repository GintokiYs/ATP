package com.hundsun.atp.servers.service.business.caserun.impl.http;

import com.hundsun.atp.servers.service.business.caserun.CaseRunParams;

import java.util.Map;

public class HttpPostCaseParams extends CaseRunParams {

    private String url;
    private String tCaseJson;
    private Map<String, String> requestProperties;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String gettCaseJson() {
        return tCaseJson;
    }

    public void settCaseJson(String tCaseJson) {
        this.tCaseJson = tCaseJson;
    }

    public Map<String, String> getRequestProperties() {
        return requestProperties;
    }

    public void setRequestProperties(Map<String, String> requestProperties) {
        this.requestProperties = requestProperties;
    }
}
