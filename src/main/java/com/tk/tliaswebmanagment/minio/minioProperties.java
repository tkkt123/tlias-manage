package com.tk.tliaswebmanagment.minio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "minio")
public class minioProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;
}

