package com.hundsun.atp.servers.service.business.caserun.impl.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.hundsun.atp.servers.service.business.caserun.CaseRunResult;

public class HttpPostCaseResult extends CaseRunResult {

    private JsonNode responseBody;

    public JsonNode getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(JsonNode responseBody) {
        this.responseBody = responseBody;
    }

    @Override
    public String toString() {
        return "HttpPostCaseResult{" +
                "responseBody=" + responseBody +
                '}';
    }
}
