package com.dzy.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigController {
    @Value("${server.port}")
    String serverPort;

    @Value("${config.info}")
    String configInfo;

    @GetMapping("/config/info")
    public String getConfigInfo() {
        return "serverPort : " + serverPort + ", configInfo : " + configInfo;
    }
}
