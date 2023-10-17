package com.hundsun.atp.servers.prompt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.hundsun.atp.common.prompt.LLMEnum;
import com.hundsun.atp.servers.prompt.casecreate.PostInterfaceCaseCreate;
import org.junit.Test;

public class PostInterfaceCaseCreateTest {
    private static final String API_KEY = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoc18zNjE0MSIsImlhdCI6MTY5NDA4NDk5MiwiZXhwIjoxNzAyNzI0OTkyfQ.E6AFtY2WD17BOK5kBK6UMPH2hxVSfbWBIx6K7eRcQoE";
    private static final String API_URL = "http://10.20.33.13:8090/uis/chat/completions";

    @Test
    public void createTestCaseByJavaCodeTest() throws Exception {
        LLMApiUtils.init(API_KEY, API_URL, LLMEnum.GPT3_5);
        String javaCode = "    @PostMapping(\"/caseRun\")\n" +
                "    @ApiOperation(\"新建测试用例\")\n" +
                "    public RpcResultDTO<Boolean> caseRun(@Validated @RequestBody CaseTestRequest caseTestRequest) {\n" +
                "        return atpUseCaseInstanceService.caseRun(caseTestRequest);\n" +
                "    }";
        String dependentClass = "public class CaseTestRequest extends AtpBaseDto {\n" +
                "\n" +
                "    private List<Long> caseIdList;\n" +
                "    private long folderId;\n" +
                "\n" +
                "    public List<Long> getCaseIdList() {\n" +
                "        return caseIdList;\n" +
                "    }\n" +
                "\n" +
                "    public void setCaseIdList(List<Long> caseIdList) {\n" +
                "        this.caseIdList = caseIdList;\n" +
                "    }\n" +
                "\n" +
                "    public long getFolderId() {\n" +
                "        return folderId;\n" +
                "    }\n" +
                "\n" +
                "    public void setFolderId(long folderId) {\n" +
                "        this.folderId = folderId;\n" +
                "    }\n" +
                "}";
        ArrayNode caseArray = PostInterfaceCaseCreate.createTestCaseByJavaCode(javaCode, dependentClass, 10);
        ObjectMapper objectMapper = new ObjectMapper();
        String caseStr = objectMapper.writeValueAsString(caseArray);
        System.out.println(caseStr);
        System.out.println("=============");
        for (JsonNode jsonNode : caseArray) {
            String caseStrTmp = objectMapper.writeValueAsString(jsonNode);
            System.out.println(caseStrTmp);
        }
    }


}
