package com.hundsun.atp.servers.service.business.caserun;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public abstract class ICaseRun<IN, OUT> {
    public abstract OUT excuteHttpPostCase(IN tCase) throws IOException;
}
