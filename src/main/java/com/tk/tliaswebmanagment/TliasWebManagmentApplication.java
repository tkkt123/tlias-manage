package com.tk.tliaswebmanagment;

import com.tk.tliaswebmanagment.minio.minioProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan //filter开启，传统web组件支持
@EnableConfigurationProperties(minioProperties.class)
public class TliasWebManagmentApplication {
    public static void main(String[] args) {
        SpringApplication.run(TliasWebManagmentApplication.class, args);
    }
}
