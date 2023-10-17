package com.hundsun.atp.common.domain.dto;

import java.util.List;

/**
 * 实例运行测试请求体
 */
public class CaseTestRequest extends AtpBaseDto {

    private List<String> caseIdList;
    private String folderId;

    public List<String> getCaseIdList() {
        return caseIdList;
    }

    public void setCaseIdList(List<String> caseIdList) {
        this.caseIdList = caseIdList;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }
}
