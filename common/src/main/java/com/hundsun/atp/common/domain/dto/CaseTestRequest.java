package com.hundsun.atp.common.domain.dto;

import java.util.List;

/**
 * 实例运行测试请求体
 */
public class CaseTestRequest extends AtpBaseDto {

    private List<Long> caseIdList;
    private long folderId;

    public List<Long> getCaseIdList() {
        return caseIdList;
    }

    public void setCaseIdList(List<Long> caseIdList) {
        this.caseIdList = caseIdList;
    }

    public long getFolderId() {
        return folderId;
    }

    public void setFolderId(long folderId) {
        this.folderId = folderId;
    }
}
