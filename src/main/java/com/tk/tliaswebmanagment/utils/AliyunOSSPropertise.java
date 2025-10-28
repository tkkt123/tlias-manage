package com.tk.tliaswebmanagment.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOSSPropertise {
    private String endpoint;
    private String BucketName;
    private String region;
}
