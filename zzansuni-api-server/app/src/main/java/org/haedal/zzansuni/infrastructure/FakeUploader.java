package org.haedal.zzansuni.infrastructure;

import lombok.RequiredArgsConstructor;
import org.haedal.zzansuni.domain.ImageUploader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class FakeUploader implements ImageUploader {
    @Override
    public String upload(MultipartFile imageFile) {
        return "https://loremflickr.com/640/480?lock=3881982306025472";
    }
}
