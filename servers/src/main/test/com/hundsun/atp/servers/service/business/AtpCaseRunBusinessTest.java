package com.hundsun.atp.servers.service.business;

import com.hundsun.atp.servers.service.business.caserun.impl.http.AtpCaseRunBusiness;
import com.hundsun.atp.servers.service.business.caserun.impl.http.HttpCaseParams;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AtpCaseRunBusinessTest {

    @Test
    public void test1() throws IOException {
        AtpCaseRunBusiness atpCaseRunBusiness = new AtpCaseRunBusiness();
        HttpCaseParams httpCaseParams = new HttpCaseParams();
        httpCaseParams.setUrl("http://localhost:8080/save_user");
        httpCaseParams.settCaseJson("{\"user\":{\"name\":\"hello world\",\"age\":100,\"userType\":3,\"userId\":999999}}");
        Map<String,String> requestProperties = new HashMap<>();
        requestProperties.put("Content-Type", "application/json");
        httpCaseParams.setRequestProperties(requestProperties);
        String response = atpCaseRunBusiness.excuteHttpPostCase(httpCaseParams);
        System.out.println(response);
    }
}
