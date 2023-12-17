package com.project.learnifyapp.service;

import com.project.learnifyapp.exceptions.S3ServiceException;
import com.project.learnifyapp.service.impl.LessonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Service
public class S3Service {
    private final Logger log = LoggerFactory.getLogger(LessonService.class);
    private String bucketName = "";
    private String cloudFrontDomain = "";

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    public S3Service(S3Client s3Client, S3Presigner s3Presigner) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
    }

    public String uploadVideoToS3(MultipartFile videoFile) {
        if (videoFile == null || videoFile.isEmpty()) {
            throw new IllegalArgumentException("Video file is null or empty.");
        }
        String uniqueVideoName = StringUtils.getFilenameExtension(videoFile.getOriginalFilename());
        if (StringUtils.isEmpty(uniqueVideoName)) {
            throw new IllegalArgumentException("Unable to determine video file extension.");
        }

        String s3Key = "video" + "/" + UUID.randomUUID().toString() + "." + uniqueVideoName;

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName) // Tên bucket S3
                    .key(s3Key) // Đường dẫn lưu trữ trên S3
                    .contentType(videoFile.getContentType())
                    .build();

            s3Client.putObject(request, RequestBody.fromInputStream(videoFile.getInputStream(), videoFile.getSize()));

            log.info("Video uploaded successfully to S3. Key: {}", s3Key);

            return s3Key;
        } catch (S3Exception e) {
            log.error("Failed to upload video to S3. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to upload video to S3: " + e.getMessage());
        } catch (IOException e) {
            log.error("IOException during video upload to S3. Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String uploadImagesToS3(MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty()) {
            throw new IllegalArgumentException("Video file is null or empty.");
        }
        String uniqueImageName = StringUtils.getFilenameExtension(imageFile.getOriginalFilename());
        if (StringUtils.isEmpty(uniqueImageName)) {
            throw new IllegalArgumentException("Unable to determine video file extension.");
        }

        String s3Key = "images" + "/" + UUID.randomUUID().toString() + "." + uniqueImageName;

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName) // Tên bucket S3
                    .key(s3Key) // Đường dẫn lưu trữ trên S3
                    .contentType(imageFile.getContentType())
                    .build();

            s3Client.putObject(request, RequestBody.fromInputStream(imageFile.getInputStream(), imageFile.getSize()));

            log.info("Video uploaded successfully to S3. Key: {}", s3Key);

            return s3Key;
        } catch (S3Exception e) {
            log.error("Failed to upload imageFile to S3. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to upload imageFile to S3: " + e.getMessage());
        } catch (IOException e) {
            log.error("IOException during imageFile upload to S3. Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void deleteFile(String path) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(path)
                .build();
        s3Client.deleteObject(request);
    }

    public String getPresignedURL(String keyName) {
        try {
            String cloudFrontURL = cloudFrontDomain + "/" + keyName;

            GetObjectRequest objectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(60))
                    .getObjectRequest(objectRequest)
                    .build();

//            PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);
//            String presignedURL = cloudFrontURL;
            return cloudFrontURL;

        } catch (S3Exception e) {
            throw new S3ServiceException("Failed to get presigned URL for " + keyName, e);
        }
    }


}
