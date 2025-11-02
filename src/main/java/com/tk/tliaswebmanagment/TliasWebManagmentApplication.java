package com.tk.tliaswebmanagment;

import com.tk.tliaswebmanagment.minio.minioPropertise;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@EnableConfigurationProperties(minioPropertise.class)
public class TliasWebManagmentApplication {
    public static void main(String[] args) {
        SpringApplication.run(TliasWebManagmentApplication.class, args);
    }
}
