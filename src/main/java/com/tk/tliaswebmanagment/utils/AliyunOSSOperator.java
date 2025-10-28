package com.tk.tliaswebmanagment.utils;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class AliyunOSSOperator {

    @Autowired
    AliyunOSSPropertise aliyunOSSPropertise;

    public String uploadFile(String fileName,byte[] content) throws Exception {
        EnvironmentVariableCredentialsProvider credentialsProvider = new EnvironmentVariableCredentialsProvider();

        String dir= LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String newfileName= UUID.randomUUID()+fileName.substring(fileName.lastIndexOf("."));
        String objectName= dir+"/"+newfileName;


        // 创建 ClientBuilderConfiguration 实例，用于配置 OSS 客户端参数
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        // 设置签名算法版本为 V4
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);

        // 创建 OSS 客户端实例
        OSS ossClient = OSSClientBuilder.create()
                .endpoint(aliyunOSSPropertise.getEndpoint())
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(aliyunOSSPropertise.getRegion())
                .build();
        try {
            ossClient.putObject(aliyunOSSPropertise.getBucketName(),objectName,new ByteArrayInputStream(content));
        } finally {
            // 当OSSClient实例不再使用时，调用shutdown方法以释放资源
            ossClient.shutdown();
        }

        return aliyunOSSPropertise.getEndpoint().split("//")+"//"+aliyunOSSPropertise.getBucketName()+"."+aliyunOSSPropertise.getEndpoint().split("//") +objectName;
    }
}
