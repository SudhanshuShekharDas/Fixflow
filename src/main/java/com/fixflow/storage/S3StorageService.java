package com.fixflow.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

@Service
public class S3StorageService {

    private final String bucket;
    private final String region;
    private S3Client s3;

    public S3StorageService(
            @Value("${aws.s3.bucket:}") String bucket,
            @Value("${aws.region:ap-south-1}") String region) {
        this.bucket = bucket;
        this.region = region;
    }

    public String upload(String fileName, InputStream inputStream, long size, String contentType)
            throws IOException {
        if (bucket == null || bucket.isBlank()) {
            throw new IllegalStateException("AWS S3 bucket is not configured");
        }

        if (s3 == null) {
            s3 = S3Client.builder().region(Region.of(region)).build();
        }

        s3.putObject(
                PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(fileName)
                        .contentType(contentType)
                        .build(),
                RequestBody.fromInputStream(inputStream, size)
        );

        return "s3://" + bucket + "/" + fileName;
    }
}
