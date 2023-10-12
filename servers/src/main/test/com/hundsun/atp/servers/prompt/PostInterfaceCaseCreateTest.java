package com.hundsun.atp.servers.prompt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.hundsun.atp.common.prompt.LLMEnum;
import com.hundsun.atp.servers.prompt.tcase.PostInterfaceCaseCreate;
import org.junit.Test;

public class PostInterfaceCaseCreateTest {
    private static final String API_KEY = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoc18zNjE0MSIsImlhdCI6MTY5NDA4NDk5MiwiZXhwIjoxNzAyNzI0OTkyfQ.E6AFtY2WD17BOK5kBK6UMPH2hxVSfbWBIx6K7eRcQoE";
    private static final String API_URL = "http://10.20.33.13:8090/uis/chat/completions";

    @Test
    public void createTestCaseByJavaCodeTest() throws Exception {
        LLMApiUtils.init(API_KEY, API_URL, LLMEnum.GPT3_5);
        String javaCode = "    " +
                "    public String saveUser(User user);";
        String dependentClass = "public class User {\n" +
                "\n" +
                "    private String name;\n" +
                "\n" +
                "    private Integer age;\n" +
                "\n" +
                "    /**\n" +
                "     * {@link UserType}\n" +
                "     */\n" +
                "    private int userType;\n" +
                "\n" +
                "    private int userId;\n" +
                "\n" +
                "}" +
                "public enum UserType {\n" +
                "\n" +
                "    ADMIN(0),\n" +
                "    SYSTEM(1),\n" +
                "    USER(2),\n" +
                "    GUEST(3);\n" +
                "\n" +
                "    private int type;\n" +
                "\n" +
                "}";
        ArrayNode caseArray = PostInterfaceCaseCreate.createTestCaseByJavaCode(javaCode, dependentClass, 10);
        ObjectMapper objectMapper = new ObjectMapper();
        String caseStr = objectMapper.writeValueAsString(caseArray);
        System.out.println(caseStr);
    }


}
