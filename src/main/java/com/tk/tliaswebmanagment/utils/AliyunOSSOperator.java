package com.tk.tliaswebmanagment.utils;

import com.tk.tliaswebmanagment.minio.minioPropertise;
import io.minio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Component
public class AliyunOSSOperator {

    @Autowired
    private MinioClient minioClient;
    @Autowired
    private minioPropertise minioPropertise;

    public String uploadFile(MultipartFile file) throws Exception {
        boolean flag=minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioPropertise.getBucketName()).build());
        if(!flag){
            minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(minioPropertise.getBucketName())
                            .build()
            );
            minioClient.setBucketPolicy(
                    SetBucketPolicyArgs.builder()
                            .bucket(minioPropertise.getBucketName())
                            .config(createBuketPolicyConfig(minioPropertise.getBucketName()))
                            .build()
            );
        }

        String filename = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
                + "/"
                + UUID.randomUUID()
                + "-"
                + file.getOriginalFilename();
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(minioPropertise.getBucketName())
                .stream(file.getInputStream(), file.getSize(), -1)
                .object(filename)
                .contentType(file.getContentType())
                .build());
        return String.join("/", minioPropertise.getEndpoint(),minioPropertise.getBucketName(),  filename);
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
