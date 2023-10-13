package com.hundsun.atp.servers.service.business.caserun;

public interface ICaseRun<IN, OUT> {
    OUT excuteHttpPostCase(IN tCase) throws Exception;
}
