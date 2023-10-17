package com.hundsun.atp.servers.service.business;

import com.fasterxml.jackson.databind.JsonNode;
import com.hundsun.atp.servers.service.business.caserun.impl.http.HttpPostCaseResult;
import com.hundsun.atp.servers.service.business.caserun.impl.http.HttpPostCaseRunHandle;
import com.hundsun.atp.servers.service.business.caserun.impl.http.HttpPostCaseParams;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpPostCaseRunBusinessTest {

    @Test
    public void caseRunTest() throws IOException {
        HttpPostCaseRunHandle httpPostCaseRunBusiness = new HttpPostCaseRunHandle();
        HttpPostCaseParams httpPostCaseParams = new HttpPostCaseParams();
        httpPostCaseParams.setUrl("http://localhost:8080/save_user");
        httpPostCaseParams.settCaseJson("{\"caseIdList\":[4444444444,5555555555,6666666666,7777777777],\"folderId\":-999}");
        Map<String,String> requestProperties = new HashMap<>();
        requestProperties.put("Content-Type", "application/json");
        httpPostCaseParams.setRequestProperties(requestProperties);
        HttpPostCaseResult response = httpPostCaseRunBusiness.excuteCase(httpPostCaseParams);
        System.out.println(response);
    }

}
