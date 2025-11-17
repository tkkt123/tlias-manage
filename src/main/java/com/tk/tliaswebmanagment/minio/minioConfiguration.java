package com.tk.tliaswebmanagment.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportSelector;

@Configuration
@ConfigurationPropertiesScan(basePackages = "com.tk.tliaswebmanagment.minio")
@ConditionalOnProperty(name = "minio.endpoint")
public class minioConfiguration {
    @Autowired
    private minioProperties properties;

     @Bean
     public MinioClient minioClient() {
         return MinioClient.builder()
                 .endpoint(properties.getEndpoint())
                 .credentials(properties.getAccessKey(), properties.getSecretKey())
                 .build();
     }
}
