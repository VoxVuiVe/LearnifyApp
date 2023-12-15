package com.project.learnifyapp.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class AwsConfiguration {
    private String accessKey = "";
    private String secretKey = "";
    // xác định khu vực
    private Region region = Region.AP_SOUTHEAST_1;

    /*
     * cấu hình và tạo đối tượng S3Client để tương tác với Amazon S3
     */
    @Bean
    public S3Client s3Client() throws URISyntaxException {
        // xác nhận đăng nhập vào aws (s3)
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Client.builder()
                .region(region) // xác định khu vực
                .credentialsProvider(() -> credentials)
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() throws URISyntaxException {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Presigner.builder()
                .region(region)
                .credentialsProvider(() -> credentials)
                .build();
    }
}
