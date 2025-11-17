package com.tk.tliaswebmanagment.utils;

import com.tk.tliaswebmanagment.minio.minioProperties;
import io.minio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Component
public class MinioOSSOperator {
    @Autowired
    private MinioClient minioClient;
    @Autowired
    private minioProperties minioProperties;

    public String uploadFile(MultipartFile file) throws Exception {
        boolean flag=minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
        if(!flag){
            minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .build()
            );
            minioClient.setBucketPolicy(
                    SetBucketPolicyArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .config(createBuketPolicyConfig(minioProperties.getBucketName()))
                            .build()
            );
        }

        String filename = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
                + "/"
                + UUID.randomUUID()
                + "-"
                + file.getOriginalFilename();
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(minioProperties.getBucketName())
                .stream(file.getInputStream(), file.getSize(), -1)
                .object(filename)
                .contentType(file.getContentType())
                .build());
        return String.join("/", minioProperties.getEndpoint(), minioProperties.getBucketName(),  filename);
    }

    private String createBuketPolicyConfig(String bucketName){
        return """
                {
                    "Version": "2012-10-17",
                    "Statement": [
                        {
                            "Effect": "Allow",
                            "Principal": {
                                "AWS": [
                                    "*"
                                ]
                            },
                            "Action": [
                                "s3:GetObject"
                            ],
                            "Resource": [
                                "arn:aws:s3:::%s/*",
                            ]
                        }
                    ]
                }
                """.formatted(bucketName);
    }

}
