package com.hundsun.atp.common.domain.dto;

import java.util.List;

/**
 * 实例运行测试请求体
 */
public class CaseTestRequest extends AtpBaseDto {

    private List<String> caseIdList;
    private String foldId;

    public List<String> getCaseIdList() {
        return caseIdList;
    }

    public void setCaseIdList(List<String> caseIdList) {
        this.caseIdList = caseIdList;
    }

    public String getFoldId() {
        return foldId;
    }

    public void setFoldId(String foldId) {
        this.foldId = foldId;
    }
}
