package ktb.goorm.community.image.application.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface ImagePersistencePort {
    String save(MultipartFile file);
}
