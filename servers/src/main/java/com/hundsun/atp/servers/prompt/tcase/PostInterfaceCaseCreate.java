package com.hundsun.atp.servers.prompt.tcase;

import cn.hutool.json.JSONArray;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.hundsun.atp.common.exception.llm.PromptException;
import com.hundsun.atp.common.prompt.LLMResponse;
import com.hundsun.atp.common.prompt.PromptModel;
import com.hundsun.atp.servers.prompt.LLMApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PostInterfaceCaseCreate {

    private static Logger log = LoggerFactory.getLogger(PostInterfaceCaseCreate.class);

    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    /**
     * 获取接口入参的结构
     * @param interfaceCode 接口代码
     * @param dependentCode 依赖类代码
     * @return
     * @throws Exception
     */
    private static LLMResponse getParamsSchema(String interfaceCode, String dependentCode) throws Exception {
        PromptModel initPromptModel = new PromptModel();
        initPromptModel.setRole("system");
        initPromptModel.setContent("你是一个java开发工程师,你可以根据java代码推测各个类型的属性的取值范围。");
        String askPrompt = String.format(
                "你需要根据提供的java代码按照后续步骤生成针对测试接口的http请求体的json结构,要测试的http接口方法使用三重方括号包裹，自定义的java类代码使用三重反引号包裹。" +
                        "测试接口为[[[ %s ]]];" +
                        "自定义java类为``` %s ```,后续使用'mycode'指代这段内容; " +
                        "根据测试接口的方法参数,生成对应的json结构体,你可以参考如下步骤：" +
                        "1.查看接口方法的参数,如果参数类型在mycode中有定义,则根据定义的类结构生成对应的json结构,key即为接口方法中的参数名,如果一个参数的类型没有定义则忽略该参数。\n" +
                        "2.从代码和注释中推断一些参数存在范围约束。 \n" +
                        "3.返回生成的json结构和参数的约束范围,返回的结果尽量精简,不需要额外的解释内容。"
                ,
                interfaceCode, dependentCode);
        PromptModel userPrompt = new PromptModel();
        userPrompt.setRole("user");
        userPrompt.setContent(askPrompt);

        List<PromptModel> list = new ArrayList<>();
        list.add(initPromptModel);
        list.add(userPrompt);
        return LLMApiUtils.request(list, 0);
    }

    /**
     *
     * @param dataDesc 需要生成用例的结构描述信息
     * @param caseNums 生成用例个数
     * @return
     * @throws Exception
     */
    private static LLMResponse createTestCase(String dataDesc, int caseNums) throws Exception {
        PromptModel initPromptModel = new PromptModel();
        initPromptModel.setRole("system");
        initPromptModel.setContent("你可以模拟一个用例生成工具,你的回答内容只有一个包含用例的json数组,数组不需要格式化,不允许有其他任何字符包括解释。");

        PromptModel promptModel = new PromptModel();
        promptModel.setRole("user");
        String promptContentTemplete = "我会给你一段文本,文本中包含了一个http结构请求体的json结构样例和一些参数的取值范围描述,你需要根据这些内容生成%d个满足该http接口请求体结构的测试用例,并放在一个数组中返回,用例不允许重复,文本内容如下: %s";
        String completePromptContent = String.format(promptContentTemplete, caseNums, dataDesc);
        promptModel.setContent(completePromptContent);
        List<PromptModel> promptModelList = new ArrayList<>();
        promptModelList.add(initPromptModel);
        promptModelList.add(promptModel);
        return  LLMApiUtils.request(promptModelList,0);
    }

    /**
     *
     * @param interfaceCode 接口代码
     * @param dependentCode
     * @param caseNums
     * @return
     * @throws Exception
     */
    public static ArrayNode createTestCaseByJavaCode(String interfaceCode, String dependentCode, int caseNums) throws Exception {
        // 获取结构用例的结构
        LLMResponse schemaAnswer = getParamsSchema(interfaceCode, dependentCode);
        // 生成用例
        LLMResponse jsonArray = createTestCase(schemaAnswer.getAnswer().getContent(),caseNums);
        JsonNode jsonNode = OBJECT_MAPPER.readTree(jsonArray.getAnswer().getContent());
        if (jsonNode.isArray()){
            return (ArrayNode) jsonNode;
        }
        log.error("生成的用例不是数组： \n大模型用例描述内容为: {} \n。 生成的测试用例结果为： {}。",
                schemaAnswer.getAnswer().getContent(),
                jsonArray.getAnswer().getContent());
        throw new PromptException("返回的结果不是一个数组");
    }
}
