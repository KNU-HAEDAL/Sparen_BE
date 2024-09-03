package org.haedal.zzansuni.common.domain;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploader {
    String upload(MultipartFile imageFile);
}
