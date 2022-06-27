package com.chuxing.nfs.server.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @date 2022/6/24 15:54
 * @author huangchenguang
 * @desc config
 */
@Getter
@Configuration
public class NfsConfig {

    /**
     * @date 2022/6/27 15:23
     * @author huangchenguang
     * @desc local file store
     */
    @Value("${local.store}")
    private String localStore;

}
