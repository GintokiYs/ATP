package com.hundsun.atp.servers.service.business;

import com.fasterxml.jackson.databind.JsonNode;
import com.hundsun.atp.servers.service.business.caserun.impl.http.HttpPostCaseRunBusiness;
import com.hundsun.atp.servers.service.business.caserun.impl.http.HttpPostCaseParams;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpPostCaseRunBusinessTest {

    @Test
    public void test1() throws IOException {
        HttpPostCaseRunBusiness httpPostCaseRunBusiness = new HttpPostCaseRunBusiness();
        HttpPostCaseParams httpPostCaseParams = new HttpPostCaseParams();
        httpPostCaseParams.setUrl("http://localhost:8080/save_user");
        httpPostCaseParams.settCaseJson("{\"user\":{\"name\":\"hello world\",\"age\":100,\"userType\":3,\"userId\":999999}}");
        Map<String,String> requestProperties = new HashMap<>();
        requestProperties.put("Content-Type", "application/json");
        httpPostCaseParams.setRequestProperties(requestProperties);
        JsonNode response = httpPostCaseRunBusiness.excuteHttpPostCase(httpPostCaseParams);
        System.out.println(response);
    }
}
