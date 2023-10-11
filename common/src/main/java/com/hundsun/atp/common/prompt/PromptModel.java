package com.hundsun.atp.common.prompt;

public class PromptModel {

    /**
     * 可选值有[user,system,assistant]
     */
    private String role;

    /**
     * 交互内容
     */
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
