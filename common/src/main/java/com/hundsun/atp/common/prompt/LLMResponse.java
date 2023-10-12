package com.hundsun.atp.common.prompt;

/**
 * {"id":"chatcmpl-87ZZ99i6tRtNqzK370CDei8hrazGs","object":"chat.completion","created":1696815703,"model":"gpt-35-turbo","choices":[{"index":0,"delta":null,"message":{"role":"assistant","content":"[\n  {\n    \"name\": \"John\",\n    \"age\": 25,\n    \"userType\": \"admin\"\n  },\n  {\n    \"name\": \"Alice\",\n    \"age\": 30,\n    \"userType\": \"guest\"\n  }\n]"},"finish_reason":"stop"}],"usage":{"prompt_tokens":383,"completion_tokens":54,"total_tokens":437},"extra_result":null}
 */
public abstract class LLMResponse {

    /**
     * 获取返回的消息
     * @return
     */
    public abstract Answer getAnswer();

    public static class Answer {
        private String role;
        private String content;

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

}
