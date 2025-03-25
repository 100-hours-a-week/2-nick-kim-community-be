package ktb.goorm.community.image.adapter.out.persistence;

import ktb.goorm.community.common.dto.ErrorCodeAndMessage;
import ktb.goorm.community.common.exception.BusinessException;
import ktb.goorm.community.image.application.port.out.ImagePersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@RequiredArgsConstructor
public class ImagePersistenceAdapter implements ImagePersistencePort {

    private final String IMAGE_DIRECTORY = "images/";

    @Override
    public String save(MultipartFile file) {
        try {
            // 파일 저장 경로 설정
            Path filePath = Paths.get(IMAGE_DIRECTORY, file.getOriginalFilename());

            // 디렉토리가 없으면 생성
            Files.createDirectories(filePath.getParent());

            // 파일 저장
            Files.write(filePath, file.getBytes());

            return file.getOriginalFilename();
        } catch (IOException e) {
            throw new BusinessException(ErrorCodeAndMessage.FILE_SAVE_FAIL);
        }
    }
}
