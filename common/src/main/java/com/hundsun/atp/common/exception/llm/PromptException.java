package com.hundsun.atp.common.exception.llm;

/**
 * 模型生成结果异常
 */
public class PromptException extends Exception{
    public PromptException(String message) {
        super(message);
    }
}
