package org.haedal.zzansuni.infrastructure;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.ImageUploader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Component
@Primary
@Profile("prod")
@RequiredArgsConstructor
public class S3ImageUploader implements ImageUploader {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String upload(MultipartFile imageFile) {
        // 파일의 원본 이름을 가져옵니다.
        String originalFilename = imageFile.getOriginalFilename();


        // 파일 확장자를 추출합니다. 파일 이름이 null인 경우 빈 문자열을 사용합니다.
        String extension =
                originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf("."))
                        : "";

        // 고유한 파일 이름을 생성합니다.
        String uniqueFilename = UUID.randomUUID().toString() + extension;

        // 파일의 InputStream을 가져옵니다. try-with-resources 문을 사용하여 InputStream을 자동으로 닫습니다.
        try (InputStream inputStream = imageFile.getInputStream()) {
            // S3에 업로드할 파일의 메타데이터를 설정합니다.
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(imageFile.getSize()); // 파일 크기를 설정합니다.
            metadata.setContentType(imageFile.getContentType()); // 파일의 MIME 타입을 설정합니다.

            // S3에 파일을 업로드합니다.
            amazonS3.putObject(new PutObjectRequest(bucket, uniqueFilename, inputStream, metadata));
        } catch (Exception e) {
            throw new RuntimeException("이미지 업로드에 실패했습니다.", e);
        }

        // 업로드된 파일의 URL을 반환합니다.
        return amazonS3.getUrl(bucket, uniqueFilename).toString();
    }
}

@Component
class MockUploader implements ImageUploader {
    @Override
    public String upload(MultipartFile imageFile) {
        return "https://example.com/image.jpg";
    }
}
