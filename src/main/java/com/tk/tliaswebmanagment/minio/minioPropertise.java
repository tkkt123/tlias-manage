package com.tk.tliaswebmanagment.minio;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties(prefix = "minio")
public class minioPropertise {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;
}
